package com.emrmiddleware.rest;

import com.emrmiddleware.action.PullDataAction;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.dao.PatientDAO;
import com.emrmiddleware.dao.PatientSyncLogDAO;
import com.emrmiddleware.dto.MpiIdentifierDTO;
import com.emrmiddleware.dto.PatientSyncLogDTO;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.resource.Resources;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api("PULL DATA")
@Path("pull")
public class PullController {
  private final Logger logger = LoggerFactory.getLogger(PullController.class);
  @Context ServletContext context;

  @Path("pulldata/{locationuuid}/{lastpulldate}/{pageno}/{limit}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response getData(
      @PathParam("locationuuid") String locationuuid,
      @PathParam("lastpulldate") String lastpulldatatime,
      @PathParam("pageno") int pageno,
      @PathParam("limit") int limit,
      @Context HttpHeaders httpHeaders) {

    ResponseDTO responsedto = new ResponseDTO();
    PullDataDTO pulldatadto = new PullDataDTO();
    String authString = null;
    Gson gson = new Gson();
    try {
      AuthenticationUtil authutil = new AuthenticationUtil();
      authString = httpHeaders.getHeaderString("authorization");

      boolean isAuthenticated = authutil.isUserAuthenticated(authString);
      if ((!isAuthenticated) || (authString == null)) {
        logger.error("No Authorization");
        responsedto.setStatusMessage(
            Resources.ERROR, Resources.AUTHERROR, Resources.UNABLETOPROCESS);
        return Response.status(403).entity(gson.toJson(responsedto)).build();
      }
      PullDataAction pulldataaction = new PullDataAction();

      pulldatadto = pulldataaction.getPullData(lastpulldatatime, locationuuid, pageno, limit);
      responsedto.setStatus(Resources.OK);
      responsedto.setData(pulldatadto);
    } catch (DAOException e) {
      logger.error(Resources.DAOEXCEPTION, e);
      responsedto.setStatusMessage(
          Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
      return Response.status(500).entity(gson.toJson(responsedto)).build();
    } catch (Exception e) {
      logger.error("{} {}", Resources.CONTROLLEREXCEPTION ,  e.getMessage());
      responsedto.setStatusMessage(
          Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
      return Response.status(500).entity(gson.toJson(responsedto)).build();
    }

    return Response.status(200).entity(gson.toJson(responsedto)).build();
  }

  @Path("pulldata/mpi/{identifier}/{patientUuid}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMpiByPatientIdentifier(
      @PathParam("identifier") String identifier,
      @PathParam("patientUuid") String patientUuid,
      @Context HttpHeaders httpHeaders) {

    ResponseDTO responsedto = new ResponseDTO();
    Gson gson = new Gson();
    try {
      AuthenticationUtil authutil = new AuthenticationUtil();
      String authString = httpHeaders.getHeaderString("authorization");

      boolean isAuthenticated = authutil.isUserAuthenticated(authString);
      if ((!isAuthenticated) || (authString == null)) {
        logger.error("No Authorization");
        responsedto.setStatusMessage(
            Resources.ERROR, Resources.AUTHERROR, Resources.UNABLETOPROCESS);
        return Response.status(403).entity(gson.toJson(responsedto)).build();
      }

      if (patientUuid == null || patientUuid.trim().isEmpty()) {
        responsedto.setStatusMessage(
            Resources.ERROR, "patientUuid path parameter is required", Resources.UNABLETOPROCESS);
        return Response.status(400).entity(gson.toJson(responsedto)).build();
      }

      String mpiIdentifier = new PatientDAO().getMpiIdentifierByPatientIdentifier(identifier);
      if (mpiIdentifier == null || mpiIdentifier.trim().isEmpty()) {
        responsedto.setStatusMessage(
            Resources.ERROR,
            "No patient found with identifier '" + identifier + "' or MPI identifier is not assigned",
            Resources.UNABLETOPROCESS);
        return Response.status(404).entity(gson.toJson(responsedto)).build();
      }

      MpiIdentifierDTO mpiIdentifierDTO = new MpiIdentifierDTO();
      mpiIdentifierDTO.setMpi(mpiIdentifier);

      PatientSyncLogDTO syncLog =
          new PatientSyncLogDAO().getLatestSyncLogByPatientUuid(patientUuid.trim());
      if (syncLog != null) {
        mpiIdentifierDTO.setAttempt_number(syncLog.getAttempt_number());
        mpiIdentifierDTO.setLast_try(syncLog.getLast_try());
        mpiIdentifierDTO.setStatus(syncLog.getStatus());
      }

      responsedto.setStatus(Resources.OK);
      responsedto.setData(mpiIdentifierDTO);
    } catch (DAOException e) {
      logger.error(Resources.DAOEXCEPTION, e);
      responsedto.setStatusMessage(
          Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
      return Response.status(500).entity(gson.toJson(responsedto)).build();
    } catch (Exception e) {
      logger.error("{} {}", Resources.CONTROLLEREXCEPTION, e.getMessage());
      responsedto.setStatusMessage(
          Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
      return Response.status(500).entity(gson.toJson(responsedto)).build();
    }

    return Response.status(200).entity(gson.toJson(responsedto)).build();
  }
}
