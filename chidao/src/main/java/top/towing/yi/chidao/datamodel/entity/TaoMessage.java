package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

import top.towing.yi.chidao.biz.enums.TaoMessageType;

public class TaoMessage extends Message {
	private TaoMessageType taoMessageType;

	public TaoMessage(String liveroom, Date timestamp, String content, TaoMessageType taoMessageType) {
		super(liveroom, timestamp, content);
		this.setTaoMessageType(taoMessageType);
	}
	
	public TaoMessage(Message message, TaoMessageType taoMessageType) {
		super(message);
		this.setTaoMessageType(taoMessageType);
	}

	public TaoMessageType getTaoMessageType() {
		return taoMessageType;
	}

	public void setTaoMessageType(TaoMessageType taoMessageType) {
		this.taoMessageType = taoMessageType;
	}

}