package top.towing.yi.chidao.biz.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.intf.TaoGoBuyBiz;
import top.towing.yi.chidao.dao.intf.TaoGoBuyMessageDao;
import top.towing.yi.chidao.dao.intf.TaoGoBuyMinutesDao;
import top.towing.yi.chidao.datamodel.entity.TaoGoBuyMessage;
import top.towing.yi.chidao.datamodel.entity.TaoGoBuyMinutes;

public class TaoGoBuyBizImpl implements TaoGoBuyBiz {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TaoGoBuyBizImpl.class);

	private Map<String, HashMap<String, TaoGoBuyMinutes>> taoGoBuyMinutesMap;	
	private TaoGoBuyMessageDao taoGoBuyMessageDao;
	private TaoGoBuyMinutesDao taoGoBuyMinutesDao;
	
	public TaoGoBuyBizImpl() {
		this.taoGoBuyMinutesMap = new HashMap<String, HashMap<String, TaoGoBuyMinutes>>();
	}
	
	public void processTaoGoBuyMessage(TaoGoBuyMessage taoGoBuyMessage) {
		taoGoBuyMessageDao.save(taoGoBuyMessage);
	}

	public void createTaoGoBuyMinutes(String liveroom, String timestampByMinutes) {
		Map<String, TaoGoBuyMinutes> taoGoBuyMinutesforLiveroomMap = null;
		if (taoGoBuyMinutesMap.containsKey(liveroom)) {
			taoGoBuyMinutesforLiveroomMap = taoGoBuyMinutesMap.get(liveroom);
		} else {
			taoGoBuyMinutesforLiveroomMap = new HashMap<String, TaoGoBuyMinutes>();
			taoGoBuyMinutesMap.put(liveroom, (HashMap<String, TaoGoBuyMinutes>) taoGoBuyMinutesforLiveroomMap);
		}
		
		taoGoBuyMinutesforLiveroomMap.put(timestampByMinutes, new TaoGoBuyMinutes(timestampByMinutes));
	}

	public void processTaoGoBuyMinutes(String liveroom, String timestampByMinute, TaoGoBuyMessage taoGoBuyMessage) {
		TaoGoBuyMinutes taoGoBuyMinutes = taoGoBuyMinutesMap.get(liveroom).get(timestampByMinute);
		taoGoBuyMinutes.countGoBuyMessage();
	}

	public void saveTaoGoBuyMinutes(String liveroom, String eventsClockByMinutes) {
		Map<String, TaoGoBuyMinutes> taoGoBuyMinutesforLiveroomMap = taoGoBuyMinutesMap.get(liveroom);
		TaoGoBuyMinutes taoGoBuyMinutes = taoGoBuyMinutesforLiveroomMap.get(eventsClockByMinutes);
		taoGoBuyMinutesDao.save(taoGoBuyMinutes);
		taoGoBuyMinutesforLiveroomMap.remove(taoGoBuyMinutes);
	}
	
}
