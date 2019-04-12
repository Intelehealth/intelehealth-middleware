package com.emrmiddleware.api.dto;

import com.google.gson.annotations.SerializedName;

public class ObsAPIDTO<T> {
	
	private String uuid;
	private String concept;

	
	private String value;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
