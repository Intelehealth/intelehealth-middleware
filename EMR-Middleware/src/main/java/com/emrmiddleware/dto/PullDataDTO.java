package com.emrmiddleware.dto;

import java.util.ArrayList;
import java.util.Date;

public class PullDataDTO {

	private String pullexecutedtime;//This will be used for sync purpose in android device
	private ArrayList<PatientDTO> patientlist;
	private ArrayList<PatientAttributeTypeDTO> patientAttributeTypeListMaster;
	private ArrayList<PatientAttributeDTO> patientAttributesList;
	private ArrayList<VisitDTO> visitlist;
	private ArrayList<EncounterDTO> encounterlist;
	private ArrayList<ObsDTO> obslist;
	
	public ArrayList<PatientAttributeDTO> getPatientAttributesList() {
		return patientAttributesList;
	}

	public void setPatientAttributesList(ArrayList<PatientAttributeDTO> patientAttributesList) {
		this.patientAttributesList = patientAttributesList;
	}

	public ArrayList<PatientAttributeTypeDTO> getPatientAttributeTypeListMaster() {
		return patientAttributeTypeListMaster;
	}

	public void setPatientAttributeTypeListMaster(ArrayList<PatientAttributeTypeDTO> patientAttributeTypeListMaster) {
		this.patientAttributeTypeListMaster = patientAttributeTypeListMaster;
	}

	public String getPullexecutedtime() {
		return pullexecutedtime;
	}

	public void setPullexecutedtime(String pullexecutedtime) {
		this.pullexecutedtime = pullexecutedtime;
	}

	public ArrayList<ObsDTO> getObslist() {
		return obslist;
	}

	public void setObslist(ArrayList<ObsDTO> obslist) {
		this.obslist = obslist;
	}

	public ArrayList<VisitDTO> getVisitlist() {
		return visitlist;
	}

	public void setVisitlist(ArrayList<VisitDTO> visitlist) {
		this.visitlist = visitlist;
	}

	public ArrayList<PatientDTO> getPatientlist() {
		return patientlist;
	}

	public void setPatientlist(ArrayList<PatientDTO> patientlist) {
		this.patientlist = patientlist;
	}

	public ArrayList<EncounterDTO> getEncounterlist() {
		return encounterlist;
	}

	public void setEncounterlist(ArrayList<EncounterDTO> encounterlist) {
		this.encounterlist = encounterlist;
	}
	
	
}
