package com.emrmiddleware.action;

import java.io.IOException;
import java.util.*;

import com.emrmiddleware.api.dto.AttributeAPIDTO;
import com.emrmiddleware.api.dto.LinkDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.dao.VisitDAO;
import com.emrmiddleware.dto.VisitDTO;

import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


import static java.text.MessageFormat.*;

public class VisitAction {

	private final Logger logger = LoggerFactory.getLogger(VisitAction.class);
	APIClient apiclient;
	RestAPI restapiintf;
	String authString;

	public VisitAction(String auth) {
		authString = auth;
		apiclient = new APIClient(authString);
		restapiintf = apiclient.getClient().create(RestAPI.class);
	}

	public ArrayList<VisitDTO> setVisits(ArrayList<VisitAPIDTO> visitList) throws DAOException {
		ArrayList<VisitDTO> visits = new ArrayList<VisitDTO>();
		VisitDTO visitdto;
		VisitAPIDTO visitforerror = new VisitAPIDTO();
		boolean isVisitSet = true;
		Gson gson = new Gson();
		try {
			for (VisitAPIDTO visit : visitList) {
				visitforerror = visit;
				if (isVisitExists(visit.getUuid())) {
					isVisitSet = editVisitOpenMRS(visit);
				} else {
					isVisitSet = addVisitOpenMRS(visit);
				}
				visitdto = new VisitDTO();
				visitdto.setUuid(visit.getUuid());
				visitdto.setSyncd(isVisitSet);
				visits.add(visitdto);
			}
		} catch (Exception e) {
			logger.error("Error occurred for json string : {}" , gson.toJson(visitforerror));
			logger.error(e.getMessage(), e);

		}
		return visits;

	}

	private boolean isVisitExists(String visituuid) throws DAOException {
		boolean isVisitExists = false;
		VisitDAO visitdao = new VisitDAO();
		VisitDTO visitdto = visitdao.getVisit(visituuid);
		if (visitdto != null) {
			isVisitExists = true;
		}
		return isVisitExists;

	}

	private boolean addVisitOpenMRS(VisitAPIDTO visitapidto) {
		Gson gson = new Gson();
		String val = "";
        logger.info("visit value : {}", gson.toJson(visitapidto));

		try {
			Call<ResponseBody> callvisit = restapiintf.addVisit(visitapidto);
			Response<ResponseBody> response = callvisit.execute();
			if (response.isSuccessful()) {
				val = response.body().string();

				JsonObject jsonObject = new JsonParser().parse(val).getAsJsonObject();
				String visitUUID = jsonObject.get("uuid").getAsString();
				logger.info("Visit UUID after insertion {}",visitUUID);
				LinkAction linkAction = new LinkAction(authString);
				LinkDTO linkDTO = new LinkDTO();
				linkDTO.setLink("/i/"+visitUUID);
				linkAction.genLink(linkDTO);

			} else {
				val = response.errorBody().string();
				logger.error("REST failed : {}" , val);
				return false;
			}
			logger.info("Response is : {}",  val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private boolean editVisitOpenMRS(VisitAPIDTO visitapidto) {
		Gson gson = new Gson();
		String val = "";
		

		try {
			visitapidto.setPatient(null);// Not allowed to be set in edit for
											// visit in openmrs
			logger.info(format("edit visit value : {0}", gson.toJson(visitapidto)));
			Call<ResponseBody> callvisit = restapiintf.editVisit(visitapidto.getUuid(), visitapidto);
			Response<ResponseBody> response = callvisit.execute();
			if (response.isSuccessful()) {
				val = response.body().string();
			} else {
				val = response.errorBody().string();
				logger.error(String.format("REST failed : %s", val));
				return false;
			}
            logger.info("Response for edit is : {}", val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}


	public void addSummaryLink(String visitUUID, AttributeAPIDTO attributeAPIDTO) {
		try{
			Call<ResponseBody> callvisit = restapiintf.addVisitAttribute(visitUUID, attributeAPIDTO);

		Response<ResponseBody> response = callvisit.execute();
		String val = response.body().string();
		logger.info("Response of addSummaryLink is : {}",val);
		}
		catch (IOException e){
			logger.error("Exception in addSummaryLink {}", e.getMessage());
		}
	}
}
