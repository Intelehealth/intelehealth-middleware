package com.emrmiddleware.dto;

import com.emrmiddleware.api.dto.*;

import java.util.ArrayList;

public class PushDataDTO {

    private ArrayList<PatientAPIDTO> patients;
    private ArrayList<PersonAPIDTO> persons;
    private ArrayList<VisitAPIDTO> visits;
    private ArrayList<EncounterAPIDTO> encounters;
    private boolean syncd = true;
    private ArrayList<ProviderDTO> providers;

    private ArrayList<AppointmentDTO> appointments;

    public ArrayList<EncounterAPIDTO> getEncounters() {
        return encounters;
    }

    public void setEncounters(ArrayList<EncounterAPIDTO> encounters) {
        this.encounters = encounters;
    }

    public ArrayList<VisitAPIDTO> getVisits() {
        return visits;
    }

    public void setVisits(ArrayList<VisitAPIDTO> visits) {
        this.visits = visits;
    }

    public ArrayList<PersonAPIDTO> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<PersonAPIDTO> persons) {
        this.persons = persons;
    }


    public ArrayList<PatientAPIDTO> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<PatientAPIDTO> patients) {
        this.patients = patients;
    }

    public boolean isSyncd() {
        return syncd;
    }

    public void setSyncd(boolean syncd) {
        this.syncd = syncd;
    }


    public ArrayList<ProviderDTO> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<ProviderDTO> providers) {
        this.providers = providers;
    }


    public ArrayList<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }
}
