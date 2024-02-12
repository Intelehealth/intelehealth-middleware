package com.emrmiddleware.dmo;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;



public interface PatientDMO {
    /**
     *
     * Code for getting patients has been modified to
     * remove deadlocks in case of a patient not having any attribute
     * or a patient has been voided
     *  26-11-2021 satyadeep-ih
     */
    /* Modified code to return Abha Number and Abha Address */

        @Select("SELECT 	person.uuid as uuid, "+
                " pa.person_id, "+
                " max(case when patient_identifier.identifier_type = 3 then identifier else null end) as openmrs_id, "+
    " max(case when patient_identifier.identifier_type = 6 then identifier else null end) as abha_number, "+
    " max(case when patient_identifier.identifier_type = 7 then identifier else null end) as abha_address, "+
    " person_name.given_name as firstname, " +
    " person_name.middle_name as middlename, " +
    " ifnull(person_name.family_name, ' ') as lastname, "+
    " person.birthdate as dateofbirth, "+
    " person_address.address1 as address1 , "+
    " person_address.address2 as address2, "+
    " person_address.city_village as cityvillage, "+
    "person_address.state_province as stateprovince, "+
    " person_address.postal_code as postalcode, "+
    " person_address.country, "+
    " person.gender, "+
    " person.dead, "+
    " person.voided "+
    " FROM 	person," +
            " patient_identifier, "+
            " person_name, "+
            " person_address , "+
            "location , "+
   " person_attribute as pa "+
   " where 	person.person_id = patient_identifier.patient_id " +
   " AND 	person.person_id = person_name.person_id "+
    " and 	person.person_id = person_address.person_id "+
    " and 	person.voided=0 "+
    " and 	patient_identifier.location_id=location.location_id "+
    " and 	person.person_id = pa.person_id "+
    " and 	person_name.preferred = 1 "+
    " and 	person_address.preferred = 1 "+
    " and 	(COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}"+
    " or 		COALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= #{lastchangedtime}"+
    " or 		COALESCE(person_name.date_changed,person_name.date_created)>= #{lastchangedtime}"+
    " or 		COALESCE(person_address.date_changed,person_address.date_created)>= #{lastchangedtime}"+
    " or 		COALESCE(pa.date_changed,pa.date_created)>= #{lastchangedtime} ) "+
    " and 	location.uuid=#{locationuuid}"+
    " group by person.uuid, "+
    " pa.person_id, "+
    " person_name.given_name , "+
    " person_name.middle_name, "+
    " ifnull(person_name.family_name, ' ') , "+
    " person.birthdate ,     person_address.address1,     person_address.address2 , "+
    " person_address.city_village ,     person_address.state_province,"+
    " person_address.postal_code,     person_address.country, "+
    " person.gender,     person.dead,    person.voided "+
    " LIMIT #{offset} , #{limit}")
	public ArrayList<PatientDTO> getPatients(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid, @Param("offset") int offset, @Param("limit") int limit);

    @Select("select uuid as uuid ,name from person_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
	public ArrayList<PatientAttributeTypeDTO> getPatientAttributeMaster(@Param("lastchangedtime") String lastpulldatatime);
    
    @Select("select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}")
    /*@Select("select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, getCreatorUUID(person.creator) as patient_creator_uuid, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}")*/

    public ArrayList<PatientAttributeDTO> getPatientAttributes(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid);
    
    @Select("SELECT A.uuid, " +
            " max(case when B.identifier_type = 3 then identifier else 'NA' end) as openmrs_id, " +
            " max(case when B.identifier_type = 6 then identifier else 'NA' end) as abha_number, " +
            " max(case when B.identifier_type = 7 then identifier else 'NA' end) as abha_address" +
            " FROM person A " +
            " JOIN patient_identifier B ON A.person_id = B.patient_id WHERE A.voided = 0" +
            " AND A.uuid=#{uuid}" +
            " GROUP BY A.person_id")
    public PatientDTO getPatient(@Param("uuid") String uuid);

    // Adding new method patientCount to return total number of records after the lastpulldatatime on that locationuuid

    @Select("SELECT COUNT(1) FROM ( SELECT 	person.uuid as uuid, "+
            " pa.person_id, "+
            " max(case when patient_identifier.identifier_type = 3 then identifier else null end) as openmrs_id, "+
            " max(case when patient_identifier.identifier_type = 6 then identifier else null end) as abha_number, "+
            " max(case when patient_identifier.identifier_type = 7 then identifier else null end) as abha_address, "+
            " person_name.given_name as firstname, " +
            " person_name.middle_name as middlename, " +
            " ifnull(person_name.family_name, ' ') as lastname, "+
            " person.birthdate as dateofbirth, "+
            " person_address.address1 as address1 , "+
            " person_address.address2 as address2, "+
            " person_address.city_village as cityvillage, "+
            "person_address.state_province as stateprovince, "+
            " person_address.postal_code as postalcode, "+
            " person_address.country, "+
            " person.gender, "+
            " person.dead, "+
            " person.voided "+
            " FROM 	person," +
            " patient_identifier, "+
            " person_name, "+
            " person_address , "+
            "location , "+
            " person_attribute as pa "+
            " where 	person.person_id = patient_identifier.patient_id " +
            " AND 	person.person_id = person_name.person_id "+
            " and 	person.person_id = person_address.person_id "+
            " and 	person.voided=0 "+
            " and 	patient_identifier.location_id=location.location_id "+
            " and 	person.person_id = pa.person_id "+
            " and 	person_name.preferred = 1 "+
            " and 	person_address.preferred = 1 "+
            " and 	(COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}"+
            " or 		COALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= #{lastchangedtime}"+
            " or 		COALESCE(person_name.date_changed,person_name.date_created)>= #{lastchangedtime}"+
            " or 		COALESCE(person_address.date_changed,person_address.date_created)>= #{lastchangedtime}"+
            " or 		COALESCE(pa.date_changed,pa.date_created)>= #{lastchangedtime} ) "+
            " and 	location.uuid=#{locationuuid}"+
            " group by person.uuid, "+
            " pa.person_id, "+
            " person_name.given_name , "+
            " person_name.middle_name, "+
            " ifnull(person_name.family_name, ' ') , "+
            " person.birthdate ,     person_address.address1,     person_address.address2 , "+
            " person_address.city_village ,     person_address.state_province,"+
            " person_address.postal_code,     person_address.country, "+
            " person.gender,     person.dead,    person.voided " +
            " ) t"
    )
    public int getPatientsCount(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid );

}
