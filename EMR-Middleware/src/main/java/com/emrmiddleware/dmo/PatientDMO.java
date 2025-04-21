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
  @Select("SELECT DISTINCT " +
          "  person.uuid AS uuid, " +
          "  pa.person_id, " +
          "  pi.identifier AS openmrs_id, " +
          "  COALESCE(mpi.identifier, '') AS mpi_id," +
          "  person_name.given_name AS firstname, " +
          "  person_name.middle_name AS middlename, " +
          "  IFNULL(person_name.family_name, ' ') AS lastname, " +
          "  person.birthdate AS dateofbirth, " +
          "  person_address.address1 AS address1, " +
          "  person_address.address2 AS address2, " +
          "  person_address.city_village AS cityvillage, " +
          "  person_address.state_province AS stateprovince, " +
          "  person_address.postal_code AS postalcode, " +
          "  person_address.country, " +
          "  person.gender, " +
          "  person.dead, " +
          "  person.voided " +
          "FROM person " +
          "JOIN patient_identifier pi " +
          "    ON person.person_id = pi.patient_id " +
          "JOIN patient_identifier_type pit1 " +
          "    ON pi.identifier_type = pit1.patient_identifier_type_id " +
          "    AND pit1.name = 'OpenMRS ID' " +
          " LEFT JOIN patient_identifier mpi " +  // Changed from JOIN to LEFT JOIN
          " ON person.person_id = mpi.patient_id " +
          " AND mpi.identifier_type = ( " +
          "        SELECT patient_identifier_type_id " +
          "        FROM patient_identifier_type " +
          "        WHERE name = 'MPI' " +
          "    ) " +
          "JOIN person_name ON person.person_id = person_name.person_id " +
          "JOIN person_address ON person.person_id = person_address.person_id " +
          "JOIN location ON pi.location_id = location.location_id " +
          "JOIN person_attribute pa ON person.person_id = pa.person_id " +
          "WHERE person.voided = 0 " +
          "  AND person_name.preferred = 1 " +
          "  AND person_address.preferred = 1 " +
          "  AND (COALESCE(person.date_changed, person.date_created) >= #{lastchangedtime} " +
          "       OR COALESCE(pi.date_changed, pi.date_created) >= #{lastchangedtime} " +
          "       OR COALESCE(person_name.date_changed, person_name.date_created) >= #{lastchangedtime} " +
          "       OR COALESCE(person_address.date_changed, person_address.date_created) >= #{lastchangedtime} " +
          "       OR COALESCE(pa.date_changed, pa.date_created) >= #{lastchangedtime}) " +
          "  AND location.uuid = #{locationuuid} " +
          "LIMIT #{offset}, #{limit}")
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

  @Select("SELECT COUNT(1) FROM ( " +
          "    SELECT DISTINCT " +
          "        person.uuid AS uuid, " +
          "        pa.person_id, " +
          "        pi.identifier AS openmrs_id, " +
          "        COALESCE(mpi.identifier, '') AS mpi_id, " +
          "        person_name.given_name AS firstname, " +
          "        person_name.middle_name AS middlename, " +
          "        IFNULL(person_name.family_name, ' ') AS lastname, " +
          "        person.birthdate AS dateofbirth, " +
          "        person_address.address1 AS address1, " +
          "        person_address.address2 AS address2, " +
          "        person_address.city_village AS cityvillage, " +
          "        person_address.state_province AS stateprovince, " +
          "        person_address.postal_code AS postalcode, " +
          "        person_address.country, " +
          "        person.gender, " +
          "        person.dead, " +
          "        person.voided " +
          "    FROM person " +
          "    JOIN patient_identifier pi " +
          "        ON person.person_id = pi.patient_id " +
          "    JOIN patient_identifier_type pit " +
          "        ON pi.identifier_type = pit.patient_identifier_type_id " +
          "        AND pit.name = 'OpenMRS ID' " +
          "    LEFT JOIN patient_identifier mpi " +
          "        ON person.person_id = mpi.patient_id " +
          "        AND mpi.identifier_type = ( " +
          "            SELECT patient_identifier_type_id " +
          "            FROM patient_identifier_type " +
          "            WHERE name = 'MPI' " +
          "        ) " +
          "    JOIN person_name " +
          "        ON person.person_id = person_name.person_id " +
          "    JOIN person_address " +
          "        ON person.person_id = person_address.person_id " +
          "    JOIN location " +
          "        ON pi.location_id = location.location_id " +
          "    JOIN person_attribute AS pa " +
          "        ON person.person_id = pa.person_id " +
          "    WHERE person.voided = 0 " +
          "        AND person_name.preferred = 1 " +
          "        AND person_address.preferred = 1 " +
          "        AND ( " +
          "            COALESCE(person.date_changed, person.date_created) >= #{lastchangedtime} " +
          "            OR COALESCE(pi.date_changed, pi.date_created) >= #{lastchangedtime} " +
          "            OR COALESCE(person_name.date_changed, person_name.date_created) >= #{lastchangedtime} " +
          "            OR COALESCE(person_address.date_changed, person_address.date_created) >= #{lastchangedtime} " +
          "            OR COALESCE(pa.date_changed, pa.date_created) >= #{lastchangedtime} " +
          "        ) " +
          "        AND location.uuid = #{locationuuid} " +
          ") t")
  public int getPatientsCount(
          @Param("lastchangedtime") String lastpulldatatime,
          @Param("locationuuid") String locationuuid);

  @Select("SELECT " +
          " DISTINCT p.uuid," +
          " pi.identifier AS openmrs_id," +
          " COALESCE(mpi.identifier, '') AS mpi_id," +
          " pn.given_name AS firstname, " +
          " pn.middle_name AS middlename, " +
          " pn.family_name AS lastname, " +
          " p.gender AS gender, " +
          " p.birthdate AS dateofbirth, " +
          " pa.value AS phonenumber " +
          " FROM person p " +
          " JOIN patient_identifier pi " +
          " ON p.person_id = pi.patient_id " +
          " JOIN patient_identifier_type pit1 " +
          " ON pi.identifier_type = pit1.patient_identifier_type_id " +
          " AND pit1.name = 'OpenMRS ID' " +
          " LEFT JOIN patient_identifier mpi " +  // Changed from JOIN to LEFT JOIN
          " ON p.person_id = mpi.patient_id " +
          " AND mpi.identifier_type = ( " +
          "        SELECT patient_identifier_type_id " +
          "        FROM patient_identifier_type " +
          "        WHERE name = 'MPI' " +
          "    ) " +
          " LEFT JOIN person_name pn " +
          " ON p.person_id = pn.person_id " +
          " LEFT JOIN person_attribute pa " +
          " ON pa.person_id = p.person_id " +
          " AND pa.person_attribute_type_id = 8 " +
          " WHERE " +
          " (p.gender = #{gender} OR #{gender} IS NULL) " +
          " and (SOUNDEX(pn.given_name) = SOUNDEX(#{firstname}) OR #{firstname} IS NULL) " +
          " and (SOUNDEX(pn.middle_name) = SOUNDEX(#{middlename}) OR #{middlename} IS NULL) " +
          " and (SOUNDEX(pn.family_name) = SOUNDEX(#{lastname}) OR #{lastname} IS NULL) " +
          " and (p.birthdate = #{dateofbirth} OR #{dateofbirth} IS NULL) " +
          " and (pa.value = #{telecom} OR #{telecom} IS NULL)")
  ArrayList<PatientDTO> searchPatientByParam(
          @Param("firstname") String firstname,
          @Param("middlename") String middlename,
          @Param("lastname") String lastname,
          @Param("gender") String gender,
          @Param("dateofbirth") String dateofbirth,
          @Param("telecom") String telecom);

}
