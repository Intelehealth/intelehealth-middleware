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
	@Select("SELECT distinct person.uuid as uuid,\n" +
            " pa.person_id,\n" +
            " patient_identifier.identifier as openmrs_id,\n" +
            "                person_name.given_name as firstname,\n" +
            "                person_name.middle_name as middlename,\n" +
            "                ifnull(person_name.family_name, ' ') as lastname,\n" +
            "                person.birthdate as dateofbirth,\n" +
            "                person_address.address1 as address1 ,\n" +
            "                person_address.address2 as address2,\n" +
            "                person_address.city_village as cityvillage,\n" +
            "                person_address.state_province as stateprovince,\n" +
            "                person_address.postal_code as postalcode,\n" +
            "                person_address.country,\n" +
            "                person.gender,\n" +
            "                person.dead,\n" +
            "                person.voided\n" +
            "FROM person,\n" +
            " patient_identifier,\n" +
            "                person_name,\n" +
            "                person_address ,\n" +
            "                location ,\n" +
            "                person_attribute as pa\n" +
            "where person.person_id = patient_identifier.patient_id\n" +
            "AND person.person_id = person_name.person_id\n" +
            "and person.person_id = person_address.person_id\n" +
            "and person.voided=0\n" +
            "and patient_identifier.location_id=location.location_id\n" +
            "and person.person_id = pa.person_id\n" +
            "and person_name.preferred = 1\n" +
            "and person_address.preferred = 1\n" +
            "and (COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}\n" +
            "or COALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= #{lastchangedtime}\n" +
            "or COALESCE(person_name.date_changed,person_name.date_created)>= #{lastchangedtime}\n" +
            "or COALESCE(person_address.date_changed,person_address.date_created)>= #{lastchangedtime}\n" +
            "or COALESCE(pa.date_changed,pa.date_created)>= #{lastchangedtime} )\n" +
            "and location.uuid=#{locationuuid} " +
            "LIMIT #{limit}  OFFSET  #{offset} "
    )
	public ArrayList<PatientDTO> getPatients(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid , @Param("offset") int offset, @Param("limit") int limit);


    @Select("select uuid as uuid ,name from person_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
	public ArrayList<PatientAttributeTypeDTO> getPatientAttributeMaster(@Param("lastchangedtime") String lastpulldatatime);
    
    @Select("select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}")
    /*@Select("select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, getCreatorUUID(person.creator) as patient_creator_uuid, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}")*/

    public ArrayList<PatientAttributeDTO> getPatientAttributes(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid);
    
    @Select("select person.uuid,patient_identifier.identifier as openmrs_id from patient_identifier,person where person.person_id=patient_identifier.patient_id and person.uuid=#{uuid}")
    public PatientDTO getPatient(@Param("uuid") String uuid);

    // Adding new method patientCount to return total number of records after the lastpulldatatime on that locationuuid

    @Select("SELECT COUNT(1) FROM ( SELECT distinct person.uuid as uuid,\n" +
            " pa.person_id,\n" +
            " patient_identifier.identifier as openmrs_id,\n" +
            "                person_name.given_name as firstname,\n" +
            "                person_name.middle_name as middlename,\n" +
            "                ifnull(person_name.family_name, ' ') as lastname,\n" +
            "                person.birthdate as dateofbirth,\n" +
            "                person_address.address1 as address1 ,\n" +
            "                person_address.address2 as address2,\n" +
            "                person_address.city_village as cityvillage,\n" +
            "                person_address.state_province as stateprovince,\n" +
            "                person_address.postal_code as postalcode,\n" +
            "                person_address.country,\n" +
            "                person.gender,\n" +
            "                person.dead,\n" +
            "                person.voided\n" +
            "FROM person,\n" +
            " patient_identifier,\n" +
            "                person_name,\n" +
            "                person_address ,\n" +
            "                location ,\n" +
            "                person_attribute as pa\n" +
            "where person.person_id = patient_identifier.patient_id\n" +
            "AND person.person_id = person_name.person_id\n" +
            "and person.person_id = person_address.person_id\n" +
            "and person.voided=0\n" +
            "and patient_identifier.location_id=location.location_id\n" +
            "and person.person_id = pa.person_id\n" +
            "and person_name.preferred = 1\n" +
            "and person_address.preferred = 1\n" +
            "and (COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}\n" +
            "or COALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= #{lastchangedtime}\n" +
            "or COALESCE(person_name.date_changed,person_name.date_created)>= #{lastchangedtime}\n" +
            "or COALESCE(person_address.date_changed,person_address.date_created)>= #{lastchangedtime}\n" +
            "or COALESCE(pa.date_changed,pa.date_created)>= #{lastchangedtime} )\n" +
            "and location.uuid=#{locationuuid}) t "
    )
    public int getPatientsCount(@Param("lastchangedtime") String lastpulldatatime,@Param("locationuuid") String locationuuid );

}
