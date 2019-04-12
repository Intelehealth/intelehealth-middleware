package com.emrmiddleware.dmo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.EncounterDTO;


public interface EncounterDMO {

	
	@Select("select distinct encounter.uuid as uuid,visit.uuid as visituuid,encounter.encounter_type from encounter,visit,obs,location where  encounter.visit_id=visit.visit_id  and visit.location_id=location.location_id and obs.encounter_id=encounter.encounter_id  and obs.obs_datetime>=#{lastchangedtime} and location.uuid=#{locationuuid}")
	public ArrayList<EncounterDTO> getEncounters(@Param("lastchangedtime") Date lastchangedtime,@Param("locationuuid") String locationuuid);
	
	@Select ("select uuid from encounter where uuid=#{uuid}")
	public EncounterDTO getEncounter(@Param("uuid") String uuid);
}
