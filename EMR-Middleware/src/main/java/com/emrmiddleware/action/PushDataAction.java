package com.emrmiddleware.action;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.PushDataDTO;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.rest.PushData;

public class PushDataAction {

	private final Logger logger = LoggerFactory.getLogger(PushData.class);

	public PullDataDTO pushData(PushDataDTO pushdatadto) throws DAOException, ActionException {

		ArrayList<PersonAPIDTO> personList = pushdatadto.getPersons();
		PullDataDTO pulldatadto = new PullDataDTO();
		try {
			PersonAction personaction = new PersonAction();
			//patientaction.setPatients(patientList);
			  ArrayList<PersonDTO> persons=personaction.setPersons(personList);
			  pulldatadto.setPersonList(persons);
			

		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);
				
		}
		return pulldatadto;
	}

}
