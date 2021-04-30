package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;
import java.util.List;

import top.towing.yi.chidao.biz.enums.TaoMessageType;

public class TaoUvMessage extends TaoMessage {
	private int onlineCount;
	private int totalCount;
	private int pageViewCount;
	private List<TaoUser> userList;
	
	public TaoUvMessage(String liveroom, Date timestamp, String content, TaoMessageType taoMessageType, int onlineCount, int totalCount, int pageViewCount, List<TaoUser> userList) {
		super(liveroom, timestamp, content, taoMessageType);
		this.onlineCount = onlineCount;
		this.totalCount = totalCount;
		this.pageViewCount = pageViewCount;
		this.userList = userList;
	}

	public TaoUvMessage(Message message, TaoMessageType taoMessageType, int onlineCount, int totalCount, int pageViewCount, List<TaoUser> userList) {
		super(message, taoMessageType);
		this.onlineCount = onlineCount;
		this.totalCount = totalCount;
		this.pageViewCount = pageViewCount;
		this.userList = userList;
	}
	
	public int getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageViewCount() {
		return pageViewCount;
	}

	public void setPageViewCount(int pageViewCount) {
		this.pageViewCount = pageViewCount;
	}

	public List<TaoUser> getUserList() {
		return userList;
	}

	public void setUserList(List<TaoUser> userList) {
		this.userList = userList;
	}

}
