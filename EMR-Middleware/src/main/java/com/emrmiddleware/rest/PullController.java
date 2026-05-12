/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  io.swagger.annotations.Api
 *  javax.servlet.ServletContext
 *  javax.ws.rs.Consumes
 *  javax.ws.rs.GET
 *  javax.ws.rs.Path
 *  javax.ws.rs.PathParam
 *  javax.ws.rs.Produces
 *  javax.ws.rs.core.Context
 *  javax.ws.rs.core.HttpHeaders
 *  javax.ws.rs.core.Response
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.emrmiddleware.rest;

import com.emrmiddleware.action.PullDataAction;
import com.emrmiddleware.authentication.AuthenticationUtil;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.DAOException;
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
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api(value="PULL DATA")
@Path(value="pull")
public class PullController {
    private final Logger logger = LoggerFactory.getLogger(PullController.class);
    @Context
    ServletContext context;

    @Path(value="pulldata/{locationuuid}/{lastpulldate}")
    @GET
    @Produces(value={"application/json"})
    @Consumes(value={"application/x-www-form-urlencoded"})
    public Response getData(@PathParam(value="locationuuid") String locationuuid, @PathParam(value="lastpulldate") String lastpulldatatime, @Context HttpHeaders httpHeaders) {
        ResponseDTO responsedto = new ResponseDTO();
        PullDataDTO pulldatadto = new PullDataDTO();
        String authString = null;
        Gson gson = new Gson();
        try {
            AuthenticationUtil authutil = new AuthenticationUtil();
            authString = httpHeaders.getHeaderString("authorization");
            boolean isAuthenticated = authutil.isUserAuthenticated(authString);
            if (!isAuthenticated || authString == null) {
                this.logger.error("No Authorization");
                responsedto.setStatusMessage("ERROR", "No Authorization", "unable_to_process_request");
                return Response.status((int)403).entity((Object)gson.toJson((Object)responsedto)).build();
            }
            PullDataAction pulldataaction = new PullDataAction();
            pulldatadto = pulldataaction.getPullData(lastpulldatatime, locationuuid);
            responsedto.setStatus("OK");
            responsedto.setData(pulldatadto);
        }
        catch (DAOException e) {
            this.logger.error("Exception in DAO : ", (Throwable)e);
            responsedto.setStatusMessage("ERROR", "Unable to process request. Please try again or Contact System Administrator", "unable_to_process_request");
            return Response.status((int)500).entity((Object)gson.toJson((Object)responsedto)).build();
        }
        catch (Exception e) {
            this.logger.error("Exception in Controller : " + e.getMessage());
            responsedto.setStatusMessage("ERROR", "Unable to process request. Please try again or Contact System Administrator", "unable_to_process_request");
            return Response.status((int)500).entity((Object)gson.toJson((Object)responsedto)).build();
        }
        return Response.status((int)200).entity((Object)gson.toJson((Object)responsedto)).build();
    }
}

