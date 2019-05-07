package com.emrmiddleware.dmo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.FileDTO;

public interface FileDMO {
	
	@Insert("insert into image(imageuuid,filename,objectuuid,objectname,imagerawname,filepath,voided,attributes,locationuuid) values(#{uuid},#{filename},#{objectuuid},#{objectname},#{imagerawname},#{filepath},#{voided},#{attributes},#{locationuuid})")
	public void insertFile(FileDTO filedto);
	
	@Select("select imagerawname,filepath from image where imageuuid=#{uuid}")
	public FileDTO getFile(@Param("uuid") String uuid);

}
