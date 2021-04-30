package top.towing.yi.chidao.datamodel.entity;

import java.util.Date;

import top.towing.yi.chidao.biz.enums.PandaMessageType;

public class PandaMessage extends Message {
	private PandaMessageType pandaMessageType;
	
	public PandaMessage(String liveroom, Date timestamp, String content, PandaMessageType pandaMessageType) {
		super(liveroom, timestamp, content);
		this.setPandaMessageType(pandaMessageType);
	}
	
	public PandaMessage(Message message, PandaMessageType pandaMessageType) {
		super(message);
		this.setPandaMessageType(pandaMessageType);
	}

	public PandaMessageType getPandaMessageType() {
		return pandaMessageType;
	}

	public void setPandaMessageType(PandaMessageType pandaMessageType) {
		this.pandaMessageType = pandaMessageType;
	}
}
