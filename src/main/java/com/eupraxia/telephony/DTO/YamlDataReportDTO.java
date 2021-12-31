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

public class YamlDataReportDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 231412413372734603L;
	private int id;
	private String phoneNo;	
	private String callId;	
	private String remarks;
	private String callTime;
	private String campaign; 
	private String client;
	private String mainDispo;
	private String subDispo;
	private String subSubDispo;
	private String agentName;
	private String extension;
	private String callStatus;
	private String callDirection;
	private String recPath;
	private LocalDateTime callStartTime;
	private LocalDateTime callEndTime;
	private String duration;
	private LocalDateTime holdStartTime;
	private LocalDateTime holdEndTime;
	private String callDroped;
	private String customerName;
	private String district;	
	private String state;	
	private String country;	
}
