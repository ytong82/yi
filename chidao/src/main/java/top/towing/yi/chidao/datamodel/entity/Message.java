package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

public class Message {
	private String liveroom;
	private Date timestamp;
	private String content;
	
	public Message(String liveroom, Date timestamp, String message) {
		this.timestamp = timestamp;
		this.liveroom = liveroom;
		this.content = message;
	}
	
	public Message(Message message) {
		this.timestamp = message.getTimestamp();
		this.liveroom = message.getLiveroom();
		this.content = message.getContent();
	}
	
	public String getLiveroom() {
		return liveroom;
	}

	public void setLiveroom(String liveroom) {
		this.liveroom = liveroom;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString() {
		return "liveroom : " + this.liveroom + ", timestamp : " + this.timestamp + ", content : " + this.content;
	}
}
