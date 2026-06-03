
package com.emrmiddleware.api;

import com.emrmiddleware.api.BasicAuthInterceptor;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.conf.ResourcesEnvironment;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private Retrofit retrofit = null;
    String authString;

    public APIClient(String authHeader) {
        this.authString = authHeader;
    }

    public Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        AuthenticationUtil authenticationUtil = new AuthenticationUtil();
        UserCredentialDTO userCredentialdto = authenticationUtil.getAuthHeader(this.authString);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor((Interceptor)new BasicAuthInterceptor(userCredentialdto.getUsername(), userCredentialdto.getPassword())).build();
        ResourcesEnvironment dbenv = new ResourcesEnvironment();
        Gson gson = new GsonBuilder().setLenient().create();
        this.retrofit = new Retrofit.Builder().baseUrl(dbenv.getAPIBaseURL()).addConverterFactory((Converter.Factory)GsonConverterFactory.create((Gson)gson)).client(client).build();
        return this.retrofit;
    }

    public Retrofit getIdClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        AuthenticationUtil authenticationUtil = new AuthenticationUtil();
        UserCredentialDTO userCredentialdto = authenticationUtil.getAuthHeader(this.authString);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor((Interceptor)new BasicAuthInterceptor(userCredentialdto.getUsername(), userCredentialdto.getPassword())).build();
        ResourcesEnvironment dbenv = new ResourcesEnvironment();
        Gson gson = new GsonBuilder().setLenient().create();
        this.retrofit = new Retrofit.Builder().baseUrl(dbenv.getIdGenUrl()).addConverterFactory((Converter.Factory)GsonConverterFactory.create((Gson)gson)).client(client).build();
        return this.retrofit;
    }

    public Retrofit getMMClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        AuthenticationUtil authenticationUtil = new AuthenticationUtil();
        UserCredentialDTO userCredentialdto = authenticationUtil.getAuthHeader(this.authString);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor((Interceptor)new BasicAuthInterceptor(userCredentialdto.getUsername(), userCredentialdto.getPassword())).build();
        ResourcesEnvironment dbenv = new ResourcesEnvironment();
        Gson gson = new GsonBuilder().setLenient().create();
        this.retrofit = new Retrofit.Builder().baseUrl(dbenv.getMMBaseURL()).addConverterFactory((Converter.Factory)GsonConverterFactory.create((Gson)gson)).client(client).build();
        return this.retrofit;
    }
}

