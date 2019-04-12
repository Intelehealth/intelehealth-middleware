package com.emrmiddleware.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.EncounterDMO;
import com.emrmiddleware.dmo.VisitDMO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.DAOException;


public class EncounterDAO {

	public ArrayList<EncounterDTO> getEncounters(Timestamp lastdatapulltime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<EncounterDTO> encounterlist = new ArrayList<EncounterDTO>();
		try {

			EncounterDMO encounterdmo = session.getMapper(EncounterDMO.class);
			encounterlist = encounterdmo.getEncounters(lastdatapulltime, locationuuid);
			return encounterlist;
		} catch (PersistenceException e) {
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
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
}