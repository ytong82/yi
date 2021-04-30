package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

import top.towing.yi.chidao.biz.enums.DouyuMessageType;

public class DouyuDanmuMessage extends DouyuMessage {
	private String username;
	private String usermsg;
	
	public DouyuDanmuMessage(String liveroom, Date timestamp, String content, DouyuMessageType douyuMessageType, String username, String usermsg) {
		super(liveroom, timestamp, content, douyuMessageType);
		this.setUsername(username);
		this.setUsermsg(usermsg);
	}
	
	public DouyuDanmuMessage(Message message, DouyuMessageType douyuMessageType, String username, String usermsg) {
		super(message, douyuMessageType);
		this.setUsername(username);
		this.setUsermsg(usermsg);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsermsg() {
		return usermsg;
	}

	public void setUsermsg(String usermsg) {
		this.usermsg = usermsg;
	}
}
