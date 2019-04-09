package com.emrmiddleware.api.dto;

import java.util.ArrayList;

public class PersonDTO {

	private String uuid;
	private ArrayList<NameDTO> names = new ArrayList<NameDTO>();
	private String birthdate;
	ArrayList < Object > attributes = new ArrayList < Object > ();
	private ArrayList<AddressDTO> addresses = new ArrayList<AddressDTO>();
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
	public ArrayList<NameDTO> getNames() {
		return names;
	}
	public void setNames(ArrayList<NameDTO> names) {
		this.names = names;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public ArrayList<Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<Object> attributes) {
		this.attributes = attributes;
	}
	public ArrayList<AddressDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<AddressDTO> addresses) {
		this.addresses = addresses;
	}
	
	public void addAddresses(AddressDTO addressdto){
		this.addresses.add(addressdto);
			
	}
	public void addName(NameDTO namedto){
		this.names.add(namedto);
	}
}
