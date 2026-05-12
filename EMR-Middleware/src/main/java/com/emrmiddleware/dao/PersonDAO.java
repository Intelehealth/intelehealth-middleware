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
import com.emrmiddleware.dmo.PersonDMO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.exception.DAOException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonDAO {
    private final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    public PersonDTO getPerson(String personuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        PersonDTO persondto = new PersonDTO();
        try (SqlSession session = sessionfactory.openSession();){
            System.out.println(personuuid + " :: PERSON UUID");
            PersonDMO persondmo = (PersonDMO)session.getMapper(PersonDMO.class);
            PersonDTO personDTO = persondto = persondmo.getPerson(personuuid);
            return personDTO;
        }
    }
}

