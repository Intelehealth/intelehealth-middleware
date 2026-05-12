/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.api.dto;

import com.emrmiddleware.api.dto.IdentifierAPIDTO;
import java.util.ArrayList;

public class PatientAPIDTO {
    private String person;
    private ArrayList<IdentifierAPIDTO> identifiers;

    public String getPerson() {
        return this.person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public ArrayList<IdentifierAPIDTO> getIdentifiers() {
        return this.identifiers;
    }

    public void setIdentifiers(ArrayList<IdentifierAPIDTO> identifiers) {
        this.identifiers = identifiers;
    }
}

