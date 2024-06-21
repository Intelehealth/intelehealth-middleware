package com.emrmiddleware.dto;

import com.emrmiddleware.api.dto.AppointmentDTO;
import com.emrmiddleware.authentication.CustomApplication;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class PullDataDTO {

	private String pullexecutedtime;//This will be used for sync purpose in android device
	private ArrayList<PersonDTO> personList;
	private ArrayList<PatientDTO> patientlist;
	private ArrayList<PatientAttributeTypeDTO> patientAttributeTypeListMaster;
	private ArrayList<PatientAttributeDTO> patientAttributesList;
	private ArrayList<VisitDTO> visitlist;
	private ArrayList<EncounterDTO> encounterlist;
	private ArrayList<ObsDTO> obslist;
	private ArrayList<LocationDTO> locationlist;
	private ArrayList<ProviderDTO> providerlist;
	private ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList;
	private ArrayList<ProviderAttributeDTO> providerAttributeList;
	private ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList;
	private ArrayList<VisitAttributeDTO> visitAttributeList;
	private ArrayList<CustomAppointmentDTO> AppointmentList;

	public JsonObject getPropertyContents() {
		return propertyContents;
	}

	public void setPropertyContents(JsonObject propertyContents) {
		this.propertyContents = propertyContents;
	}

	JsonObject propertyContents; // Added for configurability changes Ref: WEBAPP-74

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	private int pageNo;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	private int totalCount;


	public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeList() {
		return visitAttributeTypeList;
	}

	public void setVisitAttributeTypeList(ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList) {
		this.visitAttributeTypeList = visitAttributeTypeList;
	}

	public ArrayList<VisitAttributeDTO> getVisitAttributeList() {
		return visitAttributeList;
	}

	public void setVisitAttributeList(ArrayList<VisitAttributeDTO> visitAttributeList) {
		this.visitAttributeList = visitAttributeList;
	}

	public ArrayList<PersonDTO> getPersonList() {
		return personList;
	}

	public void setPersonList(ArrayList<PersonDTO> personList) {
		this.personList = personList;
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

	//public ArrayList<ProviderDTO> getProviderlist() {
	//	return providerlist;
	//}

	//public void setProviderlist(ArrayList<ProviderDTO> providerlist) {
	//	this.providerlist = providerlist;
	//}

	public ArrayList<LocationDTO> getLocationlist() {
		return locationlist;
	}

	public void setLocationlist(ArrayList<LocationDTO> locationlist) {
		this.locationlist = locationlist;
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


	public void setProviderlist(ArrayList<ProviderDTO> providerlist) {
		this.providerlist = providerlist;
	}

	public ArrayList<ProviderDTO> getProviderlist() {
		return providerlist;
	}


	public void setAppointmentList(ArrayList<CustomAppointmentDTO> updatedAppointments) {
		this.AppointmentList = updatedAppointments;
	}
}
