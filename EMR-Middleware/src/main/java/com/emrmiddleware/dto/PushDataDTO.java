package com.emrmiddleware.dto;

import java.util.ArrayList;

import com.emrmiddleware.api.dto.PersonAPIDTO;

public class PushDataDTO {
	
	private ArrayList<PatientDTO> patients;
	private ArrayList<PersonAPIDTO> persons;
	private boolean syncd=true;
	

	
	public ArrayList<PersonAPIDTO> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<PersonAPIDTO> persons) {
		this.persons = persons;
	}

	public ArrayList<PatientDTO> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<PatientDTO> patients) {
		this.patients = patients;
	}

	public boolean isSyncd() {
		return syncd;
	}

	public void setSyncd(boolean syncd) {
		this.syncd = syncd;
	}

	
	

}
