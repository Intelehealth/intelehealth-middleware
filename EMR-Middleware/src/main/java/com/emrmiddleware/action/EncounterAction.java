package com.emrmiddleware.action;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.dao.EncounterDAO;
import com.emrmiddleware.dto.EncounterDTO;

import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class EncounterAction {
	private final Logger logger = LoggerFactory.getLogger(EncounterAction.class);
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;
    static final String RESTFAILED = "Rest Failed";
	public EncounterAction(String auth) {
		authString = auth;
		apiclient = new APIClient(authString);
		restapiintf = apiclient.getClient().create(RestAPI.class);
	}

//Refactored

	public ArrayList<EncounterDTO> setEncounters(ArrayList<EncounterAPIDTO> encounterList)
			throws DAOException {
		ArrayList<EncounterDTO> encounters = new ArrayList<>();
		EncounterAPIDTO encounterForError = new EncounterAPIDTO();
		Gson gson = new Gson();

		try {
			for (EncounterAPIDTO encounter : encounterList) {
				encounterForError = encounter;
				logger.info("Encounter json : {}", gson.toJson(encounter));

				EncounterDTO encounterDto = processEncounter(encounter);
				encounters.add(encounterDto);
			}
		} catch (Exception e) {
			logError(gson, encounterForError, e);
		}

		return encounters;
	}

	private EncounterDTO processEncounter(EncounterAPIDTO encounter) throws DAOException {
		EncounterDTO encounterDto = new EncounterDTO();
		EncounterDTO existingEncounter = getEncounter(encounter.getUuid());

		boolean isEncounterPresent = existingEncounter != null;
		boolean isVoided = isEncounterVoided(encounter);
		boolean isEncounterSet = false;

		if (isEncounterPresent) {
			if (isVoided) {
				isEncounterSet = handleVoidedEncounter(existingEncounter, encounter);
			} else {
				isEncounterSet = editEncounterOpenMRS(encounter);
			}
		} else if (!isVoided) {
			isEncounterSet = addEncounterOpenMRS(encounter);
		}

		encounterDto.setUuid(encounter.getUuid());
		encounterDto.setSyncd(isEncounterSet);
		encounterDto.setVoided(isVoided ? 1 : 0);
		return encounterDto;
	}

	private boolean handleVoidedEncounter(EncounterDTO existingEncounter, EncounterAPIDTO encounter)  {
		if (existingEncounter.getVoided() == 1) {
			return true;
		}
		return deleteEncounterOpenMRS(encounter);
	}

	private void logError(Gson gson, EncounterAPIDTO encounterForError, Exception e) {
		logger.error("Error occurred for json string: {}", gson.toJson(encounterForError));
		logger.error(e.getMessage(), e);
	}

	private boolean isEncounterVoided(EncounterAPIDTO encounterapidto) {
		// This is done as voided is a string type , a null check has to be done
		// voided need not be mandatory or else Integer.parseInt would have been
		// used
		return( encounterapidto.getVoided() != null && encounterapidto.getVoided().equals("1"));

	}

	
	private EncounterDTO getEncounter(String encounteruuid) throws DAOException {
		EncounterDAO encounterdao = new EncounterDAO();
		return  encounterdao.getEncounter(encounteruuid);

	}

	private boolean addEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		

		try {
			encounterapidto.setVoided(null);// Setting voided to null as OpenMrs
											// does not accept voided in the
											// json structure
			String encounterString = gson.toJson(encounterapidto);
			logger.info("encounter value : {}", encounterString);
			Call<ResponseBody> callencounter = restapiintf.addEncounter(encounterapidto);
			Response<ResponseBody> response = callencounter.execute();

			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();

				logger.error(String.format("%s%s", RESTFAILED, val));
				return false;
			}
			logger.info("Response is {} " , val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private boolean editEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		
		try {
			encounterapidto.setVoided(null);// Setting voided to null as OpenMrs
											// does not accept voided in the
											// json structure
			logger.info("edit encounter value : {}" , gson.toJson(encounterapidto));
			Call<ResponseBody> callencounter = restapiintf.editEncounter(encounterapidto.getUuid(), encounterapidto);
			Response<ResponseBody> response = callencounter.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error(String.format("%s%s", RESTFAILED, val));
				return false;
			}
			logger.info("Response for edit is : {} " , val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private boolean deleteEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("encounter value : {}", gson.toJson(encounterapidto));

		try {
			Call<ResponseBody> callencounter = restapiintf.deleteEncounter(encounterapidto.getUuid());
			Response<ResponseBody> response = callencounter.execute();
			if (!response.isSuccessful()) {
				val = response.errorBody().string();
				logger.error(String.format("%s %s", RESTFAILED, val));
				return false;
			}
			logger.info("Encounter : {} {}",encounterapidto.getUuid(), " deleted");
		} catch (Exception e ) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
