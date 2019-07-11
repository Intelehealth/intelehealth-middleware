package com.emrmiddleware.dmo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.EncounterDTO;


public interface EncounterDMO {

	
	@Select("select distinct encounter.uuid as uuid,visit.uuid as visituuid,encounter_type.uuid as encounter_type_uuid,provider.uuid as provider_uuid,encounter.voided from encounter,visit,obs,location,encounter_type,encounter_provider,provider where  encounter.visit_id=visit.visit_id  and visit.location_id=location.location_id and encounter.encounter_id=encounter_provider.encounter_id and encounter_provider.provider_id=provider.provider_id and obs.encounter_id=encounter.encounter_id  and obs.obs_datetime>=#{lastchangedtime} and location.uuid=#{locationuuid} and encounter.encounter_type=encounter_type.encounter_type_id")
	public ArrayList<EncounterDTO> getEncounters(@Param("lastchangedtime") Date lastchangedtime,@Param("locationuuid") String locationuuid);
	
	@Select ("select uuid,voided from encounter where uuid=#{uuid}")
	public EncounterDTO getEncounter(@Param("uuid") String uuid);
}
