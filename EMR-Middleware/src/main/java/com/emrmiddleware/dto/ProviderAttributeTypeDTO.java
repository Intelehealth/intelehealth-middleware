/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.dto;

public class ProviderAttributeTypeDTO {
    private String uuid;
    private String name;
    private int retired;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getRetired() {
        return this.retired;
    }

    public void setRetired(int retired) {
        this.retired = retired;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoided() {
        return this.retired;
    }

    public void setVoided(int voided) {
        this.retired = voided;
    }
}

