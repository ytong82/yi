package top.towing.yi.chidao.biz.intf;

import top.towing.yi.chidao.datamodel.entity.Message;
import top.towing.yi.chidao.datamodel.entity.TaoMessage;

public interface TaoBiz {
	public TaoMessage processTaoMessage(Message message);
}
