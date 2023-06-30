package com.emrmiddleware.conf;


public class ResourcesEnvironment {
    ConfigProperties configProperties = new ConfigProperties();
	public String getDBEnvironment() {
		String DBEnvironment = "";
        DBEnvironment = configProperties.getDBEnvironment();
		return DBEnvironment;
	}
	
	

	public String getAPIBaseURL() {
		String Base_URL = "";
		
		String host = configProperties.getServer()+":"+configProperties.getPort();
		Base_URL=host+"/openmrs/ws/rest/v1/";

		return Base_URL;

	}

	public String getIdGenUrl() {
		String ID_URL = "";
		String host = configProperties.getServer()+":"+configProperties.getPort();	
		ID_URL=host+"/openmrs/module/idgen/";
		return ID_URL;
	}
	
	
	public String getHostPath(){
		String basepath="";
		
		String host = configProperties.getSwaggerHost()+":"+configProperties.getPort(); 
		basepath = host+"/EMR-Middleware";
		return basepath;
		
	}
}
