/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.api.dto;

import com.emrmiddleware.api.dto.EncounterProvidersAPIDTO;
import com.emrmiddleware.api.dto.ObsAPIDTO;
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
    private ArrayList<ObsAPIDTO> obs = new ArrayList();

    public ArrayList<ObsAPIDTO> getObs() {
        return this.obs;
    }

    public void setObs(ArrayList<ObsAPIDTO> obs) {
        this.obs = obs;
    }

    public String getVoided() {
        return this.voided;
    }

    public void setVoided(String voided) {
        this.voided = voided;
    }

    public String getPatient() {
        return this.patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEncounterDatetime() {
        return this.encounterDatetime;
    }

    public void setEncounterDatetime(String encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }

    public String getEncounterType() {
        return this.encounterType;
    }

    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }

    public String getVisit() {
        return this.visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public ArrayList<EncounterProvidersAPIDTO> getEncounterProviders() {
        return this.encounterProviders;
    }

    public void setEncounterProviders(ArrayList<EncounterProvidersAPIDTO> encounterProviders) {
        this.encounterProviders = encounterProviders;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

