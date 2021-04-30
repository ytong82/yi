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

public class LiveroomInterceptor implements Interceptor {
	
	private static Logger logger = LoggerFactory.getLogger(LiveroomInterceptor.class);
	
	public void close() {

	}

	public void initialize() {

	}

	public Event intercept(Event event) {
		String body = new String(event.getBody(), Charsets.UTF_8);
		String filePath = event.getHeaders().get("file");
		JSONObject msgJson = new JSONObject();	
		try {
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			String[] splits = fileName.split("\\.");
			String liveroom = splits[0] + "." + splits[1];
			msgJson.put("liveroom", liveroom);
			String newBody = msgJson.toString() + body;
			logger.debug("message after liveroom interceptor is " + newBody);
			event.setBody(newBody.getBytes());
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
	
	public static class LiveroomInterceptorBuilder implements Interceptor.Builder {	
		public Interceptor build() {
			return new LiveroomInterceptor();
		}
		
		public void configure(Context context) {

		}
	}
}
