package com.emrmiddleware.dto;

public class EncounterDTO {
	private String openmrs_encounteruuid;
	private String openmrs_visituuid;
	private String encounter_type;
	public String getOpenmrs_encounteruuid() {
		return openmrs_encounteruuid;
	}
	public void setOpenmrs_encounteruuid(String openmrs_encounteruuid) {
		this.openmrs_encounteruuid = openmrs_encounteruuid;
	}
	public String getOpenmrs_visituuid() {
		return openmrs_visituuid;
	}
	public void setOpenmrs_visituuid(String openmrs_visituuid) {
		this.openmrs_visituuid = openmrs_visituuid;
	}
	public String getEncounter_type() {
		return encounter_type;
	}
	public void setEncounter_type(String encounter_type) {
		this.encounter_type = encounter_type;
	}
	
}
