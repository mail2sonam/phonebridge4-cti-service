package com.eupraxia.telephony.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as=YmlDispositionModel.class)
public class YmlDispositionModel {
	private String id;
	private String customerName;
	private String camName;
	private String campaign;
	private String disposition;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCamName() {
		return camName;
	}
	public void setCamName(String camName) {
		this.camName = camName;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	

	 
}
