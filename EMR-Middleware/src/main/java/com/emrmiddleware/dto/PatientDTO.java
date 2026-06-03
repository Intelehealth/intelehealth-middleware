
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
    private boolean syncd = true;
    private int voided;
    private String creatoruuid;
    private String datecreated;

    public String getCreatoruuid() {
        return this.creatoruuid;
    }

    public void setCreatoruuid(String creatoruuid) {
        this.creatoruuid = creatoruuid;
    }

    public String getDatecreated() {
        return this.datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public int getVoided() {
        return this.voided;
    }

    public void setVoided(int voided) {
        this.voided = voided;
    }

    public boolean isSyncd() {
        return this.syncd;
    }

    public void setSyncd(boolean syncd) {
        this.syncd = syncd;
    }

    public int getDead() {
        return this.dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpenmrs_id() {
        return this.openmrs_id;
    }

    public void setOpenmrs_id(String openmrs_id) {
        this.openmrs_id = openmrs_id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return this.middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateofbirth() {
        return this.dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCityvillage() {
        return this.cityvillage;
    }

    public void setCityvillage(String cityvillage) {
        this.cityvillage = cityvillage;
    }

    public String getStateprovince() {
        return this.stateprovince;
    }

    public void setStateprovince(String stateprovince) {
        this.stateprovince = stateprovince;
    }

    public String getPostalcode() {
        return this.postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSdw() {
        return this.sdw;
    }

    public void setSdw(String sdw) {
        this.sdw = sdw;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPatient_photo() {
        return this.patient_photo;
    }

    public void setPatient_photo(String patient_photo) {
        this.patient_photo = patient_photo;
    }

    public String getEconomicstatus() {
        return this.economicstatus;
    }

    public void setEconomicstatus(String economicstatus) {
        this.economicstatus = economicstatus;
    }

    public String getCaste() {
        return this.caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }
}

