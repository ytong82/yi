package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

import top.towing.yi.chidao.biz.enums.DouyuMessageType;

public class DouyuMessage extends Message {
	private DouyuMessageType douyuMessageType;
	
	public DouyuMessage(String liveroom, Date timestamp, String content, DouyuMessageType douyuMessageType) {
		super(liveroom, timestamp, content);
		this.setDouyuMessageType(douyuMessageType);
	}
	
	public DouyuMessage(Message message, DouyuMessageType douyuMessageType) {
		super(message);
		this.setDouyuMessageType(douyuMessageType);
	}

	public DouyuMessageType getDouyuMessageType() {
		return douyuMessageType;
	}

	public void setDouyuMessageType(DouyuMessageType douyuMessageType) {
		this.douyuMessageType = douyuMessageType;
	}
}
