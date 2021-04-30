package top.towing.yi.chidao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

import top.towing.yi.chidao.biz.bolt.DouyuBolt;
import top.towing.yi.chidao.biz.bolt.PandaBolt;
import top.towing.yi.chidao.biz.bolt.PandaDanmuBolt;
import top.towing.yi.chidao.biz.bolt.RouteBolt;
import top.towing.yi.chidao.biz.bolt.TaoBolt;
import top.towing.yi.chidao.biz.bolt.TaoDanmuBolt;
import top.towing.yi.chidao.biz.bolt.TaoGoBuyBolt;
import top.towing.yi.chidao.biz.bolt.TaoUvBolt;
import top.towing.yi.chidao.biz.helper.PropertyHelper;
import top.towing.yi.chidao.biz.scheme.MsgScheme;

public class ChiDaoTopology {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	private final String topologyName;
	private TopologyBuilder builder;
	private Config config;
	
	private PropertyHelper propertyHelper;
	
	public ChiDaoTopology(String topologyName) {
		this.topologyName = topologyName;
		this.builder = new TopologyBuilder();
		this.config = new Config();
		
		this.propertyHelper = new PropertyHelper();
	}
	
	public void setup() {		
		// setup KAFKA spouts
		setupKafkaSpouts();
		
		// setup bolts and assemble topology
		builder.setBolt("ROUTE-BOLT", new RouteBolt()).shuffleGrouping("CHIDAO-SPOUT");
		builder.setBolt("ROUTE-BOLT", new RouteBolt()).shuffleGrouping("TAO-CHIDAO-SPOUT");
		
		builder.setBolt("TAO-BOLT", new TaoBolt()).shuffleGrouping("ROUTE-BOLT", "TAO-STREAM");
		builder.setBolt("PANDA-BOLT",  new PandaBolt()).shuffleGrouping("ROUTE-BOLT", "PANDA-STREAM");
		builder.setBolt("DOUYU-BOLT",  new DouyuBolt()).shuffleGrouping("ROUTE-BOLT", "DOUYU-STREAM");
		
		builder.setBolt("TAO-GOBUY-BOLT", new TaoGoBuyBolt()).fieldsGrouping("TAO-BOLT", "TAO-GOBUY-STREAM", new Fields("liveroom"));
		builder.setBolt("TAO-UV-BOLT", new TaoUvBolt()).fieldsGrouping("TAO-BOLT", "TAO-UV-STREAM", new Fields("liveroom"));
		builder.setBolt("TAO-DANMU-BOLT", new TaoDanmuBolt()).fieldsGrouping("TAO-BOLT", "TAO-DANMU-STREAM", new Fields("liveroom"));
		
		builder.setBolt("PANDA-DANMU-BOLT", new PandaDanmuBolt()).fieldsGrouping("PANDA-BOLT", new Fields("liveroom"));
		builder.setBolt("DOUYU-DANMU-BOLT", new PandaDanmuBolt()).fieldsGrouping("DOUYU-BOLT", new Fields("liveroom"));
	}
	
	private void setupKafkaSpouts() {
		String zkConnStr = propertyHelper.getProperty("yi.chidao.zk.server");
		BrokerHosts brokerHosts = new ZkHosts(zkConnStr);
    	String zkRootPath = propertyHelper.getProperty("yi.chidao.zk.root.path");
    	String topicName = propertyHelper.getProperty("yi.chidao.topic.name");
    	String taoTopicName = propertyHelper.getProperty("yi.chidao.tao.topic.name");
		
		// setup KAFKA spout
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topicName, zkRootPath, topicName);
		spoutConfig.forceFromStart = true;
		spoutConfig.scheme = new SchemeAsMultiScheme(new MsgScheme());
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
		
		SpoutConfig taoSpoutConfig = new SpoutConfig(brokerHosts, topicName, zkRootPath, taoTopicName);
		taoSpoutConfig.forceFromStart = true;
		taoSpoutConfig.scheme = new SchemeAsMultiScheme(new MsgScheme());
		KafkaSpout taoKafkaSpout = new KafkaSpout(taoSpoutConfig);
					
		builder.setSpout("CHIDAO-SPOUT", kafkaSpout);
		builder.setSpout("TAO-CHIDAO-SPOUT", taoKafkaSpout);
	}
	
	public void run(String runMode, int runTime) {
		// run 
		if (runMode.equals("local")) {
			LocalCluster cluster = new LocalCluster();
			logger.info("run storm cluster in " + runMode + ".");
			cluster.submitTopology(topologyName, config, builder.createTopology());
			Utils.sleep(1000 * runTime);
			cluster.killTopology(topologyName);
			logger.info("complete run storm cluster in " + runMode + ".");
			cluster.shutdown();
		} else {
			config.setNumWorkers(1);
			try {
				logger.info("run storm cluster in " + runMode + ".");
				StormSubmitter.submitTopology(topologyName, config, builder.createTopology());
			} catch (AlreadyAliveException e) {
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				e.printStackTrace();
			}
		}
	}
}
