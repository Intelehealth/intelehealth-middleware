package com.emrmiddleware.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.PatientDMO;
import com.emrmiddleware.dmo.PersonDMO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.exception.DAOException;

public class PatientDAO {

	private final Logger logger = LoggerFactory.getLogger(PatientDAO.class);
	public ArrayList<PatientDTO> getPatients(Timestamp lastdatapulltime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<PatientDTO> patientlist = new ArrayList<PatientDTO>();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientlist = patientdmo.getPatients(lastdatapulltime, locationuuid);
			return patientlist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public ArrayList<PatientAttributeTypeDTO> getPatientAttributeType(Timestamp lastdatapulltime, String locationuuid)
			throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<PatientAttributeTypeDTO> patientAttributeTypeList = new ArrayList<PatientAttributeTypeDTO>();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientAttributeTypeList = patientdmo.getPatientAttributeMaster(lastdatapulltime);
			return patientAttributeTypeList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	public ArrayList<PatientAttributeDTO> getPatientAttributes(Timestamp lastdatapulltime, String locationuuid)
			throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<PatientAttributeDTO> patientAttributesList = new ArrayList<PatientAttributeDTO>();
		try {

			PatientDMO patientdmo = session.getMapper(PatientDMO.class);
			patientAttributesList = patientdmo.getPatientAttributes(lastdatapulltime, locationuuid);
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

}
