package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.AddressDTO;
import com.emrmiddleware.api.dto.NameDTO;
import com.emrmiddleware.api.dto.PersonDTO;
import com.emrmiddleware.dto.PatientDTO;

import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.emrmiddleware.exception.ActionException;

public class PatientAction {

	private final Logger logger = LoggerFactory.getLogger(PatientAction.class);
	RestAPI restapiintf=APIClient.getClient().create(RestAPI.class);

	public ArrayList<PatientDTO> setPatients(ArrayList<PatientDTO> patientList) throws DAOException, ActionException {
		ArrayList<PatientDTO> patients = new ArrayList<PatientDTO>();
		try {
			for (PatientDTO patient : patientList) {
				processPatient(patient);
			}
		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);
		}
		return patients;

	}

	private boolean processPatient(PatientDTO patient) {
        PersonDTO persondto = getPersonFromPatient(patient);
        addPersonOpenMRS(persondto);
        logger.info("After executing post to openmrs");
		return true;
	}
   
	private PersonDTO getPersonFromPatient(PatientDTO patientdto){
		PersonDTO persondto = new PersonDTO();
		NameDTO namedto = new NameDTO();
		AddressDTO addressdto = new AddressDTO();
		persondto.setUuid(patientdto.getUuid());
		namedto.setGivenName(patientdto.getFirstname());
		namedto.setMiddleName(patientdto.getMiddlename());
		namedto.setFamilyName(patientdto.getLastname());
		persondto.addName(namedto);
		addressdto.setAddress1(patientdto.getAddress1());
		addressdto.setAddress2(patientdto.getAddress2());
		addressdto.setCityVillage(patientdto.getCityvillage());
		addressdto.setCountry(patientdto.getCountry());
		addressdto.setPostalCode(patientdto.getPostalcode());
		addressdto.setStateProvince(patientdto.getStateprovince());
		persondto.addAddresses(addressdto);
		persondto.setBirthdate("1996-02-06");
		persondto.setGender(patientdto.getGender());
		
		return persondto;
		
	}
	private boolean addPersonOpenMRS(PersonDTO persondto){
		 Gson gson = new Gson();
		logger.info("patient value : " +gson.toJson(persondto));
		 Call<ResponseBody> callperson = restapiintf.addPerson(persondto);
		 try {
			//ResponseBody response=callperson.execute().body();
		    Response<ResponseBody> response = callperson.execute();
		    String val = response.body().string();
			logger.info("Response is : "+val);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       /* callperson.enqueue(new Callback<ResponseBody>() {
	         
				@Override
				public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
					// TODO Auto-generated method stub
					
				}



				@Override
				public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
					// TODO Auto-generated method stub
					if (response.isSuccessful()){
						logger.info("post successful");
					}else{
						logger.info("post not successful");
					}
					
				}

				


				
	        });*/

		return true;
	}

}
