package com.emrmiddleware.action;

import java.io.IOException;
import java.util.ArrayList;

import com.emrmiddleware.api.dto.AttributeAPIDTO;
import com.emrmiddleware.api.dto.ObsAPIDTO;
import com.emrmiddleware.api.dto.VisitAttributeAPIDTO;
import com.emrmiddleware.dto.VisitAttributeDTO;
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

	final String VISIT_HOLDER_ATTRIBUTE_ID = "a0378be4-d9c6-4cb2-bbf5-777e27a32efc";

	final String VISIT_SPECIALITY_ATTRIBUTE_ID = "3f296939-c6d3-4d2e-b8ca-d7f4bfd42c2d";

	final String VISIT_READ_ATTRIBUTE_ID = "2e4b62a5-aa71-43e2-abc9-f4a777697b19";
	final String VISIT_DECISION_PENDING_ID = "2bfbfd6c-7714-4432-a7e8-b0130889c2ff";

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

			ArrayList<AttributeAPIDTO> vAttributeAPIDTOArrayList = new ArrayList<AttributeAPIDTO>();

			for(AttributeAPIDTO visitAttributeDTO: visitapidto.getAttributes()) {
				//Putting the logic of updating visit attributes EZ-569

				vAttributeAPIDTOArrayList.add(visitAttributeDTO);
				VisitAttributeAPIDTO vaDTO = new VisitAttributeAPIDTO();
				vaDTO.setValue(visitAttributeDTO.getValue());
				Call<ResponseBody> updateVisitAttribute = restapiintf.editVA(visitapidto.getUuid(),
						visitAttributeDTO.getUuid(),
						vaDTO );
				Response<ResponseBody> response = updateVisitAttribute.execute();
				if (response.isSuccessful()) {
					val = response.body().string();
				} else {
					val = response.errorBody().string();
					logger.error("REST failed : " + val);
					return false;
				}
				logger.info("Response for edit is : " + val);
			}


			visitapidto.getAttributes().removeAll(vAttributeAPIDTOArrayList);
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
