/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.dto;

public class PersonDTO {
    private String uuid;
    private boolean syncd = true;

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
}

