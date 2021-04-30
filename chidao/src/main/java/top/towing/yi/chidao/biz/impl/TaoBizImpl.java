package top.towing.yi.chidao.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.towing.yi.chidao.biz.enums.TaoMessageType;
import top.towing.yi.chidao.biz.intf.TaoBiz;
import top.towing.yi.chidao.datamodel.entity.Message;
import top.towing.yi.chidao.datamodel.entity.TaoDanmuMessage;
import top.towing.yi.chidao.datamodel.entity.TaoGoBuyMessage;
import top.towing.yi.chidao.datamodel.entity.TaoMessage;
import top.towing.yi.chidao.datamodel.entity.TaoUser;
import top.towing.yi.chidao.datamodel.entity.TaoUvMessage;


public class TaoBizImpl implements TaoBiz {
	private static final Logger logger = LoggerFactory.getLogger(TaoBizImpl.class);
	
	private final String taoGoBuyMessagePattern = "正在去购买的路上:(.+)$";
	private final String taoUvMessagePattern = "进入了直播间：(.+)$";
	private final String taoDanmuMessagePattern = "消息内容：(.+)$";
	
	private Pattern taoGoBuyPattern;
	private Pattern taoUvPattern;
	private Pattern taoDanmuPattern;
	
	public TaoBizImpl() {
		taoGoBuyPattern = Pattern.compile(taoGoBuyMessagePattern);
		taoUvPattern = Pattern.compile(taoUvMessagePattern);
		taoDanmuPattern = Pattern.compile(taoDanmuMessagePattern);
	}
	
	public TaoMessage processTaoMessage(Message message) {
		Matcher taoGoBuyMatcher = taoGoBuyPattern.matcher(message.getContent());
		if (taoGoBuyMatcher.find()) {
			TaoGoBuyMessage taoGoBuyMessage = processTaoGoBuyMessage(message, taoGoBuyMatcher.group(0));
			return taoGoBuyMessage;
		} else {
			Matcher taoUvMatcher = taoUvPattern.matcher(message.getContent());
			if (taoUvMatcher.find()) {
				TaoUvMessage taoUvMessage = processTaoUvMessage(message, taoUvMatcher.group(0));
				return taoUvMessage;
			} else {
				Matcher taoDanmuMatcher = taoDanmuPattern.matcher(message.getContent());
				if (taoDanmuMatcher.find()) {
					TaoDanmuMessage taoDanmuMessage;
					taoDanmuMessage = processTaoDanmuMessage(message, taoDanmuMatcher.group(0));
					return taoDanmuMessage;
				} else {
					TaoMessage taoMessage = processTaoUnknownMessage(message);
					return taoMessage;
				}
			}
		} 
	}
	
	private TaoGoBuyMessage processTaoGoBuyMessage(Message message, String contentStr) {	
		logger.debug("process tao go-buy message " + contentStr);
		
		JSONObject contentJson = new JSONObject(contentStr);
		String type = contentJson.getString("type");
		String nick = contentJson.getString("nick");
		TaoMessageType taoMsgType = TaoMessageType.GoBuyMessage;
		TaoGoBuyMessage taoGoBuyMsg = new TaoGoBuyMessage(message, taoMsgType, type, nick);
		
		return taoGoBuyMsg;
	}
	
	private TaoUvMessage processTaoUvMessage(Message message, String contentStr) {
		logger.debug("process tao uv message" + contentStr);
		
		JSONObject contentJson = new JSONObject(contentStr);
		int onlineCount = contentJson.getInt("online");
		int totalCount = contentJson.getInt("total");
		int pageViewCount = contentJson.getInt("pageViewCount");
		
		JSONArray users = contentJson.getJSONArray("userList");
		List<TaoUser> userList = new ArrayList<TaoUser>();
		for (int index=0; index<users.length(); index++) {
			JSONObject userJson = users.getJSONObject(index);
			String nick = userJson.getString("nick");
			String userId = userJson.getString("userId");
			TaoUser taoUser = new TaoUser(nick, userId);
			userList.add(taoUser);
		}
		
		TaoMessageType taoMsgType = TaoMessageType.UvMessage;
		TaoUvMessage taoUvMessage = new TaoUvMessage(message, taoMsgType, onlineCount, totalCount, pageViewCount, userList);
		
		return taoUvMessage;
	}
	
	private TaoDanmuMessage processTaoDanmuMessage(Message message, String contentStr) {
		logger.debug("process tao danmu message " + contentStr);	
		
		JSONObject contentJson = new JSONObject(contentStr);
		String nick = contentJson.getString("nick");
		String userMsg = contentJson.getString("msg");
		TaoMessageType taoMsgType = TaoMessageType.DanmuMessage;
		TaoDanmuMessage taoDanmuMsg = new TaoDanmuMessage(message, taoMsgType, nick, userMsg);
		
		return taoDanmuMsg;
	}
	
	private TaoMessage processTaoUnknownMessage(Message message) {
		return new TaoMessage(message, TaoMessageType.Unknown);
	}
}
