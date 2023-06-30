package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.dao.VisitDAO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

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

	public ArrayList<VisitDTO> setVisits(ArrayList<VisitAPIDTO> visitList) throws DAOException, ActionException {
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
			logger.error("Error occurred for json string : " + gson.toJson(visitforerror));
			logger.error(e.getMessage(), e);
			// throw new ActionException(e.getMessage(), e);
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
		logger.info("visit value : " + gson.toJson(visitapidto));

		try {
			Call<ResponseBody> callvisit = restapiintf.addVisit(visitapidto);
			Response<ResponseBody> response = callvisit.execute();
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

	private boolean editVisitOpenMRS(VisitAPIDTO visitapidto) {
		Gson gson = new Gson();
		String val = "";
		

		try {
			visitapidto.setPatient(null);// Not allowed to be set in edit for
											// visit in openmrs
			logger.info("edit visit value : " + gson.toJson(visitapidto));
			Call<ResponseBody> callvisit = restapiintf.editVisit(visitapidto.getUuid(), visitapidto);
			Response<ResponseBody> response = callvisit.execute();
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
