package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.PatientSyncLogDMO;
import com.emrmiddleware.dto.PatientSyncLogDTO;
import com.emrmiddleware.exception.DAOException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatientSyncLogDAO {

  private final Logger logger = LoggerFactory.getLogger(PatientSyncLogDAO.class);

  public PatientSyncLogDTO getLatestSyncLogByPatientUuid(String patientUuid) throws DAOException {
    SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
    SqlSession session = sessionfactory.openSession();
    try {
      PatientSyncLogDMO mapper = session.getMapper(PatientSyncLogDMO.class);
      return mapper.getLatestSyncLogByPatientUuid(patientUuid);
    } catch (PersistenceException e) {
      logger.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    } finally {
      session.close();
    }
  }
}
