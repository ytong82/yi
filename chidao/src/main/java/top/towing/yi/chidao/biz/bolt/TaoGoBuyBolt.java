package top.towing.yi.chidao.biz.bolt;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.helper.MinutesHelper;
import top.towing.yi.chidao.biz.impl.TaoGoBuyBizImpl;
import top.towing.yi.chidao.biz.intf.TaoGoBuyBiz;
import top.towing.yi.chidao.datamodel.entity.TaoGoBuyMessage;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class TaoGoBuyBolt extends BaseRichBolt {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TaoGoBuyBolt.class);
	
	private MinutesHelper minutesHelper;
	private TaoGoBuyBiz taoGoBuyBiz;
	
	private Map<String, String> eventsClock;
	

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.minutesHelper = new MinutesHelper();
		this.taoGoBuyBiz = new TaoGoBuyBizImpl();
		this.eventsClock = new HashMap<String, String>();
	}

	public void execute(Tuple input) {
		TaoGoBuyMessage taoGoBuyMessage = (TaoGoBuyMessage)input.getValue(0);
		String liveroom = taoGoBuyMessage.getLiveroom();
		Date timestamp = taoGoBuyMessage.getTimestamp();
		
		if (liveroom != null && liveroom.length() > 0) {
			if (!eventsClock.containsKey(liveroom)) {
				eventsClock.put(liveroom, null);
			}
			if (timestamp !=null) {
				String timestampByMinutes = minutesHelper.getTimestampByMinutes(timestamp);
				if (eventsClock.get(liveroom) == null || eventsClock.get(liveroom).length() == 0) {
					taoGoBuyBiz.createTaoGoBuyMinutes(liveroom, timestampByMinutes);
					eventsClock.put(liveroom, timestampByMinutes);
				}
				String eventsClockByMinutes = eventsClock.get(liveroom);
				
				// process single tao go-buy message
				taoGoBuyBiz.processTaoGoBuyMessage(taoGoBuyMessage);
				
				// process tao go-buy minutes
				try {
					boolean isEarlierThan = minutesHelper.isEarlierThan(eventsClock.get(liveroom), timestampByMinutes);
					
					if (isEarlierThan) {
						taoGoBuyBiz.saveTaoGoBuyMinutes(liveroom, eventsClockByMinutes);
						taoGoBuyBiz.createTaoGoBuyMinutes(liveroom, timestampByMinutes);
						eventsClock.put(liveroom, timestampByMinutes);
					} 
					taoGoBuyBiz.processTaoGoBuyMinutes(liveroom, timestampByMinutes, taoGoBuyMessage);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
}