package top.towing.yi.chidao;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalyos.jfiglet.FigletFont;

import top.towing.yi.chidao.ChiDaoTopology;
import top.towing.yi.chidao.biz.helper.PropertyHelper;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	private static PropertyHelper propertyHelper;
	private String runMode;
	private Integer runTime;
	
    public static void main(String[] args) {
		try {
			String label = FigletFont.convertOneLine("hello yi.chidao");
			logger.info(label);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("hello yi.chidao");
		}
		
		logger.info("David Tong (ytong82@towing.top)");
		logger.info("Developed on 2017-11-20");
    	
    	// parse arguments
    	propertyHelper = new PropertyHelper();
    	String runMode = propertyHelper.getProperty("yi.chidao.default.runmode");
    	Integer runTime = Integer.parseInt(propertyHelper.getProperty("yi.chidao.default.local.runtime"));
    	
    	if (args.length != 0) {
    		runMode = args[0];
    	}
    	logger.info("run yi.chao app in " + runMode + " mode");
    	
    	// run application
    	App app = new App(runMode, runTime);
    	app.startup();
    }
    
    public App(String runMode, Integer runTime) {
    	this.runMode = runMode;
    	this.runTime = runTime;
    }
    
    private void startup() {
    	// setup topology
    	String topologyName = propertyHelper.getProperty("yi.chidao.topology.name");
    	ChiDaoTopology topology = new ChiDaoTopology(topologyName);
    	topology.setup();
    	
    	// run topology
    	topology.run(runMode, runTime);
    }
}
