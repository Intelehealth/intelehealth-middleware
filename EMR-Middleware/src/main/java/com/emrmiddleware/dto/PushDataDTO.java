
package com.emrmiddleware.dto;

import com.emrmiddleware.api.dto.AppointmentDTO;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.dto.ProviderDTO;
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
        return this.encounters;
    }

    public void setEncounters(ArrayList<EncounterAPIDTO> encounters) {
        this.encounters = encounters;
    }

    public ArrayList<VisitAPIDTO> getVisits() {
        return this.visits;
    }

    public void setVisits(ArrayList<VisitAPIDTO> visits) {
        this.visits = visits;
    }

    public ArrayList<PersonAPIDTO> getPersons() {
        return this.persons;
    }

    public void setPersons(ArrayList<PersonAPIDTO> persons) {
        this.persons = persons;
    }

    public ArrayList<PatientAPIDTO> getPatients() {
        return this.patients;
    }

    public void setPatients(ArrayList<PatientAPIDTO> patients) {
        this.patients = patients;
    }

    public boolean isSyncd() {
        return this.syncd;
    }

    public void setSyncd(boolean syncd) {
        this.syncd = syncd;
    }

    public ArrayList<ProviderDTO> getProviders() {
        return this.providers;
    }

    public void setProviders(ArrayList<ProviderDTO> providers) {
        this.providers = providers;
    }

    public ArrayList<AppointmentDTO> getAppointments() {
        return this.appointments;
    }

    public void setAppointments(ArrayList<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }
}

