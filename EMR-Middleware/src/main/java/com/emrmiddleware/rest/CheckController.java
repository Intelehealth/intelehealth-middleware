package com.emrmiddleware.rest;


import com.emrmiddleware.action.CheckPatientAction;

import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.dto.ExternalPatientDTO;

import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.resource.Resources;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("CHECK FOR EXISITING PATIENT DATA COMING IN FROM OTHER EHRs")
@Path("check")
public class CheckController {
	private final Logger logger = LoggerFactory.getLogger(CheckController.class);
	@Context
	ServletContext context;

	@Path("id/{abhanumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getPatient(@PathParam("abhanumber") String abhaNumber,
							@Context HttpHeaders httpHeaders) {

		ResponseDTO responsedto = new ResponseDTO();
		ExternalPatientDTO externalPatientDTO = new ExternalPatientDTO();
		String authString = null;
		Gson gson = new Gson();
		try {
			AuthenticationUtil authutil = new AuthenticationUtil();	
			authString= httpHeaders.getHeaderString("authorization");

			boolean isAuthenticated = authutil.isUserAuthenticated(authString);
			if ((!isAuthenticated) || (authString == null)) {
				logger.error("No Authorization");
				responsedto.setStatusMessage(Resources.ERROR, Resources.AUTHERROR, Resources.UNABLETOPROCESS);
				return Response.status(403).entity(gson.toJson(responsedto)).build();
			}
			CheckPatientAction checkPatientAction = new CheckPatientAction();

			externalPatientDTO = checkPatientAction.getPatientData( abhaNumber);
			if(externalPatientDTO != null) {
				responsedto.setStatus(Resources.OK);
				responsedto.setData(externalPatientDTO);
			}

		} catch (DAOException e) {
			logger.error(Resources.DAOEXCEPTION, e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errMsg = Resources.CONTROLLEREXCEPTION + e.getMessage();
			logger.error(errMsg);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		}

		return Response.status(200).entity(gson.toJson(responsedto)).build();

	}

}
