package com.emrmiddleware.dto;

public class ObsDTO {
	
	private String uuid;
	private String encounteruuid;
	private int conceptid;
	private String value;
	private int creator;
	
	
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