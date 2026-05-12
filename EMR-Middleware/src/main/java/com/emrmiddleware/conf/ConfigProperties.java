/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
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
    private String mindmapPort;

    public String getMindmapPort() {
        return this.mindmapPort;
    }

    public void setMindmapPort(String mindmapPort) {
        this.mindmapPort = mindmapPort;
    }

    public ConfigProperties() {
        try {
            this.getPropValues();
        }
        catch (IOException e) {
            this.logger.error("Error in Properties File read : " + e.getMessage());
        }
    }

    public void getPropValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";
            this.inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
            if (this.inputStream == null) {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            prop.load(this.inputStream);
            this.mybatisDBEnvironment = prop.getProperty("MybatisEnvironmentId");
            this.serverhost = prop.getProperty("serverhost");
            this.swaggerhost = prop.getProperty("swaggerhost");
            this.port = prop.getProperty("port");
            this.mindmapPort = prop.getProperty("mindmapPort");
        }
        catch (Exception e) {
            this.logger.error("Exception: " + e);
        }
        finally {
            this.inputStream.close();
        }
    }

    public String getDBEnvironment() {
        return this.mybatisDBEnvironment;
    }

    public String getServer() {
        return this.serverhost;
    }

    public String getSwaggerHost() {
        return this.swaggerhost;
    }

    public String getPort() {
        return this.port;
    }
}

