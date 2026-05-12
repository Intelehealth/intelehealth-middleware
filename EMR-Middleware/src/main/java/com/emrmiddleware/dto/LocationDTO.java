/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.dto;

public class LocationDTO {
    private String name;
    private String locationuuid;
    private int retired;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationuuid() {
        return this.locationuuid;
    }

    public void setLocationuuid(String locationuuid) {
        this.locationuuid = locationuuid;
    }

    public int getRetired() {
        return this.retired;
    }

    public void setRetired(int retired) {
        this.retired = retired;
    }
}

