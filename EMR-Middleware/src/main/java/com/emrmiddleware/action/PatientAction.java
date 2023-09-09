package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.IDGenAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.dao.PatientDAO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class PatientAction {
	private final Logger logger = LoggerFactory.getLogger(PersonAction.class);

	RestAPI restIdapiintf;
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;

	public PatientAction(String auth) {
		authString = auth;
		apiclient = new APIClient(authString);
		restapiintf = apiclient.getClient().create(RestAPI.class);
		restIdapiintf = apiclient.getIdClient().create(RestAPI.class);
	}

	public ArrayList<PatientDTO> setPatients(ArrayList<PatientAPIDTO> patientList)
			throws DAOException, ActionException {
		ArrayList<PatientDTO> patients = new ArrayList<PatientDTO>();
		PatientDTO patientdto = null;
		PatientAPIDTO patientforerror = new PatientAPIDTO();
		boolean isPatientSet = true;
		Gson gson = new Gson();
		try {
			for (PatientAPIDTO patient : patientList) {
				patientforerror = patient;
				String openMrsId = "";
				PatientDAO patientdao = new PatientDAO();
				PatientDTO patientDTO = patientdao.getPatient(patient.getPerson());
				if (patientDTO == null) {
					openMrsId = getOpenMrsId();
					patient.getIdentifiers().get(0).setIdentifier(openMrsId);
					isPatientSet = addPatientOpenMRS(patient);
				} else {
					openMrsId = patientDTO.getOpenmrs_id();
				}

				patientdto = new PatientDTO();
				patientdto.setUuid(patient.getPerson());
				patientdto.setSyncd(isPatientSet);
				if (isPatientSet == true)
					patientdto.setOpenmrs_id(openMrsId);
				patients.add(patientdto);
			}
		} catch (Exception e) {
			//patientdto.setOpenmrs_id("");// Set OpenMRS ID to blank in case of
											// exception
			logger.error("Error occurred for json string : " + gson.toJson(patientforerror));
			logger.error(e.getMessage(), e);
			// throw new ActionException(e.getMessage(), e);
		} 
		return patients;

	}

	private String getOpenMrsId() {
		String openmrsid = "";
		String val = "";
		Gson gson = new Gson();

		try {
			AuthenticationUtil authenticationUtil = new AuthenticationUtil();
			UserCredentialDTO userCredentialdto = authenticationUtil.getAuthHeader(authString);
			Call<ResponseBody> call = restIdapiintf.getOpenMrsId("1", userCredentialdto.getUsername(),
					userCredentialdto.getPassword());

			Response<ResponseBody> response = call.execute();
			// IDGenAPIDTO idgenapidto = call.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
				logger.info("Response for ID gen is : " + val);
				IDGenAPIDTO idgenapidto = gson.fromJson(val, IDGenAPIDTO.class);
				openmrsid = idgenapidto.getIdentifiers()[0];
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : " + val);
				return openmrsid;
			}
			logger.info("Response is : " + val);
		} catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return openmrsid;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return openmrsid;
		}
		return openmrsid;

	}

	private boolean addPatientOpenMRS(PatientAPIDTO patientdto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("patient value : " + gson.toJson(patientdto));

		try {
			Call<ResponseBody> callpatient = restapiintf.addPatient(patientdto);
			Response<ResponseBody> response = callpatient.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : " + val);
				return false;
			}
			logger.info("Response is : " + val);
		} catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
}
