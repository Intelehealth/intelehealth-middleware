package com.emrmiddleware.dmo;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitAttributeTypeDTO;
import com.emrmiddleware.dto.VisitDTO;

public interface VisitDMO {

  @Select("select distinct person.uuid as patientuuid,visit.uuid as uuid,visit_type.uuid as visit_type_uuid,visit.date_started as startdate, visit.date_stopped as enddate,location.uuid as locationuuid,users.uuid as creator_uuid,visit.voided,visit.visit_id as visitid from patient, person,visit_type, visit,encounter,obs,location,users where person.person_id=patient.patient_id and visit.creator=users.user_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id AND visit.visit_type_id=visit_type.visit_type_id and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}) or (COALESCE(visit.date_changed,visit.date_created)>=#{lastchangedtime})) and location.uuid=#{locationuuid} and visit.voided=0  and visit.patient_id in ${patientIds}")
  public ArrayList<VisitDTO> getVisits(
          @Param("lastchangedtime") String lastpulldatatime,
          @Param("locationuuid") String locationuuid,
          @Param("patientIds") String patientIDs);

  @Select("select distinct person.uuid as patientuuid,visit.uuid as uuid,visit_type.uuid as visit_type_uuid,visit.date_started as startdate, visit.date_stopped as enddate,location.uuid as locationuuid,users.uuid as creator_uuid,visit.voided,visit.visit_id as visitid from patient, person,visit_type, visit,encounter,obs,location,users where person.person_id=patient.patient_id and visit.creator=users.user_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id AND visit.visit_type_id=visit_type.visit_type_id and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}) or (COALESCE(visit.date_changed,visit.date_created)>=#{lastchangedtime}) ) and location.uuid=#{locationuuid}  AND visit.voided=0")
  ArrayList<VisitDTO> getVisitsNew(@Param("lastchangedtime") String lastpulldatatime,
                                   @Param("locationuuid") String locationuuid);

  @Select("select uuid from visit where uuid=#{uuid}")
  public VisitDTO getVisit(@Param("uuid") String uuid);

  @Select("select uuid as uuid ,name,retired from visit_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
  public ArrayList<VisitAttributeTypeDTO> getVisitAttributeMaster(
          @Param("lastchangedtime") String lastpulldatatime);

  @Select("SELECT DISTINCT visit_attribute.uuid, " +
          "visit.uuid as visit_uuid, " +
          "visit_attribute.value_reference as value, " +
          "visit_attribute_type.uuid as visit_attribute_type_uuid " +
          "FROM visit, visit_attribute, visit_attribute_type " +
          "WHERE visit_attribute.visit_id = visit.visit_id " +
          "AND visit_attribute.attribute_type_id = visit_attribute_type.visit_attribute_type_id " +
          "AND visit.visit_id in ${visitIds} " +
          "AND visit_attribute.voided = 0 " +
          "ORDER BY visit_uuid, visit_attribute_type_uuid, value")
  ArrayList<VisitAttributeDTO> getVisitAttributes(@Param("visitIds") String visitIds);

  @Select("select now() as currenttime")
  public String getDBCurrentTime();

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
  int getVisitCount(@Param("lastchangedtime") String lastpulldatatime,
                    @Param("locationuuid") String locationuuid);
}
