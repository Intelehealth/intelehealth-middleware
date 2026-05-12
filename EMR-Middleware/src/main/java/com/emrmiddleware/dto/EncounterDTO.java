/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.dto;

public class EncounterDTO {
    private String uuid;
    private String visituuid;
    private String encounter_type_uuid;
    private String provider_uuid;
    private boolean syncd = true;
    private int voided;
    private String encounter_time;

    public String getEncounter_time() {
        return this.encounter_time;
    }

    public void setEncounter_time(String encounter_time) {
        this.encounter_time = encounter_time;
    }

    public String getProvider_uuid() {
        return this.provider_uuid;
    }

    public void setProvider_uuid(String provider_uuid) {
        this.provider_uuid = provider_uuid;
    }

    public boolean isSyncd() {
        return this.syncd;
    }

    public void setSyncd(boolean syncd) {
        this.syncd = syncd;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVisituuid() {
        return this.visituuid;
    }

    public void setVisituuid(String visituuid) {
        this.visituuid = visituuid;
    }

    public String getEncounter_type_uuid() {
        return this.encounter_type_uuid;
    }

    public void setEncounter_type_uuid(String encounter_type_uuid) {
        this.encounter_type_uuid = encounter_type_uuid;
    }

    public int getVoided() {
        return this.voided;
    }

    public void setVoided(int voided) {
        this.voided = voided;
    }
}

