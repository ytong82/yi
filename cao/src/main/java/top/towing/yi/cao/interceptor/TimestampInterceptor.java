package top.towing.yi.cao.interceptor;

import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

public class TimestampInterceptor implements Interceptor {
	
	private static Logger logger = LoggerFactory.getLogger(TimestampInterceptor.class);
	
	public void close() {

	}

	public void initialize() {

	}

	public Event intercept(Event event) {
		String body = new String(event.getBody(), Charsets.UTF_8);
		JSONObject msgJson = new JSONObject();	
		try {
			String[] splits = body.split("\\s");
			String timestamp = splits[0] + " " + splits[1];
			String message = "";
			for (int index=2; index<splits.length; index++) {
				if (index == 2) {
					message = splits[index] ; 
				} else {
					message = " " + splits[index]; 
				}
			}
			msgJson.put("timestamp", timestamp);
			msgJson.put("message", message);
			logger.debug("message after timestamp interceptor is " + msgJson.toString());
			event.setBody(msgJson.toString().getBytes());
		} catch (Exception e) {
			logger.warn(body, e);
		}
		return event;
	}

	public List<Event> intercept(List<Event> events) {
		List<Event> intercepted = Lists.newArrayListWithCapacity(events.size());
		for (Event event : events) {
			Event interceptedEvent = intercept(event);
			if (interceptedEvent != null) {
				intercepted.add(interceptedEvent);
			}
		}
		return intercepted;
	}

	public static class TimestampInterceptorBuilder implements Interceptor.Builder {
		
		public Interceptor build() {
			return new TimestampInterceptor();
		}

		public void configure(Context context) {
	
		}
	}
}
