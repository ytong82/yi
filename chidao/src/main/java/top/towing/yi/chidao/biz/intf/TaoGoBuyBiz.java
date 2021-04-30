package top.towing.yi.chidao.biz.intf;

import top.towing.yi.chidao.datamodel.entity.TaoGoBuyMessage;

public interface TaoGoBuyBiz {
	public void processTaoGoBuyMessage(TaoGoBuyMessage taoGoBuyMessage);
	public void createTaoGoBuyMinutes(String liveroom, String timestampByMinutes);
	public void processTaoGoBuyMinutes(String liveroom, String timestampByMinute, TaoGoBuyMessage taoGoBuyMessage);
	public void saveTaoGoBuyMinutes(String liveroom, String eventsClockByMinutes);	
}
