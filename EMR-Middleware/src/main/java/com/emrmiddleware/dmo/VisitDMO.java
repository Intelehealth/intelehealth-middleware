package com.emrmiddleware.dmo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.VisitDTO;

public interface VisitDMO {

	
	@Select("select distinct person.uuid as patientuuid,visit.uuid as uuid,visit_type.uuid as visit_type_uuid,visit.date_started asstartdate,visit.date_stopped as enddate,location.uuid as locationuuid,visit.creator from patient, person,visit_type, visit,encounter,obs,location where person.person_id=patient.patient_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id AND visit.visit_type_id=visit_type.visit_type_id and obs.obs_datetime>=#{lastchangedtime} and location.uuid=#{locationuuid} and visit.voided=0")
	public ArrayList<VisitDTO> getVisits(@Param("lastchangedtime") Date lastchangedtime,@Param("locationuuid") String locationuuid);
}
