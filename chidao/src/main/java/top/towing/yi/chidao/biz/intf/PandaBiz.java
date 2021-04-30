package top.towing.yi.chidao.biz.intf;

import top.towing.yi.chidao.datamodel.entity.Message;
import top.towing.yi.chidao.datamodel.entity.PandaMessage;

public interface PandaBiz {
	public PandaMessage processPandaMessage(Message message);
}
