package com.emrmiddleware.dmo;

import java.util.ArrayList;

import com.emrmiddleware.dto.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VisitDMO {

	@Select("select distinct person.uuid as patientuuid,visit.uuid as uuid,visit_type.uuid as visit_type_uuid,visit.date_started as startdate,visit.date_stopped as enddate,location.uuid as locationuuid,users.uuid as creator_uuid,visit.voided from patient, person,visit_type, visit,encounter,obs,location,users where person.person_id=patient.patient_id and visit.creator=users.user_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id AND visit.visit_type_id=visit_type.visit_type_id and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}) or (COALESCE(visit.date_changed,visit.date_created)>=#{lastchangedtime})) and location.uuid=#{locationuuid} and visit.voided=0  LIMIT #{limit} OFFSET  #{offset} ")
	public ArrayList<VisitDTO> getVisits(@Param("lastchangedtime") String lastpulldatatime,
			@Param("locationuuid") String locationuuid,
			 @Param("offset") int offset, @Param("limit") int limit
	);

	@Select("select uuid from visit where uuid=#{uuid}")
	public VisitDTO getVisit(@Param("uuid") String uuid);

	@Select("select uuid as uuid ,name,retired from visit_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
	public ArrayList<VisitAttributeTypeDTO> getVisitAttributeMaster(@Param("lastchangedtime") String lastpulldatatime);

	@Select("select visit_attribute.uuid ,visit.uuid as visit_uuid,visit_attribute.value_reference as value,visit_attribute_type.uuid as visit_attribute_type_uuid from visit,visit_attribute,visit_attribute_type,location where visit_attribute.visit_id=visit.visit_id and visit_attribute.attribute_type_id=visit_attribute_type.visit_attribute_type_id and COALESCE(visit_attribute.date_changed,visit_attribute.date_created)>=#{lastchangedtime} and visit.location_id=location.location_id and location.uuid=#{locationuuid}")
	public ArrayList<VisitAttributeDTO> getVisitAttributes(@Param("lastchangedtime") String lastpulldatatime,
			@Param("locationuuid") String locationuuid);
	
	@Select("select now() as currenttime")
	public String getDBCurrentTime();


	// Adding new method visitCount to return total number of records after the lastpulldatatime on that locationuuid
	@Select("select count(1) FROM (" +
			" SELECT distinct person.uuid as patientuuid," +
			"visit.uuid as uuid," +
			"visit_type.uuid as visit_type_uuid," +
			"visit.date_started as startdate," +
			"visit.date_stopped as enddate," +
			"location.uuid as locationuuid," +
			"users.uuid as creator_uuid," +
			"visit.voided from " +
			"patient, person,visit_type, visit,encounter,obs,location,users " +
			"where person.person_id=patient.patient_id " +
			"and visit.creator=users.user_id " +
			"and visit.patient_id=patient.patient_id" +
			" and visit.location_id=location.location_id" +
			" and encounter.visit_id=visit.visit_id " +
			" and obs.encounter_id=encounter.encounter_id" +
			" AND visit.visit_type_id=visit_type.visit_type_id " +
			"and (" +
			"(" +
			"coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}" +
			") " +
			"or (encounter.date_changed>=#{lastchangedtime}) " +
			"or (" +
			"COALESCE(visit.date_changed,visit.date_created)>=#{lastchangedtime}" +
			")" +
			") " +
			"and location.uuid=#{locationuuid} " +
			"and visit.voided=0) t ")
	public int getVisitCount(@Param("lastchangedtime") String lastpulldatatime,
							 @Param("locationuuid") String locationuuid);

	@Select(" select uuid as uuid ,name,retired from concept_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime} ")
	ArrayList<ConceptAttributeTypeDTO> getConceptAttributeTypeMaster(@Param("lastchangedtime") String lastpulldatatime);

	@Select("select distinct concept_attribute.uuid , concept.uuid as concept_uuid, concept_attribute.value_reference as value, concept_attribute_type.uuid as concept_attribute_type_uuid from concept, concept_attribute, concept_attribute_type where concept_attribute.concept_id=concept.concept_id and concept_attribute.attribute_type_id=concept_attribute_type.concept_attribute_type_id and COALESCE(concept_attribute.date_changed,concept_attribute.date_created)>=#{lastchangedtime} and concept_attribute.voided = 0 ")
	ArrayList<ConceptAttributeDTO> getConceptAttributes(@Param("lastchangedtime") String lastpulldatatime);
}
