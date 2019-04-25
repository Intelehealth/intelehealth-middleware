package com.emrmiddleware.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.action.PushDataAction;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.PushDataDTO;
import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.resource.Resources;
import com.google.gson.Gson;

@Path("push")
public class PushData {
	private final Logger logger = LoggerFactory.getLogger(PushData.class);
	@Context
	ServletContext context;
	
	@Path("pushdata")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response setData(PushDataDTO pushdatadto,@HeaderParam("authorization") String authString){
		
		ResponseDTO responsedto = new ResponseDTO();
		Gson gson = new Gson();
		PushDataAction pushdataaction = new PushDataAction(authString);
		PullDataDTO pulldatadto = new PullDataDTO();
		//PushDataDTO pushdatadto = new PushDataDTO();
		//pushdatadto = gson.fromJson(pushdata,PushDataDTO.class);
		try {
			pulldatadto=pushdataaction.pushData(pushdatadto);
			responsedto.setStatus(Resources.OK);
			responsedto.setData(pulldatadto);
		} catch (DAOException e) {
			logger.error(Resources.DAOEXCEPTION, e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		} catch (Exception e) {
			logger.error(Resources.CONTROLLEREXCEPTION + e.getMessage());
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		}
		return Response.status(200).entity(gson.toJson(responsedto)).build();
	}

}
