package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallDetailDTO implements Serializable {
private int id;
	
	private String callId;
	
	private String callType;
	
	private String incidentdate;
	
	private String incidentTime;
	
	private String caseId;
	
	private String callTypeRemarks;
	
	private String smsOption;
	
	
}
