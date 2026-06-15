package com.emrmiddleware.dto;

public class PatientSyncLogDTO {

  private Integer attempt_number;
  private String last_try;
  private String status;

  public Integer getAttempt_number() {
    return attempt_number;
  }

  public void setAttempt_number(Integer attempt_number) {
    this.attempt_number = attempt_number;
  }

  public String getLast_try() {
    return last_try;
  }

  public void setLast_try(String last_try) {
    this.last_try = last_try;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
