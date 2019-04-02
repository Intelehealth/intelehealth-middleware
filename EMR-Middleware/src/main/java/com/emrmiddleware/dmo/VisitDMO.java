package com.emrmiddleware.dmo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.VisitDTO;

public interface VisitDMO {

	
	@Select("select distinct person.uuid as openmrs_patientuuid,visit.uuid as openmrs_visituuid,visit.visit_type_id,visit.date_started as startdate,visit.date_stopped as enddate,location.uuid as locationuuid,visit.creator from patient, person, visit,encounter,obs,location where person.person_id=patient.patient_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id and obs.obs_datetime>=#{lastchangedtime} and location.uuid=#{locationuuid} and visit.voided=0")
	public ArrayList<VisitDTO> getVisits(@Param("lastchangedtime") Date lastchangedtime,@Param("locationuuid") String locationuuid);
}
