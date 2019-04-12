package com.emrmiddleware.dto;

public class ObsDTO {
	
	private String uuid;
	private String encounteruuid;
	private String conceptuuid;
	private String value;
	private int creator;
	private int voided;
	
	
	public int getVoided() {
		return voided;
	}
	public void setVoided(int voided) {
		this.voided = voided;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getEncounteruuid() {
		return encounteruuid;
	}
	public void setEncounteruuid(String encounteruuid) {
		this.encounteruuid = encounteruuid;
	}
	
	public String getConceptuuid() {
		return conceptuuid;
	}
	public void setConceptuuid(String conceptuuid) {
		this.conceptuuid = conceptuuid;
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
