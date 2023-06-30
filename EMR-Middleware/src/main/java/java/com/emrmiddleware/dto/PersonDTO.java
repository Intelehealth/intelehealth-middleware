package com.emrmiddleware.dto;

public class PersonDTO {
	
	private String uuid;
	private boolean syncd=true;
	
    
	public boolean isSyncd() {
		return syncd;
	}

	public void setSyncd(boolean syncd) {
		this.syncd = syncd;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	

}
