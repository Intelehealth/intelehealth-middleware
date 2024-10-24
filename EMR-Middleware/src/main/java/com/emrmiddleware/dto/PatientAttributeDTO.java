package com.emrmiddleware.dto;

public class PatientAttributeDTO {

    private String uuid;
    private String person_uuid;
    private String value;
    private String person_attribute_type_uuid;
    private String patientuuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPerson_uuid() {
        return person_uuid;
    }

    public void setPerson_uuid(String person_uuid) {
        this.person_uuid = person_uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPerson_attribute_type_uuid() {
        return person_attribute_type_uuid;
    }

    public void setPerson_attribute_type_uuid(String person_attribute_type_uuid) {
        this.person_attribute_type_uuid = person_attribute_type_uuid;
    }

    public String getPatientuuid() {
        return patientuuid;
    }

    public void setPatientuuid(String patientuuid) {
        this.patientuuid = patientuuid;
    }


}
