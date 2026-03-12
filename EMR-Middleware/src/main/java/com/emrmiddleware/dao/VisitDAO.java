package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.VisitDMO;
import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitAttributeTypeDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisitDAO {

  private static final Logger logger = LoggerFactory.getLogger(VisitDAO.class);

  /*public ArrayList<VisitDTO> getVisits(
      String lastpulldatatime, String locationuuid, int offset, int limit, String patientIDs) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<VisitDTO> visitlist = new ArrayList<VisitDTO>();
    try {

      VisitDMO patientdmo = session.getMapper(VisitDMO.class);
      visitlist =
          patientdmo.getVisits(lastpulldatatime, locationuuid,  patientIDs);
              //lastpulldatatime, locationuuid, offset, limit, patientIDs);
      return visitlist;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }*/

  public ArrayList<VisitDTO> getVisits(String lastpulldatatime, String locationuuid, String patientIds) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<VisitDTO> visitlist = new ArrayList<VisitDTO>();
    try {

      VisitDMO patientdmo = session.getMapper(VisitDMO.class);
      visitlist = patientdmo.getVisits(lastpulldatatime, locationuuid,patientIds);
      return visitlist;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

  public ArrayList<VisitDTO> getVisitsNew(String lastpulldatatime, String locationuuid) throws DAOException {
    logger.info("getVisitsNew");
    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<VisitDTO> visitlist = new ArrayList<VisitDTO>();
    try {

      VisitDMO patientdmo = session.getMapper(VisitDMO.class);
      visitlist = patientdmo.getVisitsNew(lastpulldatatime, locationuuid);
      return visitlist;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
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
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

  public String getDBCurrentTime() throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    String currentTime = null;
    try {

      VisitDMO visitdmo = session.getMapper(VisitDMO.class);
      currentTime = visitdmo.getDBCurrentTime();
      return currentTime;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }

  public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeMaster(String lastpulldatatime)
      throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<VisitAttributeTypeDTO> visitAttributeMasterList =
        new ArrayList<VisitAttributeTypeDTO>();
    try {

      VisitDMO visitdmo = session.getMapper(VisitDMO.class);
      visitAttributeMasterList = visitdmo.getVisitAttributeMaster(lastpulldatatime);
      return visitAttributeMasterList;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }



  public ArrayList<VisitAttributeDTO> getVisitAttributes(String visitIdS) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    ArrayList<VisitAttributeDTO> visitAttributesList = new ArrayList<VisitAttributeDTO>();
    try {

      VisitDMO visitdmo = session.getMapper(VisitDMO.class);
      //   visitAttributesList = visitdmo.getVisitAttributes(lastpulldatatime, locationuuid);
      visitAttributesList = visitdmo.getVisitAttributes(visitIdS);
      return visitAttributesList;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }



  // new method to return total number of records after the lastpulldatatime on that location
  // MHM-259


  public int getVisitCount(String lastpulldatatime, String locationuuid) throws DAOException {
    logger.info("Getting visit count for lastpulldatatime: {}, locationuuid: {}", lastpulldatatime, locationuuid);
    
    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    int totalCount = 0;
    try {
      VisitDMO visitdmo = session.getMapper(VisitDMO.class);
      totalCount = visitdmo.getVisitCount(lastpulldatatime, locationuuid);
      logger.info("Total visit count: {}", totalCount);
      return totalCount;
    } catch (PersistenceException e) {
      logger.error("Error getting visit count", e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }


/*
  public int getVisitCount(String lastpulldatatime, String locationuuid, String patientIds) throws DAOException {

    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    int totalCount = 0;
    try {

      VisitDMO visitdmo = session.getMapper(VisitDMO.class);

      totalCount = visitdmo.getVisitCount(lastpulldatatime, locationuuid, patientIds);
      return totalCount;
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }*/
}
