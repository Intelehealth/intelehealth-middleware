package com.emrmiddleware.resource;



public class Resources {
    
	public static final boolean DEV_MODE = false;//This variable is used so that certain functionalities can be enable during development alone
    public static final String OK="OK";
    public static final String ERROR="ERROR";
    public static final String AUTHERROR="No Authorization";
    public static final String DAOEXCEPTION="Exception in DAO : ";
	public static final String CONTROLLEREXCEPTION="Exception in Controller : ";
	public static final String UNABLETOPROCESS="unable_to_process_request";
	public static final String SERVER_ERROR= "Unable to process request. Please try again or Contact System Administrator";
	//public static String environment="localdev";//This is if working from local dev machine
	public static String environment="mahitidev";//If this is being hosted at mahiti droplet instance
	//public static String environment="intelehealthdev";//for demo.intelehealth.io
	//public static String environment="intelehealthAwsTest";//AWS Testing instance of intelehealth
	
   
}
