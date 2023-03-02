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

import java.io.IOException;
import java.util.ArrayList;

public class AppointmentAction {

    String authString;

    private final Logger logger = LoggerFactory.getLogger(EncounterAction.class);
    APIClient apiclient;
    RestAPI restapiintf;

    //AppointmentDTO appointmentDTO;
    public AppointmentAction(String authString) {
        this.authString = authString;
        apiclient = new APIClient(authString);
        restapiintf = apiclient.getMMClient().create(RestAPI.class);
    }
    public boolean addAppointmentOpenMRS(ArrayList<CustomAppointmentDTO> appointmentdto) {
        Gson gson = new Gson();
        String val = "";


        try {

            for (CustomAppointmentDTO appointment: appointmentdto) {


                logger.info("appointment value : " + gson.toJson(appointment));

                Call<ResponseBody> addAppointment = restapiintf.addAppointment(appointment);
                Response<ResponseBody> response = addAppointment.execute();
                logger.info(response.message());
                if (response.isSuccessful()) {
                    val = response.body().string();
                    appointment.setSyncd(true);
                } else {
                    val = response.errorBody().string();
                    logger.error("REST failed : " + val);
                    return false;
                }
                logger.info("Response is : " + val);
            }
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
