package com.emrmiddleware.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.EncounterDMO;
import com.emrmiddleware.dmo.ObsDMO;
import com.emrmiddleware.dto.ObsDTO;
import com.emrmiddleware.exception.DAOException;


public class ObsDAO {

	public ArrayList<ObsDTO> getObs(Timestamp lastdatapulltime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ObsDTO> obslist = new ArrayList<ObsDTO>();
		try {

			ObsDMO obsdmo = session.getMapper(ObsDMO.class);
			obslist = obsdmo.getObs(lastdatapulltime, locationuuid);
			
			return obslist;
		} catch (PersistenceException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
}