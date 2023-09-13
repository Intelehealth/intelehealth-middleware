package com.emrmiddleware.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private final Logger logger = LoggerFactory.getLogger(EncounterAction.class);
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;
	final static String ENCOUNTER_VISIT_NOTE = "d7151f82-c1f3-4152-a605-2f9ea7414a79";
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
				if (isEncounterVoided(encounter)) {
					voided = 1;
				} else {
					voided = 0;
				}
				encounterforerror = encounter;
				logger.info("Encounter json : " + gson.toJson(encounter));
				EncounterDTO encounterdto_present = new EncounterDTO();
				encounterdto_present = getEncounter(encounter.getUuid());
				// to prevent multiple hit to DB
                isEncounterPresent = encounterdto_present != null;
				
				// isEncounterPresent = isEncounterExists(encounter.getUuid());
				//Edit Encounter
				if ((isEncounterPresent) && (!isEncounterVoided(encounter))) {
					isEncounterSet = editEncounterOpenMRS(encounter);
				}

				//delete encounter
				if ((isEncounterPresent) && (isEncounterVoided(encounter))) {
					// check if encounter already is voided in openMRS
					if (encounterdto_present.getVoided() == 1) {
						isEncounterSet = true;
						voided = 1;
					} else {
						isEncounterSet = deleteEncounterOpenMRS(encounter);
						if (isEncounterSet)
							voided = 1;
					}
				}
				//Add Encounter
				if ((!isEncounterPresent) && (!isEncounterVoided(encounter))) {
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
			logger.info("encounter value : " + gson.toJson(encounterapidto));
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
		
		try {
			encounterapidto.setVoided(null);// Setting voided to null as OpenMrs
											// does not accept voided in the
											// json structure
			logger.info("edit encounter value : " + gson.toJson(encounterapidto));

			if(encounterapidto.getEncounterType().equals(ENCOUNTER_VISIT_NOTE) ){
				// HibernateLocationDao.getLocation expects location_id from location table not uuid
				encounterapidto.setLocation(getLocationId(encounterapidto.getLocation()));
			}
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

	private String getLocationId(String location) {
		String intLocation="0";
		try {


			Class.forName("com.mysql.jdbc.Driver");
			Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
			PreparedStatement pstmt = con.prepareStatement("SELECT location_id FROM location where uuid=?");
			pstmt.setString(1, location);
			ResultSet resultSet = pstmt.executeQuery();
			resultSet.next();
			intLocation = resultSet.getString(1);
			resultSet.close();
			pstmt.close();
			con.close();
		}
		catch(ClassNotFoundException | SQLException e) {
			logger.error(e.getMessage(), e);

		}
		return intLocation;
	}

	private boolean deleteEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("encounter value : " + gson.toJson(encounterapidto));

		try {
			Call<ResponseBody> callencounter = restapiintf.deleteEncounter(encounterapidto.getUuid());
			Response<ResponseBody> response = callencounter.execute();
			if (!response.isSuccessful()) {
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
