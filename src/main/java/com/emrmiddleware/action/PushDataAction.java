package com.emrmiddleware.action;

import java.util.ArrayList;

import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.dto.*;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.exception.ActionException;


public class PushDataAction {

	String authString;

	public PushDataAction(String auth) {
		authString = auth;
	}

	public PullDataDTO pushData(PushDataDTO pushdatadto) throws DAOException, ActionException {

		ArrayList<PersonAPIDTO> personList = new ArrayList<PersonAPIDTO>();
		ArrayList<PatientAPIDTO> patientList;
		ArrayList<VisitAPIDTO> visitList;
		ArrayList<EncounterAPIDTO> encounterList;

		PullDataDTO pulldatadto = new PullDataDTO();
		try {
			personList = pushdatadto.getPersons();
			patientList = pushdatadto.getPatients();
			visitList = pushdatadto.getVisits();
			encounterList = pushdatadto.getEncounters();


			PersonAction personaction = new PersonAction(authString);
			PatientAction patientaction = new PatientAction(authString);
			VisitAction visitaction = new VisitAction(authString);
			EncounterAction encounteraction = new EncounterAction(authString);

			// patientaction.setPatients(patientList);
			if (personList != null) {
				ArrayList<PersonDTO> persons = personaction.setPersons(personList);
				pulldatadto.setPersonList(persons);
			}
			if (patientList != null) {
				ArrayList<PatientDTO> patients = patientaction.setPatients(patientList);
				pulldatadto.setPatientlist(patients);
			}
			if (visitList != null) {
				ArrayList<VisitDTO> visits = visitaction.setVisits(visitList);
				pulldatadto.setVisitlist(visits);
			}
			if (encounterList != null) {
				ArrayList<EncounterDTO> encounters = encounteraction.setEncounters(encounterList);
				pulldatadto.setEncounterlist(encounters);
			}



		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);

		}
		return pulldatadto;
	}

}
