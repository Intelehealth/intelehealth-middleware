/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.conf;

import com.emrmiddleware.conf.ConfigProperties;

public class ResourcesEnvironment {
    ConfigProperties configProperties = new ConfigProperties();

    public String getDBEnvironment() {
        String DBEnvironment = "";
        DBEnvironment = this.configProperties.getDBEnvironment();
        return DBEnvironment;
    }

    public String getAPIBaseURL() {
        String Base_URL = "";
        String host = this.configProperties.getServer() + ":" + this.configProperties.getPort();
        Base_URL = host + "/openmrs/ws/rest/v1/";
        return Base_URL;
    }

    public String getIdGenUrl() {
        String ID_URL = "";
        String host = this.configProperties.getServer() + ":" + this.configProperties.getPort();
        ID_URL = host + "/openmrs/module/idgen/";
        return ID_URL;
    }

    public String getHostPath() {
        String basepath = "";
        String host = this.configProperties.getSwaggerHost() + ":" + this.configProperties.getPort();
        basepath = host + "/EMR-Middleware";
        return basepath;
    }

    public String getMMBaseURL() {
        String Base_URL = "";
        String host = "https://tele.med.kg:3004";
        Base_URL = host + "/api/";
        return Base_URL;
    }
}

