package com.emrmiddleware.dmo;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.ObsDTO;

public interface ObsDMO {


	@Select("select distinct a.uuid as uuid ,encounter.uuid as encounteruuid, CASE  WHEN a.value_numeric IS NOT NULL 	THEN CAST(a.value_numeric AS CHAR(50) CHARACTER SET utf8) WHEN a.value_text IS NOT NULL THEN a.value_text END as value,concept.uuid as conceptuuid,a.creator,a.voided	, coalesce(a.date_voided,a.date_created) as obsServerModifiedDate, a.comments as comment 	from obs,encounter,visit,location,concept,obs a where obs.encounter_id=encounter.encounter_id    	and encounter.visit_id=visit.visit_id and visit.location_id=location.location_id 	and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}))	and location.uuid=#{locationuuid} and a.encounter_id=encounter.encounter_id and a.concept_id=concept.concept_id	and obs.encounter_id=a.encounter_id "
			+ " union all "
			+ "	SELECT o.uuid ,pv.uuid encounteruuid ,CONCAT(cn.name, ':', do.duration, ':', do.dosing_instructions) value,'c38c0c50-2fd2-4ae3-b7ba-7dd25adca4ca' conceptuuid,o.orderer ,o.voided ,coalesce(o.date_voided, o.date_created) as obsServerModifiedDate,'' as comment from orders o join drug_order do on  o.order_id=do.order_id 	join concept_name cn  on  o.concept_id=cn.concept_id and cn.concept_name_type='FULLY_SPECIFIED' join encounter e on e.encounter_id =o.encounter_id 	join visit on  e.visit_id = visit.visit_id  join ( select e.uuid,p.person_id  from visit v join person p on v.patient_id = p.person_id join encounter e on e.visit_id =v.visit_id where e.encounter_type =9 order by  v.visit_id  desc ) as pv on e.patient_id = pv.person_id	join location on visit.location_id = location.location_id 	where 	((coalesce(o.date_voided, o.date_created)>=	#{lastchangedtime}) or (e.date_changed>=#{lastchangedtime}))	and location.uuid=#{locationuuid} and o.order_type_id= 2 "
			+ " union all "
			+ " SELECT 	o.uuid , pv.uuid encounteruuid , CONCAT('tests.',cn.name) value, '23601d71-50e6-483f-968d-aeef3031346d' conceptuuid,	o.orderer ,	o.voided ,	coalesce(o.date_voided, o.date_created) as obsServerModifiedDate,	'' as comment from 	orders o join concept_name cn on 	o.concept_id = cn.concept_id and cn.concept_name_type = 'FULLY_SPECIFIED' join encounter e on  	e.encounter_id = o.encounter_id join visit on 	e.visit_id = visit.visit_id  join ( select e.uuid,p.person_id  from visit v join person p on v.patient_id = p.person_id join encounter e on e.visit_id =v.visit_id where e.encounter_type =9 order by  v.visit_id  desc ) as pv on e.patient_id = pv.person_id join location on visit.location_id = location.location_id where ((coalesce(o.date_voided, o.date_created)>= #{lastchangedtime})  or (e.date_changed >= #{lastchangedtime})) 	and location.uuid = #{locationuuid} 	and o.order_type_id= 3 "
			+ "") // New for Ezazi , obs.comments
	//@Select("select distinct a.uuid as uuid ,encounter.uuid as encounteruuid, CASE  WHEN a.value_numeric IS NOT NULL 	THEN CAST(a.value_numeric AS CHAR(50) CHARACTER SET utf8) WHEN a.value_text IS NOT NULL THEN a.value_text END as value,concept.uuid as conceptuuid,a.creator,a.voided	, coalesce(a.date_voided,a.date_created) as obsServerModifiedDate	from obs,encounter,visit,location,concept,obs a where obs.encounter_id=encounter.encounter_id    	and encounter.visit_id=visit.visit_id and visit.location_id=location.location_id 	and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}))	and location.uuid=#{locationuuid} and a.encounter_id=encounter.encounter_id and a.concept_id=concept.concept_id	and obs.encounter_id=a.encounter_id") -- Old
	public ArrayList<ObsDTO> getObsList(@Param("lastchangedtime") String lastchangedtime,@Param("locationuuid") String locationuuid);

	@Select("select uuid,voided from obs where uuid=#{obsuuid}")
	public ObsDTO getObs(@Param("obsuuid") String uuid);
}
