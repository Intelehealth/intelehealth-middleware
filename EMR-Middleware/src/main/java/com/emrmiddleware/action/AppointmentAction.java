package com.emrmiddleware.action;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.dto.CustomAppointmentDTO;
import com.google.gson.Gson;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;

public class AppointmentAction {

    private final Logger logger = LoggerFactory.getLogger(AppointmentAction.class);
    String authString;
    APIClient apiclient;
    RestAPI restapiintf;

    public AppointmentAction(String authString) {
        this.authString = authString;
        apiclient = new APIClient(authString);
        restapiintf = apiclient.getMMClient().create(RestAPI.class);
    }

    public boolean addAppointmentOpenMRS(ArrayList<CustomAppointmentDTO> appointmentdto) {
        Gson gson = new Gson();
        String val = "";
        try {
            for (CustomAppointmentDTO appointment : appointmentdto) {
                String appointmentAsString = gson.toJson(appointment);
                logger.info("appointment value : {}", appointmentAsString);
                if (appointment.getAppointmentId() == 0) {

                    Call<ResponseBody> addAppointment = restapiintf.addAppointment(appointment);
                    logger.info("URL called {}", addAppointment.request().url());
                    Response<ResponseBody> response = addAppointment.execute();
                    String responseMessage = response.message();
                    logger.info(responseMessage);
                    if (response.isSuccessful()) {
                        val = response.body().string();
                        appointment.setSyncd(true);
                    } else {
                        val = response.errorBody().string();
                        logger.error("REST failed : {}", val);
                        return false;
                    }
                    logger.info(String.format("Response is : %s", val));
                } else {
                    Call<ResponseBody> addAppointment = restapiintf.editAppointment(appointment);
                    Response<ResponseBody> response = addAppointment.execute();
                    logger.info(response.message());
                    if (response.isSuccessful()) {
                        val = response.body().string();
                        appointment.setSyncd(true);
                    } else {
                        val = response.errorBody().string();
                        logger.error("REST failed : {}", val);
                        return false;
                    }
                    logger.info("Response is : {} ", val);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;

    }

}
