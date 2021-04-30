package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

import top.towing.yi.chidao.biz.enums.PandaMessageType;

public class PandaDanmuMessage extends PandaMessage {
	private String username;
	private String usermsg;
	
	public PandaDanmuMessage(String liveroom, Date timestamp, String content, PandaMessageType pandaMessageType, String username, String usermsg) {
		super(liveroom, timestamp, content, pandaMessageType);
		this.username = username;
		this.usermsg = usermsg;
	}
	
	public PandaDanmuMessage(Message message, PandaMessageType pandaMessageType, String username, String usermsg) {
		super(message, pandaMessageType);
		this.username = username;
		this.usermsg = usermsg;
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
