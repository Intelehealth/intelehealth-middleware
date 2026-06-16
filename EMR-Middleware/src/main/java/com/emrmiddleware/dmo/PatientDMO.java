package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.ExternalPatientDTO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;


public interface PatientDMO {
    /**
     * Code for getting patients has been modified to
     * remove deadlocks in case of a patient not having any attribute
     * or a patient has been voided
     * 26-11-2021 satyadeep-ih
     */
    /* Modified code to return Abha Number and Abha Address */
    /*Modified code to return Multiple Abha Addresses */
    @Select("SELECT person.uuid as uuid,\n" +
            "            person.person_id,\n" +
            "             group_concat(case when patient_identifier.identifier_type = 3 then identifier else null end order by patient_identifier.date_created desc) as openmrs_id,\n" +
            "             group_concat(case when patient_identifier.identifier_type = 6 then identifier else null end order by patient_identifier.date_created desc) as abha_number,\n" +
            "             group_concat(case when patient_identifier.identifier_type = 7 then identifier else null end order by patient_identifier.date_created desc) as abha_address,\n" +
            "              person_name.given_name as firstname,\n" +
            "              person_name.middle_name as middlename,\n" +
            "              ifnull(person_name.family_name, ' ') as lastname,\n" +
            "              person.birthdate as dateofbirth,\n" +
            "              person_address.address1 as address1 ,\n" +
            "              person_address.address2 as address2,\n" +
            "             person_address.address3 as address3,\n" +
            "              person_address.address4 as address4,\n" +
            "              person_address.address5 as address5,\n" +
            "              person_address.city_village as cityvillage,\n" +
            "             person_address.state_province as stateprovince,\n" +
            "              person_address.postal_code as postalcode,\n" +
            "              person_address.country,\n" +
            "              person.gender,\n" +
            "              person.dead,\n" +
            "              person.voided\n" +
            "              FROM \tperson,\n" +
            "              patient_identifier,\n" +
            "              person_name,\n" +
            "              person_address ,\n" +
            "             location ,\n" +
            "              person_attribute as pa\n" +
            "              where \tperson.person_id = patient_identifier.patient_id\n" +
            "              AND \tperson.person_id = person_name.person_id\n" +
            "              and \tperson.person_id = person_address.person_id\n" +
            "              and \tperson.voided=0\n" +
            "              and \tpatient_identifier.location_id=location.location_id\n" +
            "              and \tperson.person_id = pa.person_id\n" +
            "              and \tperson_name.preferred = 1\n" +
            "              and \tperson_address.preferred = 1\n" +
            "              and pa.voided = 0\n" +
            "              and pa.person_attribute_type_id = 8\n" +
            "              and \t(COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}\n" +
            "              or \t\tCOALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= #{lastchangedtime}\n" +
            "              or \t\tCOALESCE(person_name.date_changed,person_name.date_created)>= #{lastchangedtime}\n" +
            "              or \t\tCOALESCE(person_address.date_changed,person_address.date_created)>= #{lastchangedtime}\n" +
            "              or \t\tCOALESCE(pa.date_changed,pa.date_created)>= #{lastchangedtime} )\n" +
            "              and \tlocation.uuid=#{locationuuid}\n" +
            "              group by person.uuid,\n" +
            "              person.person_id,\n" +
            "              person_name.given_name ,\n" +
            "              person_name.middle_name,\n" +
            "              ifnull(person_name.family_name, ' ') ,\n" +
            "              person.birthdate ,     person_address.address1,     person_address.address2 ,  person_address.address3,     person_address.address4 ,  person_address.address5,\n" +
            "              person_address.city_village ,     person_address.state_province,\n" +
            "              person_address.postal_code,     person_address.country,\n" +
            "             person.gender,     person.dead,    person.voided")
    ArrayList<PatientDTO> getPatients(@Param("lastchangedtime") String lastpulldatatime, @Param("locationuuid") String locationuuid);

    @Select("select uuid as uuid ,name from person_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}")
    ArrayList<PatientAttributeTypeDTO> getPatientAttributeMaster(@Param("lastchangedtime") String lastpulldatatime);

    @Select("select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}")
    /*@Select("select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, getCreatorUUID(person.creator) as patient_creator_uuid, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}")*/ ArrayList<PatientAttributeDTO> getPatientAttributes(@Param("lastchangedtime") String lastpulldatatime, @Param("locationuuid") String locationuuid);

    @Select("SELECT A.uuid, " +
            " max(case when B.identifier_type = 3 then identifier else 'NA' end) as openmrs_id, " +
            " max(case when B.identifier_type = 6 then identifier else 'NA' end) as abha_number, " +
            " max(case when B.identifier_type = 7 then identifier else 'NA' end) as abha_address" +
            " FROM person A " +
            " JOIN patient_identifier B ON A.person_id = B.patient_id WHERE A.voided = 0" +
            " AND A.uuid=#{uuid}" +
            " GROUP BY A.person_id")
    PatientDTO getPatient(@Param("uuid") String uuid);

    @Select(" SELECT A.uuid, B.identifier FROM person A JOIN patient_identifier B  " +
            " on A.person_id = B.patient_id WHERE A.voided = 0 AND B.identifier_type = 3 " +
            " AND B.patient_id in (SELECT patient_id FROM patient_identifier WHERE " +
            "  identifier = #{abhaNumber}) ")
    PatientDTO getPatientWithABDM(@Param("abhaNumber") String abhaNumber);

    @Select(" SELECT A.uuid, B.identifier AS openmrsid FROM person A JOIN patient_identifier B  " +
            " on A.person_id = B.patient_id WHERE A.voided = 0 AND B.identifier_type = 3 " +
            " AND B.patient_id in (SELECT patient_id FROM patient_identifier WHERE " +
            "identifier = #{abhaNumber}) ")
    ExternalPatientDTO getPersonIdentifiers(@Param("abhaNumber") String abhaNumber);
}
