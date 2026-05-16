package com.emrmiddleware.api.dto;

public class SourcePatientIdentifierAPIDTO {

  private String patientUuid;
  private String identifierValue;

  public String getPatientUuid() {
    return patientUuid;
  }

  public void setPatientUuid(String patientUuid) {
    this.patientUuid = patientUuid;
  }

  public String getIdentifierValue() {
    return identifierValue;
  }

  public void setIdentifierValue(String identifierValue) {
    this.identifierValue = identifierValue;
  }
}
