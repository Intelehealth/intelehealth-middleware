package com.emrmiddleware.conf;

import com.emrmiddleware.resource.Resources;

public class ResourcesEnvironment {
    ConfigProperties configProperties = new ConfigProperties();
	public String getDBEnvironment() {
		String DBEnvironment = "";
		/*if (Resources.environment.equalsIgnoreCase("localdev")) {
			DBEnvironment = "development";
		}
		if (Resources.environment.equalsIgnoreCase("mahitidev")) {
			DBEnvironment = "development";
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthdev")) {
			DBEnvironment = "intelehealthdevelopment";
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthAwsTest")) {
			DBEnvironment = "intelehealthAwsTest";
		}*/
        DBEnvironment = configProperties.getDBEnvironment();
		return DBEnvironment;
	}
	
	

	public String getAPIBaseURL() {
		String Base_URL = "";
		String host = "http://"+configProperties.getServer();
		/*if (Resources.environment.equalsIgnoreCase("localdev")) {
			Base_URL = "http://142.93.221.37:8080/openmrs/ws/rest/v1/";
		}
		if (Resources.environment.equalsIgnoreCase("mahitidev")) {
			Base_URL = "http://142.93.221.37:8080/openmrs/ws/rest/v1/";
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthdev")) {
			Base_URL = "http://localhost:8080/openmrs/ws/rest/v1/";
			
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthAwsTest")) {
			Base_URL = "http://localhost:8080/openmrs/ws/rest/v1/";
			
		}*/
		Base_URL=host+"/openmrs/ws/rest/v1/";

		return Base_URL;

	}

	public String getIdGenUrl() {
		String ID_URL = "";
		String host = "http://"+configProperties.getServer();
		/*if (Resources.environment.equalsIgnoreCase("localdev")) {
			ID_URL = "http://142.93.221.37:8080/openmrs/module/idgen/";
		}
		if (Resources.environment.equalsIgnoreCase("mahitidev")) {
			ID_URL = "http://142.93.221.37:8080/openmrs/module/idgen/";
		} 
		if (Resources.environment.equalsIgnoreCase("intelehealthdev")) {
			ID_URL = "http://localhost:8080/openmrs/module/idgen/";
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthAwsTest")) {
			ID_URL = "http://localhost:8080/openmrs/module/idgen/";
		}*/
		ID_URL=host+"/openmrs/module/idgen/";
		return ID_URL;
	}
	
	
	public String getHostPath(){
		String basepath="";
		String host = "http://"+configProperties.getSwaggerHost();
		/*if (Resources.environment.equalsIgnoreCase("localdev")) {
			basepath = "localhost:8080/EMR-Middleware";
		}
		if (Resources.environment.equalsIgnoreCase("mahitidev")) {
			basepath = "142.93.221.37:8080/EMR-Middleware";
		} 
		if (Resources.environment.equalsIgnoreCase("intelehealthdev")) {
			basepath = "localhost:8080/EMR-Middleware";
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthAwsTest")) {
			basepath = "13.233.110.169:8080/EMR-Middleware";
		}*/
		basepath = host+"/EMR-Middleware";
		return basepath;
		
	}
}
