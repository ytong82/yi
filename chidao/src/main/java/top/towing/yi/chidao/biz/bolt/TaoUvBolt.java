package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.impl.TaoUvBizImpl;
import top.towing.yi.chidao.biz.intf.TaoUvBiz;
import top.towing.yi.chidao.datamodel.entity.TaoUvMessage;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class TaoUvBolt extends BaseRichBolt {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TaoUvBolt.class);
	private TaoUvBiz taoUvBiz;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.taoUvBiz = new TaoUvBizImpl();
	}

	public void execute(Tuple input) {
		TaoUvMessage taoUvMessage = (TaoUvMessage)input.getValue(0);
		taoUvBiz.processTaoUvMessage(taoUvMessage);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
