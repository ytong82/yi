package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.impl.DouyuDanmuBizImpl;
import top.towing.yi.chidao.biz.intf.DouyuDanmuBiz;
import top.towing.yi.chidao.datamodel.entity.DouyuDanmuMessage;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class DouyuDanmuBolt extends BaseRichBolt {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DouyuDanmuBolt.class);
	private DouyuDanmuBiz douyuDanmuBiz;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.douyuDanmuBiz = new DouyuDanmuBizImpl();
	}

	public void execute(Tuple input) {
		DouyuDanmuMessage douyuDanmuMessage = (DouyuDanmuMessage)input.getValue(0);
		douyuDanmuBiz.processDouyuDanmuMessage(douyuDanmuMessage);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
