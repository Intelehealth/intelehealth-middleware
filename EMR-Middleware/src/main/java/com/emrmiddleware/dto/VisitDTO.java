/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.dto;

import java.util.Date;

public class VisitDTO {
    private String patientuuid;
    private String uuid;
    private String visit_type_uuid;
    private Date startdate;
    private Date enddate;
    private String locationuuid;
    private String creator_uuid;
    private boolean syncd = true;
    private int voided;

    public int getVoided() {
        return this.voided;
    }

    public void setVoided(int voided) {
        this.voided = voided;
    }

    public boolean isSyncd() {
        return this.syncd;
    }

    public void setSyncd(boolean syncd) {
        this.syncd = syncd;
    }

    public String getVisit_type_uuid() {
        return this.visit_type_uuid;
    }

    public void setVisit_type_uuid(String visit_type_uuid) {
        this.visit_type_uuid = visit_type_uuid;
    }

    public String getPatientuuid() {
        return this.patientuuid;
    }

    public void setPatientuuid(String patientuuid) {
        this.patientuuid = patientuuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getStartdate() {
        return this.startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return this.enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getLocationuuid() {
        return this.locationuuid;
    }

    public void setLocationuuid(String locationuuid) {
        this.locationuuid = locationuuid;
    }

    public String getCreator_uuid() {
        return this.creator_uuid;
    }

    public void setCreator_uuid(String creator_uuid) {
        this.creator_uuid = creator_uuid;
    }
}

