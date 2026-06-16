package com.emrmiddleware.conf;


public class ResourcesEnvironment {
    ConfigProperties configProperties = new ConfigProperties();

    public String getDBEnvironment() {
        return  configProperties.getDBEnvironment() ;

    }


    public String getAPIBaseURL() {
        return configProperties.getServer() + ":" + configProperties.getPort()+ "/openmrs/ws/rest/v1/";

    }

    public String getIdGenUrl() {
        return configProperties.getServer() + ":" + configProperties.getPort() + "/openmrs/module/idgen/";

    }


    public String getHostPath() {
return  configProperties.getSwaggerHost() + ":" + configProperties.getPort() + "/EMR-Middleware";


    }

    public String getMMBaseURL() {

       return System.getenv("MMHOST") + "/api/";


    }
}
