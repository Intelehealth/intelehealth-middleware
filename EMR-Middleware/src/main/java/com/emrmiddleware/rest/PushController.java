package com.emrmiddleware.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.emrmiddleware.api.dto.*;
import com.emrmiddleware.dto.*;
import com.google.gson.JsonObject;
//import com.treblle.spring.annotation.EnableTreblle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.emrmiddleware.action.PushDataAction;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.resource.Resources;
import com.google.gson.Gson;

import io.swagger.annotations.Api;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Api("PUSH DATA")
@Path("push")
//@EnableTreblle


public class PushController {
	private final Logger logger = LoggerFactory.getLogger(PushController.class);
	@Context
	ServletContext context;

	@Path("pushdata")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response setData(PushDataDTO pushdatadto, @Context HttpHeaders httpHeaders) {

		ResponseDTO responsedto = new ResponseDTO();
		Gson gson = new Gson();


		String authString = null;
		try {
			AuthenticationUtil authutil = new AuthenticationUtil();
			authString= httpHeaders.getHeaderString("authorization");
			logger.info("AuthString : "+authString);
			boolean isAuthenticated = authutil.isUserAuthenticated(authString);
			if ((!isAuthenticated) || (authString == null)) {
				logger.error("No Authorization");
				responsedto.setStatusMessage(Resources.ERROR, Resources.AUTHERROR, Resources.UNABLETOPROCESS);
				return Response.status(403).entity(gson.toJson(responsedto)).build();
			}
			//logger.error("Sending data to push");
			PushDataAction pushdataaction = new PushDataAction(authString);

			PullDataDTO pulldatadto = new PullDataDTO();
			//logger.error("PullData DTO created");
			PushDataDTO pdd = new PushDataDTO();
			//logger.error("PushData DTO created");
			pdd = pushdatadto;

			pulldatadto = pushdataaction.pushData(pushdatadto);
			responsedto.setStatus(Resources.OK);
			responsedto.setData(pulldatadto);

		} catch (DAOException e) {
			logger.error(Resources.DAOEXCEPTION, e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		} catch (Exception e) {
			logger.error(Resources.CONTROLLEREXCEPTION + e.getMessage());
			logger.error("AAA", e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		}
		return Response.status(200).entity(gson.toJson(responsedto)).build();
	}
}
