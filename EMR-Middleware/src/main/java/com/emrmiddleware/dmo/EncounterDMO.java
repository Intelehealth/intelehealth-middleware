package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.EncounterDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;


public interface EncounterDMO {


    @Select("select distinct encounter.uuid as uuid,encounter.encounter_datetime as encounter_time, visit.uuid as visituuid,encounter_type.uuid as encounter_type_uuid,provider.uuid as provider_uuid,encounter.voided from encounter,visit,obs,location,encounter_type,encounter_provider,provider where  encounter.visit_id=visit.visit_id  and visit.location_id=location.location_id and encounter.encounter_id=encounter_provider.encounter_id and encounter_provider.provider_id=provider.provider_id and obs.encounter_id=encounter.encounter_id  and ((coalesce(obs.date_voided,obs.date_created)>=#{lastchangedtime}) or (encounter.date_changed>=#{lastchangedtime})) and location.uuid=#{locationuuid} and encounter.encounter_type=encounter_type.encounter_type_id")
    ArrayList<EncounterDTO> getEncounters(@Param("lastchangedtime") String lastpulldatatime, @Param("locationuuid") String locationuuid);

    @Select("select uuid,voided from encounter where uuid=#{uuid}")
    EncounterDTO getEncounter(@Param("uuid") String uuid);
}
