/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Select
 */
package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.EncounterDTO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EncounterDMO {
    @Select(value={"select distinct encounter.uuid as uuid,encounter.encounter_datetime as encounter_time, visit.uuid as visituuid,encounter_type.uuid as encounter_type_uuid,provider.uuid as provider_uuid,encounter.voided from encounter,visit,obs,location,encounter_type,encounter_provider,provider where  encounter.visit_id=visit.visit_id  and visit.location_id=location.location_id and encounter.encounter_id=encounter_provider.encounter_id and encounter_provider.provider_id=provider.provider_id and obs.encounter_id=encounter.encounter_id  and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime})) and location.uuid=#{locationuuid} and encounter.encounter_type=encounter_type.encounter_type_id"})
    public ArrayList<EncounterDTO> getEncounters(@Param(value="lastchangedtime") String var1, @Param(value="locationuuid") String var2);

    @Select(value={"select uuid,voided from encounter where uuid=#{uuid}"})
    public EncounterDTO getEncounter(@Param(value="uuid") String var1);
}

