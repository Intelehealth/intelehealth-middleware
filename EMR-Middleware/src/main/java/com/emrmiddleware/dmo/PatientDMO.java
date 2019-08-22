package com.emrmiddleware.dmo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;



public interface PatientDMO {
	
	@Select("SELECT distinct person.uuid as uuid, patient_identifier.identifier as openmrs_id, person_name.given_name as firstname, person_name.middle_name as middlename, person_name.family_name as lastname,person.birthdate as dateofbirth, person_address.address1 as address1 ,person_address.address2 as address2, person_address.city_village as cityvillage, person_address.state_province as stateprovince,person_address.postal_code as postalcode, person_address.country, person.gender,person.dead,person.voided FROM person,patient_identifier,person_name,person_address ,location ,(select distinct person_id from person_attribute where coalesce(date_changed,date_created) >= #{lastchangedtime}) as pa where person.person_id = patient_identifier.patient_id AND  person.person_id = person_name.person_id and person.person_id = person_address.person_id and person.voided=0 and patient_identifier.location_id=location.location_id  and (COALESCE(person.date_changed,person.date_created) >=  #{lastchangedtime} or COALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= #{lastchangedtime} or COALESCE(person_name.date_changed,person_name.date_created)>= #{lastchangedtime} or COALESCE(person_address.date_changed,person_address.date_created)>= #{lastchangedtime} or pa.person_id=person.person_id ) and location.uuid=#{locationuuid}")
	public ArrayList<PatientDTO> getPatients(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid);

    @Select("select uuid as uuid ,name from person_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
	public ArrayList<PatientAttributeTypeDTO> getPatientAttributeMaster(@Param("lastchangedtime") String lastpulldatatime);
    
    @Select("select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}")
    public ArrayList<PatientAttributeDTO> getPatientAttributes(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid);
    
    @Select("select person.uuid,patient_identifier.identifier as openmrs_id from patient_identifier,person where person.person_id=patient_identifier.patient_id and person.uuid=#{uuid}")
    public PatientDTO getPatient(@Param("uuid") String uuid);
}
