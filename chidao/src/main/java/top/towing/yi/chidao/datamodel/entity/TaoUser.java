package top.towing.yi.chidao.datamodel.entity;

public class TaoUser {
	private String nick;
	private String userId;
	
	public TaoUser(String nick, String userId) {
		this.setNick(nick);
		this.setUserId(userId);
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
