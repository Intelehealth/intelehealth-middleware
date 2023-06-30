package com.emrmiddleware.api;

import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.conf.ResourcesEnvironment;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;

public class APIClient {

	private Retrofit retrofit = null;
	String authString;
   
	public APIClient(String authHeader) {
		authString = authHeader;
	}

	public Retrofit getClient() {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		AuthenticationUtil authenticationUtil = new AuthenticationUtil();
		UserCredentialDTO userCredentialdto = authenticationUtil.getAuthHeader(authString);
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(
						new BasicAuthInterceptor(userCredentialdto.getUsername(), userCredentialdto.getPassword()))
				.build();
		ResourcesEnvironment dbenv = new ResourcesEnvironment();
		Gson gson = new GsonBuilder().setLenient().create();
		retrofit = new Retrofit.Builder().baseUrl(dbenv.getAPIBaseURL())
				.addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson)).client(client).build();

		return retrofit;
	}

	public Retrofit getIdClient() {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		AuthenticationUtil authenticationUtil = new AuthenticationUtil();
		UserCredentialDTO userCredentialdto = authenticationUtil.getAuthHeader(authString);
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(
						new BasicAuthInterceptor(userCredentialdto.getUsername(), userCredentialdto.getPassword()))
				.build();
		ResourcesEnvironment dbenv = new ResourcesEnvironment();
		Gson gson = new GsonBuilder().setLenient().create();
		retrofit = new Retrofit.Builder().baseUrl(dbenv.getIdGenUrl())
				.addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson)).client(client).build();

		return retrofit;
	}

}
