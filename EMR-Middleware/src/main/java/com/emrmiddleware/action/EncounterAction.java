/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  okhttp3.ResponseBody
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  retrofit2.Call
 *  retrofit2.Response
 */
package com.emrmiddleware.action;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.dao.EncounterDAO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class EncounterAction {
    private final Logger logger = LoggerFactory.getLogger(EncounterAction.class);
    APIClient apiclient;
    RestAPI restapiintf;
    String authString;

    public EncounterAction(String auth) {
        this.authString = auth;
        this.apiclient = new APIClient(this.authString);
        this.restapiintf = (RestAPI)this.apiclient.getClient().create(RestAPI.class);
    }

    public ArrayList<EncounterDTO> setEncounters(ArrayList<EncounterAPIDTO> encounterList) throws DAOException, ActionException {
        ArrayList<EncounterDTO> encounters = new ArrayList<EncounterDTO>();
        EncounterAPIDTO encounterforerror = new EncounterAPIDTO();
        Gson gson = new Gson();
        boolean isEncounterPresent = false;
        try {
            for (EncounterAPIDTO encounter : encounterList) {
                int voided = 0;
                boolean isEncounterSet = true;
                voided = this.isEncounterVoided(encounter) ? 1 : 0;
                encounterforerror = encounter;
                this.logger.info("Encounter json : " + gson.toJson((Object)encounter));
                EncounterDTO encounterdto_present = new EncounterDTO();
                encounterdto_present = this.getEncounter(encounter.getUuid());
                isEncounterPresent = encounterdto_present != null;
                if (isEncounterPresent && !this.isEncounterVoided(encounter)) {
                    isEncounterSet = this.editEncounterOpenMRS(encounter);
                }
                if (isEncounterPresent && this.isEncounterVoided(encounter)) {
                    if (encounterdto_present.getVoided() == 1) {
                        isEncounterSet = true;
                        voided = 1;
                    } else {
                        isEncounterSet = this.deleteEncounterOpenMRS(encounter);
                        if (isEncounterSet) {
                            voided = 1;
                        }
                    }
                }
                if (!isEncounterPresent && !this.isEncounterVoided(encounter)) {
                    isEncounterSet = this.addEncounterOpenMRS(encounter);
                }
                EncounterDTO encounterdto = new EncounterDTO();
                encounterdto.setUuid(encounter.getUuid());
                encounterdto.setSyncd(isEncounterSet);
                encounterdto.setVoided(voided);
                encounters.add(encounterdto);
            }
        }
        catch (Exception e) {
            this.logger.error("Error occurred for json string : " + gson.toJson((Object)encounterforerror));
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return encounters;
    }

    private boolean isEncounterVoided(EncounterAPIDTO encounterapidto) {
        boolean isVoided = false;
        if (encounterapidto.getVoided() != null && encounterapidto.getVoided().equals("1")) {
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
            encounterapidto.setVoided(null);
            this.logger.info("encounter value : " + gson.toJson((Object)encounterapidto));
            Call<ResponseBody> callencounter = this.restapiintf.addEncounter(encounterapidto);
            Response response = callencounter.execute();
            if (!response.isSuccessful()) {
                val = response.errorBody().string();
                this.logger.error("REST failed : " + val);
                return false;
            }
            val = ((ResponseBody)response.body()).string();
            this.logger.info("Response is : " + val);
        }
        catch (IOException | NullPointerException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }

    private boolean editEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
        Gson gson = new Gson();
        String val = "";
        try {
            encounterapidto.setVoided(null);
            this.logger.info("edit encounter value : " + gson.toJson((Object)encounterapidto));
            Call<ResponseBody> callencounter = this.restapiintf.editEncounter(encounterapidto.getUuid(), encounterapidto);
            Response response = callencounter.execute();
            if (!response.isSuccessful()) {
                val = response.errorBody().string();
                this.logger.error("REST failed : " + val);
                return false;
            }
            val = ((ResponseBody)response.body()).string();
            this.logger.info("Response for edit is : " + val);
        }
        catch (IOException | NullPointerException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }

    private boolean deleteEncounterOpenMRS(EncounterAPIDTO encounterapidto) {
        Gson gson = new Gson();
        String val = "";
        this.logger.info("encounter value : " + gson.toJson((Object)encounterapidto));
        try {
            Call<ResponseBody> callencounter = this.restapiintf.deleteEncounter(encounterapidto.getUuid());
            Response response = callencounter.execute();
            if (!response.isSuccessful()) {
                val = response.errorBody().string();
                this.logger.error("REST failed : " + val);
                return false;
            }
            this.logger.info("Encounter : " + encounterapidto.getUuid() + " deleted");
        }
        catch (IOException | NullPointerException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }
}

