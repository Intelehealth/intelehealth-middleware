package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.ConceptDMO;
import com.emrmiddleware.dto.ConceptAttributeDTO;
import com.emrmiddleware.dto.ConceptAttributeTypeDTO;
import com.emrmiddleware.exception.DAOException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ConceptDAO {
    private final Logger logger = LoggerFactory.getLogger(ConceptDAO.class);

    public ArrayList<ConceptAttributeTypeDTO> getConceptAttributeTypeMaster(String lastpulldatatime) throws DAOException {
        logger.debug("GCATM start");
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        logger.debug("GCATM session factory received");
        SqlSession session = sessionfactory.openSession();
        ArrayList<ConceptAttributeTypeDTO> conceptAttributeMasterList= new ArrayList<ConceptAttributeTypeDTO>();
        try {

            ConceptDMO conceptdmo = session.getMapper(ConceptDMO.class);
            logger.debug("GCATM concept dmo received");
            conceptAttributeMasterList = conceptdmo.getConceptAttributeTypeMaster(lastpulldatatime);
            logger.debug("GCATM conceptAttributeMasterListSize :"+ conceptAttributeMasterList.size());
            return conceptAttributeMasterList;
        } catch (PersistenceException e) {
            logger.error(e.getMessage(),e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    public ArrayList<ConceptAttributeDTO> getConceptAttributes(String lastpulldatatime, String locationuuid) throws DAOException {
        logger.debug("GCA start");
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        SqlSession session = sessionfactory.openSession();
        ArrayList<ConceptAttributeDTO> conceptAttributesList= new ArrayList<ConceptAttributeDTO>();
        try {

            ConceptDMO conceptdmo = session.getMapper(ConceptDMO.class);
            logger.debug("GCA conceptdmo received");
            conceptAttributesList = conceptdmo.getConceptAttributes(lastpulldatatime, locationuuid);
            logger.debug("GCA conceptattriblist size "+conceptAttributesList.size());
            return conceptAttributesList;
        } catch (PersistenceException e) {
            logger.error(e.getMessage(),e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}
