package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

import top.towing.yi.chidao.biz.enums.TaoMessageType;

public class TaoGoBuyMessage extends TaoMessage {
	private String type;
	private String nick;

	public TaoGoBuyMessage(String liveroom, Date timestamp, String content, TaoMessageType taoMessageType, String type, String nick) {
		super(liveroom, timestamp, content, taoMessageType);
		this.setType(type);
		this.setNick(nick);
	}
	
	public TaoGoBuyMessage(Message message, TaoMessageType taoMessageType, String type, String nick) {
		super(message, taoMessageType);
		this.setType(type);
		this.setNick(nick);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
