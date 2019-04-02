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

}
