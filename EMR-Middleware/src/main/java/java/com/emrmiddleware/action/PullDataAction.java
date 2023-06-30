package com.emrmiddleware.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitAttributeTypeDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;

public class PullDataAction {

	public PullDataDTO getPullData(String lastpulldatatime, String locationuuid, int pageno, int limit) throws ActionException, DAOException {

		PullDataDTO pulldata = new PullDataDTO();


		PatientDAO patientdao = new PatientDAO();
		VisitDAO visitdao = new VisitDAO();
		pulldata.setPullexecutedtime(visitdao.getDBCurrentTime());//Used by device for syncing purpose
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
		ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList = new ArrayList<VisitAttributeTypeDTO>();
		ArrayList<VisitAttributeDTO> visitAttributesList = new ArrayList<VisitAttributeDTO>();
		try {
			int offset = 0 ;
			if (pageno == 0 )
			{
				offset = 0 ;
			}
			else {
				offset = pageno * limit + 1;
			}


			patientlist = patientdao.getPatients(lastpulldatatime, locationuuid, offset, limit); // Adding offset and limit
			patientAttributeTypeList = patientdao.getPatientAttributeType(lastpulldatatime, locationuuid);
			patientAttributesList = patientdao.getPatientAttributes(lastpulldatatime, locationuuid);
			visitlist = visitdao.getVisits(lastpulldatatime, locationuuid , offset, limit); // Adding offset and limit
			visitAttributeTypeList = visitdao.getVisitAttributeTypeMaster(lastpulldatatime);
			visitAttributesList = visitdao.getVisitAttributes(lastpulldatatime, locationuuid);
			encounterlist = encounterdao.getEncounters(lastpulldatatime, locationuuid);
			obslist = obsdao.getObsList(lastpulldatatime, locationuuid);
			locationlist = locationdao.getLocations(lastpulldatatime);
			providerlist = providerdao.getProviders(lastpulldatatime);
			providerAttributeTypeList = providerdao.getProviderAttributeTypeMaster(lastpulldatatime);
			providerAttributeList = providerdao.getProviderAttributes(lastpulldatatime);
			pulldata.setLocationlist(locationlist);
			pulldata.setPatientlist(patientlist);
			pulldata.setPatientAttributeTypeListMaster(patientAttributeTypeList);
			pulldata.setPatientAttributesList(patientAttributesList);
			pulldata.setProviderlist(providerlist);
			pulldata.setProviderAttributeTypeList(providerAttributeTypeList);
			pulldata.setProviderAttributeList(providerAttributeList);
			pulldata.setVisitlist(visitlist);
			pulldata.setVisitAttributeTypeList(visitAttributeTypeList);
			pulldata.setVisitAttributeList(visitAttributesList);
			pulldata.setEncounterlist(encounterlist);
			pulldata.setObslist(obslist);

			/*if(patientlist.isEmpty()) { // Patch not to emit visit, encounter,  obs when patient is empty
				pulldata.setEncounterlist(new ArrayList<EncounterDTO>());
				pulldata.setObslist(new ArrayList<ObsDTO>());
				pulldata.setVisitAttributeList(new ArrayList<VisitAttributeDTO>());
				pulldata.setVisitlist(new ArrayList<VisitDTO>());
				pulldata.setPatientAttributesList(new ArrayList<PatientAttributeDTO>());

			}*/
			int patientCount = patientdao.getPatientsCount(lastpulldatatime, locationuuid);
			int visitCount = visitdao.getVisitCount(lastpulldatatime, locationuuid);
			pulldata.setTotalCount(Math.max(patientCount, visitCount));

			if(offset <= pulldata.getTotalCount()  ) {
				//if (pulldata.getTotalCount() - offset >= limit)
					pulldata.setPageNo(pageno + 1);

			}
			else{
				pulldata.setPageNo(-1);
			}

			

		} catch (DAOException e) {
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);
		}

		return pulldata;

	}

}
