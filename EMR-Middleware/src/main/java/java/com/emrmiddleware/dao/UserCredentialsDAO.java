package com.emrmiddleware.dao;


import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.UserCredentialsDMO;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.emrmiddleware.exception.DAOException;

public class UserCredentialsDAO {
	
	private final Logger logger = LoggerFactory.getLogger(UserCredentialsDAO.class);
	public UserCredentialDTO getUserCredentail(String username) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		UserCredentialDTO userCredentialdto =null;
		try {

			UserCredentialsDMO usercredentialsdmo = session.getMapper(UserCredentialsDMO.class);
			userCredentialdto = usercredentialsdmo.getUserCredentials(username);
			return userCredentialdto;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	

}
