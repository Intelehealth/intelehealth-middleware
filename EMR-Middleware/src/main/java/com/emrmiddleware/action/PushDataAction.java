package com.emrmiddleware.action;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.PushDataDTO;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.rest.PushData;

public class PushDataAction {

	private final Logger logger = LoggerFactory.getLogger(PushData.class);

	public PushDataDTO pushData(PushDataDTO pushdatadto) throws DAOException, ActionException {

		ArrayList<PatientDTO> patientList = pushdatadto.getPatients();
		try {
			PatientAction patientaction = new PatientAction();
			patientaction.setPatients(patientList);

		} /*
			 * catch (DAOException e) { throw new DAOException(e.getMessage(),
			 * e); }
			 */catch (Exception e) {
			throw new ActionException(e.getMessage(), e);
		}
		return pushdatadto;
	}

}
