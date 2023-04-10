/**
 * 
 */
package org.intelehealth.middleware.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.sql.DataSource;

import org.intelehealth.middleware.api.dto.AppointmentDTO;
import org.intelehealth.middleware.dto.CustomAppointmentDTO;
import org.intelehealth.middleware.dto.EncounterDTO;
import org.intelehealth.middleware.dto.LocationDTO;
import org.intelehealth.middleware.dto.ObsDTO;
import org.intelehealth.middleware.dto.PatientAttributeDTO;
import org.intelehealth.middleware.dto.PatientAttributeTypeDTO;
import org.intelehealth.middleware.dto.PatientDTO;
import org.intelehealth.middleware.dto.ProviderAttributeDTO;
import org.intelehealth.middleware.dto.ProviderAttributeTypeDTO;
import org.intelehealth.middleware.dto.ProviderDTO;
import org.intelehealth.middleware.dto.VisitAttributeDTO;
import org.intelehealth.middleware.dto.VisitAttributeTypeDTO;
import org.intelehealth.middleware.dto.VisitDTO;
import org.intelehealth.middleware.models.User;
import org.intelehealth.middleware.utils.HikariCPDataSource;
import org.intelehealth.middleware.utils.WebClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * @author satya
 *
 */
@Service
public class MySQLService {
	@Autowired
	public DataSource dataSource;

	static final String USER_CREDS_QUERY="SELECT username, password, salt FROM users u WHERE u.username=?";

	static final String CURRENT_TIME = "SELECT now() ";
	static final String PATIENT_LIST = "SELECT distinct person.uuid as uuid,\n" +
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
			"AND person.person_id = person_name.person_id \n" +
			"and person.person_id = person_address.person_id \n" +
			"and person.voided=0 \n" +
			"and patient_identifier.location_id=location.location_id \n" +
			"and person.person_id = pa.person_id \n" +
			"and person_name.preferred = 1 \n" +
			"and person_address.preferred = 1 \n" +
			"and (COALESCE(person.date_changed,person.date_created) >= ? \n" +
			"or COALESCE(patient_identifier.date_changed,patient_identifier.date_created)>= ? \n" +
			"or COALESCE(person_name.date_changed,person_name.date_created)>= ? \n" +
			"or COALESCE(person_address.date_changed,person_address.date_created)>= ? \n" +
			"or COALESCE(pa.date_changed,pa.date_created)>= ? )\n" +
			"and location.uuid= ? ";
	static final String PATIENT_ATTRIBUTE_TYPE_LIST = "select uuid as uuid ,name from person_attribute_type where COALESCE(date_changed,date_created) >= ?";
	static final String PATIENT_ATTRIBUTE_LIST = "select person_attribute.uuid as uuid,person.uuid as patientuuid,person_attribute.value, person_attribute_type.uuid as person_attribute_type_uuid from person_attribute,patient_identifier,person,person_attribute_type ,location where person_attribute.person_id=person.person_id and patient_identifier.location_id=location.location_id and person_attribute.person_attribute_type_id=person_attribute_type.person_attribute_type_id and patient_identifier.patient_id=person.person_id and  COALESCE(person_attribute.date_changed,person_attribute.date_created) >= ? and location.uuid= ?";
	static final String VISIT_LIST = "select distinct person.uuid as patientuuid,visit.uuid as uuid,visit_type.uuid as visit_type_uuid,date_format(visit.date_started, '%b %d,%Y %H:%i:%s %p') as startdate,date_format(visit.date_stopped,  '%b %d,%Y %H:%i:%s %p')  as enddate,location.uuid as locationuuid,users.uuid as creator_uuid,visit.voided from patient, person,visit_type, visit,encounter,obs,location,users where person.person_id=patient.patient_id and visit.creator=users.user_id and visit.patient_id=patient.patient_id and visit.location_id=location.location_id and encounter.visit_id=visit.visit_id and obs.encounter_id=encounter.encounter_id AND visit.visit_type_id=visit_type.visit_type_id and ((coalesce(obs.date_voided,obs.date_created)>= ?) or (encounter.date_changed>= ?) or (COALESCE(visit.date_changed,visit.date_created)>= ? )) and location.uuid= ? and visit.voided=0";
	static final String VISIT_ATTRIBUTE_TYPE_LIST = "select uuid as uuid ,name,retired from visit_attribute_type where COALESCE(date_changed,date_created) >= ?";
	static final String VISIT_ATTRIBUTE_LIST = "select visit_attribute.uuid ,visit.uuid as visit_uuid,visit_attribute.value_reference as value,visit_attribute_type.uuid as visit_attribute_type_uuid from visit,visit_attribute,visit_attribute_type,location where visit_attribute.visit_id=visit.visit_id and visit_attribute.attribute_type_id=visit_attribute_type.visit_attribute_type_id and COALESCE(visit_attribute.date_changed,visit_attribute.date_created)>=? and visit.location_id=location.location_id and location.uuid=?";
	static final String ENCOUNTER_LIST = "select distinct encounter.uuid as uuid,encounter.encounter_datetime as encounter_time, visit.uuid as visituuid,encounter_type.uuid as encounter_type_uuid,provider.uuid as provider_uuid,encounter.voided from encounter,visit,obs,location,encounter_type,encounter_provider,provider where  encounter.visit_id=visit.visit_id  and visit.location_id=location.location_id and encounter.encounter_id=encounter_provider.encounter_id and encounter_provider.provider_id=provider.provider_id and obs.encounter_id=encounter.encounter_id  and ((coalesce(obs.date_voided,obs.date_created)>=?) or (encounter.date_changed>=?)) and location.uuid=? and encounter.encounter_type=encounter_type.encounter_type_id";
	static final String OBS_LIST = "select distinct a.uuid as uuid ,encounter.uuid as encounteruuid, CASE  WHEN a.value_numeric IS NOT NULL 	THEN CAST(a.value_numeric AS CHAR(50) CHARACTER SET utf8) WHEN a.value_text IS NOT NULL THEN a.value_text END as value,concept.uuid as conceptuuid,a.creator,a.voided	, coalesce(a.date_voided,a.date_created) as obsServerModifiedDate, a.comments as comment 	from obs,encounter,visit,location,concept,obs a where obs.encounter_id=encounter.encounter_id    	and encounter.visit_id=visit.visit_id and visit.location_id=location.location_id 	and ((coalesce(obs.date_voided,obs.date_created)>=?) or (encounter.date_changed>=?))	and location.uuid=? and a.encounter_id=encounter.encounter_id and a.concept_id=concept.concept_id	and obs.encounter_id=a.encounter_id";
	static final String LOCATION_LIST = "select name,uuid as locationuuid,retired from location where COALESCE(date_changed,date_created)>=?";
	static final String PROVIDER_LIST= "select distinct provider.uuid as uuid, users.uuid as useruuid, " +
			"provider.identifier, " +
			"person_name.given_name, " +
			"person_name.middle_name, " +
			"person_name.family_name, " +
			"person.gender AS gender, " +
			"person.birthdate AS dateofbirth, " +
			"provider.provider_id AS providerId, "+
			"getProviderEmailAttributeValue(provider.provider_id) AS emailId, " +
			" getProviderPhoneAttributeValue(provider.provider_id) AS telephoneNumber, "+
			" getProviderCountryCodeAttributeValue(provider.provider_id) AS countryCode, "+
			"user_role.role AS role , " +
			"person.voided from provider, " +
			"person,person_name,user_role, provider_attribute, users " +
			" where person.person_id=provider.person_id and person.person_id=person_name.person_id " +
			" AND provider.person_id = users.person_id and user_role.user_id = users.user_id" +
			" AND user_role.role IN ('Organizational: Nurse', 'Organizational: Doctor')"+
			" and (COALESCE(provider.date_changed,provider.date_created) >=? OR COALESCE(person.date_changed,person.date_created)>=? or COALESCE(person_name.date_changed,person_name.date_created)>=? or provider.provider_id in (select provider_id from provider_attribute where COALESCE(provider_attribute.date_changed,provider_attribute.date_created)>=?))";
	static final String PROVIDER_ATTRIBUTE_TYPE_LIST = "select uuid as uuid,name,retired from provider_attribute_type where COALESCE(date_changed,date_created)>=?";
	static final String PROVIDER_ATTRIBUTE_LIST = "select provider_attribute.uuid as uuid,provider.uuid as provideruuid,provider_attribute_type.uuid as attributetypeuuid,provider_attribute.value_reference as value ,provider_attribute.voided from provider_attribute ,provider,provider_attribute_type where provider_attribute.provider_id=provider.provider_id and provider_attribute.attribute_type_id=provider_attribute_type.provider_attribute_type_id and COALESCE(provider_attribute.date_changed,provider_attribute.date_created)>=?";
	static final String PROVIDER_NEW_LIST = "select distinct provider.uuid as uuid, users.uuid as useruuid," +
			"provider.identifier," +
			"person_name.given_name," +
			"person_name.middle_name, " +
			"person_name.family_name, " +
			// Adding fields for new requirement
			"person.gender AS gender, " +
			"person.birthdate AS dateofbirth, " +
			"provider.provider_id AS providerId, "+
			"getProviderEmailAttributeValue(provider.provider_id) AS emailId, " +
			" getProviderPhoneAttributeValue(provider.provider_id) AS telephoneNumber, "+
			" getProviderCountryCodeAttributeValue(provider.provider_id) AS countryCode, "+
			"user_role.role AS role ," +
			"person.voided from provider, " +
			"person,person_name,user_role, provider_attribute, users" +
			" where person.person_id=provider.person_id and person.person_id=person_name.person_id " +
			" AND provider.person_id = users.person_id and user_role.user_id = users.user_id" +
			" AND provider.provider_id = ?" +
			" AND user_role.role IN ('Organizational: Nurse', 'Organizational: Doctor')" +
			" AND person_name.voided = 0 and person_name.preferred = 1 AND provider.retired = 0 ";


	static final int ATTRIBUTE_EMAIL = 3;
	static final int ATTRIBUTE_PHONENUMBER = 4;
	static final int ATTRIBUTE_COUNTRY_CODE = 16;

	static final String UPDATEPROVIDEREMAIL = "UPDATE provider_attribute SET value_reference = ? WHERE attribute_type_id = ? AND provider_id = ?";
	static final String UPDATEPROVIDERPHONENUMBER = "UPDATE provider_attribute SET value_reference = ? WHERE attribute_type_id = ? AND provider_id = ?";
	static final String UPDATEPROVIDERCOUNTRYCODE = "UPDATE provider_attribute SET value_reference = ? WHERE attribute_type_id = ? AND provider_id = ?";

	static final String UPDATEPERSONFIRSTNAME = "UPDATE person_name SET given_name  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";
	static final String UPDATEPERSONMIDDLENAME = "UPDATE person_name SET middle_name  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";
	static final String UPDATEPERSONLASTNAME = "UPDATE person_name SET family_name  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";

	static final String UPDATEDOB = "UPDATE person SET birthdate = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";
	static final String UPDATEGENDER = "UPDATE person SET gender  = ? WHERE person_id = (SELECT person_id FROM provider where provider_id = ?)";

	static final String PERSON_EXISTS = "SELECT count(1) as uuid from person where uuid=?";

	static final String PATIENT_EXISTS = "select person.uuid,patient_identifier.identifier as openmrs_id from patient_identifier,person where person.person_id=patient_identifier.patient_id and person.uuid=?";

	public static final String VISIT_EXISTS = "select count(1) from visit where uuid=?";

	public static final Logger LOG = LoggerFactory.getLogger(WebClientFilter.class);

	public static final String ENCOUNTER_EXISTS = "select uuid,voided from encounter where uuid=?";

	 
	
	public User findUser(String username) {
		// TODO Auto-generated method stub

		User user = new User();
		try {

			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(USER_CREDS_QUERY);
			pstmt.setString(1, username);
			ResultSet rst = pstmt.executeQuery();
			rst.next();
			user.setUsername(rst.getString(1));
			user.setPassword(rst.getString(2));
			user.setSalt(rst.getString(3));
			rst.close();
			pstmt.close();
			con.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.error("Error in findUser ", e.getMessage());
		}

		return user;


	}


	public ArrayList<VisitDTO> getVisitList(String locationUUID, String dateFrom) {
		ArrayList<VisitDTO> visitList = new ArrayList<VisitDTO>();
		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(VISIT_LIST);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateFrom);
			pstmt.setString(3, dateFrom);
			pstmt.setString(4,  locationUUID);

			ResultSet rs = pstmt.executeQuery();




			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				VisitDTO visit = new VisitDTO();
				visit.setPatientuuid(rs.getString(1));
				visit.setUuid(rs.getString(2));
				visit.setVisit_type_uuid(rs.getString(3));

				visit.setStartdate(rs.getString(4));
				visit.setEnddate(rs.getString(5));
				visit.setLocationuuid(rs.getString(6));
				visit.setCreator_uuid(rs.getString(7));
				visit.setVoided(rs.getInt(8));

				visitList.add(visit);

			}
			rs.close();
			pstmt.close();
			con.close();



		}
		catch(SQLException e) {
			LOG.error("Exception in getVisitList " + e.getMessage());
		}  
		return visitList;
	}


	public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeList( String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList = new ArrayList<VisitAttributeTypeDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(VISIT_ATTRIBUTE_TYPE_LIST);
			pstmt.setString(1,  dateFrom);

			ResultSet rs = pstmt.executeQuery();




			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				VisitAttributeTypeDTO visitAttribute = new VisitAttributeTypeDTO();
				visitAttribute.setName(rs.getString(2));
				visitAttribute.setUuid(rs.getString(1));
				visitAttribute.setRetired(rs.getInt(3));


				visitAttributeTypeList.add(visitAttribute);

			}


			rs.close();
			pstmt.close();
			con.close();

		}
		catch(SQLException e) {
			LOG.error("Exception in getVisitAttributeTypeList " +e.getMessage());
		}  
		return visitAttributeTypeList;
	}

	public ArrayList<VisitAttributeDTO> getVisitAttributeList( String locationUUID, String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<VisitAttributeDTO> visitAttributeList = new ArrayList<VisitAttributeDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(VISIT_ATTRIBUTE_LIST);
			pstmt.setString(1,  dateFrom);
			pstmt.setString(2,  locationUUID); 
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				VisitAttributeDTO visitAttribute = new VisitAttributeDTO();

				visitAttribute.setUuid(rs.getString(1));
				visitAttribute.setVisit_uuid(rs.getString(2));
				visitAttribute.setValue(rs.getString(3));
				visitAttribute.setVisit_attribute_type_uuid(rs.getString(4));


				visitAttributeList.add(visitAttribute);

			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getVisitAttributes " +e.getMessage());
		}  
		return visitAttributeList;
	}


	 
	public ArrayList<EncounterDTO> getEncounters( String locationUUID, String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<EncounterDTO> encounterList = new ArrayList<EncounterDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(ENCOUNTER_LIST);
			pstmt.setString(1,  dateFrom);
			pstmt.setString(2,  dateFrom);
			pstmt.setString(3,  locationUUID); 
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				EncounterDTO encounter = new EncounterDTO();


				encounter.setUuid(rs.getString(1));
				encounter.setEncounter_time(rs.getString(2));
				encounter.setVisituuid(rs.getString(3));
				encounter.setEncounter_type_uuid(rs.getString(4));
				encounter.setProvider_uuid(rs.getString(5));
				encounter.setVoided(rs.getInt(6));


				encounterList.add(encounter);

			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getEncounters " +e.getMessage());
		}  
		return encounterList;
	}

	public ArrayList<PatientDTO> getPatients( String locationUUID, String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<PatientDTO> patientList = new ArrayList<PatientDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(PATIENT_LIST);
			pstmt.setString(1,  dateFrom);
			pstmt.setString(2,  dateFrom);
			pstmt.setString(3,  dateFrom);
			pstmt.setString(4,  dateFrom);
			pstmt.setString(5,  dateFrom);
			pstmt.setString(6,  locationUUID); 
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				PatientDTO patientDTO = new PatientDTO();


				patientDTO.setUuid(rs.getString(1));
				patientDTO.setOpenmrs_id(rs.getString(3));
				patientDTO.setFirstname(rs.getString(4));
				patientDTO.setMiddlename(rs.getString(5));
				patientDTO.setLastname(rs.getString(6));
				patientDTO.setDateofbirth(rs.getDate(7));
				patientDTO.setAddress1(rs.getString(8));
				patientDTO.setAddress2(rs.getString(9));
				patientDTO.setCityvillage(rs.getString(10));
				patientDTO.setStateprovince(rs.getString(11));
				patientDTO.setPostalcode(rs.getString(12));
				patientDTO.setCountry(rs.getString(13));
				patientDTO.setGender(rs.getString(14));
				patientDTO.setDead(rs.getInt(15));
				patientDTO.setVoided(rs.getInt(16));



				patientList.add(patientDTO);

			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getPatients " +e.getMessage());
		}  
		return patientList;
	}

	public ArrayList<PatientAttributeTypeDTO> getPatientAttributeTypeList( String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<PatientAttributeTypeDTO> patientAttributeTypeList = new ArrayList<PatientAttributeTypeDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(PATIENT_ATTRIBUTE_TYPE_LIST);
			pstmt.setString(1,  dateFrom);

			ResultSet rs = pstmt.executeQuery();




			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				PatientAttributeTypeDTO patientAttribute = new PatientAttributeTypeDTO();
				patientAttribute.setName(rs.getString(2));
				patientAttribute.setUuid(rs.getString(1));



				patientAttributeTypeList.add(patientAttribute);

			}
			rs.close();
			pstmt.close();
			con.close();



		}
		catch(SQLException e) {
			LOG.error("Exception in getPatientAttributeTypes " +e.getMessage());
		}  
		return patientAttributeTypeList;
	}

	public ArrayList<PatientAttributeDTO> getPatientAttributeList( String locationUUID, String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<PatientAttributeDTO> patientAttributeList = new ArrayList<PatientAttributeDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(PATIENT_ATTRIBUTE_LIST);
			pstmt.setString(1,  dateFrom);
			pstmt.setString(2,  locationUUID); 
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				PatientAttributeDTO patientAttribute = new PatientAttributeDTO();

				patientAttribute.setUuid(rs.getString(1));
				patientAttribute.setPatientuuid(rs.getString(2));
				patientAttribute.setValue(rs.getString(3));
				patientAttribute.setPerson_attribute_type_uuid(rs.getString(4));


				patientAttributeList.add(patientAttribute);

			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getPatientAttributes " +e.getMessage());
		}  
		return patientAttributeList;
	}

	public ArrayList<ObsDTO> getObs( String locationUUID, String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<ObsDTO> obsList = new ArrayList<ObsDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(OBS_LIST);
			pstmt.setString(1,  dateFrom);
			pstmt.setString(3,  locationUUID);
			pstmt.setString(2,  dateFrom);
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				ObsDTO obs = new ObsDTO();


				obs.setUuid(rs.getString(1));
				obs.setEncounteruuid(rs.getString(2));
				obs.setValue(rs.getString(3));
				obs.setConceptuuid(rs.getString(4));
				obs.setCreator(rs.getInt(5));
				obs.setVoided(rs.getInt(6));
				obs.setObsServerModifiedDate(rs.getString(7));
				obs.setComment(rs.getString(8));


				obsList.add(obs);

			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getObs " +e.getMessage());
		}  
		return obsList;
	}

	public ArrayList<LocationDTO> getLocationList(String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<LocationDTO> locationList = new ArrayList<LocationDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(LOCATION_LIST);
			pstmt.setString(1,  dateFrom);
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				LocationDTO location = new LocationDTO();
				location.setName(rs.getString(1));
				location.setLocationuuid(rs.getString(2));
				location.setRetired(rs.getInt(3));
				locationList.add(location);

			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getLocations " +e.getMessage());
		}  
		return locationList;

	}

	public ArrayList<ProviderDTO> getProviderList(String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<ProviderDTO> providerList = new ArrayList<ProviderDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(PROVIDER_LIST);
			pstmt.setString(1,  dateFrom);
			pstmt.setString(2,  dateFrom);
			pstmt.setString(3,  dateFrom);
			pstmt.setString(4,  dateFrom);
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				ProviderDTO provider = new ProviderDTO();
				provider.setUuid(rs.getString(1));
				provider.setUseruuid(rs.getString(2));
				provider.setIdentifier(rs.getString(3));
				provider.setGiven_name(rs.getString(4));
				provider.setMiddle_name(rs.getString(5));
				provider.setFamily_name(rs.getString(6));
				provider.setGender(rs.getString(7));
				provider.setDateofbirth(rs.getString(8));
				provider.setProviderId(rs.getString(9));
				provider.setEmailId(rs.getString(10));
				provider.setTelephoneNumber(rs.getString(11));
				provider.setCountryCode(rs.getString(12));
				provider.setRole(rs.getString(13));
				provider.setVoided(rs.getInt(14));


				providerList.add(provider);
			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getProviders " +e.getMessage());
		}  
		return providerList;

	}

	public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeList(String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList = new ArrayList<ProviderAttributeTypeDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(PROVIDER_ATTRIBUTE_TYPE_LIST);
			pstmt.setString(1,  dateFrom);
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				ProviderAttributeTypeDTO providerAttributeTypeDTO = new ProviderAttributeTypeDTO();


				providerAttributeTypeDTO.setUuid(rs.getString(1));
				providerAttributeTypeDTO.setName(rs.getString(2));
				providerAttributeTypeDTO.setRetired(rs.getInt(3));

				providerAttributeTypeList.add(providerAttributeTypeDTO);
			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getProviderAttributeTypes " +e.getMessage());
		}  
		return providerAttributeTypeList;

	}

	public ArrayList<ProviderAttributeDTO> getProviderAttributeList(String dateFrom) {
		// TODO Auto-generated method stub
		ArrayList<ProviderAttributeDTO> providerAttributeList = new ArrayList<ProviderAttributeDTO>();

		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pstmt = con.prepareStatement(PROVIDER_ATTRIBUTE_LIST);
			pstmt.setString(1,  dateFrom);
			ResultSet rs = pstmt.executeQuery();
			int counter = 0; 
			while (rs.next()) {
				counter = counter + 1; 
				ProviderAttributeDTO providerAttributeDTO = new ProviderAttributeDTO();


				providerAttributeDTO.setUuid(rs.getString(1));
				providerAttributeDTO.setProvideruuid(rs.getString(2));
				providerAttributeDTO.setAttributetypeuuid(rs.getString(3));
				providerAttributeDTO.setValue(rs.getString(4));
				providerAttributeDTO.setVoided(rs.getInt(5));

				providerAttributeList.add(providerAttributeDTO);
			}
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException e) {
			LOG.error("Exception in getProviderAttributes " +e.getMessage());
		}  
		return providerAttributeList;

	}

	public String getCurTime() {
		String curTime = null;
		try {
			Connection con = dataSource.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(CURRENT_TIME);
			rs.next();
			curTime = rs.getString(1);
			rs.close();
			stmt.close();
			con.close();

		}
		catch(SQLException e){
			LOG.error("Exception in getCurTime " +e.getMessage());
		}
		return curTime;
	}


	public void updateAttributes(ProviderDTO provider) {
		// TODO Auto-generated method stub
		int providerId = Integer.parseInt(provider.providerId);
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = null;
			if(provider.emailId != null) {
				pstmt = con.prepareStatement(UPDATEPROVIDEREMAIL);
				pstmt.setString(1, provider.emailId);
				pstmt.setInt(2,ATTRIBUTE_EMAIL);
				pstmt.setInt(3, providerId);
				pstmt.executeUpdate();
			}

			if(provider.telephoneNumber != null) {
				pstmt = con.prepareStatement(UPDATEPROVIDERPHONENUMBER);
				pstmt.setString(1, provider.telephoneNumber);
				pstmt.setInt(2,ATTRIBUTE_PHONENUMBER);
				pstmt.setInt(3, providerId);
				pstmt.executeUpdate();
			}

			if(provider.countryCode != null) {
				pstmt = con.prepareStatement(UPDATEPROVIDERCOUNTRYCODE);
				pstmt.setString(1, provider.countryCode);
				pstmt.setInt(2,ATTRIBUTE_COUNTRY_CODE);
				pstmt.setInt(3, providerId);
				pstmt.executeUpdate();
			}

			if(provider.given_name != null) {
				pstmt = con.prepareStatement(UPDATEPERSONFIRSTNAME);
				pstmt.setString(1, provider.given_name);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.family_name != null) {
				pstmt = con.prepareStatement(UPDATEPERSONLASTNAME);
				pstmt.setString(1, provider.family_name);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.middle_name != null) {
				pstmt = con.prepareStatement(UPDATEPERSONMIDDLENAME);
				pstmt.setString(1, provider.middle_name);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.dateofbirth != null) {
				pstmt = con.prepareStatement(UPDATEDOB);
				pstmt.setString(1, provider.dateofbirth);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}

			if(provider.gender != null) {
				pstmt = con.prepareStatement(UPDATEGENDER);
				pstmt.setString(1, provider.gender);
				pstmt.setInt(2, providerId);
				pstmt.executeUpdate();
			}


			pstmt.close();
			con.close();



		}  
		catch(SQLException sqle) {
			LOG.error("SQL Error while Updating Attributes of Provider" + sqle.getMessage());

		}
	}


	public boolean isPersonExists(String uuid) {
		// TODO Auto-generated method stub
		boolean ReturnValue = false;
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(PERSON_EXISTS);
			pstmt.setString(1, uuid);
			ResultSet rst = pstmt.executeQuery();
			rst.next();

			if(rst.getInt(1) >  0 ) {
				ReturnValue = true;
			}
			else {
				ReturnValue = false;
			}
			rst.close();
			pstmt.close();
			con.close();


		}
		catch(SQLException sqle) {
			LOG.error("SQL Error in isPersonExists" + sqle.getMessage());	
		}
		return ReturnValue;
	}


	public PatientDTO getPatient(String person) {
		// TODO Auto-generated method stub
		System.out.println("Inside getPatient");
		PatientDTO patientDTO  = new PatientDTO(); 
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(PATIENT_EXISTS);
			pstmt.setString(1, person);
			ResultSet rst = pstmt.executeQuery();
			if(rst.next()) {
				//rst.next();
				patientDTO.setUuid(rst.getString(1));
				patientDTO.setOpenmrs_id(rst.getString(2));
				System.out.println(rst.getString(2));
			}
			else {

				patientDTO = null;
			}


			rst.close();
			pstmt.close();
			con.close();

		}
		catch(SQLException sqle) {
			LOG.error("SQL Error in getPatient" + sqle.getMessage());

		}
		return patientDTO;
	}


	public boolean isVisitExists(String uuid) {
		// TODO Auto-generated method stub
		boolean ReturnValue = false;
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(VISIT_EXISTS);
			pstmt.setString(1, uuid);
			ResultSet rst = pstmt.executeQuery();
			rst.next();

			if(rst.getInt(1) >  0 ) {
				ReturnValue = true;
			}
			else {
				ReturnValue = false;
			}
			rst.close();
			pstmt.close();
			con.close();


		}
		catch(SQLException sqle) {
			LOG.error("SQL Error in isVisitExists" , sqle.getMessage());	
		}
		return ReturnValue;
	}


	public EncounterDTO getEncounter(String uuid) {
		// TODO Auto-generated method stub
		EncounterDTO encounterdto = new EncounterDTO();

		try {
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(ENCOUNTER_EXISTS);
			pstmt.setString(1, uuid);
			ResultSet rst = pstmt.executeQuery();
			if(rst.next())
			{	
			encounterdto.setUuid(rst.getString(1));
			encounterdto.setVoided(rst.getInt(2));
			}
			else {
				encounterdto = null;
			}
			rst.close();
			pstmt.close();
			con.close();


		}
		catch(SQLException sqle) {
			LOG.error("SQL Error in getEncounter " , sqle.getMessage());	
		}
		return encounterdto;
	}


	public ArrayList<ProviderDTO> getProviders(String providerId) {
		// TODO Auto-generated method stub
		int providerid = Integer.parseInt(providerId);
		ArrayList<ProviderDTO> providerlist = new ArrayList<ProviderDTO>();
		try {
			ProviderDTO provider = new ProviderDTO();

			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(PROVIDER_NEW_LIST);
			pstmt.setInt(1, providerid);
			ResultSet rst = pstmt.executeQuery();
			rst.next();

			provider.setUuid(rst.getString(1));
			provider.setUseruuid(rst.getString(2));
			provider.setIdentifier(rst.getString(3));
			provider.setGiven_name(rst.getString(4));
			provider.setMiddle_name(rst.getString(5));
			provider.setFamily_name(rst.getString(6));
			provider.setGender(rst.getString(7));
			provider.setDateofbirth(rst.getString(8));
			provider.setProviderId(rst.getString(9));
			provider.setEmailId(rst.getString(10));
			provider.setTelephoneNumber(rst.getString(11));
			provider.setCountryCode(rst.getString(12));
			provider.setRole(rst.getString(13));
			provider.setVoided(rst.getInt(14));
			providerlist.add(provider);

			rst.close();
			pstmt.close();
			con.close();
		}
		catch(SQLException sqle) {
			LOG.error("Error getting provider Details after Updation in getProviders");
		}
		return providerlist;
	}





	public ArrayList<CustomAppointmentDTO> updateAppointments(ArrayList<AppointmentDTO> appointmentDTOArrayList) {
		// TODO Auto-generated method stub
		ArrayList<CustomAppointmentDTO> customAppointmentDTOArrayList = new ArrayList<CustomAppointmentDTO>();
		for(AppointmentDTO appointmentDTO: appointmentDTOArrayList) {
			CustomAppointmentDTO customAppointmentDTO = new CustomAppointmentDTO();

			customAppointmentDTO.setAppointmentId(appointmentDTO.getAppointmentId());

			customAppointmentDTO.setSlotDay(appointmentDTO.getSlotDay());

			customAppointmentDTO.setSlotDate(appointmentDTO.getSlotDate());

			customAppointmentDTO.setSlotDuration(appointmentDTO.getSlotDuration());

			customAppointmentDTO.setSlotDurationUnit(appointmentDTO.getSlotDurationUnit());

			customAppointmentDTO.setSlotTime(appointmentDTO.getSlotTime());

			customAppointmentDTO.setSpeciality(appointmentDTO.getSpeciality());

			customAppointmentDTO.setUserUuid(appointmentDTO.getUserUuid());

			customAppointmentDTO.setDrName(appointmentDTO.getDrName());

			customAppointmentDTO.setVisitUuid(appointmentDTO.getVisitUuid());

			customAppointmentDTO.setPatientName(appointmentDTO.getPatientName());

			customAppointmentDTO.setOpenMrsId(appointmentDTO.getOpenMrsId());

			customAppointmentDTO.setPatientId(appointmentDTO.getPatientId());

			customAppointmentDTO.setLocationUuid(appointmentDTO.getLocationUuid());

			customAppointmentDTO.setHwUUID(appointmentDTO.getHwUUID());

			customAppointmentDTO.setReason(appointmentDTO.getReason());

			customAppointmentDTO.setVoided(appointmentDTO.getVoided());
			customAppointmentDTO.setSyncd(false);
			try {

				Connection con = dataSource.getConnection();
				PreparedStatement pstmtPatient = con.prepareStatement("select gender, TIMESTAMPDIFF(YEAR, birthdate, now()) FROM person WHERE uuid = ? ");
				PreparedStatement pstmtHealthWorker = con.prepareStatement("select a.gender, ifnull(TIMESTAMPDIFF(YEAR, a.birthdate, now()), 'NA'), concat_ws(' ', b.given_name, b.middle_name, b.family_name) FROM person a, person_name b WHERE a.person_id = b.person_id and a.person_id  = (select person_id from provider where uuid = ?)  ");
				pstmtPatient.setString(1,  appointmentDTO.getPatientId());
				ResultSet rstPatient = pstmtPatient.executeQuery();
				rstPatient.next();
				customAppointmentDTO.setPatientGender(rstPatient.getString(1));
				customAppointmentDTO.setPatientAge(rstPatient.getString(2));
				pstmtHealthWorker.setString(1, appointmentDTO.getHwUUID());
				ResultSet rstHW = pstmtHealthWorker.executeQuery();
				rstHW.next();
				customAppointmentDTO.setHwName(rstHW.getString(3));
				customAppointmentDTO.setHwAge(rstHW.getString(2));
				customAppointmentDTO.setHwGender(rstHW.getString(1));
				customAppointmentDTOArrayList.add(customAppointmentDTO);
				rstHW.close();
				rstPatient.close();
				pstmtHealthWorker.close();
				pstmtPatient.close();
				con.close();
			}
			catch(Exception e) {
				LOG.error("Error in updateAppointments ", e.getMessage());
			}


		}
		return customAppointmentDTOArrayList;
	}

}
