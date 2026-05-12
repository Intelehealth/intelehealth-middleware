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

import com.emrmiddleware.action.PersonAction;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class PatientAction {
    private final Logger logger = LoggerFactory.getLogger(PersonAction.class);
    RestAPI restIdapiintf;
    APIClient apiclient;
    RestAPI restapiintf;
    String authString;

    public PatientAction(String auth) {
        this.authString = auth;
        this.apiclient = new APIClient(this.authString);
        this.restapiintf = (RestAPI)this.apiclient.getClient().create(RestAPI.class);
        this.restIdapiintf = (RestAPI)this.apiclient.getIdClient().create(RestAPI.class);
    }

    public ArrayList<PatientDTO> setPatients(ArrayList<PatientAPIDTO> patientList) throws DAOException, ActionException {
        ArrayList<PatientDTO> patients = new ArrayList<PatientDTO>();
        PatientDTO patientdto = null;
        PatientAPIDTO patientforerror = new PatientAPIDTO();
        boolean isPatientSet = true;
        Gson gson = new Gson();
        try {
            Iterator<PatientAPIDTO> iterator = patientList.iterator();
            while (iterator.hasNext()) {
                PatientAPIDTO patient;
                patientforerror = patient = iterator.next();
                String openMrsId = "";
                PatientDAO patientdao = new PatientDAO();
                PatientDTO patientDTO = patientdao.getPatient(patient.getPerson());
                if (patientDTO == null) {
                    openMrsId = this.getOpenMrsId();
                    patient.getIdentifiers().get(0).setIdentifier(openMrsId);
                    isPatientSet = this.addPatientOpenMRS(patient);
                } else {
                    openMrsId = patientDTO.getOpenmrs_id();
                }
                patientdto = new PatientDTO();
                patientdto.setUuid(patient.getPerson());
                patientdto.setSyncd(isPatientSet);
                if (isPatientSet) {
                    patientdto.setOpenmrs_id(openMrsId);
                }
                patients.add(patientdto);
            }
        }
        catch (Exception e) {
            this.logger.error("Error occurred for json string : " + gson.toJson((Object)patientforerror));
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return patients;
    }

    private String getOpenMrsId() {
        String openmrsid = "";
        String val = "";
        Gson gson = new Gson();
        try {
            AuthenticationUtil authenticationUtil = new AuthenticationUtil();
            UserCredentialDTO userCredentialdto = authenticationUtil.getAuthHeader(this.authString);
            Call<ResponseBody> call = this.restIdapiintf.getOpenMrsId("1", userCredentialdto.getUsername(), userCredentialdto.getPassword());
            Response response = call.execute();
            if (!response.isSuccessful()) {
                val = response.errorBody().string();
                this.logger.error("REST failed : " + val);
                return openmrsid;
            }
            val = ((ResponseBody)response.body()).string();
            this.logger.info("Response for ID gen is : " + val);
            IDGenAPIDTO idgenapidto = (IDGenAPIDTO)gson.fromJson(val, IDGenAPIDTO.class);
            openmrsid = idgenapidto.getIdentifiers()[0];
            this.logger.info("Response is : " + val);
        }
        catch (IOException | NullPointerException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return openmrsid;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return openmrsid;
        }
        return openmrsid;
    }

    private boolean addPatientOpenMRS(PatientAPIDTO patientdto) {
        Gson gson = new Gson();
        String val = "";
        this.logger.info("patient value : " + gson.toJson((Object)patientdto));
        try {
            Call<ResponseBody> callpatient = this.restapiintf.addPatient(patientdto);
            Response response = callpatient.execute();
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
}

