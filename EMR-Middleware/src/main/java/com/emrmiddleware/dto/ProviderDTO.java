package com.emrmiddleware.dto;

public class ProviderDTO {
	
	private String openmrs_provideruuid;
	private String identifier;
	private String given_name;
	private String family_name;
	private int voided;
	public String getOpenmrs_provideruuid() {
		return openmrs_provideruuid;
	}
	public void setOpenmrs_provideruuid(String openmrs_provideruuid) {
		this.openmrs_provideruuid = openmrs_provideruuid;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	public int getVoided() {
		return voided;
	}
	public void setVoided(int voided) {
		this.voided = voided;
	}
	

}
