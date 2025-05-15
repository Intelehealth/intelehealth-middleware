package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.UserCredentialDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserCredentialsDMO {

  @Select("select username,password,salt from users where username=#{username}")
  public UserCredentialDTO getUserCredentials(@Param("username") String username);
}
