package com.emrmiddleware.dto;

public class EncounterDTO {
	private String uuid;
	private String visituuid;
	private String encounter_type;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getVisituuid() {
		return visituuid;
	}
	public void setVisituuid(String visituuid) {
		this.visituuid = visituuid;
	}
	public String getEncounter_type() {
		return encounter_type;
	}
	public void setEncounter_type(String encounter_type) {
		this.encounter_type = encounter_type;
	}
	
}
