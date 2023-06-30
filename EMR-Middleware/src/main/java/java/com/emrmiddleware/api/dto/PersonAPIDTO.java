package com.emrmiddleware.api.dto;

import java.util.ArrayList;

public class PersonAPIDTO {

	private String uuid;
	private ArrayList<NameAPIDTO> names = new ArrayList<NameAPIDTO>();
	private String birthdate;
	ArrayList < AttributeAPIDTO > attributes = new ArrayList < AttributeAPIDTO > ();
	private ArrayList<AddressAPIDTO> addresses = new ArrayList<AddressAPIDTO>();
	private String gender;
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public ArrayList<NameAPIDTO> getNames() {
		return names;
	}
	public void setNames(ArrayList<NameAPIDTO> names) {
		this.names = names;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	public ArrayList<AttributeAPIDTO> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<AttributeAPIDTO> attributes) {
		this.attributes = attributes;
	}
	public ArrayList<AddressAPIDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<AddressAPIDTO> addresses) {
		this.addresses = addresses;
	}
	
	public void addAddresses(AddressAPIDTO addressdto){
		this.addresses.add(addressdto);
			
	}
	public void addName(NameAPIDTO namedto){
		this.names.add(namedto);
	}
}
