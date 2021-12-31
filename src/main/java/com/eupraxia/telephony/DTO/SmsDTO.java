package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter 
@Setter
public class SmsDTO implements Serializable{
	private String id; 
	private String messageId;
	private Long number;
	private String message;
	private String date;
	private boolean isNew;
	private String status;
	private Date enteredDate;
	private String userExtension;
	private String caseId;
}
