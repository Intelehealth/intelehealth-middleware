package com.emrmiddleware.api;
import com.emrmiddleware.conf.DBEnvironment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;



public class APIClient {
	
	 private  Retrofit retrofit = null;

	    public  Retrofit getClient() {

	        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
	        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
	        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor("admin", "Admin123")).build();//Hardcode of username and password will go in final release
            DBEnvironment dbenv = new DBEnvironment();
	        Gson gson = new GsonBuilder()
	                .setLenient()
	                .create();
	        retrofit = new Retrofit.Builder()
	                .baseUrl(dbenv.getAPIBaseURL())
	                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
	                .client(client)
	                .build();



	        return retrofit;
	    }
	    
	    public  Retrofit getIdClient() {

	        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
	        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
	        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor("admin", "Admin123")).build();//Hardcode of username and password will go in final release
            DBEnvironment dbenv = new DBEnvironment();
	        Gson gson = new GsonBuilder()
	                .setLenient()
	                .create();
	        retrofit = new Retrofit.Builder()
	                .baseUrl(dbenv.getIdGenUrl())
	                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
	                .client(client)
	                .build();



	        return retrofit;
	    }
	    
	    

}
