package com.emrmiddleware.action;
import java.util.ArrayList;
import java.util.Objects;

import com.emrmiddleware.api.dto.IdentifierAPIDTO;
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

import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class PatientAction {
	private final Logger logger = LoggerFactory.getLogger(PatientAction.class);

	RestAPI restIdapiintf;
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;

	public static final String OPENMRS_ID = "05a29f94-c0ed-11e2-94be-8c13b969e334";
	public static final String ABHA_NUMBER = "6ad4e308-33aa-4afc-9879-6033d1984876";
	public static final String ABHA_ADDRESS = "59077d8f-8bee-4a6f-a1a8-64365a297da6";

	public PatientAction(String auth) {
		authString = auth;
		apiclient = new APIClient(authString);
		restapiintf = apiclient.getClient().create(RestAPI.class);
		restIdapiintf = apiclient.getIdClient().create(RestAPI.class);
	}

	public ArrayList<PatientDTO> setPatients(ArrayList<PatientAPIDTO> patientList)
			throws DAOException {
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

					//patient.getIdentifiers().get(0).setIdentifier(openMrsId); Making the code more generic
					for(IdentifierAPIDTO identifier: patient.getIdentifiers()) {
						if(Objects.equals(identifier.getIdentifierType(), OPENMRS_ID)) { // Set identifier only for OpenMRS ID

							identifier.setIdentifier(openMrsId);
						}

					}
					isPatientSet = addPatientOpenMRS(patient);

				} else {
					openMrsId = patientDTO.getOpenmrs_id();
				}

				patientdto = new PatientDTO();
				patientdto.setUuid(patient.getPerson());
				patientdto.setSyncd(isPatientSet);
				if (isPatientSet)
					patientdto.setOpenmrs_id(openMrsId);
				patients.add(patientdto);

			}
		} catch (Exception e) {
			//patientdto.setOpenmrs_id("");// Set OpenMRS ID to blank in case of
											// exception
			logger.error("Error occurred for json string : {}" , gson.toJson(patientforerror));
			logger.error(e.getMessage(), e);

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

			if (response.isSuccessful()) {
				val = response.body().string();
				logger.info("Response for ID gen is : {}",  val);
				IDGenAPIDTO idgenapidto = gson.fromJson(val, IDGenAPIDTO.class);
				openmrsid = idgenapidto.getIdentifiers()[0];
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : {} " , val);
				return openmrsid;
			}
			logger.info("Response is : {} ", val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return openmrsid;
		}
		return openmrsid;

	}

	private boolean addPatientOpenMRS(PatientAPIDTO patientdto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("patient value : {}" ,  gson.toJson(patientdto));

		try {
			Call<ResponseBody> callpatient = restapiintf.addPatient(patientdto);
			Response<ResponseBody> response = callpatient.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : {} " , val);
				return false;
			}
			logger.info("Response is : {} " , val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
}
