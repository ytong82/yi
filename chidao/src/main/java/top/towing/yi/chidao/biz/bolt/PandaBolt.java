package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.enums.PandaMessageType;
import top.towing.yi.chidao.biz.impl.PandaBizImpl;
import top.towing.yi.chidao.datamodel.entity.Message;
import top.towing.yi.chidao.datamodel.entity.PandaMessage;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class PandaBolt extends BaseRichBolt {
	private static final Logger logger = LoggerFactory.getLogger(PandaBolt.class);
	
	private OutputCollector collector;
	private PandaBizImpl pandaBiz;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.pandaBiz = new PandaBizImpl();
	}

	public void execute(Tuple input) {
		Message message = (Message)input.getValue(0);
		PandaMessage pandaMessage = pandaBiz.processPandaMessage(message);
		
		if (pandaMessage != null) {
			PandaMessageType pandaMessageType = pandaMessage.getPandaMessageType();
			String liveroom = pandaMessage.getLiveroom();
			
			logger.debug("emit tuple " + message.toString() + " to " + pandaMessageType + " Stream.");
			if (pandaMessageType == PandaMessageType.DanmuMessage) {
				collector.emit("PANDA-DANMU-STREAM", new Values(liveroom, pandaMessage));
			} else if (pandaMessageType == PandaMessageType.GiftMessage) {
				
			} else if (pandaMessageType == PandaMessageType.Unknown) {
				
			} else {
				
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream("PANDA-DANMU-STREAM", new Fields("liveroom", "panda-danmu-message"));
	}

}
