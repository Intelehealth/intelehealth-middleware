package com.emrmiddleware.dto;

public class FileDTO {
	
	String uuid;
	String objectuuid;
	String objectname;
	String imagerawname;
	String filepath;
	String attributes;
	int voided;
	String filename;
	String locationuuid;
	
	
	public String getLocationuuid() {
		return locationuuid;
	}
	public void setLocationuuid(String locationuuid) {
		this.locationuuid = locationuuid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getObjectuuid() {
		return objectuuid;
	}
	public void setObjectuuid(String objectuuid) {
		this.objectuuid = objectuuid;
	}
	public String getObjectname() {
		return objectname;
	}
	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}
	public String getImagerawname() {
		return imagerawname;
	}
	public void setImagerawname(String imagerawname) {
		this.imagerawname = imagerawname;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public int getVoided() {
		return voided;
	}
	public void setVoided(int voided) {
		this.voided = voided;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	

}
