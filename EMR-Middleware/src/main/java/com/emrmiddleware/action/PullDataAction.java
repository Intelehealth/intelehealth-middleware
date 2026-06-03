
package com.emrmiddleware.action;

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
import java.util.ArrayList;

public class PullDataAction {
    public PullDataDTO getPullData(String lastpulldatatime, String locationuuid) throws ActionException, DAOException {
        PullDataDTO pulldata = new PullDataDTO();
        PatientDAO patientdao = new PatientDAO();
        VisitDAO visitdao = new VisitDAO();
        ObsDAO obsdao = new ObsDAO();
        EncounterDAO encounterdao = new EncounterDAO();
        LocationDAO locationdao = new LocationDAO();
        ProviderDAO providerdao = new ProviderDAO();
        ArrayList<PatientDTO> patientlist = new ArrayList();
        ArrayList<PatientAttributeTypeDTO> patientAttributeTypeList = new ArrayList();
        ArrayList<PatientAttributeDTO> patientAttributesList = new ArrayList();
        ArrayList<VisitDTO> visitlist = new ArrayList();
        ArrayList<EncounterDTO> encounterlist = new ArrayList();
        ArrayList<ObsDTO> obslist = new ArrayList();
        ArrayList<LocationDTO> locationlist = new ArrayList();
        ArrayList<ProviderDTO> providerlist = new ArrayList();
        ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList = new ArrayList();
        ArrayList<ProviderAttributeDTO> providerAttributeList = new ArrayList();
        ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList = new ArrayList();
        ArrayList<VisitAttributeDTO> visitAttributesList = new ArrayList();
        try {
            pulldata.setPullexecutedtime(visitdao.getDBCurrentTime());
            patientlist = patientdao.getPatients(lastpulldatatime, locationuuid);
            patientAttributeTypeList = patientdao.getPatientAttributeType(lastpulldatatime, locationuuid);
            patientAttributesList = patientdao.getPatientAttributes(lastpulldatatime, locationuuid);
            visitlist = visitdao.getVisits(lastpulldatatime, locationuuid);
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
        }
        catch (DAOException e) {
            throw new DAOException(e.getMessage(), e);
        }
        catch (Exception e) {
            throw new ActionException(e.getMessage(), e);
        }
        return pulldata;
    }
}

