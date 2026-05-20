package com.emrmiddleware.action;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.IDGenAPIDTO;
import com.emrmiddleware.api.dto.IdentifierAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.IdentifierUpdateAPIDTO;
import com.emrmiddleware.api.dto.OpenMrsUuidRefAPIDTO;
import com.emrmiddleware.api.dto.PatientUpdateAPIDTO;
import com.emrmiddleware.api.dto.SourcePatientIdentifierAPIDTO;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.conf.ResourcesEnvironment;
import com.emrmiddleware.dao.PatientDAO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.regex.Pattern;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class PatientAction {
	private static final Pattern UUID_PATTERN = Pattern.compile(
			"^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

	private final Logger logger = LoggerFactory.getLogger(PatientAction.class);
	private final ResourcesEnvironment resourcesEnvironment = new ResourcesEnvironment();

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
				isPatientSet = true;
				String openMrsId = "";
				String sourcePatientId = getSourcePatientId(patient);
				String personUuid = patient.getPerson();
				if (personUuid == null || personUuid.trim().isEmpty()) {
					logger.error("Patient push skipped: person uuid is required");
					isPatientSet = false;
					patientdto = new PatientDTO();
					patientdto.setUuid(personUuid);
					patientdto.setSyncd(false);
					patients.add(patientdto);
					continue;
				}
				personUuid = personUuid.trim();
				PatientDAO patientdao = new PatientDAO();
				PatientDTO patientDTO = patientdao.getPatient(personUuid);
				if (patientDTO == null) {
					openMrsId = getOpenMrsId();
					patient.getIdentifiers().get(0).setIdentifier(openMrsId);
					isPatientSet = addPatientOpenMRS(patient);
				} else {
					openMrsId = patientDTO.getOpenmrs_id();
					isPatientSet = editPatientOpenMRS(personUuid, openMrsId, patient);
				}
				
				
				if (sourcePatientId != null && !sourcePatientId.trim().isEmpty() && isPatientSet) {
					isPatientSet = upsertSourcePatientIdentifier(personUuid, sourcePatientId,patient.getIdentifiers().get(0).getLocation());
				}

				patientdto = new PatientDTO();
				patientdto.setUuid(personUuid);
				patientdto.setSyncd(isPatientSet);
				if (isPatientSet)
					patientdto.setOpenmrs_id(openMrsId);
				if (sourcePatientId != null)
					patientdto.setMpi_id(sourcePatientId);
				patients.add(patientdto);
			}
		} catch (Exception e) {
			//patientdto.setOpenmrs_id("");// Set OpenMRS ID to blank in case of  exception
			logger.error("Error occurred for json string : {}" , gson.toJson(patientforerror));
			logger.error(e.getMessage(), e);

		} 
		return patients;

	}

	private String getSourcePatientId(PatientAPIDTO patient) {
		if (patient == null || patient.getIdentifiers() == null) {
			return null;
		}
		String sourcePatientIdentifierType =
				resourcesEnvironment.getSourcePatientIdentifierTypeName();
		for (IdentifierAPIDTO identifier : patient.getIdentifiers()) {
			if (identifier != null
					&& sourcePatientIdentifierType.equals(identifier.getIdentifierType())) {
				return identifier.getIdentifier();
			}
		}
		return null;
	}

	private boolean upsertSourcePatientIdentifier(String patientUuid, String identifierValue,String location) {
		SourcePatientIdentifierAPIDTO request = new SourcePatientIdentifierAPIDTO();
		request.setPatientUuid(patientUuid);
		request.setIdentifierValue(identifierValue);
		request.setLocationUuid(location);
		try {
			Call<ResponseBody> call = restapiintf.upsertSourcePatientIdentifier(request);
			Response<ResponseBody> response = call.execute();
			if (response.isSuccessful()) {
				logger.info("Source patient identifier upserted for patient {}", patientUuid);
				return true;
			}
			String error = response.errorBody() != null ? response.errorBody().string() : "";
			logger.error("Source patient identifier REST failed: {}", error);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
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
				logger.info("Response for ID gen is : {}" , val);
				IDGenAPIDTO idgenapidto = gson.fromJson(val, IDGenAPIDTO.class);
				openmrsid = idgenapidto.getIdentifiers()[0];
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : {} " , val);
				return openmrsid;
			}
			logger.info("Response is : {} " , val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return openmrsid;
		}
		return openmrsid;

	}

	private boolean addPatientOpenMRS(PatientAPIDTO patientdto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("patient value : {}" , gson.toJson(patientdto));

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
			logger.info("Response is : {}" , val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private PatientUpdateAPIDTO buildPatientUpdateRequest(
			String personUuid, String openMrsId, PatientAPIDTO patient) {
		PatientUpdateAPIDTO updateRequest = new PatientUpdateAPIDTO();
		updateRequest.setPerson(new OpenMrsUuidRefAPIDTO(personUuid));

		IdentifierUpdateAPIDTO identifierUpdate = new IdentifierUpdateAPIDTO();
		identifierUpdate.setIdentifier(openMrsId);

		IdentifierAPIDTO template = findOpenMrsIdentifier(patient);
		if (template != null) {
			identifierUpdate.setIdentifierType(toUuidRef(template.getIdentifierType()));
			identifierUpdate.setLocation(toUuidRef(template.getLocation()));
			identifierUpdate.setPreferred(parsePreferred(template.getPreferred()));
		}

		ArrayList<IdentifierUpdateAPIDTO> identifiers = new ArrayList<>();
		identifiers.add(identifierUpdate);
		updateRequest.setIdentifiers(identifiers);
		return updateRequest;
	}

	private IdentifierAPIDTO findOpenMrsIdentifier(PatientAPIDTO patient) {
		if (patient == null || patient.getIdentifiers() == null) {
			return null;
		}
		String openMrsType = resourcesEnvironment.getOpenMrsIdentifierTypeName();
		String sourceType = resourcesEnvironment.getSourcePatientIdentifierTypeName();
		for (IdentifierAPIDTO identifier : patient.getIdentifiers()) {
			if (identifier != null && openMrsType.equals(identifier.getIdentifierType())) {
				return identifier;
			}
		}
		for (IdentifierAPIDTO identifier : patient.getIdentifiers()) {
			if (identifier != null && !sourceType.equals(identifier.getIdentifierType())) {
				return identifier;
			}
		}
		return null;
	}

	private OpenMrsUuidRefAPIDTO toUuidRef(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		if (UUID_PATTERN.matcher(trimmed).matches()) {
			return new OpenMrsUuidRefAPIDTO(trimmed);
		}
		return null;
	}

	private Boolean parsePreferred(String preferred) {
		if (preferred == null || preferred.trim().isEmpty()) {
			return null;
		}
		return Boolean.parseBoolean(preferred.trim());
	}

	private boolean editPatientOpenMRS(String patientUuid, String openMrsId, PatientAPIDTO patient) {
		Gson gson = new Gson();
		String val = "";
		PatientUpdateAPIDTO updateRequest = buildPatientUpdateRequest(patientUuid, openMrsId, patient);
		logger.info("edit patient (savePatient) uuid={} body={}", patientUuid, gson.toJson(updateRequest));

		try {
			Call<ResponseBody> callpatient = restapiintf.editPatient(patientUuid, updateRequest);
			Response<ResponseBody> response = callpatient.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody() != null ? response.errorBody().string() : "";
				logger.error("Patient update REST failed : {}", val);
				return false;
			}
			logger.info("Patient update response is : {}", val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

}
