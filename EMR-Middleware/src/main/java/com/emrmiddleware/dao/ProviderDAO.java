package com.emrmiddleware.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.PatientDMO;
import com.emrmiddleware.dmo.ProviderDMO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.ProviderAttributeDTO;
import com.emrmiddleware.dto.ProviderAttributeTypeDTO;
import com.emrmiddleware.dto.ProviderDTO;
import com.emrmiddleware.exception.DAOException;

public class ProviderDAO {
	
	public ArrayList<ProviderDTO> getProviders(Timestamp lastdatapulltime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ProviderDTO> providerlist = new ArrayList<ProviderDTO>();
		try {

			ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
			providerlist = providerdmo.getProviders(lastdatapulltime);
			return providerlist;
		} catch (PersistenceException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeMaster(Timestamp lastdatapulltime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList = new ArrayList<ProviderAttributeTypeDTO>();
		try {

			ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
			providerAttributeTypeList = providerdmo.getProviderAttributeTypeMaster(lastdatapulltime);
			return providerAttributeTypeList;
		} catch (PersistenceException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}


	public ArrayList<ProviderAttributeDTO> getProviderAttributes(Timestamp lastdatapulltime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ProviderAttributeDTO> providerAttributeList = new ArrayList<ProviderAttributeDTO>();
		try {

			ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
			providerAttributeList = providerdmo.getProviderAttributes(lastdatapulltime);
			return providerAttributeList;
		} catch (PersistenceException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

}
