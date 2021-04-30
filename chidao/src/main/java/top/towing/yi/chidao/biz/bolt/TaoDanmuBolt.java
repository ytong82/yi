package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.impl.TaoDanmuBizImpl;
import top.towing.yi.chidao.biz.intf.TaoDanmuBiz;
import top.towing.yi.chidao.datamodel.entity.TaoDanmuMessage;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class TaoDanmuBolt extends BaseRichBolt {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TaoDanmuBolt.class);
	private TaoDanmuBiz taoDanmuBiz; 

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.taoDanmuBiz = new TaoDanmuBizImpl();
	}

	public void execute(Tuple input) {
		TaoDanmuMessage taoDanmuMessage = (TaoDanmuMessage)input.getValue(0);
		taoDanmuBiz.processTaoDanmuMessage(taoDanmuMessage);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
