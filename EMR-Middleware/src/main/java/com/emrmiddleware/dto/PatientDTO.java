package com.emrmiddleware.dto;

import java.util.Date;

public class PatientDTO {

  private String uuid;
  private String openmrs_id;
  private String mpi_id;
  private String firstname;
  private String middlename;
  private String lastname;
  private Date dateofbirth;
  private String phonenumber;
  private String address1;

  private String address2;
  private String address3;
  private String address4;
  private String address5;
  private String countyDistrict = "NA";
  private String address6 = "NA";
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
  private boolean syncd = true;
  private int voided;
  private int patientid;

  public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  public String getAddress4() {
    return address4;
  }

  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  public String getAddress5() {
    return address5;
  }

  public void setAddress5(String address5) {
    this.address5 = address5;
  }

  public String getCountyDistrict() {
    return countyDistrict;
  }

  public void setCountyDistrict(String countyDistrict) {
    this.countyDistrict = countyDistrict;
  }

  public String getAddress6() {
    return address6;
  }

  public void setAddress6(String address6) {
    this.address6 = address6;
  }

  public int getPatientid() {
    return patientid;
  }

  public void setPatientid(int patientid) {
    this.patientid = patientid;
  }

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

  public String getMpi_id() {
    return mpi_id;
  }

  public void setMpi_id(String mpi_id) {
    this.mpi_id = mpi_id;
  }

  @Override
  public String toString() {
    return "PatientDTO{" +
            "uuid='" + uuid + '\'' +
            ", openmrs_id='" + openmrs_id + '\'' +
            ", mpi_id='" + mpi_id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", middlename='" + middlename + '\'' +
            ", lastname='" + lastname + '\'' +
            ", dateofbirth=" + dateofbirth +
            ", phonenumber='" + phonenumber + '\'' +
            ", address1='" + address1 + '\'' +
            ", address2='" + address2 + '\'' +
            ", address3='" + address3 + '\'' +
            ", address4='" + address4 + '\'' +
            ", address5='" + address5 + '\'' +
            ", countyDistrict='" + countyDistrict + '\'' +
            ", address6='" + address6 + '\'' +
            ", cityvillage='" + cityvillage + '\'' +
            ", stateprovince='" + stateprovince + '\'' +
            ", postalcode='" + postalcode + '\'' +
            ", country='" + country + '\'' +
            ", gender='" + gender + '\'' +
            ", sdw='" + sdw + '\'' +
            ", dead=" + dead +
            ", occupation='" + occupation + '\'' +
            ", patient_photo='" + patient_photo + '\'' +
            ", economicstatus='" + economicstatus + '\'' +
            ", caste='" + caste + '\'' +
            ", syncd=" + syncd +
            ", voided=" + voided +
            ", patientid=" + patientid +
            '}';
  }
}
