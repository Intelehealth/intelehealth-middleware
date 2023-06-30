package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.dao.PersonDAO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import com.emrmiddleware.exception.ActionException;

public class PersonAction {

	private final Logger logger = LoggerFactory.getLogger(PersonAction.class);
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;

	public PersonAction(String auth) {
		authString = auth;
		apiclient = new APIClient(authString);
		restapiintf = apiclient.getClient().create(RestAPI.class);
	}

	public ArrayList<PersonDTO> setPersons(ArrayList<PersonAPIDTO> personList) throws DAOException, ActionException {
		ArrayList<PersonDTO> persons = new ArrayList<PersonDTO>();
		PersonDTO persondto ;
		PersonAPIDTO personforerror = new PersonAPIDTO();
		boolean isPersonSet=true;
		Gson gson = new Gson();
		try {
			for ( PersonAPIDTO person : personList) {
				personforerror=person;
				if (isPersonExists(person.getUuid())) {
					isPersonSet=editPersonOpenMRS(person);
				} else {
					isPersonSet=addPersonOpenMRS(person);
				}
				persondto = new PersonDTO();
				persondto.setUuid(person.getUuid());
				persondto.setSyncd(isPersonSet);
				persons.add(persondto);
			}
		} catch (Exception e) {
			 logger.error("Error occurred for json string : "+gson.toJson(personforerror));
			 logger.error(e.getMessage(),e);
			//throw new ActionException(e.getMessage(), e);
		}
		return persons;

	}

	private boolean isPersonExists(String personuuid) throws DAOException {
		boolean isPersonExists = false;
		PersonDAO persondao = new PersonDAO();
		PersonDTO persondto = persondao.getPerson(personuuid);
		if (persondto != null) {
			isPersonExists = true;
		}
		return isPersonExists;

	}

	private boolean addPersonOpenMRS(PersonAPIDTO persondto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("add person value : " + gson.toJson(persondto));
		
		try {
			Call<ResponseBody> callperson = restapiintf.addPerson(persondto);
			Response<ResponseBody> response = callperson.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : " + val);
				return false;
			}
			logger.info("Response is : " + val);
		} catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return false;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private boolean editPersonOpenMRS(PersonAPIDTO persondto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("edit person value : " + gson.toJson(persondto));
		
		try {
			Call<ResponseBody> callperson = restapiintf.editPerson(persondto.getUuid(), persondto);
			Response<ResponseBody> response = callperson.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error("REST failed : " + val);
				return false;
			}
			logger.info("Response for edit is : " + val);
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
