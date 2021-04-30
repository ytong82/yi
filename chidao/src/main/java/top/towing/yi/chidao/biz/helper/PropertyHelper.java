package top.towing.yi.chidao.biz.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {
	private final String propertyPath = "/config.properties";
	private Properties properties;
	
	public PropertyHelper() {
		properties = new Properties();
    	try {
    		InputStream is = getClass().getResourceAsStream(propertyPath);
    		properties.load(is);
    		is.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	public String getProperty(String propertyName) {
    	return properties.getProperty(propertyName);
    }
}