package com.emrmiddleware.dto;

import java.util.Date;

public class VisitDTO {
	
	private String openmrs_patientuuid;
	private String openmrs_visituuid;
	private int visit_type_id;
	private Date startdate;
	private Date enddate;
	private String locationuuid;
	private int creator;
	public String getOpenmrs_patientuuid() {
		return openmrs_patientuuid;
	}
	public void setOpenmrs_patientuuid(String openmrs_patientuuid) {
		this.openmrs_patientuuid = openmrs_patientuuid;
	}
	public String getOpenmrs_visituuid() {
		return openmrs_visituuid;
	}
	public void setOpenmrs_visituuid(String openmrs_visituuid) {
		this.openmrs_visituuid = openmrs_visituuid;
	}
	public int getVisit_type_id() {
		return visit_type_id;
	}
	public void setVisit_type_id(int visit_type_id) {
		this.visit_type_id = visit_type_id;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getLocationuuid() {
		return locationuuid;
	}
	public void setLocationuuid(String locationuuid) {
		this.locationuuid = locationuuid;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	

}
