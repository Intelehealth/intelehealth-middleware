package com.emrmiddleware.dto;

import java.util.ArrayList;

import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;

public class PushDataDTO {
	
	private ArrayList<PatientAPIDTO> patients;
	private ArrayList<PersonAPIDTO> persons;
	private ArrayList<VisitAPIDTO> visits;
	private ArrayList<EncounterAPIDTO> encounters;
	private boolean syncd=true;
	

	
	public ArrayList<EncounterAPIDTO> getEncounters() {
		return encounters;
	}

	public void setEncounters(ArrayList<EncounterAPIDTO> encounters) {
		this.encounters = encounters;
	}

	public ArrayList<VisitAPIDTO> getVisits() {
		return visits;
	}

	public void setVisits(ArrayList<VisitAPIDTO> visits) {
		this.visits = visits;
	}

	public ArrayList<PersonAPIDTO> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<PersonAPIDTO> persons) {
		this.persons = persons;
	}

	

	public ArrayList<PatientAPIDTO> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<PatientAPIDTO> patients) {
		this.patients = patients;
	}

	public boolean isSyncd() {
		return syncd;
	}

	public void setSyncd(boolean syncd) {
		this.syncd = syncd;
	}

	
	

}
