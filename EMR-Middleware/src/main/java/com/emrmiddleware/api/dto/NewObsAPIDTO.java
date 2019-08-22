package com.emrmiddleware.api.dto;

import com.google.gson.annotations.SerializedName;

public class NewObsAPIDTO {
	
	private String concept;
	private String value;
	private String encounter;
	private String obsDatetime;
	private String person;
	
	public String getEncounter() {
		return encounter;
	}
	public void setEncounter(String encounter) {
		this.encounter = encounter;
	}
	public String getObsDatetime() {
		return obsDatetime;
	}
	public void setObsDatetime(String obsDatetime) {
		this.obsDatetime = obsDatetime;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
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
