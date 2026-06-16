package com.emrmiddleware.api.dto;

import java.util.ArrayList;
import java.util.Arrays;

public class PatientAPIDTO {
    private String person;
    private ArrayList<IdentifierAPIDTO> identifiers;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public ArrayList<IdentifierAPIDTO> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(ArrayList<IdentifierAPIDTO> identifiers) {
        this.identifiers = identifiers;
    }


}
