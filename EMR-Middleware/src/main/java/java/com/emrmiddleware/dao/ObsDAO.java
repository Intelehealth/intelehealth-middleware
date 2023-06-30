package com.emrmiddleware.dao;

import java.util.ArrayList;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.ObsDMO;
import com.emrmiddleware.dto.ObsDTO;
import com.emrmiddleware.exception.DAOException;


public class ObsDAO {

	private final Logger logger = LoggerFactory.getLogger(ObsDAO.class);

	public ObsDTO getObs(String obsuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ObsDTO obs = new ObsDTO();
		try {
			ObsDMO obsdmo = session.getMapper(ObsDMO.class);
			obs = obsdmo.getObs(obsuuid);
			return obs;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public ArrayList<ObsDTO> getObsList(String lastpulldatatime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ObsDTO> obslist = new ArrayList<ObsDTO>();
		try {

			ObsDMO obsdmo = session.getMapper(ObsDMO.class);
			
			obslist = obsdmo.getObsList(lastpulldatatime, locationuuid);
			
			return obslist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
}