package com.emrmiddleware.api.dto;

public class VisitAPIDTO {
	
	private String uuid;
	private String startDatetime;
	private String stopDatetime;
	private String visitType;
	private String patient;
	private String location;
	
	
	public String getStopDatetime() {
		return stopDatetime;
	}
	public void setStopDatetime(String stopDatetime) {
		this.stopDatetime = stopDatetime;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getStartDatetime() {
		return startDatetime;
	}
	public void setStartDatetime(String startDatetime) {
		this.startDatetime = startDatetime;
	}
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
