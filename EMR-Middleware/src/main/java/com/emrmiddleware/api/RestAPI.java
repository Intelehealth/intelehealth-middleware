/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  okhttp3.ResponseBody
 *  retrofit2.Call
 *  retrofit2.http.Body
 *  retrofit2.http.DELETE
 *  retrofit2.http.GET
 *  retrofit2.http.POST
 *  retrofit2.http.Path
 *  retrofit2.http.Query
 */
package com.emrmiddleware.api;

import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.dto.CustomAppointmentDTO;
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
    @POST(value="patients")
    public Call<PatientDTO> addPatient(@Body PatientDTO var1);

    @POST(value="person")
    public Call<ResponseBody> addPerson(@Body PersonAPIDTO var1);

    @POST(value="person/{uuid}")
    public Call<ResponseBody> editPerson(@Path(value="uuid") String var1, @Body PersonAPIDTO var2);

    @POST(value="patient")
    public Call<ResponseBody> addPatient(@Body PatientAPIDTO var1);

    @POST(value="person/{uuid}")
    public Call<ResponseBody> editPerson(@Path(value="uuid") String var1, @Body PatientAPIDTO var2);

    @GET(value="generateIdentifier.form")
    public Call<ResponseBody> getOpenMrsId(@Query(value="source") String var1, @Query(value="username") String var2, @Query(value="password") String var3);

    @POST(value="visit")
    public Call<ResponseBody> addVisit(@Body VisitAPIDTO var1);

    @POST(value="visit/{uuid}")
    public Call<ResponseBody> editVisit(@Path(value="uuid") String var1, @Body VisitAPIDTO var2);

    @POST(value="encounter")
    public Call<ResponseBody> addEncounter(@Body EncounterAPIDTO var1);

    @POST(value="encounter/{uuid}")
    public Call<ResponseBody> editEncounter(@Path(value="uuid") String var1, @Body EncounterAPIDTO var2);

    @DELETE(value="encounter/{uuid}")
    public Call<ResponseBody> deleteEncounter(@Path(value="uuid") String var1);

    @POST(value="appointment/bookAppointment")
    public Call<ResponseBody> addAppointment(@Body CustomAppointmentDTO var1);

    @POST(value="appointment/rescheduleAppointment")
    public Call<ResponseBody> editAppointment(@Body CustomAppointmentDTO var1);
}

