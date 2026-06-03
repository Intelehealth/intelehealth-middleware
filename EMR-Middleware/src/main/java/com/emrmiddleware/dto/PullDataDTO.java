
package com.emrmiddleware.dto;

import com.emrmiddleware.dto.CustomAppointmentDTO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.dto.LocationDTO;
import com.emrmiddleware.dto.ObsDTO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.dto.ProviderAttributeDTO;
import com.emrmiddleware.dto.ProviderAttributeTypeDTO;
import com.emrmiddleware.dto.ProviderDTO;
import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitAttributeTypeDTO;
import com.emrmiddleware.dto.VisitDTO;
import java.util.ArrayList;

public class PullDataDTO {
    private String pullexecutedtime;
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

    public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeList() {
        return this.visitAttributeTypeList;
    }

    public void setVisitAttributeTypeList(ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList) {
        this.visitAttributeTypeList = visitAttributeTypeList;
    }

    public ArrayList<VisitAttributeDTO> getVisitAttributeList() {
        return this.visitAttributeList;
    }

    public void setVisitAttributeList(ArrayList<VisitAttributeDTO> visitAttributeList) {
        this.visitAttributeList = visitAttributeList;
    }

    public ArrayList<PersonDTO> getPersonList() {
        return this.personList;
    }

    public void setPersonList(ArrayList<PersonDTO> personList) {
        this.personList = personList;
    }

    public ArrayList<ProviderAttributeDTO> getProviderAttributeList() {
        return this.providerAttributeList;
    }

    public void setProviderAttributeList(ArrayList<ProviderAttributeDTO> providerAttributeList) {
        this.providerAttributeList = providerAttributeList;
    }

    public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeList() {
        return this.providerAttributeTypeList;
    }

    public void setProviderAttributeTypeList(ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList) {
        this.providerAttributeTypeList = providerAttributeTypeList;
    }

    public ArrayList<LocationDTO> getLocationlist() {
        return this.locationlist;
    }

    public void setLocationlist(ArrayList<LocationDTO> locationlist) {
        this.locationlist = locationlist;
    }

    public ArrayList<PatientAttributeDTO> getPatientAttributesList() {
        return this.patientAttributesList;
    }

    public void setPatientAttributesList(ArrayList<PatientAttributeDTO> patientAttributesList) {
        this.patientAttributesList = patientAttributesList;
    }

    public ArrayList<PatientAttributeTypeDTO> getPatientAttributeTypeListMaster() {
        return this.patientAttributeTypeListMaster;
    }

    public void setPatientAttributeTypeListMaster(ArrayList<PatientAttributeTypeDTO> patientAttributeTypeListMaster) {
        this.patientAttributeTypeListMaster = patientAttributeTypeListMaster;
    }

    public String getPullexecutedtime() {
        return this.pullexecutedtime;
    }

    public void setPullexecutedtime(String pullexecutedtime) {
        this.pullexecutedtime = pullexecutedtime;
    }

    public ArrayList<ObsDTO> getObslist() {
        return this.obslist;
    }

    public void setObslist(ArrayList<ObsDTO> obslist) {
        this.obslist = obslist;
    }

    public ArrayList<VisitDTO> getVisitlist() {
        return this.visitlist;
    }

    public void setVisitlist(ArrayList<VisitDTO> visitlist) {
        this.visitlist = visitlist;
    }

    public ArrayList<PatientDTO> getPatientlist() {
        return this.patientlist;
    }

    public void setPatientlist(ArrayList<PatientDTO> patientlist) {
        this.patientlist = patientlist;
    }

    public ArrayList<EncounterDTO> getEncounterlist() {
        return this.encounterlist;
    }

    public void setEncounterlist(ArrayList<EncounterDTO> encounterlist) {
        this.encounterlist = encounterlist;
    }

    public void setProviderlist(ArrayList<ProviderDTO> providerlist) {
        this.providerlist = providerlist;
    }

    public ArrayList<ProviderDTO> getProviderlist() {
        return this.providerlist;
    }

    public void setAppointmentList(ArrayList<CustomAppointmentDTO> updatedAppointments) {
        this.AppointmentList = updatedAppointments;
    }
}

