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

import com.emrmiddleware.action.EncounterAction;
import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.dto.CustomAppointmentDTO;
import com.google.gson.Gson;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class AppointmentAction {
    String authString;
    private final Logger logger = LoggerFactory.getLogger(EncounterAction.class);
    APIClient apiclient;
    RestAPI restapiintf;

    public AppointmentAction(String authString) {
        this.authString = authString;
        this.apiclient = new APIClient(authString);
        this.restapiintf = (RestAPI)this.apiclient.getMMClient().create(RestAPI.class);
    }

    public boolean addAppointmentOpenMRS(ArrayList<CustomAppointmentDTO> appointmentdto) {
        Gson gson = new Gson();
        String val = "";
        try {
            for (CustomAppointmentDTO appointment : appointmentdto) {
                Response response;
                Call<ResponseBody> addAppointment;
                this.logger.info("appointment value : " + gson.toJson((Object)appointment));
                if (appointment.getAppointmentId() == 0) {
                    addAppointment = this.restapiintf.addAppointment(appointment);
                    response = addAppointment.execute();
                    this.logger.info(response.message());
                    if (!response.isSuccessful()) {
                        val = response.errorBody().string();
                        this.logger.error("REST failed : " + val);
                        return false;
                    }
                    val = ((ResponseBody)response.body()).string();
                    appointment.setSyncd(true);
                    this.logger.info("Response is : " + val);
                    continue;
                }
                addAppointment = this.restapiintf.editAppointment(appointment);
                response = addAppointment.execute();
                this.logger.info(response.message());
                if (!response.isSuccessful()) {
                    val = response.errorBody().string();
                    this.logger.error("REST failed : " + val);
                    return false;
                }
                val = ((ResponseBody)response.body()).string();
                appointment.setSyncd(true);
                this.logger.info("Response is : " + val);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }
}

