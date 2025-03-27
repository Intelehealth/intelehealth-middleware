package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.PersonDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PersonDMO {

    @Select("SELECT distinct person.uuid as uuid from person where uuid=#{uuid}")
    PersonDTO getPerson(@Param("uuid") String uuid);
}
