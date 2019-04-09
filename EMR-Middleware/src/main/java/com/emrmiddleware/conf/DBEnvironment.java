package com.emrmiddleware.conf;

import com.emrmiddleware.resource.Resources;

public class DBEnvironment {
	
	public String getDBEnvironment(){
		String DBEnvironment="";
		if (Resources.DEV_MODE){
			DBEnvironment = "development";
		}
		else{
			DBEnvironment = "staging";
			//DBEnvironment = "ProtoType_Env";
		}
		
		return DBEnvironment;
	}
	
	public String getAPIBaseURL(){
		String Base_URL = "";
		if (Resources.DEV_MODE){
			Base_URL = "http://142.93.221.37:8080/openmrs/ws/rest/v1/";
		}
		else{
			Base_URL = "staging";
			//DBEnvironment = "ProtoType_Env";
		}
		
		return Base_URL;
		
	}

}
