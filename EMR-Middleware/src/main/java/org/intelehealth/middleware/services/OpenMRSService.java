package org.intelehealth.middleware.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import org.intelehealth.middleware.api.dto.AttributeAPIDTO;
import org.intelehealth.middleware.api.dto.EncounterAPIDTO;
import org.intelehealth.middleware.api.dto.IDGenAPIDTO;
import org.intelehealth.middleware.api.dto.PatientAPIDTO;
import org.intelehealth.middleware.api.dto.PersonAPIDTO;
import org.intelehealth.middleware.api.dto.VisitAPIDTO;
import org.intelehealth.middleware.dto.CustomAppointmentDTO;
import org.intelehealth.middleware.dto.EncounterDTO;
import org.intelehealth.middleware.dto.PatientDTO;
import org.intelehealth.middleware.dto.PersonDTO;
import org.intelehealth.middleware.dto.VisitDTO;
import org.intelehealth.middleware.utils.WebClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.logging.LogLevel;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Service
public class OpenMRSService {
@Autowired
MySQLService mysqlService;
@Autowired
ConstantsService constantsService;
@Autowired
AuthService authService; 

private static final Logger LOG = LoggerFactory.getLogger(WebClientFilter.class);

	public ArrayList<PersonDTO> setPersons(ArrayList<PersonAPIDTO> personList, String authString) {
		// TODO Auto-generated method stub
		
		ArrayList<PersonDTO> persons = new ArrayList<PersonDTO>(); 
		for ( PersonAPIDTO person : personList) {
			 
			boolean isPersonSet = false;
			if (mysqlService.isPersonExists(person.getUuid())) {
				isPersonSet=editPersonOpenMRS(person,authString);
			} else {
				LOG.debug("444");
				isPersonSet=addPersonOpenMRS(person,authString);
			}
			PersonDTO  persondto = new PersonDTO();
			persondto.setUuid(person.getUuid());
			persondto.setSyncd(isPersonSet);
			persons.add(persondto);
		}
		return null;
	}

	private boolean addPersonOpenMRS(PersonAPIDTO person, String authString) {
		// TODO Auto-generated method stub
		 LOG.debug("44");
		HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
			
		WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		 
		 LOG.debug(person.toString());
		
		ResponseEntity response = webClient.post()
				.uri(constantsService.OPENMRS_ADD_PERSON_ENDPOINT)
				.bodyValue(person)
				 .retrieve()
				 .toEntity(String.class)
				 .block();
		
	    
		if (response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
			LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
			return false;
		}
			
		
		
		
		
	}

 

	 

	private boolean editPersonOpenMRS(PersonAPIDTO person,String authString) {
		// TODO Auto-generated method stub
		HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		
		WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		ResponseEntity<String> response = webClient.post()
				.uri(constantsService.OPENMRS_EDIT_PERSON_ENDPOINT+person.getUuid())
				.bodyValue(person)
				 .retrieve()
				 .toEntity(String.class)
				 .block();
		
		if (response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
			LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
			return false;
		}
			
	}

	private Consumer<HttpHeaders> httpHeaders(String authString){
        return headers -> {
            headers.set(HttpHeaders.CONTENT_ENCODING, String.valueOf(MediaType.APPLICATION_JSON));
            String decodedAuth=authService.getUserNameAndPasswordFromHeader(authString);
		    String username = decodedAuth.substring(0,decodedAuth.indexOf(constantsService.COLON));
	        String password = decodedAuth.substring(decodedAuth.indexOf(constantsService.COLON) + 1);
            headers.setBasicAuth(username, password);
          
            headers.set(HttpHeaders.ACCEPT_ENCODING, String.valueOf(MediaType.APPLICATION_JSON));
        };
    }

	public ArrayList<PatientDTO> setPatients(ArrayList<PatientAPIDTO> patientList, String authString) {
		ArrayList<PatientDTO> patients = new ArrayList<PatientDTO>();
		PatientDTO patientdto = null;
		PatientAPIDTO patientforerror = new PatientAPIDTO();
		boolean isPatientSet = true;
		 
		try {
			for (PatientAPIDTO patient : patientList) {
				patientforerror = patient;
				String openMrsId = "";
				 
				PatientDTO patientDTO = mysqlService.getPatient(patient.getPerson());
				
				if (patientDTO == null) {
				 
							
					openMrsId = getOpenMrsId(authString);
					patient.getIdentifiers().get(0).setIdentifier(openMrsId);
					isPatientSet = addPatientOpenMRS(patient,authString);
				} else {
					 
					openMrsId = patientDTO.getOpenmrs_id();
				}

				patientdto = new PatientDTO();
				patientdto.setUuid(patient.getPerson());
				patientdto.setSyncd(isPatientSet);
				if (isPatientSet == true)
					patientdto.setOpenmrs_id(openMrsId);
				patients.add(patientdto);
			}
		} catch (Exception e) {
			LOG.error("Error in setPatients: " , e.getMessage() );
		} 
		return patients;
	}

	private boolean addPatientOpenMRS(PatientAPIDTO patient,String authString) {
		// TODO Auto-generated method stub
		HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
			
		WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		 
		 
		
		ResponseEntity response = webClient.post()
				.uri(constantsService.OPENMRS_ADD_PATIENT_ENDPOINT)
				.bodyValue(patient)
				 .retrieve()
				 .toEntity(String.class)
				 .block();
		
	    
		if (response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
			LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
			return false;
		}
	}

	private String getOpenMrsId(String authString) {
		// TODO Auto-generated method stub
			String decodedAuth=authService.getUserNameAndPasswordFromHeader(authString);
		    String username = decodedAuth.substring(0,decodedAuth.indexOf(constantsService.COLON));
	        String password = decodedAuth.substring(decodedAuth.indexOf(constantsService.COLON) + 1);
	        HttpClient httpClient = HttpClient
					  .create()
					  .wiretap("reactor.netty.http.client.HttpClient", 
							    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
	        
	        WebClient webClient = WebClient
			          .builder()
			          .defaultHeaders(httpHeaders(authString))
			          .clientConnector(new ReactorClientHttpConnector(httpClient))
			          .baseUrl(constantsService.OPENMRS_IDGEN_BASE_URL)
			          .build();
	        IDGenAPIDTO idgenAPIDTO = webClient.get()
					.uri(uriBuilder->uriBuilder.path(constantsService.OPENMRS_IDGEN_ENDPOINT)
					.queryParam("source",1)
					.queryParam("username",username)
					.queryParam("password",password)
					.build())
					.retrieve()
					.bodyToMono(IDGenAPIDTO.class)
					.block();
	       String  openmrsid = idgenAPIDTO.getIdentifiers()[0];
	       
		return openmrsid;
	}

	public ArrayList<VisitDTO> setVisits(ArrayList<VisitAPIDTO> visitList, String authString) {
		// TODO Auto-generated method stub
		ArrayList<VisitDTO> visits = new ArrayList<VisitDTO>();
		VisitDTO visitdto;
		VisitAPIDTO visitforerror = new VisitAPIDTO();
		boolean isVisitSet = true;
		 
		try {
			for (VisitAPIDTO visit : visitList) {
				visitforerror = visit;
				if (isVisitExists(visit.getUuid())) {
					isVisitSet = editVisitOpenMRS(visit,authString);
				} else {
					isVisitSet = addVisitOpenMRS(visit,authString);
				}
				visitdto = new VisitDTO();
				visitdto.setUuid(visit.getUuid());
				visitdto.setSyncd(isVisitSet);
				visits.add(visitdto);
			}
		} catch (Exception e) {
			 
			LOG.error(e.getMessage(), e);
			// throw new ActionException(e.getMessage(), e);
		}
		return visits;
	}

	private boolean addVisitOpenMRS(VisitAPIDTO visit,String authString) {
		// TODO Auto-generated method stub
		String decodedAuth=authService.getUserNameAndPasswordFromHeader(authString);
	    String username = decodedAuth.substring(0,decodedAuth.indexOf(constantsService.COLON));
        String password = decodedAuth.substring(decodedAuth.indexOf(constantsService.COLON) + 1);
        HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		 LOG.debug(visit.toString());
		 
		
		ResponseEntity response = webClient.post()
				.uri(constantsService.OPENMRS_ADD_VISIT_ENDPOINT)
				.bodyValue(visit)
				 .retrieve()
				 .toEntity(String.class)
				 .block();
		if(response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
		LOG.error("Error adding visit", visit);
        LOG.error("error in addVisitOpenMRS",response.getBody());
        
		return false;
		}
	}

	private boolean editVisitOpenMRS(VisitAPIDTO visit,String authString) {
		// TODO Auto-generated method stub
		HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		
		WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		ResponseEntity<String> response = webClient.post()
				.uri(constantsService.OPENMRS_EDIT_VISIT_ENDPOINT+visit.getUuid())
				.bodyValue(visit)
				 .retrieve()
				 .toEntity(String.class)
				 .block();
		
		if (response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
			LOG.error("Error editing a visit ",visit);
			LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
			return false;
		}
	}

	private boolean isVisitExists(String uuid) {
		// TODO Auto-generated method stub
		boolean isVisitExists = false;
		 
		isVisitExists  = mysqlService.isVisitExists(uuid);
		
		return isVisitExists;
	}

	public ArrayList<EncounterDTO> setEncounters(ArrayList<EncounterAPIDTO> encounterList, String authString) {
		// TODO Auto-generated method stub
		ArrayList<EncounterDTO> encounters = new ArrayList<EncounterDTO>();
		EncounterDTO encounterdto;
		EncounterAPIDTO encounterforerror = new EncounterAPIDTO();
		
		 
		boolean isEncounterPresent = false;
		try {
			for (EncounterAPIDTO encounter : encounterList) {
				int voided = 0;
				boolean isEncounterSet = true;
				if (isEncounterVoided(encounter) == true) {
					voided = 1;
				} else {
					voided = 0;
				}
				encounterforerror = encounter;
				 
				EncounterDTO encounterdto_present = new EncounterDTO();
				encounterdto_present = getEncounter(encounter.getUuid());
				// to prevent multiple hit to DB
				if (encounterdto_present != null) {
					isEncounterPresent = true;
				} else {
					isEncounterPresent = false;
				}
				
				// isEncounterPresent = isEncounterExists(encounter.getUuid());
				//Edit Encounter
				if ((isEncounterPresent) && (isEncounterVoided(encounter) == false)) {
					isEncounterSet = editEncounterOpenMRS(encounter,authString);
				}

				//delete encounter
				if ((isEncounterPresent) && (isEncounterVoided(encounter) == true)) {
					// check if encounter already is voided in openMRS
					if (encounterdto_present.getVoided() == 1) {
						isEncounterSet = true;
						voided = 1;
					} else {
						isEncounterSet = deleteEncounterOpenMRS(encounter,authString);
						if (isEncounterSet == true)
							voided = 1;
					}
				}
				//Add Encounter
				if ((isEncounterPresent == false) && (isEncounterVoided(encounter) == false)) {
					isEncounterSet = addEncounterOpenMRS(encounter,authString);
				}
				encounterdto = new EncounterDTO();
				encounterdto.setUuid(encounter.getUuid());
				encounterdto.setSyncd(isEncounterSet);
				encounterdto.setVoided(voided);
				encounters.add(encounterdto);
			}
		} catch (Exception e) {
			LOG.error("Errorin setEncounters: " , encounterforerror);
			LOG.error(e.getMessage(), e);
		}
		return encounters;
	}

	private boolean addEncounterOpenMRS(EncounterAPIDTO encounterapidto, String authString) {
		// TODO Auto-generated method stub
		encounterapidto.setVoided(null);
		HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		
		WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		ResponseEntity<String> response = webClient.post()
				.uri(constantsService.OPENMRS_ADD_ENCOUNTER_ENDPOINT)
				.bodyValue(encounterapidto)
				 .retrieve()
				 .toEntity(String.class)
				 .block();
		
		if (response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
			LOG.error("Error adding an encounter ",encounterapidto);
			LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
			return false;
		}
	}

	private boolean deleteEncounterOpenMRS(EncounterAPIDTO encounterapidto, String authString) {
		// TODO Auto-generated method stub
		HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		
		WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		ResponseEntity<String> response = webClient.delete()
				.uri(constantsService.OPENMRS_DELETE_ENCOUNTER_ENDPOINT+encounterapidto.getUuid())
				.retrieve()
				 .toEntity(String.class)
				 .block();
		
		if (response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
			LOG.error("Error deleting an encounter ",encounterapidto);
			LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
			return false;
		}
	}

	private boolean editEncounterOpenMRS(EncounterAPIDTO encounterapidto, String authString) {
		// TODO Auto-generated method stub
		HttpClient httpClient = HttpClient
				  .create()
				  .wiretap("reactor.netty.http.client.HttpClient", 
						    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		
		WebClient webClient = WebClient
		          .builder()
		          .defaultHeaders(httpHeaders(authString))
		          .clientConnector(new ReactorClientHttpConnector(httpClient))
		          .baseUrl(constantsService.OPENMRS_BASE_URL)
		          .build();
		ResponseEntity<String> response = webClient.post()
				.uri(constantsService.OPENMRS_EDIT_ENCOUNTER_ENDPOINT+encounterapidto.getUuid())
				.bodyValue(encounterapidto)
				 .retrieve()
				 .toEntity(String.class)
				 .block();
		
		if (response.getStatusCodeValue() <=399) {
			return true;
		}
		else {
			LOG.error("Error editing an encounter ",encounterapidto);
			LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
			return false;
		}
	}

	private EncounterDTO getEncounter(String uuid) {
		// TODO Auto-generated method stub
		EncounterDTO encounterdto = new EncounterDTO();
				
		encounterdto = mysqlService.getEncounter(uuid);
		return encounterdto;
		
	}

	private boolean isEncounterVoided(EncounterAPIDTO encounterapidto) {
		// TODO This method needs to be re-looked into 
		boolean isVoided = false;
		if (encounterapidto.getVoided() != null) {
			if (encounterapidto.getVoided().equals("1"))
				isVoided = true;
		}
		return isVoided;
	}

	public boolean addAppointmentOpenMRS(ArrayList<CustomAppointmentDTO> customAppointmentDTOArrayList) {
		// TODO Auto-generated method stub
		boolean rtnVal = false;
		for(CustomAppointmentDTO appointment: customAppointmentDTOArrayList) {
			HttpClient httpClient = HttpClient
					  .create()
					  .wiretap("reactor.netty.http.client.HttpClient", 
							    LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
			
			WebClient webClient = WebClient
			          .builder()
			          .clientConnector(new ReactorClientHttpConnector(httpClient))
			          .baseUrl(constantsService.MINDMAP_BASE_URL)
			          .build();
			ResponseEntity<String> response = webClient.post()
					.uri(constantsService.MINDMAP_ADD_APPOINTMENT_ENDPOINT)
					.bodyValue(appointment)
					 .retrieve()
					 .toEntity(String.class)
					 .block();
			
			if (response.getStatusCodeValue() <=399) {
				
				rtnVal = true;
			}
			else {
				LOG.error("Error editing an encounter ",appointment);
				LOG.error(response.getStatusCodeValue() + "--" + response.getBody())	;
				rtnVal = false;
			}
			
		}
		return rtnVal;
	}
	
}
