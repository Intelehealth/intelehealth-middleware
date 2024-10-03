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


    public ArrayList<ConceptAttributeTypeDTO> getConceptAttributeTypeMaster(String lastpulldatatime) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        SqlSession session = sessionfactory.openSession();
        ArrayList<ConceptAttributeTypeDTO> conceptAttributeMasterList= new ArrayList<ConceptAttributeTypeDTO>();
        try {

            ConceptDMO conceptdmo = session.getMapper(ConceptDMO.class);
             conceptAttributeMasterList = conceptdmo.getConceptAttributeTypeMaster(lastpulldatatime);
            return conceptAttributeMasterList;
        } catch (PersistenceException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    public ArrayList<ConceptAttributeDTO> getConceptAttributes(String lastpulldatatime) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        SqlSession session = sessionfactory.openSession();
        ArrayList<ConceptAttributeDTO> conceptAttributesList= new ArrayList<ConceptAttributeDTO>();
        try {

            ConceptDMO conceptdmo = session.getMapper(ConceptDMO.class);
            conceptAttributesList = conceptdmo.getConceptAttributes(lastpulldatatime);

            return conceptAttributesList;
        } catch (PersistenceException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}
