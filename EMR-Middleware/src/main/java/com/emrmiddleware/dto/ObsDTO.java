package com.emrmiddleware.dto;

public class ObsDTO {
	
	private String openmrs_obsuuid;
	private String openmrs_encounteruuid;
	private int conceptid;
	private String value;
	private int creator;
	
	public String getOpenmrs_obsuuid() {
		return openmrs_obsuuid;
	}
	public void setOpenmrs_obsuuid(String openmrs_obsuuid) {
		this.openmrs_obsuuid = openmrs_obsuuid;
	}
	public String getOpenmrs_encounteruuid() {
		return openmrs_encounteruuid;
	}
	public void setOpenmrs_encounteruuid(String openmrs_encounteruuid) {
		this.openmrs_encounteruuid = openmrs_encounteruuid;
	}
	public int getConceptid() {
		return conceptid;
	}
	public void setConceptid(int conceptid) {
		this.conceptid = conceptid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	

}
