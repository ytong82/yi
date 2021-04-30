package top.towing.yi.chidao.biz.impl;

import top.towing.yi.chidao.biz.intf.TaoDanmuBiz;
import top.towing.yi.chidao.dao.intf.TaoDanmuMessageDao;
import top.towing.yi.chidao.datamodel.entity.TaoDanmuMessage;

public class TaoDanmuBizImpl implements TaoDanmuBiz {
	public TaoDanmuMessageDao taoDanmuMessageDao;
	
	public TaoDanmuBizImpl() {
	
	}
	
	public void processTaoDanmuMessage(TaoDanmuMessage taoDanmuMessage) {
		taoDanmuMessageDao.save(taoDanmuMessage);
	}
}
