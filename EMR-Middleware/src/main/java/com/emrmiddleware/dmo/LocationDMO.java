/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Select
 */
package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.LocationDTO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LocationDMO {
    @Select(value={"select name,uuid as locationuuid,retired from location where COALESCE(date_changed,date_created)>=#{lastchangedtime}"})
    public ArrayList<LocationDTO> getLocations(@Param(value="lastchangedtime") String var1);
}

