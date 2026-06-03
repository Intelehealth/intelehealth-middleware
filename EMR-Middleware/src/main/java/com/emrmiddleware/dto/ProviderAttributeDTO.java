
package com.emrmiddleware.dto;

public class ProviderAttributeDTO {
    private String uuid;
    private String provideruuid;
    private String attributetypeuuid;
    private String value;
    private int voided;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProvideruuid() {
        return this.provideruuid;
    }

    public void setProvideruuid(String provideruuid) {
        this.provideruuid = provideruuid;
    }

    public String getAttributetypeuuid() {
        return this.attributetypeuuid;
    }

    public void setAttributetypeuuid(String attributetypeuuid) {
        this.attributetypeuuid = attributetypeuuid;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getVoided() {
        return this.voided;
    }

    public void setVoided(int voided) {
        this.voided = voided;
    }
}

