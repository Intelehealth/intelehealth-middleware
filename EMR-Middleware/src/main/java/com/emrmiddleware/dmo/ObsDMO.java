package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.ObsDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface ObsDMO {

    @Select("select distinct a.uuid as uuid , " +
            " encounter.uuid as encounteruuid, " +
            "CASE " +
            " WHEN a.value_numeric IS NOT NULL THEN CAST(a.value_numeric AS CHAR(50) CHARACTER SET utf8) " +
            " WHEN a.value_text IS NOT NULL THEN a.value_text " +
            " WHEN a.value_coded IS NOT NULL THEN ( " +
            " select concept.uuid from concept where concept.concept_id=a.value_coded) " +
            " END as value, " +
            " concept.uuid as conceptuuid, " +
            " IFNULL((SELECT cs_concept.uuid " +
            " FROM concept_set cs " +
            " JOIN concept cs_concept ON cs.concept_set = cs_concept.concept_id " +
            " WHERE cs.concept_id = a.concept_id " +
            " LIMIT 1),'NA') AS conceptsetuuid, " +
            "'NA' AS interpretation , " +
            " a.creator, " +
            " a.voided , " +
            " coalesce(a.date_voided,a.date_created) as obsServerModifiedDate, " +
            " a.comments as comment " +
            " from " +
            " obs a, encounter, visit, location, concept " +
            " where a.encounter_id=encounter.encounter_id " +
            " and encounter.visit_id=visit.visit_id " +
            " and visit.location_id=location.location_id " +
            " and ((coalesce(a.date_voided,a.date_created)>=#{lastchangedtime}) " +
            " or (encounter.date_changed>=#{lastchangedtime})) " +
            " and location.uuid=#{locationuuid} " +
            " and a.concept_id=concept.concept_id")
    ArrayList<ObsDTO> getObsList(@Param("lastchangedtime") String lastchangedtime, @Param("locationuuid") String locationuuid);

    @Select("select uuid,voided from obs where uuid=#{obsuuid}")
    ObsDTO getObs(@Param("obsuuid") String uuid);
}
