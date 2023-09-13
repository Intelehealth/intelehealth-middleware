package com.emrmiddleware.rest;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.emrmiddleware.api.dto.AttributeAPIDTO;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.EncounterProvidersAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.treblle.spring.annotation.EnableTreblle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.emrmiddleware.action.PushDataAction;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.PushDataDTO;
import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.resource.Resources;
import com.google.gson.Gson;

import io.swagger.annotations.Api;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

@Api("PUSH DATA")
@Path("push")
@EnableTreblle


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
			if ((isAuthenticated == false) || (authString == null)) {
				logger.error("No Authorization");
				responsedto.setStatusMessage(Resources.ERROR, Resources.AUTHERROR, Resources.UNABLETOPROCESS);
				return Response.status(403).entity(gson.toJson(responsedto)).build();
			}
			PushDataAction pushdataaction = new PushDataAction(authString);
			PullDataDTO pulldatadto = new PullDataDTO();

			PushDataDTO pdd = new PushDataDTO();
			pdd = pushdatadto;

			ArrayList<EncounterAPIDTO> e = new ArrayList<EncounterAPIDTO>();
			e = pdd.getEncounters();
			System.out.println("Number of Encounters" + e.size());
			if(e.size() > 0) {

				ArrayList<VisitAPIDTO> v = new ArrayList<VisitAPIDTO>();
				v = pdd.getVisits();


				ArrayList<EncounterProvidersAPIDTO> eproviders = new ArrayList<EncounterProvidersAPIDTO>();
				String providerUID = e.get(0).getEncounterProviders().get(0).getProvider();
				String[][] notifiers = new String[20][3];


				int result = 0;
				for (int i = 0; i < v.size(); i++) {
					String patientUID = v.get(i).getPatient();
					String visitUID = v.get(i).getUuid();
					ArrayList<AttributeAPIDTO> attribList = new ArrayList<AttributeAPIDTO>();
					attribList = v.get(i).getAttributes();
					String speciality = "";
					//for (int iv = 0; iv < attribList.size(); iv++) {

						//speciality = attribList.get(iv).getValue();
				//	}
					int iv = 0 ;
					speciality = attribList.get(iv).getValue(); // MSF Specific 25102021 Satyadeep

					result = checkVisit(visitUID, patientUID);
					String patientName = "";
					String providerString = "";
					if (result == 0) {
						patientName = getPatientName(patientUID);
						providerString = findProvider(providerUID);
						notifiers[i][0] = speciality;
						notifiers[i][1] = patientName;
						notifiers[i][2] = providerString;
					}
				}

				pulldatadto = pushdataaction.pushData(pushdatadto);

				pushNotifications(notifiers,v.size());
				responsedto.setStatus(Resources.OK);
				responsedto.setData(pulldatadto);
			}
			else {

				pulldatadto = pushdataaction.pushData(pushdatadto);
				responsedto.setStatus(Resources.OK);
				responsedto.setData(pulldatadto);
			}


			//pulldatadto = pushdataaction.pushData(pushdatadto);
			// responsedto.setStatus(Resources.OK);
			// responsedto.setData(pulldatadto);
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

	private void pushNotifications(String[][] notifiers,int noOfElements)  {

		String url = "http://localhost:3004/notification/push";

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();


			// Setting basic post request
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			con.setRequestProperty("Content-Type", "application/json");
			for (int row = 0; row < noOfElements; row++) {

				JsonObject mainPacket = new JsonObject();
				JsonObject patientDetails = new JsonObject();



				Gson gson = new Gson();



				patientDetails.addProperty("name", notifiers[row][1]);
				patientDetails.addProperty("provider", notifiers[row][2]);

				mainPacket.addProperty("speciality", notifiers[row][0]);
				mainPacket.addProperty("skipFlag", false);

				mainPacket.add("patient", patientDetails);

				System.out.println(mainPacket);

				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(String.valueOf(mainPacket));

				wr.flush();
					System.out.println("Response for notification push to URL "+ url + " is : "+con.getResponseCode()+"--"+con.getResponseMessage());

				wr.close();


			}
		}catch(Exception e) {
			System.out.println("Exception caught in pushnotification ");
			e.printStackTrace();

		}

	}

	public String findProvider(String providerUID) {
		String s = "select concat_ws(':',family_name, given_name)  AS provider FROM person_name where person_id = (select person_id from provider WHERE uuid=?)";
		String result = "";
		java.sql.Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, providerUID);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getString(1);
			rs.close();
			pstmt.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Exception caught in findprovider ");
			e.printStackTrace();
		}

		return result;
	}

	public int checkVisit(String visitUid, String patientUid) {
		String s = "SELECT COUNT(1) FROM visit WHERE uuid=? AND patient_id = (SELECT person_id from person WHERE uuid=?)";
		int result = 0;
		java.sql.Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		try {

			con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, visitUid);
			pstmt.setString(2, patientUid);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException  e) {
			System.out.println("Exception caught in checkvisit ");
			e.printStackTrace();
		}

		return result;
	}

	public String getPatientName(String patientUID) {
		String s = "select given_name from person_name where person_id =(select person_id from person where uuid=?)";
		String result = "";
		java.sql.Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");d
			con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, patientUID);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getString(1);
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Exception caught in getPatientName ");
			e.printStackTrace();
		}

		return result;

	}

}