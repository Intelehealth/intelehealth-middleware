package com.emrmiddleware.dto;

public class PatientAttributeDTO {

	private String openmrs_uuid;
	private String person_openmrs_uuid;
	private String value;
	private String openmrs_person_attribute_type_uuid;
	private String openmrs_patientuuid;

	
	public String getOpenmrs_patientuuid() {
		return openmrs_patientuuid;
	}

	public void setOpenmrs_patientuuid(String openmrs_patientuuid) {
		this.openmrs_patientuuid = openmrs_patientuuid;
	}

	public String getOpenmrs_uuid() {
		return openmrs_uuid;
	}

	public void setOpenmrs_uuid(String openmrs_uuid) {
		this.openmrs_uuid = openmrs_uuid;
	}

	public String getPerson_openmrs_uuid() {
		return person_openmrs_uuid;
	}

	public void setPerson_openmrs_uuid(String person_openmrs_uuid) {
		this.person_openmrs_uuid = person_openmrs_uuid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOpenmrs_person_attribute_type_uuid() {
		return openmrs_person_attribute_type_uuid;
	}

	public void setOpenmrs_person_attribute_type_uuid(String openmrs_person_attribute_type_uuid) {
		this.openmrs_person_attribute_type_uuid = openmrs_person_attribute_type_uuid;
	}

}
