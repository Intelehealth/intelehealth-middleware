package com.emrmiddleware.action;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dao.EncounterDAO;
import com.emrmiddleware.dao.LocationDAO;
import com.emrmiddleware.dao.ObsDAO;
import com.emrmiddleware.dao.PatientDAO;
import com.emrmiddleware.dao.ProviderDAO;
import com.emrmiddleware.dao.VisitDAO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.dto.LocationDTO;
import com.emrmiddleware.dto.ObsDTO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientAttributeTypeDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.ProviderAttributeDTO;
import com.emrmiddleware.dto.ProviderAttributeTypeDTO;
import com.emrmiddleware.dto.ProviderDTO;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.utils.EmrUtils;

public class PullDataAction {

	public PullDataDTO getPullData(Timestamp lastdatapulltime, String locationuuid) throws ActionException, DAOException {

		PullDataDTO pulldata = new PullDataDTO();
		PatientDAO patientdao = new PatientDAO();
		VisitDAO visitdao = new VisitDAO();
		ObsDAO obsdao = new ObsDAO();
		EncounterDAO encounterdao = new EncounterDAO();
		LocationDAO locationdao = new LocationDAO();
		ProviderDAO providerdao = new ProviderDAO();
		ArrayList<PatientDTO> patientlist = new ArrayList<PatientDTO>();
		ArrayList<PatientAttributeTypeDTO> patientAttributeTypeList = new ArrayList<PatientAttributeTypeDTO>();
		ArrayList<PatientAttributeDTO> patientAttributesList = new ArrayList<PatientAttributeDTO>();
		ArrayList<VisitDTO> visitlist = new ArrayList<VisitDTO>();
		ArrayList<EncounterDTO> encounterlist = new ArrayList<EncounterDTO>();
		ArrayList<ObsDTO> obslist = new ArrayList<ObsDTO>();
		ArrayList<LocationDTO> locationlist = new ArrayList<LocationDTO>();
		ArrayList<ProviderDTO> providerlist = new ArrayList<ProviderDTO>();
		ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList = new ArrayList<ProviderAttributeTypeDTO>();
		ArrayList<ProviderAttributeDTO>  providerAttributeList = new ArrayList<ProviderAttributeDTO>();
		try {
			patientlist = patientdao.getPatients(lastdatapulltime, locationuuid);
			patientAttributeTypeList = patientdao.getPatientAttributeType(lastdatapulltime, locationuuid);
			patientAttributesList = patientdao.getPatientAttributes(lastdatapulltime, locationuuid);
			visitlist = visitdao.getVisits(lastdatapulltime, locationuuid);
			encounterlist = encounterdao.getEncounters(lastdatapulltime, locationuuid);
			obslist = obsdao.getObs(lastdatapulltime, locationuuid);
			locationlist = locationdao.getLocations(lastdatapulltime);
			providerlist = providerdao.getProviders(lastdatapulltime);
			providerAttributeTypeList = providerdao.getProviderAttributeTypeMaster(lastdatapulltime);
			providerAttributeList = providerdao.getProviderAttributes(lastdatapulltime);
			pulldata.setLocationlist(locationlist);
			pulldata.setPatientlist(patientlist);
			pulldata.setPatientAttributeTypeListMaster(patientAttributeTypeList);
			pulldata.setPatientAttributesList(patientAttributesList);
			pulldata.setProviderlist(providerlist);
			pulldata.setProviderAttributeTypeList(providerAttributeTypeList);
			pulldata.setProviderAttributeList(providerAttributeList);
			pulldata.setVisitlist(visitlist);
			pulldata.setEncounterlist(encounterlist);
			pulldata.setObslist(obslist);
			
			pulldata.setPullexecutedtime(EmrUtils.getCurrentTime());//Used by device for syncing purpose
		} catch (DAOException e) {
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);
		}

		return pulldata;

	}

}
