package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.NewObsAPIDTO;
import com.emrmiddleware.api.dto.ObsAPIDTO;
import com.emrmiddleware.dao.ObsDAO;
import com.emrmiddleware.dto.ObsDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.utils.EmrUtils;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ObsAction {
	private final Logger logger = LoggerFactory.getLogger(ObsAction.class);

	RestAPI restIdapiintf;
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;

	public ObsAction(String auth) {
		authString = auth;
		apiclient = new APIClient(authString);
		restapiintf = apiclient.getClient().create(RestAPI.class);
		restIdapiintf = apiclient.getIdClient().create(RestAPI.class);
	}

	public boolean setObs(ArrayList<ObsAPIDTO> obsList,String encounteruuid,String personuuid)
			throws DAOException, ActionException {
		boolean isSetAllObs=true;
		//ArrayList<ObsDTO> obs = new ArrayList<ObsDTO>();
		
		ObsAPIDTO obsforerror = new ObsAPIDTO();
		boolean isObsSet = true;
		Gson gson = new Gson();
		
		try {
			for (ObsAPIDTO obsapidto : obsList) {
				obsapidto.setEncounter(encounteruuid);
				obsapidto.setPerson(personuuid);
				if (obsapidto.getObsDatetime()==null){
					obsapidto.setObsDatetime(EmrUtils.getCurrentTime());
				}
				obsforerror = obsapidto;
				
				ObsDTO obsdto = getObs(obsapidto.getUuid());
				if (obsdto == null){
					//add obs
					isObsSet=addObsOpenMRS(obsapidto);
					if (isObsSet==false)
						isSetAllObs=false;
				}
				//obs exists add obs , if already voided do nothing
				if ((obsdto!=null)&&(isObsVoided(obsdto)==false)){
					//edit obs
					isObsSet=editObsOpenMRS(obsapidto);
					if (isObsSet==false)
						isSetAllObs=false;
				}
				
			}
		} catch (Exception e) {
			logger.error("Error occurred for json string : " + gson.toJson(obsforerror));
			logger.error(e.getMessage(), e);
			// throw new ActionException(e.getMessage(), e);
		}
		return isSetAllObs;
	}
	
	private boolean isObsVoided(ObsDTO obsdto){
		boolean isObsvoided = true;
		if (obsdto!=null){
			if (obsdto.getVoided()==0){
			    isObsvoided=false;
			}
		}
		return isObsvoided;
	}
	private ObsDTO getObs(String obsuuid) throws DAOException{
		ObsDTO obsdto = new ObsDTO();
		ObsDAO obsdao = new ObsDAO();
		obsdto = obsdao.getObs(obsuuid);
		return obsdto;
	}
	
	private boolean addObsOpenMRS(ObsAPIDTO obsapidto) {
		Gson gson = new Gson();
		String val = "";
		logger.info("obs value : " + gson.toJson(obsapidto));

		try {
			Call<ResponseBody> callobs = restapiintf.addObs(obsapidto);
			Response<ResponseBody> response = callobs.execute();
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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private boolean editObsOpenMRS(ObsAPIDTO obsapidto) {
		Gson gson = new Gson();
		String val = "";
		

		try {
			String uuid=obsapidto.getUuid();
			uuid=uuid.replaceAll("\\s", "");
			obsapidto.setUuid(null);
			NewObsAPIDTO newobsapidto = new NewObsAPIDTO();
			newobsapidto.setConcept(obsapidto.getConcept());
			newobsapidto.setEncounter(obsapidto.getEncounter());
			newobsapidto.setObsDatetime(obsapidto.getObsDatetime());
			newobsapidto.setPerson(obsapidto.getPerson());
			newobsapidto.setValue(obsapidto.getValue());
			logger.info("obs uuid is : "+uuid);
			logger.info("obs value : " + gson.toJson(newobsapidto));
			
			Call<ResponseBody> callobs = restapiintf.editObs(uuid,newobsapidto);
			Response<ResponseBody> response = callobs.execute();
			
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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
}
