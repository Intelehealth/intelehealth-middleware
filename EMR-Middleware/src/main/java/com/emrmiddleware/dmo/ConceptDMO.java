package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.ConceptAttributeDTO;
import com.emrmiddleware.dto.ConceptAttributeTypeDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface ConceptDMO {

    @Select(" select uuid as uuid ,name,retired from concept_attribute_type where COALESCE(date_changed,date_created) >= #{lastchangedtime} ")
    ArrayList<ConceptAttributeTypeDTO> getConceptAttributeTypeMaster(@Param("lastchangedtime") String lastpulldatatime);

    @Select("select concept_attribute.uuid , concept.uuid as concept_uuid, concept_attribute.value_reference as value, concept_attribute_type.uuid as concept_attribute_type_uuid from concept, concept_attribute, concept_attribute_type  where concept_attribute.concept_id=concept.concept_id and concept_attribute.attribute_type_id=concept_attribute_type.concept_attribute_type_id and COALESCE(concept_attribute.date_changed,concept_attribute.date_created)>=#{lastchangedtime}  AND concept_attribute.voided = 0")
    ArrayList<ConceptAttributeDTO> getConceptAttributes(@Param("lastchangedtime") String lastpulldatatime);

}
