package com.emrmiddleware.dto;

public class ProviderAttributeDTO {
	
	private String openmrs_uuid;
	private String openmrs_provideruuid;
	private String openmrs_attributetypeuuid;
	private String value;
	private int voided;
	public String getOpenmrs_uuid() {
		return openmrs_uuid;
	}
	public void setOpenmrs_uuid(String openmrs_uuid) {
		this.openmrs_uuid = openmrs_uuid;
	}
	public String getOpenmrs_provideruuid() {
		return openmrs_provideruuid;
	}
	public void setOpenmrs_provideruuid(String openmrs_provideruuid) {
		this.openmrs_provideruuid = openmrs_provideruuid;
	}
	public String getOpenmrs_attributetypeuuid() {
		return openmrs_attributetypeuuid;
	}
	public void setOpenmrs_attributetypeuuid(String openmrs_attributetypeuuid) {
		this.openmrs_attributetypeuuid = openmrs_attributetypeuuid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getVoided() {
		return voided;
	}
	public void setVoided(int voided) {
		this.voided = voided;
	}
	
	

}
