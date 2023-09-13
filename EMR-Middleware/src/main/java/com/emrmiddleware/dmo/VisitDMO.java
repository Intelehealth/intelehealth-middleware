package com.emrmiddleware.dmo;

import java.util.ArrayList;

import com.emrmiddleware.dto.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VisitDMO {

	@Select("select distinct person.uuid as patientuuid,visit.uuid as uuid,visit_type.uuid as visit_type_uuid,date_format(visit.date_started,'%b %d, %Y %I:%i:%S %p') as startdate,date_format(visit.date_stopped,'%b %d, %Y %I:%i:%S %p') as enddate,location.uuid as locationuuid,users.uuid as creator_uuid,visit.voided from patient, person,visit_type, visit,encounter,obs,location,users where person.person_id=patient.patient_id and visit.creator=users.user_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id AND visit.visit_type_id=visit_type.visit_type_id and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}) or (COALESCE(visit.date_changed,visit.date_created)>=#{lastchangedtime})) and location.uuid=#{locationuuid} and visit.voided=0")
    ArrayList<VisitDTO> getVisits(@Param("lastchangedtime") String lastpulldatatime,
                                  @Param("locationuuid") String locationuuid);

	@Select("select uuid from visit where uuid=#{uuid}")
    VisitDTO getVisit(@Param("uuid") String uuid);

	@Select("select uuid as uuid ,name,retired from visit_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
    ArrayList<VisitAttributeTypeDTO> getVisitAttributeMaster(@Param("lastchangedtime") String lastpulldatatime);

	@Select("select visit_attribute.uuid , visit.visit_id as visit_id, visit.uuid as visit_uuid,visit_attribute.value_reference as value,visit_attribute_type.uuid as visit_attribute_type_uuid from visit,visit_attribute,visit_attribute_type,location where visit_attribute.visit_id=visit.visit_id and visit_attribute.attribute_type_id=visit_attribute_type.visit_attribute_type_id and COALESCE(visit_attribute.date_changed,visit_attribute.date_created)>=#{lastchangedtime} and visit.location_id=location.location_id and location.uuid=#{locationuuid}")
    ArrayList<VisitAttributeDTO> getVisitAttributes(@Param("lastchangedtime") String lastpulldatatime,
                                                    @Param("locationuuid") String locationuuid);
	
	@Select("select now() as currenttime")
    String getDBCurrentTime();

    @Select(" select uuid as uuid ,name,retired from concept_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime} ")
    ArrayList<ConceptAttributeTypeDTO> getConceptAttributeTypeMaster(@Param("lastchangedtime") String lastpulldatatime);

    @Select("select distinct concept_attribute.uuid , concept.uuid as concept_uuid, concept_attribute.value_reference as value, concept_attribute_type.uuid as concept_attribute_type_uuid from concept, concept_attribute, concept_attribute_type, location where concept_attribute.concept_id=concept.concept_id and concept_attribute.attribute_type_id=concept_attribute_type.concept_attribute_type_id and COALESCE(concept_attribute.date_changed,concept_attribute.date_created)>=#{lastchangedtime} ")
    ArrayList<ConceptAttributeDTO> getConceptAttributes(@Param("lastchangedtime") String lastpulldatatime,
                                                        @Param("locationuuid") String locationuuid);
}
