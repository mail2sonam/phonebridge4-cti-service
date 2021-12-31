package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name = "eupraxia_cdr")
public class CdrReportModel implements Serializable{
	
		private static final long serialVersionUID = 4996213363169999448L;
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
		@GenericGenerator(name="native", strategy="native")
		private int id;
		
		@Column(name="call_date")
		private LocalDateTime callDate;
		
		
		@Column(name="doc_Id")
		private String docId;   
		
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
			private String callStartTime;
		
		@Column(name="call_End_Time")
			private String callEndTime;
		
		@Column(name="Call_Connect_Time")
			private String CallConnectTime;
		
		@Column(name="trunk_Channel")
			private String trunkChannel;
		
		@Column(name="sip_Channel")
			private String sipChannel;
		
		@Column(name="second_Channel")
			private String secondChannel;
		
		@Column(name="call_direction")
			private String callDirection;
		
		@Column(name="second_Number")
			private String secondNumber;
		
		/*
		 * @Column(name="agent_Id") private List<String> channels;
		 */
		
		@Column(name="Hold_Start_Time")
			private String HoldStartTime;
		
		@Column(name="Hold_End_Time")
			private String HoldEndTime;
		
		
		
		@Column(name="disposition")
			private String disposition;
		
		@Column(name="comments")
			private String comments;
		
		@Column(name="callback_Date")
			private String callbackDate;
		
		@Column(name="Queue_Start_Time")
		private String QueueStartTime;
	
		@Column(name="Queue_End_Time")
		private String QueueEndTime;
		
		@Column(name="Queue_Duration")
		private String queueDuration;
		
		@Column(name="Ivr_Start_Time")
		private String IvrStartTime;
		
		@Column(name="Ivr_End_Time")
		private String IvrEndTime;
		
		@Column(name="Ivr_Duration")
		private String ivrDuration;
		
		
		@Column(name="Hold_Duration")
		private String holdDuration;
		
		@Column(name="Agent_End_Time")
		private String agentEndTime;
		
		@Column(name="Agent_Start_Time")
		private String agentStartTime;
		
		@Column(name="Agent_Duration")
		private String agentDuration;
		
		
		@Column(name="duration")
		private String duration;
		
		@Column(name="traverse")
		private String traverse;
		
		@Column(name="queue_vdn")
		private String queueVdn;
		
		@Column(name="rec_path")
		private String recPath;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	

}
