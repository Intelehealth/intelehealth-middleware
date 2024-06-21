package com.emrmiddleware.action;

import com.emrmiddleware.dao.PatientDAO;
import com.emrmiddleware.dto.ExternalPatientDTO;

public class CheckPatientAction {
    public ExternalPatientDTO getPatientData(String abhaNumber) {
        PatientDAO patientDAO = new PatientDAO();
        return patientDAO.getPersonIdentifiers(abhaNumber);
    }
}
