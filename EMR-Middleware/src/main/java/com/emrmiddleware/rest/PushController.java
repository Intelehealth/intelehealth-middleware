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

			ArrayList<ProviderDTO> customProviders = pushdatadto.getProviders();
			//logger.error("Received " + customProviders.isEmpty()? "0" : customProviders.size() + "providers from pushdata DTO");
			if(!customProviders.isEmpty() ) {
				for (ProviderDTO provider : customProviders) {

				//	updateAttributes(provider);

				}
			}



			//	logger.error("Sending data to push");
				pulldatadto = pushdataaction.pushData(pushdatadto);
			//		logger.error("Received data from push");
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



	private void updateAttributes(ProviderDTO provider) {




		final int ATTRIBUTE_EMAIL = 3;
		final int ATTRIBUTE_PHONENUMBER = 4;
		final int ATTRIBUTE_COUNTRY_CODE = 16;

		try {
			Class.forName("com.mysql.jdbc.Driver");


			PreparedStatement pstmt = null;
			Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs?useSSL=false", "root", "i10hi1c");
			int providerId = new Integer(provider.providerId);


			String updateProviderEmail = "UPDATE provider_attribute SET value_reference = ? WHERE attribute_type_id = ? AND provider_id = ?";
			String updateProviderPhoneNumber = "UPDATE provider_attribute SET value_reference = ? WHERE attribute_type_id = ? AND provider_id = ?";
			String updateProviderCountryCode = "UPDATE provider_attribute SET value_reference = ? WHERE attribute_type_id = ? AND provider_id = ?";

			String updatePersonFirstName = "UPDATE person_name SET given_name  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";
			String updatePersonMiddleName = "UPDATE person_name SET middle_name  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";

			String updatePersonLastName = "UPDATE person_name SET family_name  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";
			String updateDOB = "UPDATE person SET birthdate = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";
			String updateGender = "UPDATE person SET gender  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";


			if(provider.emailId != null) {
				pstmt = con.prepareStatement(updateProviderEmail);
				pstmt.setString(1, provider.emailId);
				pstmt.setInt(2,ATTRIBUTE_EMAIL);
				pstmt.setInt(3, providerId);
				pstmt.executeUpdate();
			}

			if(provider.telephoneNumber != null) {
				pstmt = con.prepareStatement(updateProviderPhoneNumber);
				pstmt.setString(1, provider.telephoneNumber);
				pstmt.setInt(2,ATTRIBUTE_PHONENUMBER);
				pstmt.setInt(3, providerId);
				pstmt.executeUpdate();
			}

			if(provider.countryCode != null) {
				pstmt = con.prepareStatement(updateProviderCountryCode);
				pstmt.setString(1, provider.countryCode);
				pstmt.setInt(2,ATTRIBUTE_COUNTRY_CODE);
				pstmt.setInt(3, providerId);
				pstmt.executeUpdate();
			}

			if(provider.given_name != null) {
				pstmt = con.prepareStatement(updatePersonFirstName);
				pstmt.setString(1, provider.given_name);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.family_name != null) {
				pstmt = con.prepareStatement(updatePersonLastName);
				pstmt.setString(1, provider.family_name);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.middle_name != null) {
				pstmt = con.prepareStatement(updatePersonMiddleName);
				pstmt.setString(1, provider.middle_name);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.dateofbirth != null) {
				pstmt = con.prepareStatement(updateDOB);
				pstmt.setString(1, provider.dateofbirth);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.gender != null) {
				pstmt = con.prepareStatement(updateGender);
				pstmt.setString(1, provider.gender);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}


			pstmt.close();
			con.close();



		} catch (ClassNotFoundException cnfe) {
			logger.error("Class Not Found while Updating Attributes of Provider" + cnfe.getMessage());
		}
		catch(SQLException sqle) {
			logger.error("SQL Error while Updating Attributes of Provider" + sqle.getMessage());

		}
	}



}