package com.emrmiddleware.dto;

import java.util.Objects;

public class IdentifierDTO {

    private String identifier;
    private String identifiertype;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifiertype() {
        return identifiertype;
    }

    public void setIdentifiertype(String identifiertype) {
        this.identifiertype = identifiertype;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IdentifierDTO that = (IdentifierDTO) o;
        return Objects.equals(identifier, that.identifier) && Objects.equals(identifiertype, that.identifiertype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, identifiertype);
    }

    @Override
    public String toString() {
        return "IdentifierDTO{" +
                "identifier='" + identifier + '\'' +
                ", identifiertype='" + identifiertype + '\'' +
                '}';
    }
}
