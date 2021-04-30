package top.towing.yi.chidao.biz.scheme;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class MsgScheme implements Scheme {
	private static final Logger logger = LoggerFactory.getLogger(MsgScheme.class);
	
	public List<Object> deserialize(byte[] ser) {
		try {
			String message = new String(ser, "UTF-8");
			logger.info("get one message is {}", message);
			return new Values(message);
		} catch (UnsupportedEncodingException ignored) {
            return null;
        }
	}

	public Fields getOutputFields() {
		return new Fields("danmu");
	}
}
