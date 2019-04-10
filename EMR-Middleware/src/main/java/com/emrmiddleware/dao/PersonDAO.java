package com.emrmiddleware.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.PatientDMO;
import com.emrmiddleware.dmo.PersonDMO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.exception.DAOException;

public class PersonDAO {

	public PersonDTO getPerson(String personuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		PersonDTO persondto = new PersonDTO();
		try {

			PersonDMO persondmo = session.getMapper(PersonDMO.class);
			persondto = persondmo.getPerson(personuuid);
			return persondto;
		} catch (PersistenceException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
}
