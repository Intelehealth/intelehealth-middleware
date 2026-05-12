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
import com.emrmiddleware.dmo.PatientDMO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatientDAO {
    private final Logger logger = LoggerFactory.getLogger(PatientDAO.class);

    public ArrayList<PatientDTO> getPatients(String lastpulldatatime, String locationuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<PatientDTO> patientlist = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            PatientDMO patientdmo = (PatientDMO)session.getMapper(PatientDMO.class);
            ArrayList<PatientDTO> arrayList = patientlist = patientdmo.getPatients(lastpulldatatime, locationuuid);
            return arrayList;
        }
    }

    public ArrayList<PatientAttributeTypeDTO> getPatientAttributeType(String lastpulldatatime, String locationuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<PatientAttributeTypeDTO> patientAttributeTypeList = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            PatientDMO patientdmo = (PatientDMO)session.getMapper(PatientDMO.class);
            ArrayList<PatientAttributeTypeDTO> arrayList = patientAttributeTypeList = patientdmo.getPatientAttributeMaster(lastpulldatatime);
            return arrayList;
        }
    }

    public ArrayList<PatientAttributeDTO> getPatientAttributes(String lastpulldatatime, String locationuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<PatientAttributeDTO> patientAttributesList = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            PatientDMO patientdmo = (PatientDMO)session.getMapper(PatientDMO.class);
            ArrayList<PatientAttributeDTO> arrayList = patientAttributesList = patientdmo.getPatientAttributes(lastpulldatatime, locationuuid);
            return arrayList;
        }
    }

    public PatientDTO getPatient(String personuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        PatientDTO patientdto = new PatientDTO();
        try (SqlSession session = sessionfactory.openSession();){
            PatientDMO patientdmo = (PatientDMO)session.getMapper(PatientDMO.class);
            PatientDTO patientDTO = patientdto = patientdmo.getPatient(personuuid);
            return patientDTO;
        }
    }
}

