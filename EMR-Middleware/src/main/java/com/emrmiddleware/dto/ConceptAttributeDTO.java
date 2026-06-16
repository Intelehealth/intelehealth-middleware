package com.emrmiddleware.dto;

public class ConceptAttributeDTO {
    private String uuid;
    private String concept_uuid;
    private String value;
    private String concept_attribute_type_uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConcept_uuid() {
        return concept_uuid;
    }

    public void setConcept_uuid(String concept_uuid) {
        this.concept_uuid = concept_uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConcept_attribute_type_uuid() {
        return concept_attribute_type_uuid;
    }

    public void setConcept_attribute_type_uuid(String concept_attribute_type_uuid) {
        this.concept_attribute_type_uuid = concept_attribute_type_uuid;
    }
}
