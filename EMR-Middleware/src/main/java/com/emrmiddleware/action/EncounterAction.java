package com.emrmiddleware.action;


import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.dao.EncounterDAO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;


public class EncounterAction {
    static final String RESTFAILED = "Rest Failed";
    private final Logger logger = LoggerFactory.getLogger(EncounterAction.class);
    APIClient apiclient;
    RestAPI restapiintf;
    String authString;

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

    private boolean handleVoidedEncounter(EncounterDTO existingEncounter, EncounterAPIDTO encounter) {
        if (existingEncounter.getVoided() == 1) {
            return true;
        }
        return deleteEncounterOpenMRS(encounter);
    }

    private void logError(Gson gson, EncounterAPIDTO encounterForError, Exception e) {
        logger.error("Error for Encounter: {}", gson.toJson(encounterForError));
        logger.error(e.getMessage(), e);
    }

    private boolean isEncounterVoided(EncounterAPIDTO encounterapidto) {

        return (encounterapidto.getVoided() != null && encounterapidto.getVoided().equals("1"));

    }


    private EncounterDTO getEncounter(String encounteruuid) throws DAOException {
        EncounterDAO encounterdao = new EncounterDAO();
        return encounterdao.getEncounter(encounteruuid);

    }


    public boolean addEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
        try {
            prepareEncounterForAdd(encounterapidto);

            logEncounterObject(encounterapidto);

            Response<ResponseBody> response = executeAddEncounterApi(encounterapidto);

            return handleApiResponse(response);

        } catch (Exception e) {
            logException(e);
            return false;
        }
    }


    private void prepareEncounterForAdd(EncounterAPIDTO encounterapidto) {
        encounterapidto.setVoided(null); // OpenMRS does not accept 'voided' in the JSON structure
    }


    private Response<ResponseBody> executeAddEncounterApi(EncounterAPIDTO encounterapidto) throws Exception {
        Call<ResponseBody> call = restapiintf.addEncounter(encounterapidto);
        return call.execute();
    }


    public boolean editEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
        try {
            prepareEncounterForEdit(encounterapidto);
            logEncounterObject(encounterapidto);
            Response<ResponseBody> response = executeEditEncounterApi(encounterapidto);
            return handleApiResponse(response);
        } catch (Exception e) {
            logException(e);
            return false;
        }
    }

    private void prepareEncounterForEdit(EncounterAPIDTO encounterapidto) {
        encounterapidto.setVoided(null);
    }

    private void logEncounterObject(EncounterAPIDTO encounterapidto) {
        Gson gson = new Gson();
        logger.info("edit encounter value : {}", gson.toJson(encounterapidto));
    }

    private Response<ResponseBody> executeEditEncounterApi(EncounterAPIDTO encounterapidto) throws Exception {
        Call<ResponseBody> callencounter = restapiintf.editEncounter(encounterapidto.getUuid(), encounterapidto);
        return callencounter.execute();
    }

    private boolean handleApiResponse(Response<ResponseBody> response) throws Exception {
        String val;
        if (response.isSuccessful()) {
            val = response.body().string();
        } else {
            val = response.errorBody().string();
            logger.error("{} {}", RESTFAILED, val);
            return false;
        }

        logger.info("Response for edit is : {}", val);
        return true;
    }

    private void logException(Exception e) {
        logger.error(e.getMessage(), e);
    }


    public boolean deleteEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
        try {
            logEncounterObject(encounterapidto);

            Response<ResponseBody> response = executeDeleteEncounterApi(encounterapidto);

            return handleDeleteApiResponse(response, encounterapidto);

        } catch (Exception e) {
            logException(e);
            return false;
        }
    }

    // Executes the API call to delete the encounter using the RestApiInterface
    private Response<ResponseBody> executeDeleteEncounterApi(EncounterAPIDTO encounterapidto) throws Exception {
        Call<ResponseBody> call = restapiintf.deleteEncounter(encounterapidto.getUuid());
        return call.execute();
    }

    // Handles the API response for the delete operation
    private boolean handleDeleteApiResponse(Response<ResponseBody> response, EncounterAPIDTO encounterapidto) throws Exception {
        if (!response.isSuccessful()) {
            logErrorResponse(response.errorBody());
            return false;
        }
        logSuccessDeletion(encounterapidto);
        return true;
    }

    // Logs a successful deletion response
    private void logSuccessDeletion(EncounterAPIDTO encounterapidto) {
        logger.info("Encounter : {} {}", encounterapidto.getUuid(), "deleted");
    }

    // Logs an error response
    private void logErrorResponse(ResponseBody errorBody) throws Exception {
        String errorVal = errorBody.string();
        logger.error("{} {}", RESTFAILED, errorVal);
    }


}
