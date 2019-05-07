package com.emrmiddleware.dao;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBFileConfig;
import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.EncounterDMO;
import com.emrmiddleware.dmo.FileDMO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.dto.FileDTO;
import com.emrmiddleware.exception.DAOException;

public class FileDAO {

	private final Logger logger = LoggerFactory.getLogger(FileDAO.class);
	public boolean insertFile(FileDTO filedto) throws DAOException {

		SqlSessionFactory sessionfactory = DBFileConfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		try {
			
            FileDMO filedmo = session.getMapper(FileDMO.class);		
            filedmo.insertFile(filedto);
            session.commit();
			return true;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public String getFilePath(String uuid) throws DAOException{
		SqlSessionFactory sessionfactory = DBFileConfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		try {
			String filePath="";
			FileDTO filedto = new FileDTO();
            FileDMO filedmo = session.getMapper(FileDMO.class);		
            
            filedto=filedmo.getFile(uuid);
            if (filedto==null){
            	return filePath;
            }
           filePath=filedto.getFilepath()+File.separator+filedto.getImagerawname(); 
           return filePath;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
}
