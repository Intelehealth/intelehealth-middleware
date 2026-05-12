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
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.dao.VisitDAO;
import com.emrmiddleware.dto.VisitDTO;
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

public class VisitAction {
    private final Logger logger = LoggerFactory.getLogger(VisitAction.class);
    APIClient apiclient;
    RestAPI restapiintf;
    String authString;

    public VisitAction(String auth) {
        this.authString = auth;
        this.apiclient = new APIClient(this.authString);
        this.restapiintf = (RestAPI)this.apiclient.getClient().create(RestAPI.class);
    }

    public ArrayList<VisitDTO> setVisits(ArrayList<VisitAPIDTO> visitList) throws DAOException, ActionException {
        ArrayList<VisitDTO> visits = new ArrayList<VisitDTO>();
        VisitAPIDTO visitforerror = new VisitAPIDTO();
        boolean isVisitSet = true;
        Gson gson = new Gson();
        try {
            Iterator<VisitAPIDTO> iterator = visitList.iterator();
            while (iterator.hasNext()) {
                VisitAPIDTO visit;
                visitforerror = visit = iterator.next();
                isVisitSet = this.isVisitExists(visit.getUuid()) ? this.editVisitOpenMRS(visit) : this.addVisitOpenMRS(visit);
                VisitDTO visitdto = new VisitDTO();
                visitdto.setUuid(visit.getUuid());
                visitdto.setSyncd(isVisitSet);
                visits.add(visitdto);
            }
        }
        catch (Exception e) {
            this.logger.error("Error occurred for json string : " + gson.toJson((Object)visitforerror));
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return visits;
    }

    private boolean isVisitExists(String visituuid) throws DAOException {
        boolean isVisitExists = false;
        VisitDAO visitdao = new VisitDAO();
        VisitDTO visitdto = visitdao.getVisit(visituuid);
        if (visitdto != null) {
            isVisitExists = true;
        }
        return isVisitExists;
    }

    private boolean addVisitOpenMRS(VisitAPIDTO visitapidto) {
        Gson gson = new Gson();
        String val = "";
        this.logger.info("visit value : " + gson.toJson((Object)visitapidto));
        try {
            Call<ResponseBody> callvisit = this.restapiintf.addVisit(visitapidto);
            Response response = callvisit.execute();
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

    private boolean editVisitOpenMRS(VisitAPIDTO visitapidto) {
        Gson gson = new Gson();
        String val = "";
        try {
            visitapidto.setPatient(null);
            this.logger.info("edit visit value : " + gson.toJson((Object)visitapidto));
            Call<ResponseBody> callvisit = this.restapiintf.editVisit(visitapidto.getUuid(), visitapidto);
            Response response = callvisit.execute();
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
}

