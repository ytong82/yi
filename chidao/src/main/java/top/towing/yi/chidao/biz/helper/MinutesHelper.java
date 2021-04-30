package top.towing.yi.chidao.biz.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinutesHelper {
	private static final Logger logger = LoggerFactory.getLogger(MinutesHelper.class);
	private final SimpleDateFormat hmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public String getTimestampByMinutes(Date timestamp) {
		String timestampByMinutes = hmFormat.format(timestamp);
		return timestampByMinutes;
	}
	
	public boolean isEarlierThan(String timestampByMinutesStr, String timestampByMinutesToCompareStr) 
			throws ParseException {
		Date timestampByMinutes = parseTimestampByMinutes(timestampByMinutesStr);
		Date timestampByMinutesToCompare = parseTimestampByMinutes(timestampByMinutesToCompareStr);
		
		if (timestampByMinutes.before(timestampByMinutesToCompare)) {
			return true;
		} else {
			return false;
		}
	}
	
	private Date parseTimestampByMinutes(String timestampByMinutesStr) throws ParseException {
		Date timestampByMinutes = null;
		try {
			timestampByMinutes = hmFormat.parse(timestampByMinutesStr);
		} catch (ParseException e) {
			logger.error("can't parse timestamp by minutes for " +  timestampByMinutesStr);
			throw new ParseException("can't parse timestamp by minutes", 0);
		}
		if (timestampByMinutes == null) {
			throw new ParseException("can't parse timestamp by minutes", 0);
		}
		return timestampByMinutes;
	}
}
