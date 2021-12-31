package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class WarRoomReportDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 231412413372734603L;
	private int id;
	private String phoneNo;
	private String employeeName;
	private String empCode;
	private String branch;
	private String callerId;
	private String typeOfQuery;
	private String remarks;
	private String startDate;
	private String endDate;
	private String callStatus;	
	private String campaignName;
	private String agentName;
	private String callDirection;
	private String extension;	
	private long duration;
	private String recPath;
	private LocalDateTime HoldStartTime;
	private LocalDateTime HoldEndTime;
	private String callDroped;
	
}
