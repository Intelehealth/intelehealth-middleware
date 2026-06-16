package com.emrmiddleware.action;

import com.emrmiddleware.dao.*;
import com.emrmiddleware.dto.*;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;

import java.util.ArrayList;


public class PullDataAction {

    public PullDataDTO getPullData(String lastpulldatatime, String locationuuid) throws ActionException, DAOException {

        PullDataDTO pulldata = new PullDataDTO();
        PatientDAO patientdao = new PatientDAO();
        VisitDAO visitdao = new VisitDAO();
        ObsDAO obsdao = new ObsDAO();
        MindmapDAO mindmapDAO = new MindmapDAO();

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
        ArrayList<ProviderAttributeDTO> providerAttributeList = new ArrayList<ProviderAttributeDTO>();
        ArrayList<VisitAttributeTypeDTO> visitAttributeTypeList = new ArrayList<VisitAttributeTypeDTO>();


        ArrayList<ConceptAttributeTypeDTO> conceptAttributeTypeList = new ArrayList<ConceptAttributeTypeDTO>();
        ArrayList<ConceptAttributeDTO> conceptAttributesList = new ArrayList<ConceptAttributeDTO>();

        ArrayList<VisitAttributeDTO> visitAttributesList = new ArrayList<VisitAttributeDTO>();
     //   pulldata.setPropertyContents(mindmapDAO.getConfigFile()); // Config file

        try {
            pulldata.setPullexecutedtime(visitdao.getDBCurrentTime());//Used by device for syncing purpose
            patientlist = patientdao.getPatients(lastpulldatatime, locationuuid);
            patientAttributeTypeList = patientdao.getPatientAttributeType(lastpulldatatime, locationuuid);
            patientAttributesList = patientdao.getPatientAttributes(lastpulldatatime, locationuuid);
            visitlist = visitdao.getVisits(lastpulldatatime, locationuuid);
            visitAttributeTypeList = visitdao.getVisitAttributeTypeMaster(lastpulldatatime);
            visitAttributesList = visitdao.getVisitAttributes(lastpulldatatime, locationuuid);
            encounterlist = encounterdao.getEncounters(lastpulldatatime, locationuuid);
            obslist = obsdao.getObsList(lastpulldatatime, locationuuid);
            locationlist = locationdao.getLocations(lastpulldatatime);
            providerlist = providerdao.getProviders();
            providerAttributeTypeList = providerdao.getProviderAttributeTypeMaster(lastpulldatatime);
            providerAttributeList = providerdao.getProviderAttributes(lastpulldatatime);
            conceptAttributeTypeList = visitdao.getConceptAttributeTypeMaster(lastpulldatatime);
            conceptAttributesList = visitdao.getConceptAttributes(lastpulldatatime);
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
            pulldata.setConceptAttributeTypeList(conceptAttributeTypeList);
            pulldata.setConceptAttributeList(conceptAttributesList);
        } catch (DAOException e) {
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new ActionException(e.getMessage(), e);
        }
        return pulldata;
    }
}
