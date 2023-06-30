package com.emrmiddleware.dmo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.PersonDTO;

public interface PersonDMO {

	@Select("SELECT distinct person.uuid as uuid from person where uuid=#{uuid}")
	public PersonDTO getPerson(@Param("uuid") String uuid);
}
