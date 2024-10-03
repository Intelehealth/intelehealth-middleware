package com.emrmiddleware.dao;

import java.util.ArrayList;

import com.emrmiddleware.dto.ExternalPatientDTO;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.PatientDMO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.exception.DAOException;

public class PatientDAO {

	private final Logger logger = LoggerFactory.getLogger(PatientDAO.class);
	public ArrayList<PatientDTO> getPatients(String lastpulldatatime, String locationuuid, int offset, int limit) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<PatientDTO> patientlist = new ArrayList<PatientDTO>();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientlist = patientdmo.getPatients(lastpulldatatime, locationuuid, offset, limit);

			return patientlist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public ArrayList<PatientAttributeTypeDTO> getPatientAttributeType(String lastpulldatatime, String locationuuid)
			throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<PatientAttributeTypeDTO> patientAttributeTypeList = new ArrayList<PatientAttributeTypeDTO>();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientAttributeTypeList = patientdmo.getPatientAttributeMaster(lastpulldatatime);
			return patientAttributeTypeList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public ArrayList<PatientAttributeDTO> getPatientAttributes(String lastpulldatatime, String locationuuid)
			throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<PatientAttributeDTO> patientAttributesList = new ArrayList<PatientAttributeDTO>();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientAttributesList = patientdmo.getPatientAttributes(lastpulldatatime, locationuuid);
			return patientAttributesList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public PatientDTO getPatient(String personuuid) throws DAOException {
		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		PatientDTO patientdto = new PatientDTO();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientdto = patientdmo.getPatient(personuuid);
			return patientdto;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	// new method to return total number of records after the lastpulldatatime on that location  MHM-259

	public int getPatientsCount(String lastpulldatatime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		int totalCount = 0;
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);

			totalCount = patientdmo.getPatientsCount(lastpulldatatime, locationuuid);
			return totalCount;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public PatientDTO getPatientWithABDM(String abhaAddress, String abhaNumber) throws  PersistenceException {
		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		PatientDTO patientdto = new PatientDTO();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientdto = patientdmo.getPatientWithABDM(abhaNumber);
			if(patientdto !=null){
			patientdto.setAbha_address(abhaAddress);
			patientdto.setAbha_number(abhaNumber);
			logger.debug("In getPatientWithABDM");
			logger.debug(patientdto.toString());
			}return patientdto;

		} finally {
			session.close();
		}
	}

    public ExternalPatientDTO getPersonIdentifiers(String abhaNumber) {
		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ExternalPatientDTO patientdto = new ExternalPatientDTO();
		try {
			logger.debug("In getPersonIdentifiers");
			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientdto = patientdmo.getPersonIdentifiers(abhaNumber);
			if(patientdto != null ){
				logger.debug(patientdto.toString());
			}
			else {
				patientdto = new ExternalPatientDTO();
			}

			return patientdto;

		} finally {
			session.close();
		}
    }
}
