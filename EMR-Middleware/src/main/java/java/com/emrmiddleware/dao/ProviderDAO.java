package com.emrmiddleware.dao;

import java.util.ArrayList;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.ProviderDMO;
import com.emrmiddleware.dto.ProviderAttributeDTO;
import com.emrmiddleware.dto.ProviderAttributeTypeDTO;
import com.emrmiddleware.dto.ProviderDTO;
import com.emrmiddleware.exception.DAOException;

public class ProviderDAO {
	private final Logger logger = LoggerFactory.getLogger(ProviderDAO.class);
	public ArrayList<ProviderDTO> getProviders(String lastpulldatatime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ProviderDTO> providerlist = new ArrayList<ProviderDTO>();
		try {

			ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
			providerlist = providerdmo.getProviders(lastpulldatatime);
			return providerlist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeMaster(String lastpulldatatime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList = new ArrayList<ProviderAttributeTypeDTO>();
		try {

			ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
			providerAttributeTypeList = providerdmo.getProviderAttributeTypeMaster(lastpulldatatime);
			return providerAttributeTypeList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}


	public ArrayList<ProviderAttributeDTO> getProviderAttributes(String lastpulldatatime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ProviderAttributeDTO> providerAttributeList = new ArrayList<ProviderAttributeDTO>();
		try {

			ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
			providerAttributeList = providerdmo.getProviderAttributes(lastpulldatatime);
			return providerAttributeList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

}
