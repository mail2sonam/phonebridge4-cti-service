package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReportDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phoneNumber;
	private String startDate;
	private String endDate;
	private String callStatus;
	private String status;
	private String campaignName;
	private String agentName;
	private String callDirection;
	private String extension;
	private String ringStartSecond;
	private String ringEndSecond;
	private long duration;
	private String feedback;
	private String caseId;
	
	
	

}
