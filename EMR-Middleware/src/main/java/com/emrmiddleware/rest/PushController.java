
package com.emrmiddleware.rest;

import com.emrmiddleware.action.PushDataAction;
import com.emrmiddleware.api.dto.AttributeAPIDTO;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.PushDataDTO;
import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.treblle.spring.annotation.EnableTreblle;
import io.swagger.annotations.Api;
import java.io.DataOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api(value="PUSH DATA")
@Path(value="push")
//@EnableTreblle
public class PushController {
    private final Logger logger = LoggerFactory.getLogger(PushController.class);
    @Context
    ServletContext context;

    @Path(value="pushdata")
    @POST
    @Consumes(value={"application/json"})
    @Produces(value={"application/json"})
    public Response setData(PushDataDTO pushdatadto, @Context HttpHeaders httpHeaders) {
        ResponseDTO responsedto = new ResponseDTO();
        Gson gson = new Gson();
        String ATTRIB_VISIT_SPECIALITY = "3f296939-c6d3-4d2e-b8ca-d7f4bfd42c2d";
        String authString = null;
        try {
            AuthenticationUtil authutil = new AuthenticationUtil();
            authString = httpHeaders.getHeaderString("authorization");
            this.logger.info("AuthString : " + authString);
            boolean isAuthenticated = authutil.isUserAuthenticated(authString);
            if (!isAuthenticated || authString == null) {
                this.logger.error("No Authorization");
                responsedto.setStatusMessage("ERROR", "No Authorization", "unable_to_process_request");
                return Response.status((int)403).entity((Object)gson.toJson((Object)responsedto)).build();
            }
            PushDataAction pushdataaction = new PushDataAction(authString);
            PullDataDTO pulldatadto = new PullDataDTO();
            PushDataDTO pdd = new PushDataDTO();
            pdd = pushdatadto;
            ArrayList<Object> e = new ArrayList();
            e = pdd.getEncounters();
            if (!e.isEmpty()) {
                ArrayList<Object> v = new ArrayList();
                v = pdd.getVisits();
                ArrayList eproviders = new ArrayList();
                String providerUID = ((EncounterAPIDTO)e.get(0)).getEncounterProviders().get(0).getProvider();
                String[][] notifiers = new String[20][3];
                int result = 0;
                for (int i = 0; i < v.size(); ++i) {
                    String patientUID = ((VisitAPIDTO)v.get(i)).getPatient();
                    String visitUID = ((VisitAPIDTO)v.get(i)).getUuid();
                    ArrayList<Object> attribList = new ArrayList();
                    attribList = ((VisitAPIDTO)v.get(i)).getAttributes();
                    String[] speciality = new String[]{""};
                    boolean iv = false;
                    attribList.forEach((Consumer<Object>)((Consumer<AttributeAPIDTO>)attrib -> {
                        if (attrib.getAttributeType().equals("3f296939-c6d3-4d2e-b8ca-d7f4bfd42c2d")) {
                            speciality[0] = attrib.getValue();
                        }
                    }));
                    result = this.checkVisit(visitUID, patientUID);
                    String patientName = "";
                    String providerString = "";
                    if (result != 0) continue;
                    patientName = this.getPatientName(patientUID);
                    providerString = this.findProvider(providerUID);
                    notifiers[i][0] = speciality[0];
                    notifiers[i][1] = patientName;
                    notifiers[i][2] = providerString;
                }
                pulldatadto = pushdataaction.pushData(pushdatadto);
                this.pushNotifications(notifiers, v.size());
                responsedto.setStatus("OK");
                responsedto.setData(pulldatadto);
            } else {
                pulldatadto = pushdataaction.pushData(pushdatadto);
                responsedto.setStatus("OK");
                responsedto.setData(pulldatadto);
            }
        }
        catch (DAOException e) {
            this.logger.error("Exception in DAO : ", (Throwable)e);
            responsedto.setStatusMessage("ERROR", "Unable to process request. Please try again or Contact System Administrator", "unable_to_process_request");
            return Response.status((int)500).entity((Object)gson.toJson((Object)responsedto)).build();
        }
        catch (Exception e) {
            this.logger.error("Exception in Controller : " + e.getMessage());
            this.logger.error("AAA", (Throwable)e);
            responsedto.setStatusMessage("ERROR", "Unable to process request. Please try again or Contact System Administrator", "unable_to_process_request");
            return Response.status((int)500).entity((Object)gson.toJson((Object)responsedto)).build();
        }
        return Response.status((int)200).entity((Object)gson.toJson((Object)responsedto)).build();
    }

    private void pushNotifications(String[][] notifiers, int noOfElements) {
        String url = "https://tele.med.kg:3004/notification/push";
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            for (int row = 0; row < noOfElements; ++row) {
                JsonObject mainPacket = new JsonObject();
                JsonObject patientDetails = new JsonObject();
                Gson gson = new Gson();
                patientDetails.addProperty("name", notifiers[row][1]);
                patientDetails.addProperty("provider", notifiers[row][2]);
                mainPacket.addProperty("speciality", notifiers[row][0]);
                mainPacket.addProperty("skipFlag", Boolean.valueOf(false));
                mainPacket.add("patient", (JsonElement)patientDetails);
                System.out.println(mainPacket);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(String.valueOf(mainPacket));
                wr.flush();
                System.out.println("Response for notification push to URL " + url + " is : " + con.getResponseCode() + "--" + con.getResponseMessage());
                wr.close();
            }
        }
        catch (Exception e) {
            System.out.println("Exception caught in pushnotification ");
            e.printStackTrace();
        }
    }

    public String findProvider(String providerUID) {
        String s = "select concat_ws(':',family_name, given_name)  AS provider FROM person_name where person_id = (select person_id from provider WHERE uuid=?)";
        String result = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
            pstmt = con.prepareStatement(s);
            pstmt.setString(1, providerUID);
            rs = pstmt.executeQuery();
            rs.next();
            result = rs.getString(1);
            rs.close();
            pstmt.close();
            con.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception caught in findprovider ");
            e.printStackTrace();
        }
        return result;
    }

    public int checkVisit(String visitUid, String patientUid) {
        String s = "SELECT COUNT(1) FROM visit WHERE uuid=? AND patient_id = (SELECT person_id from person WHERE uuid=?)";
        int result = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
            pstmt = con.prepareStatement(s);
            pstmt.setString(1, visitUid);
            pstmt.setString(2, patientUid);
            rs = pstmt.executeQuery();
            rs.next();
            result = rs.getInt(1);
            rs.close();
            pstmt.close();
            con.close();
        }
        catch (SQLException e) {
            System.out.println("Exception caught in checkvisit ");
            e.printStackTrace();
        }
        return result;
    }

    public String getPatientName(String patientUID) {
        String s = "select given_name from person_name where person_id =(select person_id from person where uuid=?)";
        String result = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
            pstmt = con.prepareStatement(s);
            pstmt.setString(1, patientUID);
            rs = pstmt.executeQuery();
            rs.next();
            result = rs.getString(1);
            rs.close();
            pstmt.close();
            con.close();
        }
        catch (SQLException e) {
            System.out.println("Exception caught in getPatientName ");
            e.printStackTrace();
        }
        return result;
    }
}

