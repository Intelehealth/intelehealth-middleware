/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Select
 */
package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.PersonDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PersonDMO {
    @Select(value={"SELECT distinct person.uuid as uuid from person where uuid=#{uuid}"})
    public PersonDTO getPerson(@Param(value="uuid") String var1);
}

