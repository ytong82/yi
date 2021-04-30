package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

import top.towing.yi.chidao.biz.enums.TaoMessageType;

public class TaoDanmuMessage extends TaoMessage {
	private String nick;
	private String userMsg;

	public TaoDanmuMessage(String liveroom, Date timestamp, String content, TaoMessageType taoMessageType, String nick, String userMsg) {
		super(liveroom, timestamp, content, taoMessageType);
		this.nick = nick;
		this.userMsg = userMsg;
	}
	
	public TaoDanmuMessage(Message message, TaoMessageType taoMessageType, String nick, String userMsg) {
		super(message, taoMessageType);
		this.nick = nick;
		this.userMsg = userMsg;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMessage() {
		return userMsg;
	}

	public void setMessage(String userMsg) {
		this.userMsg = userMsg;
	}

}
