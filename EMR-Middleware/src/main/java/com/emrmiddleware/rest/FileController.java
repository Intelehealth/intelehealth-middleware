package com.emrmiddleware.rest;

import java.io.File;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.action.FileAction;
import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.resource.Resources;
import com.google.gson.Gson;

@Path("file")
public class FileController {
	private final Logger logger = LoggerFactory.getLogger(FileController.class);

	@POST
	@Path("fileupload")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)

	public Response fileUpload(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("uuid") String uuid,
			@FormDataParam("objectuuid") String objectuuid, @FormDataParam("objectname") String objectname,
			@FormDataParam("attributes") String attributes, @FormDataParam("locationuuid") String locationuuid)

	{
		boolean isFileUploaded = true;
		ResponseDTO responsedto = new ResponseDTO();
		Gson gson = new Gson();
		try {

			FileAction fileaction = new FileAction();
			String fileName = fileMetaData.getFileName();
			isFileUploaded = fileaction.writeToFile(fileInputStream, fileName, uuid, objectuuid, objectname, attributes,
					locationuuid);
			if (isFileUploaded == true) {
				responsedto.setStatus(Resources.OK);
				// return
				// Response.status(200).entity(gson.toJson(responsedto)).build();
			} else {
				responsedto.setStatus(Resources.ERROR);
				// return
				// Response.status(201).entity(gson.toJson(responsedto)).build();
			}
		} catch (ActionException e) {
			logger.error(e.getMessage(), e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		}
		logger.info(gson.toJson(responsedto));
		return Response.status(200).entity(gson.toJson(responsedto)).build();

	}

	@GET
	@Path("/getImage/{uuid}")
	@Produces({ "image/png", "image/jpg", "image/jpeg", MediaType.APPLICATION_JSON })
	public Response getFile(@PathParam("uuid") String uuid) {

		ResponseBuilder response = null;
		ResponseDTO responsedto = new ResponseDTO();
		Gson gson = new Gson();
		try {
			FileAction fileaction = new FileAction();
			String filePath = fileaction.getFilePath(uuid);
			if (filePath==""){
				//logger.error(e.getMessage(), e);
				responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, "No File found");
				return Response.status(500).entity(gson.toJson(responsedto)).build();
			}
			File file = new File(filePath);
			response = Response.ok((Object) file);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		}

		// response.header("Content-Disposition",
		// "attachment; filename=image_from_server.png");
		return response.build();

	}
}
