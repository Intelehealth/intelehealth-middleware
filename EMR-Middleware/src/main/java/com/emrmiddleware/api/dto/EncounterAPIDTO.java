package com.emrmiddleware.api.dto;

import java.util.ArrayList;

public class EncounterAPIDTO {
	
	private String uuid;
	
	private String encounterDatetime;
	private String encounterType;
	private String visit;
	private String patient;
	
	private ArrayList<EncounterProvidersAPIDTO> encounterProviders;
	private String location;
	private String voided;
	
	
	public String getVoided() {
		return voided;
	}
	public void setVoided(String voided) {
		this.voided = voided;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getEncounterDatetime() {
		return encounterDatetime;
	}
	public void setEncounterDatetime(String encounterDatetime) {
		this.encounterDatetime = encounterDatetime;
	}
	public String getEncounterType() {
		return encounterType;
	}
	public void setEncounterType(String encounterType) {
		this.encounterType = encounterType;
	}
	public String getVisit() {
		return visit;
	}
	public void setVisit(String visit) {
		this.visit = visit;
	}
	public ArrayList<EncounterProvidersAPIDTO> getEncounterProviders() {
		return encounterProviders;
	}
	public void setEncounterProviders(ArrayList<EncounterProvidersAPIDTO> encounterProviders) {
		this.encounterProviders = encounterProviders;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
