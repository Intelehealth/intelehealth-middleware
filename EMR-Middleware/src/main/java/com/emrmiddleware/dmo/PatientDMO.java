/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Select
 */
package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PatientDMO {
    @Select(value={"SELECT distinct person.uuid as uuid,\n pa.person_id,\n patient_identifier.identifier as openmrs_id,\n                person_name.given_name as firstname,\n                person_name.middle_name as middlename,\n                ifnull(person_name.family_name, ' ') as lastname,\n                person.birthdate as dateofbirth,\n                person_address.address1 as address1 ,\n                person_address.address2 as address2,\n                person_address.city_village as cityvillage,\n                person_address.state_province as stateprovince,\n                person_address.postal_code as postalcode,\n                person_address.country,\n                person.gender,\n                person.dead,\n                person.date_created as datecreated, \n                users.uuid as creatoruuid, \n                person.voided\nFROM person,\n patient_identifier,\n                person_name,\n                person_address ,\n                location ,\n                users   ,\n                person_attribute as pa\nwhere person.person_id = patient_identifier.patient_id\nAND person.person_id = person_name.person_id\nand person.person_id = person_address.person_id\nand person.voided=0\nand users.user_id = person.creator \nand patient_identifier.location_id=location.location_id\nand person.person_id = pa.person_id\nand person_name.preferred = 1\nand person_address.preferred = 1\nand (COALESCE(person.date_changed,person.date_created) >= #{lastchangedtime}\nor COALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= #{lastchangedtime}\nor COALESCE(person_name.date_changed,person_name.date_created)>= #{lastchangedtime}\nor COALESCE(person_address.date_changed,person_address.date_created)>= #{lastchangedtime}\nor COALESCE(pa.date_changed,pa.date_created)>= #{lastchangedtime} )\nand location.uuid=#{locationuuid} "})
    public ArrayList<PatientDTO> getPatients(@Param(value="lastchangedtime") String var1, @Param(value="locationuuid") String var2);

    @Select(value={"select uuid as uuid ,name from person_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime}"})
    public ArrayList<PatientAttributeTypeDTO> getPatientAttributeMaster(@Param(value="lastchangedtime") String var1);

    @Select(value={"select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >=#{lastchangedtime} and location.uuid=#{locationuuid}"})
    public ArrayList<PatientAttributeDTO> getPatientAttributes(@Param(value="lastchangedtime") String var1, @Param(value="locationuuid") String var2);

    @Select(value={"select person.uuid,patient_identifier.identifier as openmrs_id from patient_identifier,person where person.person_id=patient_identifier.patient_id and person.uuid=#{uuid}"})
    public PatientDTO getPatient(@Param(value="uuid") String var1);
}

