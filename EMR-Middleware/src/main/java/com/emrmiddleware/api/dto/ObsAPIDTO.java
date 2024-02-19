package com.emrmiddleware.api.dto;

import com.google.gson.annotations.SerializedName;

public class ObsAPIDTO {

    public String comment;
    private String uuid;
    private String concept;
    private String value;
    private String obsDateTime;

    // Adding comment field for Ezazi obs.comment
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getObsDateTime() {
        return obsDateTime;
    }

    public void setObsDateTime(String obsDateTime) {
        this.obsDateTime = obsDateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}