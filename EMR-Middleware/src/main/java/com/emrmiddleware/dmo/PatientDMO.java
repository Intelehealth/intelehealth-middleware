package com.emrmiddleware.dmo;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;

public interface PatientDMO {
   Logger logger = LoggerFactory.getLogger(PatientDMO.class);

  /**
   * Code for getting patients has been modified to remove deadlocks in case of a patient not having
   * any attribute or a patient has been voided 26-11-2021 satyadeep-ih
   */
  @Select(
      "SELECT distinct person.uuid as uuid,\n"
          + " pa.person_id as patientid,\n"
          + " patient_identifier.identifier as openmrs_id,\n"
          + "                person_name.given_name as firstname,\n"
          + "                person_name.middle_name as middlename,\n"
          + "                ifnull(person_name.family_name, ' ') as lastname,\n"
          + "                person.birthdate as dateofbirth,\n"
          + "                person_address.address1 as address1 ,\n"
          + "                person_address.address2 as address2,\n"
              + "                person_address.address3 as address3,\n"
              + "                person_address.address4 as address4,\n"
               + " person_address.address5 as address5, "
              + "                person_address.address6 as address6,\n"
             + " person_address.county_district as countyDistrict, "
              + "                person_address.city_village as cityvillage,\n"
          + "                person_address.state_province as stateprovince,\n"
          + "                person_address.postal_code as postalcode,\n"
          + "                person_address.country,\n"
          + "                person.gender,\n"
          + "                person.dead,\n"
          + "                person.voided\n"
          + "FROM person\n"
          + "INNER JOIN patient_identifier ON person.person_id = patient_identifier.patient_id\n"
          + "INNER JOIN person_name ON person.person_id = person_name.person_id\n"
          + "INNER JOIN person_address ON person.person_id = person_address.person_id\n"
          + "INNER JOIN location ON patient_identifier.location_id = location.location_id\n"
          + "INNER JOIN person_attribute pa ON person.person_id = pa.person_id\n"
          + "WHERE person.voided = 0\n"
          + "AND person_name.preferred = 1\n"
          + "AND person_address.preferred = 1\n"
          + "AND location.uuid = #{locationuuid}\n"
          + "AND (COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(patient_identifier.date_changed,patient_identifier.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(person_name.date_changed,person_name.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(person_address.date_changed,person_address.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(pa.date_changed,pa.date_created) >= #{lastchangedtime})\n"
          + "AND person.person_id IN (\n"
          + "    SELECT DISTINCT pa2.person_id\n"
          + "    FROM person_attribute pa2\n"
          + "    WHERE pa2.voided = 0\n"
          + ")\n"
          + "LIMIT #{offset}, #{limit}")
  public ArrayList<PatientDTO> getPatients(
          @Param("lastchangedtime") String lastpulldatatime,
          @Param("locationuuid") String locationuuid,
          @Param("offset") int offset,
          @Param("limit") int limit);

  @Select(
      "select uuid as uuid ,name from person_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime} and retired=0")
  public ArrayList<PatientAttributeTypeDTO> getPatientAttributeMaster(
          @Param("lastchangedtime") String lastpulldatatime);

  /*@Select(
          "select person_attribute.uuid as uuid," +
                  "person.uuid as patientuuid," +
                  "person_attribute.value, " +
                  "person_attribute_type.uuid as person_attribute_type_uuid " +
                  "from person_attribute,patient_identifier,person,person_attribute_type,location " +
                  "where person_attribute.person_id=person.person_id " +
                  "and patient_identifier.location_id=location.location_id " +
                  "and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id " +
                  "and patient_identifier.patient_id=person.person_id " +
                  "and COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} " +
                  "and location.uuid=#{locationuuid} " +
                  "and person_attribute.person_id in  ${patientIds}")
//Added patientIds to get the patient attributes for the given patientIds
  public ArrayList<PatientAttributeDTO> getPatientAttributes(
          @Param("lastchangedtime") String lastpulldatatime,
          @Param("locationuuid") String locationuuid,   @Param("patientIds") String patientIds);*/

  @Select(
          "select person_attribute.uuid as uuid," +
                  "person.uuid as patientuuid," +
                  "person_attribute.value, " +
                  "person_attribute_type.uuid as person_attribute_type_uuid " +
                  "from person_attribute,person,person_attribute_type " +
                  "where person_attribute.person_id=person.person_id " +
                  "and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id " +
                  "and person_attribute.person_id in  ${patientIds} and person_attribute.voided=0 " )
//Added patientIds to get the patient attributes for the given patientIds
  ArrayList<PatientAttributeDTO> getPatientAttributes(
          @Param("patientIds") String patientIds);

  @Select(
      "select person.uuid,patient_identifier.identifier as openmrs_id from patient_identifier,person where person.person_id=patient_identifier.patient_id and person.uuid=#{uuid}")
  public PatientDTO getPatient(@Param("uuid") String uuid);

  // Adding new method patientCount to return total number of records after the lastpulldatatime on
  // that locationuuid

  @Select("SELECT COUNT(1) FROM (" +
          " SELECT distinct person.uuid as uuid,\n"
          + " pa.person_id as patientid,\n"
          + " patient_identifier.identifier as openmrs_id,\n"
          + "                person_name.given_name as firstname,\n"
          + "                person_name.middle_name as middlename,\n"
          + "                ifnull(person_name.family_name, ' ') as lastname,\n"
          + "                person.birthdate as dateofbirth,\n"
          + "                person_address.address1 as address1 ,\n"
          + "                person_address.address2 as address2,\n"
              + "                person_address.address3 as address3,\n"
              + "                person_address.address4 as address4,\n"
               + " person_address.address5 as address5, "
              + "                person_address.address6 as address6,\n"
             + " person_address.county_district as countyDistrict, "
              + "                person_address.city_village as cityvillage,\n"
          + "                person_address.state_province as stateprovince,\n"
          + "                person_address.postal_code as postalcode,\n"
          + "                person_address.country,\n"
          + "                person.gender,\n"
          + "                person.dead,\n"
          + "                person.voided\n"
          + "FROM person\n"
          + "INNER JOIN patient_identifier ON person.person_id = patient_identifier.patient_id\n"
          + "INNER JOIN person_name ON person.person_id = person_name.person_id\n"
          + "INNER JOIN person_address ON person.person_id = person_address.person_id\n"
          + "INNER JOIN location ON patient_identifier.location_id = location.location_id\n"
          + "INNER JOIN person_attribute pa ON person.person_id = pa.person_id\n"
          + "WHERE person.voided = 0\n"
          + "AND person_name.preferred = 1\n"
          + "AND person_address.preferred = 1\n"
          + "AND location.uuid = #{locationuuid}\n"
          + "AND (COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(patient_identifier.date_changed,patient_identifier.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(person_name.date_changed,person_name.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(person_address.date_changed,person_address.date_created) >= #{lastchangedtime}\n"
          + "OR COALESCE(pa.date_changed,pa.date_created) >= #{lastchangedtime})\n"
          + "AND person.person_id IN (\n"
          + "    SELECT DISTINCT pa2.person_id\n"
          + "    FROM person_attribute pa2\n"
          + "    WHERE pa2.voided = 0\n"
          + ")"
          + ") t ")
  public int getPatientsCount(
          @Param("lastchangedtime") String lastpulldatatime,
          @Param("locationuuid") String locationuuid);
}
