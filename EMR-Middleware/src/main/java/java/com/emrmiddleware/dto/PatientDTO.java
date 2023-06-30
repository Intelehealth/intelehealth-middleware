package com.emrmiddleware.dto;

import java.util.Date;

public class PatientDTO {
	
	private String uuid;
	private String openmrs_id;
	private String firstname;
	private String middlename;
	private String lastname;
	private Date dateofbirth;
	private String phonenumber;
	private String address1;
	private String address2;
	private String cityvillage;
	private String stateprovince;
	private String postalcode;
	private String country;
	private String gender;
	private String sdw;
	private int dead;
	private String occupation;
	private String patient_photo;
	private String economicstatus;
	private String caste;
	private boolean syncd=true;
	private int voided;
	
	
	
	public int getVoided() {
		return voided;
	}
	public void setVoided(int voided) {
		this.voided = voided;
	}
	public boolean isSyncd() {
		return syncd;
	}
	public void setSyncd(boolean syncd) {
		this.syncd = syncd;
	}
	public int getDead() {
		return dead;
	}
	public void setDead(int dead) {
		this.dead = dead;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getOpenmrs_id() {
		return openmrs_id;
	}
	public void setOpenmrs_id(String openmrs_id) {
		this.openmrs_id = openmrs_id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCityvillage() {
		return cityvillage;
	}
	public void setCityvillage(String cityvillage) {
		this.cityvillage = cityvillage;
	}
	public String getStateprovince() {
		return stateprovince;
	}
	public void setStateprovince(String stateprovince) {
		this.stateprovince = stateprovince;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSdw() {
		return sdw;
	}
	public void setSdw(String sdw) {
		this.sdw = sdw;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getPatient_photo() {
		return patient_photo;
	}
	public void setPatient_photo(String patient_photo) {
		this.patient_photo = patient_photo;
	}
	public String getEconomicstatus() {
		return economicstatus;
	}
	public void setEconomicstatus(String economicstatus) {
		this.economicstatus = economicstatus;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	
	

}
