package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import com.emrmiddleware.api.dto.VoidAPIDTO;
import com.emrmiddleware.dto.ObsDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.dao.EncounterDAO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.api.dto.ObsAPIDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class EncounterAction {
	public final String MEDICINE_CONCEPT_UUID = "c38c0c50-2fd2-4ae3-b7ba-7dd25adca4ca";
	private final Logger logger = LoggerFactory.getLogger(EncounterAction.class);
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;

	public EncounterAction(String auth) {
		authString = auth;
		apiclient = new APIClient(authString);
		restapiintf = apiclient.getClient().create(RestAPI.class);
	}

	public ArrayList<EncounterDTO> setEncounters(ArrayList<EncounterAPIDTO> encounterList)
			throws DAOException, ActionException {
		ArrayList<EncounterDTO> encounters = new ArrayList<EncounterDTO>();
		EncounterDTO encounterdto;
		EncounterAPIDTO encounterforerror = new EncounterAPIDTO();
		
		Gson gson = new Gson();
		boolean isEncounterPresent = false;
		try {
			for (EncounterAPIDTO encounter : encounterList) {
				int voided = 0;
				boolean isEncounterSet = true;
				if (isEncounterVoided(encounter) == true) {
					voided = 1;
				} else {
					voided = 0;
				}
				encounterforerror = encounter;
				logger.info("Encounter json : " + gson.toJson(encounter));
				EncounterDTO encounterdto_present = new EncounterDTO();
				encounterdto_present = getEncounter(encounter.getUuid());
				// to prevent multiple hit to DB
				if (encounterdto_present != null) {
					isEncounterPresent = true;
				} else {
					isEncounterPresent = false;
				}
				
				// isEncounterPresent = isEncounterExists(encounter.getUuid());
				//Edit Encounter
				if ((isEncounterPresent) && (isEncounterVoided(encounter) == false)) {
					isEncounterSet = editEncounterOpenMRS(encounter);
				}

				//delete encounter
				if ((isEncounterPresent) && (isEncounterVoided(encounter) == true)) {
					// check if encounter already is voided in openMRS
					if (encounterdto_present.getVoided() == 1) {
						isEncounterSet = true;
						voided = 1;
					} else {
						isEncounterSet = deleteEncounterOpenMRS(encounter);
						if (isEncounterSet == true)
							voided = 1;
					}
				}
				//Add Encounter
				if ((isEncounterPresent == false) && (isEncounterVoided(encounter) == false)) {
					isEncounterSet = addEncounterOpenMRS(encounter);
				}
				encounterdto = new EncounterDTO();
				encounterdto.setUuid(encounter.getUuid());
				encounterdto.setSyncd(isEncounterSet);
				encounterdto.setVoided(voided);
				encounters.add(encounterdto);
			}
		} catch (Exception e) {
			logger.error("Error occurred for json string : " + gson.toJson(encounterforerror));
			logger.error(e.getMessage(), e);
		}
		return encounters;

	}

	private boolean isEncounterVoided(EncounterAPIDTO encounterapidto) {
		// This is done as voided is a string type , a null check has to be done
		// voided need not be mandatory or else Integer.parseInt would have been
		// used
		boolean isVoided = false;
		if (encounterapidto.getVoided() != null) {
			if (encounterapidto.getVoided().equals("1"))
				isVoided = true;
		}
		return isVoided;
	}

	
	private EncounterDTO getEncounter(String encounteruuid) throws DAOException {
		EncounterDAO encounterdao = new EncounterDAO();
		EncounterDTO encounterdto = encounterdao.getEncounter(encounteruuid);

		return encounterdto;

	}

	private boolean addEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		

		try {
			encounterapidto.setVoided(null);// Setting voided to null as OpenMrs
											// does not accept voided in the
											// json structure
			logger.info(String.format("encounter value : %s", gson.toJson(encounterapidto)));
			Call<ResponseBody> callencounter = restapiintf.addEncounter(encounterapidto);
			Response<ResponseBody> response = callencounter.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error(String.format("REST failed : %s", val));
				return false;
			}
			logger.info(String.format("Response is : %s", val));
		} catch (IOException | NullPointerException e) {
	 			logger.error(e.getMessage(), e);
			return false;
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
			//Adding code for finding empty obs starts Jira: EZ-303
			ArrayList<ObsAPIDTO> obsAPIDTOArrayList = new ArrayList<ObsAPIDTO>();


			 for(ObsAPIDTO obsAPIDTO: encounterapidto.getObs()) {
				 boolean emptyObs = false;
				 if(obsAPIDTO.getValue().isEmpty())  {
					logger.info(String.format("Obs to delete : %s", obsAPIDTO.getUuid()));
					 Call<ResponseBody> callobs = restapiintf.deleteObs(obsAPIDTO.getUuid()); // Void it
					 Response<ResponseBody> responseObs = callobs.execute();


						 obsAPIDTOArrayList.add(obsAPIDTO); // Add to obsList to remove
				 }
                 if (!(obsAPIDTO.getComment() == null)) {
					 if (obsAPIDTO.getComment().equals("Void this")) {
						 logger.info(String.format("Obs to delete : %s", obsAPIDTO.getUuid()));
						 Call<ResponseBody> callobs = restapiintf.deleteObs(obsAPIDTO.getUuid()); // Void it
						 Response<ResponseBody> responseObs = callobs.execute();


						 obsAPIDTOArrayList.add(obsAPIDTO); // Add to obsList to remove
					 }
				 }

			 }
			// Remove empty obs from encounter
			encounterapidto.getObs().removeAll(obsAPIDTOArrayList);



			//Adding code for finding empty obs ends Jira: EZ-303
			logger.info(String.format("edit encounter value : %s", gson.toJson(encounterapidto)));




			Call<ResponseBody> callencounter = restapiintf.editEncounter(encounterapidto.getUuid(), encounterapidto);
			Response<ResponseBody> response = callencounter.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : " + val);
				return false;
			}
			logger.info("Response for edit is : " + val);
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
	
	private boolean deleteEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("encounter value : " + gson.toJson(encounterapidto));

		try {
			Call<ResponseBody> callencounter = restapiintf.deleteEncounter(encounterapidto.getUuid());
			Response<ResponseBody> response = callencounter.execute();
			if (response.isSuccessful() == false) {
				val = response.errorBody().string();
				logger.error("REST failed : " + val);
				return false;
			}
			logger.info("Encounter : "+encounterapidto.getUuid()+" deleted");
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
