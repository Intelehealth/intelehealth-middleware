package com.emrmiddleware.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.ResourcesEnvironment;
import com.emrmiddleware.dao.FileDAO;
import com.emrmiddleware.dto.FileDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.rest.FileController;

public class FileAction {
	private final Logger logger = LoggerFactory.getLogger(FileAction.class);

	public boolean writeToFile(InputStream uploadedInputStream, String fileName, String uuid, String objectuuid,
			String objectname,String attributes,String locationuuid) throws ActionException, DAOException {
		boolean isWritenToFile = true;
		try {
			ResourcesEnvironment DBEnv = new ResourcesEnvironment();
			String extension =fileName.substring(fileName.lastIndexOf("."));
			String newFileName = uuid+extension;
			String uploadedFileLocation = DBEnv.getFilePath() + newFileName;
			String filepath = DBEnv.getFilePath();
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			
			FileDTO filedto = new FileDTO();
			filedto.setUuid(uuid);
			filedto.setObjectuuid(objectuuid);
			filedto.setObjectname(objectname);
			filedto.setFilename(fileName);
			filedto.setFilepath(filepath);
			filedto.setImagerawname(newFileName);
			filedto.setAttributes(attributes);
			filedto.setLocationuuid(locationuuid);
			FileDAO filedao = new FileDAO();
			filedao.insertFile(filedto);
			
			return isWritenToFile;
		} catch (IOException e) {

			logger.error("Exception", e);
			isWritenToFile = false;
			throw new ActionException(e.getMessage(), e);
		}
		
	}
	
	public String getFilePath(String uuid) throws ActionException,DAOException{
		String filePath="";
		try{
			FileDAO filedao = new FileDAO();
			filePath = filedao.getFilePath(uuid);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new ActionException(e.getMessage(),e);
		}
		return filePath;
	}

}
