package org.intelehealth.middleware.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.intelehealth.middleware.models.User;
import org.intelehealth.middleware.utils.WebClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
@Autowired
MySQLService mysqlService;


@Autowired
ConstantsService constantsService;


private static final Logger LOG = LoggerFactory.getLogger(WebClientFilter.class);


	public boolean validate(String authString) {
		// TODO Auto-generated method stub
		String decodedAuth = "";
         
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        // Decode the data back to original string
        byte[] bytes = null;
        
        try {
            bytes = Base64.getDecoder().decode(authInfo);
        } 
        catch(Exception e) {}
        decodedAuth = new String(bytes);
        String username = decodedAuth.substring(0,decodedAuth.indexOf(constantsService.COLON));
        String password = decodedAuth.substring(decodedAuth.indexOf(constantsService.COLON) + 1);
         
        User u = null;
		u = mysqlService.findUser(username);
        String sentPassword= getSHA512SecurePassword(password,u.getSalt());
         
        if(sentPassword.equals(u.getPassword())) {
        	 
        	return true;
        }        	
        else {
        	 
        	return false;
        }
		
  	}
	
	public String getSHA512SecurePassword(String originalPassword, String salt) {
		String transformedPassword="";
		try { 
            // getInstance() method is called with algorithm SHA-512 
            MessageDigest md = MessageDigest.getInstance("SHA-512"); 
  
            // digest() method is called 
            // to calculate message digest of the input string 
            // returned as array of byte 
            String input = originalPassword+salt;
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            // Add preceding 0s to make it 32 bit 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            // return the HashText 
            transformedPassword=hashtext;
            return transformedPassword; 
        } 
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
	//	return transformedPassword;
		
	}

	public String getUserNameAndPasswordFromHeader(String authString) {
		String decodedAuth = "";
        
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        
        // Decode the data back to original string
        byte[] bytes = null;
        
        try {
            bytes = Base64.getDecoder().decode(authInfo);
        } 
        catch(Exception e) {}
        decodedAuth = new String(bytes);
        return decodedAuth;
	}
	
	
}
