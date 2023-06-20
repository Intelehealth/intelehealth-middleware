package org.intelehealth.middleware.services;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.intelehealth.middleware.api.dto.AppointmentDTO;
import org.intelehealth.middleware.api.dto.EncounterAPIDTO;
import org.intelehealth.middleware.api.dto.PatientAPIDTO;
import org.intelehealth.middleware.api.dto.PersonAPIDTO;
import org.intelehealth.middleware.api.dto.VisitAPIDTO;
import org.intelehealth.middleware.dto.CustomAppointmentDTO;
import org.intelehealth.middleware.dto.DataTraveller;
import org.intelehealth.middleware.dto.EncounterDTO;
import org.intelehealth.middleware.dto.PatientDTO;
import org.intelehealth.middleware.dto.PersonDTO;
import org.intelehealth.middleware.dto.ProviderDTO;
import org.intelehealth.middleware.dto.ReturnPullData;
import org.intelehealth.middleware.dto.ReturnPushData;
import org.intelehealth.middleware.dto.VisitDTO;
import org.intelehealth.middleware.utils.WebClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PushService {
@Autowired
MySQLService mysqlService;
@Autowired
OpenMRSService openMRSService;
private static final Logger LOG = LoggerFactory.getLogger(WebClientFilter.class);

	public ReturnPullData returnData(DataTraveller dataTraveller, String authString) {
		// TODO Auto-generated method stub
		ReturnPullData returnPullData = new ReturnPullData();
		 
		
		 ArrayList<ProviderDTO> customProviders = dataTraveller.getProviders();
		 
		 if(!customProviders.isEmpty() ) {
				for (ProviderDTO provider : customProviders) {

					mysqlService.updateAttributes(provider);

				}
			}
		 
		 ArrayList<PersonAPIDTO> personList = dataTraveller.getPersons();
		 if (personList != null) {
			 LOG.debug("Person found");
			 
				ArrayList<PersonDTO> persons = openMRSService.setPersons(personList,authString);
				returnPullData.setPersonList(persons);
			}
		
		 ArrayList<PatientAPIDTO> patientList = dataTraveller.getPatients();
		 if (patientList != null ) {
			 ArrayList<PatientDTO> patients = openMRSService.setPatients(patientList, authString);
			 returnPullData.setPatientlist(patients);
			 
		 }
		 
		 ArrayList<VisitAPIDTO> visitList = dataTraveller.getVisits();
		 if(visitList !=null) {
			 ArrayList<VisitDTO> visits = openMRSService.setVisits(visitList, authString);
			 returnPullData.setVisitlist(visits);
		 }
		 
		 ArrayList<EncounterAPIDTO> encounterList = dataTraveller.getEncounters();
		 if (encounterList != null) {
			 ArrayList<EncounterDTO> encounters = openMRSService.setEncounters(encounterList, authString);
			 returnPullData.setEncounterlist(encounters);
		 }
		 
		 ArrayList<ProviderDTO> providerlist =  dataTraveller.getProviders();
		 if(providerlist !=null) {
			 for(ProviderDTO provider : providerlist) {
				 ArrayList<ProviderDTO> providerlist2 = mysqlService.getProviders(provider.providerId);
				 returnPullData.setProviderlist(providerlist2);

				}
		 }
		 
		 ArrayList<AppointmentDTO> appointmentDTOArrayList = dataTraveller.getAppointments();
		 if(appointmentDTOArrayList !=null) {
			 ArrayList<CustomAppointmentDTO> customAppointmentDTOArrayList = mysqlService.updateAppointments(appointmentDTOArrayList);

				boolean b = openMRSService.addAppointmentOpenMRS(customAppointmentDTOArrayList);
				returnPullData.setAppointmentList(customAppointmentDTOArrayList);
		 }
		return returnPullData;
		 
	}
	
	
	
	 
	
	 
	 
	
}
