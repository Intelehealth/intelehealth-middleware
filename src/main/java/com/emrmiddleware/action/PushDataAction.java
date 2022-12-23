package com.emrmiddleware.action;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dao.ProviderDAO;
import com.emrmiddleware.dmo.ProviderDMO;
import com.emrmiddleware.dto.*;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.exception.ActionException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;


public class PushDataAction {

	String authString;

	public PushDataAction(String auth) {
		authString = auth;
	}

	public PullDataDTO pushData(PushDataDTO pushdatadto) throws DAOException, ActionException {

		ArrayList<PersonAPIDTO> personList = new ArrayList<PersonAPIDTO>();
		ArrayList<PatientAPIDTO> patientList;
		ArrayList<VisitAPIDTO> visitList;
		ArrayList<EncounterAPIDTO> encounterList;


		PullDataDTO pulldatadto = new PullDataDTO();
		try {
			personList = pushdatadto.getPersons();
			patientList = pushdatadto.getPatients();
			visitList = pushdatadto.getVisits();
			encounterList = pushdatadto.getEncounters();
			ArrayList<ProviderDTO> providerlist = pushdatadto.getProviders();

			PersonAction personaction = new PersonAction(authString);
			PatientAction patientaction = new PatientAction(authString);
			VisitAction visitaction = new VisitAction(authString);
			EncounterAction encounteraction = new EncounterAction(authString);

			// patientaction.setPatients(patientList);
			if (personList != null) {
				ArrayList<PersonDTO> persons = personaction.setPersons(personList);
				pulldatadto.setPersonList(persons);
			}
			if (patientList != null) {
				ArrayList<PatientDTO> patients = patientaction.setPatients(patientList);
				pulldatadto.setPatientlist(patients);
			}
			if (visitList != null) {
				ArrayList<VisitDTO> visits = visitaction.setVisits(visitList);
				pulldatadto.setVisitlist(visits);
			}
			if (encounterList != null) {
				ArrayList<EncounterDTO> encounters = encounteraction.setEncounters(encounterList);
				pulldatadto.setEncounterlist(encounters);
			}
			if(providerlist != null)
			{

				for(ProviderDTO provider : providerlist) {
					pulldatadto.setProviderlist(getProviders(new Integer(provider.providerId).intValue()));


				}

			}


		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);

		}
		return pulldatadto;
	}

	private ArrayList<ProviderDTO> getProviders(int providerId) {
		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<ProviderDTO> providerlist = new ArrayList<ProviderDTO>();

		try {

			ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
			providerlist = providerdmo.getProviders2(providerId);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return providerlist;
		
		}

}
