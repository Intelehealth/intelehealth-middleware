package org.intelehealth.middleware.services;

import org.springframework.stereotype.Service;

@Service
public class ConstantsService {
	public static final  String COLON = ":";
	public static final String USER_CREDS_QUERY="SELECT username, password, salt FROM users u WHERE u.username=:username";
	public static final String OK="OK";
	public static final String ERROR="ERROR";
	public static final String AUTHERROR="No Authorization";
	public static final String DAOEXCEPTION="Exception in DAO : ";
	public static final String CONTROLLEREXCEPTION="Exception in Controller : ";
	public static final String UNABLETOPROCESS="unable_to_process_request";
	public static final String SERVER_ERROR= "Unable to process request. Please try again or Contact System Administrator";

	
	public static final String OPENMRS_BASE_URL = "http://localhost:8080/openmrs/ws/rest/v1";
	public static final String OPENMRS_IDGEN_BASE_URL ="http://localhost:8080/openmrs/module/idgen"; 
	public static final String OPENMRS_ADD_PERSON_ENDPOINT= "/person";
	public static final String OPENMRS_EDIT_PERSON_ENDPOINT= "/person/";
	public static final String OPENMRS_ADD_PATIENT_ENDPOINT= "/patient";
	public static final String OPENMRS_ADD_VISIT_ENDPOINT= "/visit";
	public static final String OPENMRS_EDIT_VISIT_ENDPOINT= "/visit";
	public static final String OPENMRS_ADD_ENCOUNTER_ENDPOINT="/encounter";
	public static final String OPENMRS_EDIT_ENCOUNTER_ENDPOINT="/encounter/";
	public static final String OPENMRS_DELETE_ENCOUNTER_ENDPOINT="/encounter/";
	
	public static final String OPENMRS_IDGEN_ENDPOINT="/generateIdentifier.form";
	
	public static final String MINDMAP_BASE_URL="https://uiux.intelehealth.org:3004/api/appointment";
	public static final String MINDMAP_ADD_APPOINTMENT_ENDPOINT="/push";
	
	
	
	
}
