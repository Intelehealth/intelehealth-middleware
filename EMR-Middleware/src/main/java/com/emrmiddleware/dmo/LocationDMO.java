package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.LocationDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;


public interface LocationDMO {

    @Select("select name,uuid as locationuuid,retired from location where COALESCE(date_changed,date_created)>=#{lastchangedtime}")
    ArrayList<LocationDTO> getLocations(@Param("lastchangedtime") String lastpulldatatime);
}
