package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.PatientDMO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatientDAO {

  private final Logger logger = LoggerFactory.getLogger(PatientDAO.class);

  public ArrayList<PatientDTO> getPatients(
      String lastpulldatatime, String locationuuid, int offset, int limit) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<PatientDTO> patientlist = new ArrayList<PatientDTO>();
    try {

      PatientDMO patientdmo = session.getMapper(PatientDMO.class);
      patientlist =
          patientdmo.getPatients(
              lastpulldatatime, locationuuid, offset, limit);
      return patientlist;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

  public ArrayList<PatientAttributeTypeDTO> getPatientAttributeType(
      String lastpulldatatime, String locationuuid) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<PatientAttributeTypeDTO> patientAttributeTypeList =
        new ArrayList<PatientAttributeTypeDTO>();
    try {

      PatientDMO patientdmo = session.getMapper(PatientDMO.class);
      patientAttributeTypeList = patientdmo.getPatientAttributeMaster(lastpulldatatime);
      return patientAttributeTypeList;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

  /*public ArrayList<PatientAttributeDTO> getPatientAttributes(
          String lastpulldatatime, String locationuuid, String patientIds) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<PatientAttributeDTO> patientAttributesList = new ArrayList<PatientAttributeDTO>();
    try {

      PatientDMO patientdmo = session.getMapper(PatientDMO.class);

      patientAttributesList = patientdmo.getPatientAttributes(lastpulldatatime, locationuuid, patientIds);
      return patientAttributesList;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }*/

  public ArrayList<PatientAttributeDTO> getPatientAttributes(
           String patientIds) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<PatientAttributeDTO> patientAttributesList = new ArrayList<PatientAttributeDTO>();
    try {

      PatientDMO patientdmo = session.getMapper(PatientDMO.class);

      patientAttributesList = patientdmo.getPatientAttributes(patientIds);
      return patientAttributesList;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
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
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

  // new method to return total number of records after the lastpulldatatime on that location
  // MHM-259

  public int getPatientsCount(String lastpulldatatime, String locationuuid) throws DAOException {
    logger.info("Getting patient count for lastpulldatatime: {}, locationuuid: {}", lastpulldatatime, locationuuid);
    
    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    int totalCount = 0;
    try {
      PatientDMO patientdmo = session.getMapper(PatientDMO.class);
      totalCount = patientdmo.getPatientsCount(lastpulldatatime, locationuuid);
      logger.info("Total patient count: {}", totalCount);
      return totalCount;
    } catch (PersistenceException e) {
      logger.error("Error getting patient count", e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

  public HashMap<String, Object>  searchPatientByParam(String firstname, String middlename, String lastname, String gender, String dob, String telecom, int offset, int limit) throws DAOException {
    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    HashMap<String, Object> itemMap = new HashMap<String, Object>();
    try {
      PatientDMO patientdmo = session.getMapper(PatientDMO.class);
      int count = patientdmo.searchPatientCountByParam(firstname,middlename,lastname,gender,dob,telecom);
      ArrayList<PatientDTO> items = patientdmo.searchPatientByParam(firstname,middlename,lastname,gender,dob,telecom, offset, limit);
      itemMap.put("totalCount", count);
      itemMap.put("searchItem",items);
      return itemMap;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(),e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

}
