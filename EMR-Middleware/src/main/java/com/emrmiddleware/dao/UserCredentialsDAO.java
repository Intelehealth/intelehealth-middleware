package com.emrmiddleware.dao;


import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.UserCredentialsDMO;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.emrmiddleware.exception.DAOException;

public class UserCredentialsDAO {
	
	
	public UserCredentialDTO getUserCredentail(String username) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		UserCredentialDTO userCredentialdto =null;
		try {

			UserCredentialsDMO usercredentialsdmo = session.getMapper(UserCredentialsDMO.class);
			userCredentialdto = usercredentialsdmo.getUserCredentials(username);
			return userCredentialdto;
		} catch (PersistenceException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	

}
