package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.enums.Vendor;
import top.towing.yi.chidao.biz.helper.MessageHelper;
import top.towing.yi.chidao.datamodel.entity.Message;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class RouteBolt extends BaseRichBolt {
	private static final Logger logger = LoggerFactory.getLogger(RouteBolt.class);
	
	private OutputCollector collector;
	private MessageHelper messageHelper;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.messageHelper = new MessageHelper();
	}

	public void execute(Tuple input) {
		String messageStr = (String) input.getValue(0);
		Message message = messageHelper.processMessage(messageStr);
		
		if (message != null && message.getLiveroom() != null && message.getTimestamp() != null) {
			Vendor vendor = messageHelper.getVendor(message);
			logger.debug("emit tuple " + message.toString() + " to " + vendor.toString() + "-stream.");
			if (vendor == Vendor.Tao) {
				collector.emit("TAO-STREAM", new Values(message));
			} else if (vendor == Vendor.Panda) {
				collector.emit("PANDA-STREAM", new Values(message));
			} else if (vendor == Vendor.Douyu) {
				collector.emit("DOUYU-STREAM", new Values(message));
			} else if (vendor == Vendor.Unknown) { 
				
			} else {
				
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream("TAO-STREAM", new Fields("tao-message"));
		declarer.declareStream("PANDA-STREAM", new Fields("panda-message"));
		declarer.declareStream("DOUYU-STREAM", new Fields("douyu-message"));
	}
}
