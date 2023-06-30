package com.emrmiddleware.dmo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.UserCredentialDTO;

public interface UserCredentialsDMO {
	
	@Select("select username,password,salt from users where username=#{username}")
	public UserCredentialDTO getUserCredentials(@Param("username") String username);

}
