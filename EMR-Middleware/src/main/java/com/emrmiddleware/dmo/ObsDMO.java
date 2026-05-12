/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Select
 */
package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.ObsDTO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ObsDMO {
    @Select(value={"select distinct a.uuid as uuid ,encounter.uuid as encounteruuid, CASE  WHEN a.value_numeric IS NOT NULL \tTHEN CAST(a.value_numeric AS CHAR(50) CHARACTER SET utf8) WHEN a.value_text IS NOT NULL THEN a.value_text END as value,concept.uuid as conceptuuid,a.creator,a.voided\t, coalesce(a.date_voided,a.date_created) as obsServerModifiedDate, a.comments as comment \tfrom obs,encounter,visit,location,concept,obs a where obs.encounter_id=encounter.encounter_id    \tand encounter.visit_id=visit.visit_id and visit.location_id=location.location_id \tand ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime}))\tand location.uuid=#{locationuuid} and a.encounter_id=encounter.encounter_id and a.concept_id=concept.concept_id\tand obs.encounter_id=a.encounter_id"})
    public ArrayList<ObsDTO> getObsList(@Param(value="lastchangedtime") String var1, @Param(value="locationuuid") String var2);

    @Select(value={"select uuid,voided from obs where uuid=#{obsuuid}"})
    public ObsDTO getObs(@Param(value="obsuuid") String var1);
}

