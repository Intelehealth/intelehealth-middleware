/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.session.SqlSession
 *  org.apache.ibatis.session.SqlSessionFactory
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.VisitDMO;
import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitAttributeTypeDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisitDAO {
    private final Logger logger = LoggerFactory.getLogger(VisitDAO.class);

    public ArrayList<VisitDTO> getVisits(String lastpulldatatime, String locationuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<VisitDTO> visitlist = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            VisitDMO patientdmo = (VisitDMO)session.getMapper(VisitDMO.class);
            ArrayList<VisitDTO> arrayList = visitlist = patientdmo.getVisits(lastpulldatatime, locationuuid);
            return arrayList;
        }
    }

    public VisitDTO getVisit(String visituuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        VisitDTO visitdto = new VisitDTO();
        try (SqlSession session = sessionfactory.openSession();){
            VisitDMO visitdmo = (VisitDMO)session.getMapper(VisitDMO.class);
            VisitDTO visitDTO = visitdto = visitdmo.getVisit(visituuid);
            return visitDTO;
        }
    }

    public String getDBCurrentTime() throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        String currentTime = null;
        try (SqlSession session = sessionfactory.openSession();){
            VisitDMO visitdmo = (VisitDMO)session.getMapper(VisitDMO.class);
            String string = currentTime = visitdmo.getDBCurrentTime();
            return string;
        }
    }

    public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeMaster(String lastpulldatatime) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<VisitAttributeTypeDTO> visitAttributeMasterList = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            VisitDMO visitdmo = (VisitDMO)session.getMapper(VisitDMO.class);
            ArrayList<VisitAttributeTypeDTO> arrayList = visitAttributeMasterList = visitdmo.getVisitAttributeMaster(lastpulldatatime);
            return arrayList;
        }
    }

    public ArrayList<VisitAttributeDTO> getVisitAttributes(String lastpulldatatime, String locationuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<VisitAttributeDTO> visitAttributesList = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            VisitDMO visitdmo = (VisitDMO)session.getMapper(VisitDMO.class);
            ArrayList<VisitAttributeDTO> arrayList = visitAttributesList = visitdmo.getVisitAttributes(lastpulldatatime, locationuuid);
            return arrayList;
        }
    }
}

