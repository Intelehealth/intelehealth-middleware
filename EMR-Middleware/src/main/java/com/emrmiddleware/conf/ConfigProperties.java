package com.emrmiddleware.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigProperties {
    private final Logger logger = LoggerFactory.getLogger(ConfigProperties.class);
    String result = "";
    InputStream inputStream;
    private String mybatisDBEnvironment;
    private String serverhost;
    private String swaggerhost;
    private String port;
    private String mindmapPort;
    private String mindmapHost;

    public ConfigProperties() {
        try {
            getPropValues();
        } catch (IOException e) {

            logger.error("Error in Properties File read : {}", e.getMessage());
        }
    }

    public String getMindmapHost() {
        return mindmapHost;
    }

    public void setMindmapHost(String mindmapHost) {
        this.mindmapHost = mindmapHost;
    }

    public String getMindmapPort() {
        return mindmapPort;
    }

    public void setMindmapPort(String mindmapPort) {
        this.mindmapPort = mindmapPort;
    }

    public void getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            mybatisDBEnvironment = prop.getProperty("MybatisEnvironmentId");
            serverhost = prop.getProperty("serverhost");
            swaggerhost = prop.getProperty("swaggerhost");
            port = prop.getProperty("port");
            mindmapPort = prop.getProperty("mindmapPort");
            mindmapHost = prop.getProperty("mindmapHost");

        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
        } finally {
            if (inputStream != null)
                inputStream.close();
        }

    }


    public String getDBEnvironment() {
        return mybatisDBEnvironment;
    }

    public String getServer() {
        return serverhost;
    }

    public String getSwaggerHost() {
        return swaggerhost;
    }

    public String getPort() {
        return port;
    }

}
