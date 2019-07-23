package com.emrmiddleware.conf;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.emrmiddleware.resource.Resources;

public class ResourcesEnvironment {
    ConfigProperties configProperties = new ConfigProperties();
	public String getDBEnvironment() {
		String DBEnvironment = "";
        DBEnvironment = configProperties.getDBEnvironment();
		return DBEnvironment;
	}
	
	

	public String getAPIBaseURL() {
		String Base_URL = "";
		
		String host = "http://"+configProperties.getServer()+":"+configProperties.getPort();
		Base_URL=host+"/openmrs/ws/rest/v1/";

		return Base_URL;

	}

	public String getIdGenUrl() {
		String ID_URL = "";
		String host = "http://"+configProperties.getServer()+":"+configProperties.getPort();	
		ID_URL=host+"/openmrs/module/idgen/";
		return ID_URL;
	}
	
	
	public String getHostPath(){
		String basepath="";
		InetAddress thisIp = null;
		try {
			thisIp = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String thisIpAddress = thisIp.getHostAddress().toString();
		//String host = "http://"+configProperties.getSwaggerHost();
        String host = "http://"+thisIpAddress+":"+configProperties.getPort();
		
		basepath = host+"/EMR-Middleware";
		return basepath;
		
	}
}
