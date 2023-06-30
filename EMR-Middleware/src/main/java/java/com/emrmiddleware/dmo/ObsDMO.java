package com.emrmiddleware.dmo;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.ObsDTO;

public interface ObsDMO {


	@Select("select distinct a.uuid as uuid ,encounter.uuid as encounteruuid, CASE  WHEN a.value_numeric IS NOT NULL 	THEN CAST(a.value_numeric AS CHAR(50) CHARACTER SET utf8) WHEN a.value_text IS NOT NULL THEN a.value_text END as value,concept.uuid as conceptuuid,a.creator,a.voided	, coalesce(a.date_voided,a.date_created) as obsServerModifiedDate, a.comments as comment 	from obs,encounter,visit,location,concept,obs a where obs.encounter_id=encounter.encounter_id    	and encounter.visit_id=visit.visit_id and visit.location_id=location.location_id 	and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}))	and location.uuid=#{locationuuid} and a.encounter_id=encounter.encounter_id and a.concept_id=concept.concept_id	and obs.encounter_id=a.encounter_id") // New for Ezazi , obs.comments
	//@Select("select distinct a.uuid as uuid ,encounter.uuid as encounteruuid, CASE  WHEN a.value_numeric IS NOT NULL 	THEN CAST(a.value_numeric AS CHAR(50) CHARACTER SET utf8) WHEN a.value_text IS NOT NULL THEN a.value_text END as value,concept.uuid as conceptuuid,a.creator,a.voided	, coalesce(a.date_voided,a.date_created) as obsServerModifiedDate	from obs,encounter,visit,location,concept,obs a where obs.encounter_id=encounter.encounter_id    	and encounter.visit_id=visit.visit_id and visit.location_id=location.location_id 	and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}))	and location.uuid=#{locationuuid} and a.encounter_id=encounter.encounter_id and a.concept_id=concept.concept_id	and obs.encounter_id=a.encounter_id") -- Old
	public ArrayList<ObsDTO> getObsList(@Param("lastchangedtime") String lastchangedtime,@Param("locationuuid") String locationuuid);

	@Select("select uuid,voided from obs where uuid=#{obsuuid}")
	public ObsDTO getObs(@Param("obsuuid") String uuid);
}
