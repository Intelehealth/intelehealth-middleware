package com.emrmiddleware.api.dto;

import java.util.ArrayList;

public class PatientAPIDTO {
	private String person;
	private ArrayList<IdentifierAPIDTO> identifiers;
	private ArrayList < AttributeAPIDTO > attributes;
	
	
	public ArrayList<AttributeAPIDTO> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<AttributeAPIDTO> attributes) {
		this.attributes = attributes;
	}
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
