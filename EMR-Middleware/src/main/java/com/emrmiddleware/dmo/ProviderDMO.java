
package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.ProviderAttributeDTO;
import com.emrmiddleware.dto.ProviderAttributeTypeDTO;
import com.emrmiddleware.dto.ProviderDTO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProviderDMO {
    @Select(value={"select \tp.uuid as uuid, \t\tu.uuid as useruuid, \t\tp.identifier,         p.person_id,         pn.given_name, \t\tpn.middle_name, \t\tpn.family_name, \t\tp.provider_id AS providerId,         pr.gender AS gender, \t\tpr.birthdate AS dateofbirth,         pr.voided,         ur.role AS role ,         max(case            when pa.attribute_type_id = 3 then value_reference else 'NA' end) as emailId,         max(case            when pa.attribute_type_id = 4 then value_reference else 'NA' end) as telephoneNumber,         max(case            when pa.attribute_type_id = 16 then value_reference else 'NA' end) as countryCode from \tprovider p left join person pr on (pr.person_id = p.person_id) left join users u on (u.person_id = p.person_id) left join user_role ur on (ur.user_id = u.user_id ) left join person_name pn on (pn.person_id = p.person_id and pn.voided = 0 ) left join provider_attribute pa on (pa.provider_id = p.provider_id and pa.voided = 0 and pa.attribute_type_id in (3,4,16)) where\tur.role IN ('Organizational: Nurse', 'Organizational: Doctor') group by p.uuid , \t\tu.uuid , \t\tp.identifier,         p.person_id,         pn.given_name, \t\tpn.middle_name, \t\tpn.family_name, \t\tp.provider_id ,         pr.gender , \t\tpr.birthdate ,         pr.voided,         ur.role order by p.provider_id"})
    public ArrayList<ProviderDTO> getProviders();

    @Select(value={"select uuid as uuid,name,retired from provider_attribute_type where COALESCE(date_changed,date_created)>=#{lastchangedtime}"})
    public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeMaster(@Param(value="lastchangedtime") String var1);

    @Select(value={"select provider_attribute.uuid as uuid,provider.uuid as provideruuid,provider_attribute_type.uuid as attributetypeuuid,provider_attribute.value_reference as value ,provider_attribute.voided from provider_attribute ,provider,provider_attribute_type where provider_attribute.provider_id=provider.provider_id and provider_attribute.attribute_type_id=provider_attribute_type.provider_attribute_type_id and COALESCE(provider_attribute.date_changed,provider_attribute.date_created)>=#{lastchangedtime}"})
    public ArrayList<ProviderAttributeDTO> getProviderAttributes(@Param(value="lastchangedtime") String var1);

    @Select(value={"select distinct provider.uuid as uuid, users.uuid as useruuid,provider.identifier,person_name.given_name,person_name.middle_name, person_name.family_name, person.gender AS gender, person.birthdate AS dateofbirth, provider.provider_id AS providerId, getProviderEmailAttributeValue(provider.provider_id) AS emailId,  getProviderPhoneAttributeValue(provider.provider_id) AS telephoneNumber,  getProviderCountryCodeAttributeValue(provider.provider_id) AS countryCode, user_role.role AS role ,person.voided from provider, person,person_name,user_role, provider_attribute, users where person.person_id=provider.person_id and person.person_id=person_name.person_id  AND provider.person_id = users.person_id and user_role.user_id = users.user_id AND provider.provider_id = #{provider_id} AND user_role.role IN ('Organizational: Nurse', 'Organizational: Doctor') AND person_name.voided = 0 and person_name.preferred = 1 AND provider.retired = 0\t\t"})
    public ArrayList<ProviderDTO> getProviders2(@Param(value="provider_id") int var1);
}

