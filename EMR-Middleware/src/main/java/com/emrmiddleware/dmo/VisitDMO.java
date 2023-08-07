package com.emrmiddleware.dmo;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitAttributeTypeDTO;
import org.apache.ibatis.annotations.Update;

public interface VisitDMO {

	@Select("select distinct person.uuid as patientuuid,visit.uuid as uuid,visit_type.uuid as visit_type_uuid,visit.date_started as startdate,visit.date_stopped as enddate,location.uuid as locationuuid,users.uuid as creator_uuid,visit.voided from patient, person,visit_type, visit,encounter,obs,location,users where person.person_id=patient.patient_id and visit.creator=users.user_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id AND visit.visit_type_id=visit_type.visit_type_id and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}) or (COALESCE(visit.date_changed,visit.date_created)>=#{lastchangedtime})) and location.uuid=#{locationuuid} and visit.voided=0")
	public ArrayList<VisitDTO> getVisits(@Param("lastchangedtime") String lastpulldatatime,
			@Param("locationuuid") String locationuuid);

	@Select("select uuid from visit where uuid=#{uuid}")
	public VisitDTO getVisit(@Param("uuid") String uuid);

	@Select("select uuid as uuid ,name,retired from visit_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
	public ArrayList<VisitAttributeTypeDTO> getVisitAttributeMaster(@Param("lastchangedtime") String lastpulldatatime);

	@Select("select visit_attribute.uuid ,visit.uuid as visit_uuid,visit_attribute.value_reference as value,visit_attribute_type.uuid as visit_attribute_type_uuid from visit,visit_attribute,visit_attribute_type,location where visit_attribute.visit_id=visit.visit_id and visit_attribute.attribute_type_id=visit_attribute_type.visit_attribute_type_id and COALESCE(visit_attribute.date_changed,visit_attribute.date_created)>=#{lastchangedtime} and visit.location_id=location.location_id and location.uuid=#{locationuuid} ")
	public ArrayList<VisitAttributeDTO> getVisitAttributes(@Param("lastchangedtime") String lastpulldatatime,
			@Param("locationuuid") String locationuuid);
	
	@Select("select now() as currenttime")
	public String getDBCurrentTime();


}
