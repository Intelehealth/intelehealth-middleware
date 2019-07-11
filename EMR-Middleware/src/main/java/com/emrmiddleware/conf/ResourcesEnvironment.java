package com.emrmiddleware.conf;

import com.emrmiddleware.resource.Resources;

public class ResourcesEnvironment {

	public String getDBEnvironment() {
		String DBEnvironment = "";
		if (Resources.environment.equalsIgnoreCase("localdev")) {
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
		}

		return DBEnvironment;
	}
	
	public String getFileDBEnvironment() {
		String DBEnvironment = "";
		if (Resources.environment.equalsIgnoreCase("localdev")) {
			DBEnvironment = "filedevelopment";
		}
		if (Resources.environment.equalsIgnoreCase("mahitidev")) {
			DBEnvironment = "filedevelopment";
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthdev")) {
			DBEnvironment = "intelehealthfiledevelopment";
		}

		return DBEnvironment;
	}

	public String getAPIBaseURL() {
		String Base_URL = "";
		if (Resources.environment.equalsIgnoreCase("localdev")) {
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
			
		}

		return Base_URL;

	}

	public String getIdGenUrl() {
		String ID_URL = "";
		if (Resources.environment.equalsIgnoreCase("localdev")) {
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
		}
		return ID_URL;
	}
	
	public String getFilePath(){
		String FILE_PATH="";
		if (Resources.environment.equalsIgnoreCase("localdev")) {
			FILE_PATH = "C://openmrsimages//";
		}
		if (Resources.environment.equalsIgnoreCase("mahitidev")) {
			FILE_PATH = "/home/mahadmin/OpenMrs_images/";
		} 
		if (Resources.environment.equalsIgnoreCase("intelehealthdev")) {
			FILE_PATH = "//openmrsimages//";
		}
		return FILE_PATH;
		
		
	}
	public String getHostPath(){
		String basepath="";
		if (Resources.environment.equalsIgnoreCase("localdev")) {
			basepath = "localhost:8080/EMR-Middleware";
		}
		if (Resources.environment.equalsIgnoreCase("mahitidev")) {
			basepath = "142.93.221.37:8080/EMR-Middleware";
		} 
		if (Resources.environment.equalsIgnoreCase("intelehealthdev")) {
			basepath = "localhost:8080/EMR-Middleware";
		}
		if (Resources.environment.equalsIgnoreCase("intelehealthAwsTest")) {
			basepath = "13.233.110.169:8080:8080/EMR-Middleware";
		}
		return basepath;
		
	}
}