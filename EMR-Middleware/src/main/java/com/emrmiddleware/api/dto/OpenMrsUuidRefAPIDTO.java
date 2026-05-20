package com.emrmiddleware.api.dto;

public class OpenMrsUuidRefAPIDTO {
  private String uuid;

  public OpenMrsUuidRefAPIDTO() {}

  public OpenMrsUuidRefAPIDTO(String uuid) {
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }
}
