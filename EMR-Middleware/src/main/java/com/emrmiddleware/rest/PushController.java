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


import com.emrmiddleware.dto.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.emrmiddleware.action.PushDataAction;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.resource.Resources;
import com.google.gson.Gson;
import io.swagger.annotations.Api;

@Api("PUSH DATA")
@Path("push")



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
			logger.info("AuthString : {} ", authString);
			boolean isAuthenticated = authutil.isUserAuthenticated(authString);
			if ((!isAuthenticated) || (authString == null)) {
				logger.error("No Authorization");
				responsedto.setStatusMessage(Resources.ERROR, Resources.AUTHERROR, Resources.UNABLETOPROCESS);
				return Response.status(403).entity(gson.toJson(responsedto)).build();
			}

			PushDataAction pushdataaction = new PushDataAction(authString);

            PullDataDTO pulldatadto = pushdataaction.pushData(pushdatadto);
			responsedto.setStatus(Resources.OK);
			responsedto.setData(pulldatadto);

		} catch (DAOException e) {
			logger.error(Resources.DAOEXCEPTION, e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		} catch (Exception e) {
			logger.error(String.format("%s %s", Resources.CONTROLLEREXCEPTION, e.getMessage()));

			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		}
		return Response.status(200).entity(gson.toJson(responsedto)).build();
	}
}
