package top.towing.yi.chidao.biz.impl;

import top.towing.yi.chidao.biz.intf.TaoUvBiz;
import top.towing.yi.chidao.dao.intf.TaoUvMessageDao;
import top.towing.yi.chidao.datamodel.entity.TaoUvMessage;

public class TaoUvBizImpl implements TaoUvBiz {
	private TaoUvMessageDao taoUvMessageDao;
	
	public TaoUvBizImpl() {
		
	}

	public void processTaoUvMessage(TaoUvMessage taoUvMessage) {
		taoUvMessageDao.save();
	}

}
