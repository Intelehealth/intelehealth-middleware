package com.emrmiddleware.api;

import com.emrmiddleware.api.dto.*;
import com.emrmiddleware.dto.CustomAppointmentDTO;
import com.emrmiddleware.dto.PatientDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface RestAPI {

    @POST("patients")
    Call<PatientDTO> addPatient(@Body PatientDTO patientdto);

   @GET("patient")
   Call<PatientDTO>  getPatient(@Query("firstname") String firstname,@Query("middlename") String middlename,
                                @Query("lastname") String lastname, @Query("gender") String gender,
                                @Query("dateofbirth") String dateofbirth, @Query("phonenumber") String phoneNumber);

    @POST("person")
    Call<ResponseBody> addPerson(@Body PersonAPIDTO persondto);

    @POST("person/{uuid}")
    Call<ResponseBody> editPerson(@Path("uuid") String uuid, @Body PersonAPIDTO persondto);

    @POST("patient")
    Call<ResponseBody> addPatient(@Body PatientAPIDTO patientapidto);

    @POST("person/{uuid}")
    Call<ResponseBody> editPerson(@Path("uuid") String uuid, @Body PatientAPIDTO patientapidto);

    @GET("generateIdentifier.form")
    Call<ResponseBody> getOpenMrsId(@Query("source") String source, @Query("username") String username, @Query("password") String password);

    @POST("visit")
    Call<ResponseBody> addVisit(@Body VisitAPIDTO visitapidto);

    @POST("visit/{uuid}")
    Call<ResponseBody> editVisit(@Path("uuid") String uuid, @Body VisitAPIDTO visitapidto);

    @POST("visit/{uuid}/attribute")
    Call<ResponseBody> addVisitAttribute(@Path("uuid") String uuid, @Body AttributeAPIDTO visitattributedto);

    @POST("encounter")
    Call<ResponseBody> addEncounter(@Body EncounterAPIDTO encounterapidto);

    @POST("encounter/{uuid}")
    Call<ResponseBody> editEncounter(@Path("uuid") String uuid, @Body EncounterAPIDTO visitapidto);

    @DELETE("encounter/{uuid}")
    Call<ResponseBody> deleteEncounter(@Path("uuid") String uuid);

    @POST("appointment/bookAppointment")
    Call<ResponseBody> addAppointment(@Body CustomAppointmentDTO appointmentdto);

    @POST("appointment/rescheduleAppointment")
    Call<ResponseBody> editAppointment(@Body CustomAppointmentDTO appointmentdto);

    @POST("links/shortLink")
    Call<ResponseBody> generateShortLink(@Body LinkDTO linkdto);
}
