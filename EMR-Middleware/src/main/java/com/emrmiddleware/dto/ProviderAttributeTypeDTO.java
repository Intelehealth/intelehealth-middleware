package com.emrmiddleware.dto;

public class ProviderAttributeTypeDTO {
	
	private String openmrs_uuid;
	private String name;
	private int retired;
	public String getOpenmrs_providerAttributeTypeuuid() {
		return openmrs_uuid;
	}
	public void setOpenmrs_providerAttributeTypeuuid(String openmrs_providerAttributeTypeuuid) {
		this.openmrs_uuid = openmrs_providerAttributeTypeuuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVoided() {
		return retired;
	}
	public void setVoided(int voided) {
		this.retired = voided;
	}
	
	

}
