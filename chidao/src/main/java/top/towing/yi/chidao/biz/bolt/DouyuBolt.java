package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.enums.DouyuMessageType;
import top.towing.yi.chidao.biz.impl.DouyuBizImpl;
import top.towing.yi.chidao.datamodel.entity.DouyuMessage;
import top.towing.yi.chidao.datamodel.entity.Message;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class DouyuBolt extends BaseRichBolt {
	private static final Logger logger = LoggerFactory.getLogger(DouyuBolt.class);
	
	private OutputCollector collector;
	private DouyuBizImpl douyuBiz;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.douyuBiz = new DouyuBizImpl();
	}

	public void execute(Tuple input) {
		Message message = (Message)input.getValue(0);
		DouyuMessage douyuMessage = douyuBiz.processDouyuMessage(message);
		
		if (douyuMessage != null) {
			DouyuMessageType douyuMessageType = douyuMessage.getDouyuMessageType();
			String liveroom = douyuMessage.getLiveroom();
			
			logger.debug("emit tuple " + message.toString() + " to " + douyuMessageType + " Stream.");
			if (douyuMessageType == DouyuMessageType.DanmuMessage) {
				collector.emit("DOUYU-DANMU-STREAM", new Values(liveroom, douyuMessage));
			} else if (douyuMessageType == DouyuMessageType.GiftMessage) {
				
			} else if (douyuMessageType == DouyuMessageType.Unknown) {
				
			} else {
				
			}
		} 
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
