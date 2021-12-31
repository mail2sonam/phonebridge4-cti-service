package com.eupraxia.telephony.Model.Reports;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
@Entity
@Table(name = "eupraxia_report")
public class ReportsModel implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4996213363169999448L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="doc_Id")
	private String docId; 	

	@Column(name="case_Id")
	private String caseId; 

	@Column(name="phone_No")
	private String phoneNo;

	@Column(name="extension")
	private String extension;

	@Column(name="is_Closed")
	private String isClosed;

	@Column(name="call_Status")

	private String callStatus;

	@Column(name="popup_Status")
	private String popupStatus;

	@Column(name="call_Start_Time")
	private LocalDateTime callStartTime;

	@Column(name="call_End_Time")
	private LocalDateTime callEndTime;

	@Column(name="Call_Connect_Time")
	private LocalDateTime CallConnectTime;

	@Column(name="trunk_Channel")
	private String trunkChannel;

	@Column(name="sip_Channel")
	private String sipChannel;

	@Column(name="second_Channel")
	private String secondChannel;

	@Column(name="call_Direction")
	private String callDirection;

	@Column(name="second_Number")
	private String secondNumber;

	/*
	 * @Column(name="agent_Id") private List<String> channels;
	 */

	@Column(name="Hold_Start_Time")
	private LocalDateTime HoldStartTime;

	@Column(name="Hold_End_Time")
	private LocalDateTime HoldEndTime;

	@Column(name="disposition")
	private String disposition;

	@Column(name="comments")
	private String comments;

	@Column(name="callback_Date")
	private Date callbackDate;

	@Column(name="rec_path")
	private String recPath;
	
	@Column(name="cam_id")
	private int camId;
	
	@Column(name="cam_name")
	private String camName;
	
	@Column(name="call_droped")
	private String callDroped;
	
	@Column(name="cus_name")
	private String cusName;
	
	@Column(name="second_status")
	private String secondStatus;
	
	@Column(name="dispo")
	 private String dispo;
	
	@Column(name="maindispo")
	private String maindispo;
	
	@Column(name="subdispo")
	   private String subdispo; 
	
	@Column(name="subsubdispo")
	 private String subsubdispo;
	
	
	//No Checking
}
