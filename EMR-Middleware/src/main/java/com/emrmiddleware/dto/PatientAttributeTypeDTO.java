package com.emrmiddleware.dto;

public class PatientAttributeTypeDTO {
	private String openmrs_uuid;
    private String name;
	
	public String getOpenmrs_uuid() {
		return openmrs_uuid;
	}
	public void setOpenmrs_uuid(String openmrs_uuid) {
		this.openmrs_uuid = openmrs_uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
