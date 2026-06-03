
package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.UserCredentialsDMO;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.emrmiddleware.exception.DAOException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCredentialsDAO {
    private final Logger logger = LoggerFactory.getLogger(UserCredentialsDAO.class);

    public UserCredentialDTO getUserCredentail(String username) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        UserCredentialDTO userCredentialdto = null;
        try (SqlSession session = sessionfactory.openSession();){
            UserCredentialsDMO usercredentialsdmo = (UserCredentialsDMO)session.getMapper(UserCredentialsDMO.class);
            UserCredentialDTO userCredentialDTO = userCredentialdto = usercredentialsdmo.getUserCredentials(username);
            return userCredentialDTO;
        }
    }
}

