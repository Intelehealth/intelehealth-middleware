/**
 * 
 */
package org.intelehealth.middleware.dto;

/**
 * @author satya
 *
 */
public class ResponseDTO {
	private String message;
    private String status;
    private Object data;
    private String label;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setStatusMessage(String status,String message,String label) {
		this.status=status;
    	this.message = message;
    	this.label=label;
		
	}
}
