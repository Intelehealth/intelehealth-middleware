package com.emrmiddleware.dmo;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.LocationDTO;


public interface LocationDMO {

	@Select("select name,uuid as locationuuid,retired from location where COALESCE(date_changed,date_created)>=#{lastchangedtime}")
	public ArrayList<LocationDTO> getLocations(@Param("lastchangedtime") String lastpulldatatime);
}
