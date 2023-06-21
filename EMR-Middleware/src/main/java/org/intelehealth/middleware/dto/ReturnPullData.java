package org.intelehealth.middleware.dto;

import java.util.ArrayList;

public class ReturnPullData {

	ArrayList<VisitDTO> visitlist;

	ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList;
	
	ArrayList<VisitAttributeDTO> visitAttributeList;
	
	ArrayList<EncounterDTO> encounterlist;
	
	ArrayList<PatientDTO> patientlist;
	
	ArrayList<PatientAttributeTypeDTO> patientAttributeTypeListMaster;
	
	ArrayList<PatientAttributeDTO> patientAttributesList;
	
	ArrayList<ObsDTO> obslist;
	
	ArrayList<LocationDTO> locationlist;
	
	ArrayList<ProviderDTO> providerlist;
	
	 ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList;

	 ArrayList<ProviderAttributeDTO> providerAttributeList;
	 
	 String pullexecutedtime;
	 
	 ArrayList<PersonDTO> personList;
	 
	 private ArrayList<CustomAppointmentDTO> AppointmentList;

	public ArrayList<CustomAppointmentDTO> getAppointmentList() {
		return AppointmentList;
	}

	public void setAppointmentList(ArrayList<CustomAppointmentDTO> appointmentList) {
		AppointmentList = appointmentList;
	}

	public String getPullexecutedtime() {
		return pullexecutedtime;
	}

	public void setPullexecutedtime(String pullexecutedtime) {
		this.pullexecutedtime = pullexecutedtime;
	}

	public ArrayList<ProviderAttributeDTO> getProviderAttributeList() {
		return providerAttributeList;
	}

	public void setProviderAttributeList(ArrayList<ProviderAttributeDTO> providerAttributeList) {
		this.providerAttributeList = providerAttributeList;
	}

	public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeList() {
		return providerAttributeTypeList;
	}

	public void setProviderAttributeTypeList(ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList) {
		this.providerAttributeTypeList = providerAttributeTypeList;
	}

	public ArrayList<ProviderDTO> getProviderlist() {
		return providerlist;
	}

	public void setProviderlist(ArrayList<ProviderDTO> providerlist) {
		this.providerlist = providerlist;
	}

	public ArrayList<LocationDTO> getLocationlist() {
		return locationlist;
	}

	public void setLocationlist(ArrayList<LocationDTO> locationlist) {
		this.locationlist = locationlist;
	}

	public ArrayList<ObsDTO> getObslist() {
		return obslist;
	}

	public void setObslist(ArrayList<ObsDTO> obslist) {
		this.obslist = obslist;
	}

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

	public ArrayList<VisitAttributeDTO> getVisitAttributeList() {
		return visitAttributeList;
	}

	public void setVisitAttributeList(ArrayList<VisitAttributeDTO> visitAttributeList) {
		this.visitAttributeList = visitAttributeList;
	}

	public ArrayList<VisitDTO> getVisitlist() {
		return visitlist;
	}

	public void setVisitlist(ArrayList<VisitDTO> visitlist) {
		this.visitlist = visitlist;
	}

	public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeList() {
		return visitAttributeTypeList;
	}

	public void setVisitAttributeTypeList(ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList) {
		this.visitAttributeTypeList = visitAttributeTypeList;
	}

	
	public ArrayList<PersonDTO> getPersonList() {
		return personList;
	}

	public void setPersonList(ArrayList<PersonDTO> personList) {
		this.personList = personList;
	}
	 

	 
	 
}
