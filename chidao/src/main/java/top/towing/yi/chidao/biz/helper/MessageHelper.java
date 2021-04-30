package top.towing.yi.chidao.biz.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.enums.Vendor;
import top.towing.yi.chidao.datamodel.entity.Message;

public class MessageHelper {
	private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);
	private final SimpleDateFormat hmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat hmssFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	
	private final String taobaolive = "taobaolive";
	private final String panda = "panda";
	private final String douyu = "douyu";
	
	public Message processMessage(String messageStr) {
		logger.debug("process message " + messageStr);
		
		JSONObject messageJson = new JSONObject(messageStr);
		Message message = null;
		try {
			String liveroom = messageJson.getString("liveroom");
			Date timestamp = parseTimestamp(messageJson.getString("timestamp"));
			String content = messageJson.getString("content");
			message = new Message(liveroom, timestamp, content);
		} catch (ParseException e) {
			logger.error("can't parse timestamp and initialize message.");
			e.printStackTrace();
			return null;
		}
		return message;
	}
	
	public Date parseTimestamp(String timestampStr) throws ParseException {
		Date timestamp = null;
		try {
			timestamp = hmssFormat.parse(timestampStr);
		} catch (java.text.ParseException e) {
			try {
				timestamp = hmsFormat.parse(timestampStr);
			} catch (java.text.ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		if (timestamp == null) {
			throw new ParseException("parse result is null", 0);
		}
		return timestamp;
	}
	
	public Vendor getVendor(Message message) {
		String liveroom = message.getLiveroom();
		if (liveroom.indexOf(taobaolive) > 0) {
			return Vendor.Tao;
		} else if (liveroom.indexOf(panda) > 0) {
			return Vendor.Panda;
		} else if (liveroom.indexOf(douyu) > 0) {
			return Vendor.Douyu;
		} else {
			return Vendor.Unknown;
		}
	}
}
