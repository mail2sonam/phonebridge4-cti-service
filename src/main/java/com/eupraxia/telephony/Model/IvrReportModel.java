package com.eupraxia.telephony.Model;

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
@Entity
@Table(name = "ivr_report")
public class IvrReportModel implements Serializable {
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

	@Column(name="Ivr_Start_Time")
	private String IvrStartTime;
	
	@Column(name="Ivr_End_Time")
	private String IvrEndTime;
	
	@Column(name="Ivr_Duration")
	private String ivrDuration;
	
	@Column(name="traverse")
	private String traverse;

}
