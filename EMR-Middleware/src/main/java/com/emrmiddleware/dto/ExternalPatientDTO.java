package com.emrmiddleware.dto;

public class ExternalPatientDTO {
    String uuid;
    String openmrsid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpenmrsid() {
        return openmrsid;
    }

    public void setOpenmrsid(String openmrsid) {
        this.openmrsid = openmrsid;
    }

    @Override
    public String toString() {
        return "ExternalPatientDTO{" +
                "uuid='" + uuid + '\'' +
                ", Openmrs id='" + openmrsid + '\'' +
                '}';
    }

    public ExternalPatientDTO() {
        this.uuid="NA";
        this.openmrsid="NA";
    }
}
