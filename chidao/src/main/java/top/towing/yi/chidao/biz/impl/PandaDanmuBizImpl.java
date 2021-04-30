package top.towing.yi.chidao.biz.impl;

import top.towing.yi.chidao.biz.intf.PandaDanmuBiz;
import top.towing.yi.chidao.dao.intf.PandaDanmuMessageDao;
import top.towing.yi.chidao.datamodel.entity.PandaDanmuMessage;

public class PandaDanmuBizImpl implements PandaDanmuBiz {
	public PandaDanmuMessageDao pandaDanmuMessageDao;
	
	public PandaDanmuBizImpl() {
	
	}
	
	public void processPandaDanmuMessage(PandaDanmuMessage pandaDanmuMessage) {
		pandaDanmuMessageDao.save(pandaDanmuMessage);
	}

}
