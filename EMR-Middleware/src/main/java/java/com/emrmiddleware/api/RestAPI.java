package com.emrmiddleware.api;

import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.dto.PatientDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestAPI {
	
	@POST("patients")
	Call<PatientDTO> addPatient(@Body PatientDTO patientdto);
	
   @POST("person")
   Call<ResponseBody> addPerson(@Body PersonAPIDTO persondto);
   
   @POST("person/{uuid}")
   Call<ResponseBody> editPerson(@Path("uuid") String uuid,@Body PersonAPIDTO persondto);
   
   @POST("patient")
   Call<ResponseBody> addPatient(@Body PatientAPIDTO patientapidto);
   
   @POST("person/{uuid}")
   Call<ResponseBody> editPerson(@Path("uuid") String uuid,@Body PatientAPIDTO patientapidto);
   
   @GET("generateIdentifier.form")
   Call<ResponseBody> getOpenMrsId(@Query("source") String source,@Query("username") String username,@Query("password") String password);
   
   @POST("visit")
   Call<ResponseBody> addVisit(@Body VisitAPIDTO visitapidto);
   
   @POST("visit/{uuid}")
   Call<ResponseBody> editVisit(@Path("uuid") String uuid,@Body VisitAPIDTO visitapidto);
   
   @POST("encounter")
   Call<ResponseBody> addEncounter(@Body EncounterAPIDTO encounterapidto);
   
   @POST("encounter/{uuid}")
   Call<ResponseBody> editEncounter(@Path("uuid") String uuid,@Body EncounterAPIDTO visitapidto);
   
   @DELETE("encounter/{uuid}")
   Call<ResponseBody> deleteEncounter(@Path("uuid") String uuid);
   
   
   

}
