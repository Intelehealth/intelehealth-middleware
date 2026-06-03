
package com.emrmiddleware.api.dto;

import com.emrmiddleware.api.dto.AttributeAPIDTO;
import java.util.ArrayList;

public class VisitAPIDTO {
    private String uuid;
    private String startDatetime;
    private String stopDatetime;
    private String visitType;
    ArrayList<AttributeAPIDTO> attributes;
    private String patient;
    private String location;

    public String getStopDatetime() {
        return this.stopDatetime;
    }

    public void setStopDatetime(String stopDatetime) {
        this.stopDatetime = stopDatetime;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStartDatetime() {
        return this.startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getVisitType() {
        return this.visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getPatient() {
        return this.patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<AttributeAPIDTO> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(ArrayList<AttributeAPIDTO> attributes) {
        this.attributes = attributes;
    }
}

