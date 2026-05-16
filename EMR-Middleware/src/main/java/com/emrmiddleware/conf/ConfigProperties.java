package com.emrmiddleware.conf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigProperties {
  private final Logger logger = LoggerFactory.getLogger(ConfigProperties.class);
  String result = "";
  InputStream inputStream;
  private String mybatisDBEnvironment;
  private String serverhost;
  private String swaggerhost;
  private String port;
  private String mindmapPort;
  private String openMrsIdentifierTypeName;
  private String mpiIdentifierTypeName;

  private static final String DEFAULT_OPENMRS_IDENTIFIER_TYPE = "OpenMRS ID";
  private static final String DEFAULT_MPI_IDENTIFIER_TYPE = "MPI";

  public ConfigProperties() {
    try {
      getPropValues();
    } catch (IOException e) {
      logger.error("Error in Properties File read : {} ", e.getMessage());
    }
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
        throw new FileNotFoundException(
            "property file '" + propFileName + "' not found in the classpath");
      }

      mybatisDBEnvironment = prop.getProperty("MybatisEnvironmentId");
      serverhost = prop.getProperty("serverhost");
      swaggerhost = prop.getProperty("swaggerhost");
      port = prop.getProperty("port");
      mindmapPort = prop.getProperty("mindmapPort");
      openMrsIdentifierTypeName =
          defaultIfBlank(
              prop.getProperty("patient.identifier.type.openmrs"), DEFAULT_OPENMRS_IDENTIFIER_TYPE);
      mpiIdentifierTypeName =
          defaultIfBlank(
              prop.getProperty("patient.identifier.type.mpi"), DEFAULT_MPI_IDENTIFIER_TYPE);
    } catch (Exception e) {
      logger.error("Exception: {}", e.getMessage());
    } finally {
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

  public String getOpenMrsIdentifierTypeName() {
    return defaultIfBlank(openMrsIdentifierTypeName, DEFAULT_OPENMRS_IDENTIFIER_TYPE);
  }

  public String getMpiIdentifierTypeName() {
    return defaultIfBlank(mpiIdentifierTypeName, DEFAULT_MPI_IDENTIFIER_TYPE);
  }

  private static String defaultIfBlank(String value, String defaultValue) {
    if (value == null || value.trim().isEmpty()) {
      return defaultValue;
    }
    return value.trim();
  }
}
