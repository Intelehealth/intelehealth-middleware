package com.emrmiddleware.conf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigProperties {
	String result = "";
	private String mybatisDBEnvironment;
	private String serverhost;
	private String swaggerhost;
	private String port;
	InputStream inputStream;
	private final Logger logger = LoggerFactory.getLogger(ConfigProperties.class);
	
	public ConfigProperties() {
		try {
			getPropValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error in Properties File read : "+e.getMessage());
		}
	}
	public void getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
     
			mybatisDBEnvironment = prop.getProperty("MybatisEnvironmentId");
			serverhost = prop.getProperty("serverhost");
			swaggerhost = prop.getProperty("swaggerhost");
			port = prop.getProperty("port");
			
		} catch (Exception e) {
			logger.error("Exception: " + e);
		} finally {
			inputStream.close();
		}
		
	}
	
	
	public String getDBEnvironment(){
		return mybatisDBEnvironment;
	}
	
	public String getServer(){
		return serverhost;
	}
	
	public String getSwaggerHost(){
		return swaggerhost;
	}
	public String getPort(){
		return port;
	}

}
