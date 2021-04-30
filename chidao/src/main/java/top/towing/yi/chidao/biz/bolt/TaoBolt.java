package top.towing.yi.chidao.biz.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.enums.TaoMessageType;
import top.towing.yi.chidao.biz.impl.TaoBizImpl;
import top.towing.yi.chidao.datamodel.entity.Message;
import top.towing.yi.chidao.datamodel.entity.TaoMessage;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class TaoBolt extends BaseRichBolt {
	private static final Logger logger = LoggerFactory.getLogger(TaoBolt.class);
	private OutputCollector collector;
	private TaoBizImpl taoMessageHelper;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.taoMessageHelper = new TaoBizImpl();
	}

	public void execute(Tuple input) {
		Message message = (Message)input.getValue(0);
		TaoMessage taoMessage = taoMessageHelper.processTaoMessage(message);
		
		if (taoMessage != null) {
			TaoMessageType taoMessageType = taoMessage.getTaoMessageType();
			String liveroom = taoMessage.getLiveroom();
			
			logger.debug("emit tuple " + message.toString() + " to " + taoMessageType + " Stream.");
			if (taoMessageType == TaoMessageType.GoBuyMessage) {
				collector.emit("TAO-GOBUY-STREAM", new Values(liveroom, taoMessage));
			} else if (taoMessageType == TaoMessageType.UvMessage) {
				collector.emit("TAO-UV-STREAM", new Values(liveroom, taoMessage));
			} else if (taoMessageType == TaoMessageType.DanmuMessage) {
				collector.emit("TAO-DANMU-STREAM", new Values(liveroom, taoMessage));
			} else if (taoMessageType == TaoMessageType.Unknown) {
	
			} else {
				
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream("TAO-GOBUY-STREAM", new Fields("liveroom", "tao-gobuy-message"));
		declarer.declareStream("TAO-UV-STREAM", new Fields("liveroom", "tao-uv-message"));
		declarer.declareStream("TAO-DANMU-STREAM", new Fields("liveroom", "tao-danmu-message"));
	}

}
