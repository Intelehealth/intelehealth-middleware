package com.emrmiddleware.api.dto;

public class IdentifierUpdateAPIDTO {
  private String identifier;
  private OpenMrsUuidRefAPIDTO identifierType;
  private OpenMrsUuidRefAPIDTO location;
  private Boolean preferred;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public OpenMrsUuidRefAPIDTO getIdentifierType() {
    return identifierType;
  }

  public void setIdentifierType(OpenMrsUuidRefAPIDTO identifierType) {
    this.identifierType = identifierType;
  }

  public OpenMrsUuidRefAPIDTO getLocation() {
    return location;
  }

  public void setLocation(OpenMrsUuidRefAPIDTO location) {
    this.location = location;
  }

  public Boolean getPreferred() {
    return preferred;
  }

  public void setPreferred(Boolean preferred) {
    this.preferred = preferred;
  }
}
