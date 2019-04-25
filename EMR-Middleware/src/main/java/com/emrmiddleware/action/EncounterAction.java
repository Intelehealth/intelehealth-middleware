package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.dao.EncounterDAO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/* This class also handles obs .In Openmrs if we pass obs with uuid in a new encounter , the obs gets created
 *  .If obs with existing uuid is sent with an encounter where encounteruuid already exists , a new obs uuid gets
 *   generated and the old uuid gets voided.Not coding for obs individually for now , as openmrs is handling and there is 
 *   safety that it is happening as a transaction in openmrs.Either both encounter and obs works or nothing works.There is also 
 *   no need of multiple api calls from middleware to openmrs.This will have to be taken into consideration in the mobile*/

public class EncounterAction {
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
		boolean isEncounterSet = true;
		Gson gson = new Gson();
		try {
			for (EncounterAPIDTO encounter : encounterList) {
				encounterforerror = encounter;
				logger.info("Encounter json : " + gson.toJson(encounter));
				if (isEncounterExists(encounter.getUuid())) {
					isEncounterSet = editEncounterOpenMRS(encounter);
				} else {
					isEncounterSet = addEncounterOpenMRS(encounter);
				}
				encounterdto = new EncounterDTO();
				encounterdto.setUuid(encounter.getUuid());
				encounterdto.setSyncd(isEncounterSet);
				encounters.add(encounterdto);
			}
		} catch (Exception e) {
			logger.error("Error occurred for json string : " + gson.toJson(encounterforerror));
			logger.error(e.getMessage(), e);
		}
		return encounters;

	}

	private boolean isEncounterExists(String encounteruuid) throws DAOException {
		boolean isEncounterExists = false;
		EncounterDAO encounterdao = new EncounterDAO();
		EncounterDTO encounterdto = encounterdao.getEncounter(encounteruuid);
		if (encounterdto != null) {
			isEncounterExists = true;
		}
		return isEncounterExists;

	}

	private boolean addEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("encounter value : " + gson.toJson(encounterapidto));

		try {
			Call<ResponseBody> callencounter = restapiintf.addEncounter(encounterapidto);
			Response<ResponseBody> response = callencounter.execute();
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

	private boolean editEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("edit encounter value : " + gson.toJson(encounterapidto));

		try {

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

}
