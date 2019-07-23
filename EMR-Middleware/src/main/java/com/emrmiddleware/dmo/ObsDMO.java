package com.emrmiddleware.dmo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.ObsDTO;

public interface ObsDMO {

	@Select("select obs.uuid as uuid ,encounter.uuid as encounteruuid, CASE  WHEN obs.value_numeric IS NOT NULL THEN CAST(obs.value_numeric AS CHAR(50) CHARACTER SET utf8) WHEN obs.value_text IS NOT NULL THEN obs.value_text END as value,concept.uuid as conceptuuid,obs.creator,obs.voided from obs,encounter,visit,location,concept where obs.encounter_id=encounter.encounter_id     and encounter.visit_id=visit.visit_id and visit.location_id=location.location_id and coalesce(obs.date_voided,obs.obs_datetime)>=#{lastchangedtime} and location.uuid=#{locationuuid} and obs.concept_id=concept.concept_id")
	public ArrayList<ObsDTO> getObs(@Param("lastchangedtime") Date lastchangedtime,@Param("locationuuid") String locationuuid);
}
