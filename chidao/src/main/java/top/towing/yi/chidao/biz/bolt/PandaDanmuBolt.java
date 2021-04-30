package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import top.towing.yi.chidao.biz.impl.PandaDanmuBizImpl;
import top.towing.yi.chidao.biz.intf.PandaDanmuBiz;
import top.towing.yi.chidao.datamodel.entity.PandaDanmuMessage;

@SuppressWarnings("serial")
public class PandaDanmuBolt extends BaseRichBolt {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PandaDanmuBolt.class);
	private PandaDanmuBiz pandaDanmuBiz; 

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.pandaDanmuBiz = new PandaDanmuBizImpl();
	}

	public void execute(Tuple input) {
		PandaDanmuMessage pandaDanmuMessage = (PandaDanmuMessage)input.getValue(0);
		pandaDanmuBiz.processPandaDanmuMessage(pandaDanmuMessage);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
