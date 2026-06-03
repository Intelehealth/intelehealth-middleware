
package com.emrmiddleware.api.dto;

public class IdentifierAPIDTO {
    private String identifier;
    private String identifierType;
    private String location;
    private String preferred;

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierType() {
        return this.identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPreferred() {
        return this.preferred;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }
}

