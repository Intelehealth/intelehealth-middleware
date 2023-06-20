package org.intelehealth.middleware.api.dto;

//import com.google.gson.annotations.SerializedName;

public class ObsAPIDTO {

    private String uuid;
    private String concept;


    private String value;
// Adding comment field for Ezazi obs.comment
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String comment;


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