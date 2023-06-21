/**
 * 
 */
package org.intelehealth.middleware.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.intelehealth.middleware.dto.ReturnPullData;
import org.intelehealth.middleware.dto.VisitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author satya
 *
 */
@Service
public class PullService {

	@Autowired 
	MySQLService mySQLService;
	
	@Autowired
	DataSource dataSource;
	
	public ReturnPullData gatherData(String locationUUID, String dateFrom) {
		// TODO Auto-generated method stub
		
		ReturnPullData returnPullData = new ReturnPullData();
		returnPullData.setPullexecutedtime(mySQLService.getCurTime());
		returnPullData.setPatientlist(mySQLService.getPatients(locationUUID, dateFrom));
		returnPullData.setVisitlist(mySQLService.getVisitList(locationUUID,dateFrom));
		returnPullData.setVisitAttributeTypeList(mySQLService.getVisitAttributeTypeList(dateFrom));
		returnPullData.setVisitAttributeList(mySQLService.getVisitAttributeList(locationUUID,dateFrom));
		returnPullData.setEncounterlist(mySQLService.getEncounters(locationUUID, dateFrom));
		returnPullData.setPatientAttributeTypeListMaster(mySQLService.getPatientAttributeTypeList(dateFrom));
		returnPullData.setPatientAttributesList(mySQLService.getPatientAttributeList(locationUUID,dateFrom));
		returnPullData.setObslist(mySQLService.getObs(locationUUID,dateFrom));
		returnPullData.setLocationlist(mySQLService.getLocationList(dateFrom));
		returnPullData.setProviderlist(mySQLService.getProviderList(dateFrom));
		returnPullData.setProviderAttributeTypeList(mySQLService.getProviderAttributeTypeList(dateFrom));
		returnPullData.setProviderAttributeList(mySQLService.getProviderAttributeList(dateFrom));
		return returnPullData;
	
	}

}
