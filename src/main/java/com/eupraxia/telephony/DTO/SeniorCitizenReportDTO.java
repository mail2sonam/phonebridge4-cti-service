package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeniorCitizenReportDTO implements Serializable{
private String caseId;
private String callerName;
private String age;
private String callDate;
private String district;
private String phoneNo;
private String caseType;
private String actionTaken;

}
