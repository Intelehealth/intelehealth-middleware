package com.emrmiddleware.dao;


import java.util.ArrayList;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.EncounterDMO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.exception.DAOException;


public class EncounterDAO {

	private final Logger logger = LoggerFactory.getLogger(EncounterDAO.class);
	public ArrayList<EncounterDTO> getEncounters(String lastpulldatatime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<EncounterDTO> encounterlist = new ArrayList<EncounterDTO>();
		try {

			EncounterDMO encounterdmo = session.getMapper(EncounterDMO.class);
			encounterlist = encounterdmo.getEncounters(lastpulldatatime, locationuuid);
			return encounterlist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public EncounterDTO getEncounter(String encounteruuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		EncounterDTO encounterdto = new EncounterDTO();
		try {

			EncounterDMO encounterdmo = session.getMapper(EncounterDMO.class);
			encounterdto = encounterdmo.getEncounter(encounteruuid);
			return encounterdto;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
}