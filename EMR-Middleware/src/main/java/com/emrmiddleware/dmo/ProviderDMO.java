package com.emrmiddleware.dmo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.emrmiddleware.dto.ProviderAttributeDTO;
import com.emrmiddleware.dto.ProviderAttributeTypeDTO;
import com.emrmiddleware.dto.ProviderDTO;

public interface ProviderDMO {
	
	
	@Select("select distinct provider.uuid as openmrs_provideruuid,provider.identifier,person_name.given_name,person_name.family_name, person.voided from provider, person,person_name where person.person_id=provider.person_id and person.person_id=person_name.person_id and (COALESCE(provider.date_changed,provider.date_created) >=#{lastchangedtime} OR COALESCE(person.date_changed,person.date_created)>=#{lastchangedtime} or COALESCE(person_name.date_changed,person_name.date_created)>=#{lastchangedtime} or provider.provider_id in (select provider_id from provider_attribute where COALESCE(provider_attribute.date_changed,provider_attribute.date_created)>=#{lastchangedtime}))")
	public ArrayList<ProviderDTO> getProviders(@Param("lastchangedtime") Date lastchangedtime);
	
	@Select("select uuid as openmrs_uuid,name,retired from provider_attribute_type where COALESCE(date_changed,date_created)>=#{lastchangedtime}")
	public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeMaster(@Param("lastchangedtime") Date lastchangedtime);
	
	@Select("select provider_attribute.uuid as openmrs_uuid,provider.uuid as openmrs_provideruuid,provider_attribute_type.uuid as openmrs_attributetypeuuid,provider_attribute.value_reference as value ,provider_attribute.voided from provider_attribute ,provider,provider_attribute_type where provider_attribute.provider_id=provider.provider_id and provider_attribute.attribute_type_id=provider_attribute_type.provider_attribute_type_id and COALESCE(provider_attribute.date_changed,provider_attribute.date_created)>=#{lastchangedtime}")
	public ArrayList<ProviderAttributeDTO> getProviderAttributes(@Param("lastchangedtime") Date lastchangedtime);

}
