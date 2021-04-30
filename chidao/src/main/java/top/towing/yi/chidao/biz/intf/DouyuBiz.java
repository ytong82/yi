package top.towing.yi.chidao.biz.intf;

import top.towing.yi.chidao.datamodel.entity.DouyuMessage;
import top.towing.yi.chidao.datamodel.entity.Message;

public interface DouyuBiz {
	public DouyuMessage processDouyuMessage(Message message);
}
