package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignDTO implements Serializable{
	
	private static final long serialVersionUID = -5826478536392533418L;

	private Long campaignId;
	private String campaignName;
	private String dialMethod;
	private String status;
	private String wrapUpTime;
	private boolean isDefault;
	private Date campaignCreatedOn;
	private String didNumber;
	private String campaign_Source;
	private String callDirection;
	private String resourceURL;
	private String queueName;
	private String trunk;
	private long beingcalled;
	private String departmentcode;
	
}