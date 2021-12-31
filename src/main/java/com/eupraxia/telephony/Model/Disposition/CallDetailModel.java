package com.eupraxia.telephony.Model.Disposition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "call_case_details")
public class CallDetailModel implements Serializable{
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="call_id")
	private String callId;
	
	@Column(name="call_type")
	private String callType;
	
	@Column(name="incident_date")
	private String incidentdate;
	
	@Column(name="incident_time")
	private String incidentTime;
	
	@Column(name="case_id")
	private String caseId;
	
	@Lob
	@Column(name="call_type_remarks")
	private String callTypeRemarks;
	
	
}
