package com.emrmiddleware.dto;

import java.util.ArrayList;

public class PushDataDTO {
	
	private ArrayList<PatientDTO> patients;
	private boolean syncd=true;
	

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
