package com.emrmiddleware.dto;

public class EncounterDTO {
	private String uuid;
	private String visituuid;
	private String encounter_type_uuid;
	private boolean syncd=true;
	private int voided;
	
	
	public boolean isSyncd() {
		return syncd;
	}
	public void setSyncd(boolean syncd) {
		this.syncd = syncd;
	}
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
	public String getEncounter_type_uuid() {
		return encounter_type_uuid;
	}
	public void setEncounter_type_uuid(String encounter_type_uuid) {
		this.encounter_type_uuid = encounter_type_uuid;
	}
	public int getVoided() {
		return voided;
	}
	public void setVoided(int voided) {
		this.voided = voided;
	}
	
	
}
