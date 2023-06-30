package com.emrmiddleware.dao;

import java.util.ArrayList;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.LocationDMO;
import com.emrmiddleware.dto.LocationDTO;
import com.emrmiddleware.exception.DAOException;

public class LocationDAO {

	private final Logger logger = LoggerFactory.getLogger(LocationDAO.class);
	public ArrayList<LocationDTO> getLocations(String lastpulldatatime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<LocationDTO> locationlist = new ArrayList<LocationDTO>();
		try {

			LocationDMO locationdmo = session.getMapper(LocationDMO.class);
			locationlist = locationdmo.getLocations(lastpulldatatime);
			return locationlist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
}
