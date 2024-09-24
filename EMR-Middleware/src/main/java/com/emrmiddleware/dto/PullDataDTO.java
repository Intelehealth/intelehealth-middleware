package com.emrmiddleware.dto;

import com.emrmiddleware.api.dto.AppointmentDTO;
import com.emrmiddleware.authentication.CustomApplication;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class PullDataDTO {

    JsonObject propertyContents; // Added for configurability changes Ref: WEBAPP-74
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
    private int pageNo;
    private int totalCount;
    private ArrayList<ConceptAttributeTypeDTO> conceptAttributeTypeList;
    private ArrayList<ConceptAttributeDTO> conceptAttributeList;

    public JsonObject getPropertyContents() {
        return propertyContents;
    }

    public void setPropertyContents(JsonObject propertyContents) {
        this.propertyContents = propertyContents;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

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

    //public ArrayList<ProviderDTO> getProviderlist() {
    //	return providerlist;
    //}

    //public void setProviderlist(ArrayList<ProviderDTO> providerlist) {
    //	this.providerlist = providerlist;
    //}

    public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeList() {
        return providerAttributeTypeList;
    }

    public void setProviderAttributeTypeList(ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList) {
        this.providerAttributeTypeList = providerAttributeTypeList;
    }

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

    public ArrayList<ProviderDTO> getProviderlist() {
        return providerlist;
    }

    public void setProviderlist(ArrayList<ProviderDTO> providerlist) {
        this.providerlist = providerlist;
    }

    public void setAppointmentList(ArrayList<CustomAppointmentDTO> updatedAppointments) {
        this.AppointmentList = updatedAppointments;
    }

    public ArrayList<ConceptAttributeTypeDTO> getConceptAttributeTypeList() {
        return conceptAttributeTypeList;
    }

    public void setConceptAttributeTypeList(ArrayList<ConceptAttributeTypeDTO> conceptAttributeTypeList) {
        this.conceptAttributeTypeList = conceptAttributeTypeList;
    }

    public ArrayList<ConceptAttributeDTO> getConceptAttributeList() {
        return conceptAttributeList;
    }

    public void setConceptAttributeList(ArrayList<ConceptAttributeDTO> conceptAttributeList) {
        this.conceptAttributeList = conceptAttributeList;
    }

}
