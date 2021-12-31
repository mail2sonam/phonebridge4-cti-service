package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowupDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String callId;
	private String statusofCase;
	private String caseId;
	private String remarks;
	private String shiftTiming;
	private String followupDate;

}