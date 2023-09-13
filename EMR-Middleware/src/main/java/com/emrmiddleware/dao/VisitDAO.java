package com.emrmiddleware.dao;



import java.util.ArrayList;

import com.emrmiddleware.dmo.ConceptDMO;
import com.emrmiddleware.dto.*;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.VisitDMO;
import com.emrmiddleware.exception.DAOException;

public class VisitDAO {

	private final Logger logger = LoggerFactory.getLogger(VisitDAO.class);
	public ArrayList<VisitDTO> getVisits(String lastpulldatatime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<VisitDTO> visitlist = new ArrayList<VisitDTO>();
		try {

			VisitDMO patientdmo = session.getMapper(VisitDMO.class);
			visitlist = patientdmo.getVisits(lastpulldatatime, locationuuid);
			return visitlist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public VisitDTO getVisit(String visituuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		VisitDTO visitdto = new VisitDTO();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			visitdto = visitdmo.getVisit(visituuid);
			return visitdto;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public String getDBCurrentTime() throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		String currentTime=null;
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			currentTime = visitdmo.getDBCurrentTime();
			return currentTime;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeMaster(String lastpulldatatime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<VisitAttributeTypeDTO> visitAttributeMasterList= new ArrayList<VisitAttributeTypeDTO>();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			visitAttributeMasterList = visitdmo.getVisitAttributeMaster(lastpulldatatime);
			return visitAttributeMasterList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public ArrayList<VisitAttributeDTO> getVisitAttributes(String lastpulldatatime,String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<VisitAttributeDTO> visitAttributesList= new ArrayList<VisitAttributeDTO>();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			visitAttributesList = visitdmo.getVisitAttributes(lastpulldatatime, locationuuid);
			return visitAttributesList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public ArrayList<ConceptAttributeTypeDTO> getConceptAttributeTypeMaster(String lastpulldatatime) throws DAOException {
		//System.out.print("VisitDao getConceptAttributeTypeMaster");
		//System.out.print("GCATM start");
		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		//System.out.print("GCATM session factory received");
		SqlSession session = sessionfactory.openSession();
		ArrayList<ConceptAttributeTypeDTO> conceptAttributeMasterList= new ArrayList<ConceptAttributeTypeDTO>();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			//System.out.print("GCATM concept dmo received");
			conceptAttributeMasterList = visitdmo.getConceptAttributeTypeMaster(lastpulldatatime);
			//System.out.print("GCATM conceptAttributeMasterListSize :"+ conceptAttributeMasterList.size());
			return conceptAttributeMasterList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public ArrayList<ConceptAttributeDTO> getConceptAttributes(String lastpulldatatime, String locationuuid) throws DAOException {
		//System.out.print("VisitDao getConceptAttributes");
		//System.out.print("GCA start");
		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ConceptAttributeDTO> conceptAttributesList= new ArrayList<ConceptAttributeDTO>();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			//System.out.print("GCA conceptdmo received");
			conceptAttributesList = visitdmo.getConceptAttributes(lastpulldatatime, locationuuid);
			//System.out.print("GCA conceptattriblist size "+conceptAttributesList.size());
			return conceptAttributesList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	

}

