/*
 * Decompiled with CFR 0.152.
 */
package com.emrmiddleware.dto;

public class CustomAppointmentDTO {
    public String patientAge;
    public String patientGender;
    public String hwName;
    public String hwAge;
    public String hwGender;
    int appointmentId;
    String slotDay;
    String slotDate;
    int slotDuration;
    String slotDurationUnit;
    String slotTime;
    String speciality;
    String userUuid;
    String drName;
    String uuid;
    String patientName;
    String openMrsId;
    String patientId;
    String locationUuid;
    String hwUUID;
    String reason;
    String voided;
    String visitUuid;
    private boolean syncd = true;

    public int getAppointmentId() {
        return this.appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getSlotDay() {
        return this.slotDay;
    }

    public void setSlotDay(String slotDay) {
        this.slotDay = slotDay;
    }

    public String getSlotDate() {
        return this.slotDate;
    }

    public void setSlotDate(String slotDate) {
        this.slotDate = slotDate;
    }

    public int getSlotDuration() {
        return this.slotDuration;
    }

    public void setSlotDuration(int slotDuration) {
        this.slotDuration = slotDuration;
    }

    public String getSlotDurationUnit() {
        return this.slotDurationUnit;
    }

    public void setSlotDurationUnit(String slotDurationUnit) {
        this.slotDurationUnit = slotDurationUnit;
    }

    public String getSlotTime() {
        return this.slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getSpeciality() {
        return this.speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getUserUuid() {
        return this.userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getDrName() {
        return this.drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getOpenMrsId() {
        return this.openMrsId;
    }

    public void setOpenMrsId(String openMrsId) {
        this.openMrsId = openMrsId;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLocationUuid() {
        return this.locationUuid;
    }

    public void setLocationUuid(String locationUuid) {
        this.locationUuid = locationUuid;
    }

    public String getHwUUID() {
        return this.hwUUID;
    }

    public void setHwUUID(String hwUUID) {
        this.hwUUID = hwUUID;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSyncd() {
        return this.syncd;
    }

    public void setSyncd(boolean syncd) {
        this.syncd = syncd;
    }

    public String getVoided() {
        return this.voided;
    }

    public void setVoided(String voided) {
        this.voided = voided;
    }

    public String getPatientAge() {
        return this.patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return this.patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getHwName() {
        return this.hwName;
    }

    public void setHwName(String hwName) {
        this.hwName = hwName;
    }

    public String getHwAge() {
        return this.hwAge;
    }

    public void setHwAge(String hwAge) {
        this.hwAge = hwAge;
    }

    public String getHwGender() {
        return this.hwGender;
    }

    public void setHwGender(String hwGender) {
        this.hwGender = hwGender;
    }

    public String getVisitUuid() {
        return this.visitUuid;
    }

    public void setVisitUuid(String visitUuid) {
        this.visitUuid = visitUuid;
    }
}

