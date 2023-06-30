package com.emrmiddleware.authentication;


import java.util.Base64;

import com.emrmiddleware.dao.UserCredentialsDAO;
import com.emrmiddleware.dto.UserCredentialDTO;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.utils.EmrUtils;



public class AuthenticationUtil {
	public boolean isUserAuthenticated(String authString) throws DAOException{
        
        boolean isAuthenticated=false;
        if (authString==null)
        	return false;
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        // Decode the data back to original string
        Base64.Decoder decoder = Base64.getDecoder();
        String dStr = new String(decoder.decode(authInfo)); 
        // logger.info("Decoded String is : "+dStr);
         String[] userParts = dStr.split(":");
         String username = userParts[0];
         String password = userParts[1];
         UserCredentialsDAO userCredentialsdao = new UserCredentialsDAO();
         UserCredentialDTO userCredentialdto = new UserCredentialDTO();
         userCredentialdto = userCredentialsdao.getUserCredentail(username);
         if (userCredentialdto==null){
        	 return false;
         }
         String salt = userCredentialdto.getSalt();
         String hashpassword = EmrUtils.get_SHA_512_SecurePassword(password, salt);
         if (hashpassword.equals(userCredentialdto.getPassword()))
        	 isAuthenticated=true;
         else
        	 isAuthenticated=false;
           
        return isAuthenticated;
    } 
	
	public UserCredentialDTO getAuthHeader(String header){
		UserCredentialDTO userCredentialdto = new UserCredentialDTO();
		String[] authParts = header.split("\\s+");
        String authInfo = authParts[1];
        // Decode the data back to original string
        Base64.Decoder decoder = Base64.getDecoder();
        String dStr = new String(decoder.decode(authInfo)); 
        // logger.info("Decoded String is : "+dStr);
         String[] userParts = dStr.split(":");
         userCredentialdto.setUsername(userParts[0]);
         userCredentialdto.setPassword(userParts[1]);
         return userCredentialdto;
	}

}
