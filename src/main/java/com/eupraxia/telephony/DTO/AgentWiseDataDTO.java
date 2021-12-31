package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AgentWiseDataDTO implements Serializable{
private String caseId;
private String submitOn;
private String identication;
private String caseType;
private String mobileNumber;
private String district;
private String caseStatus;
private String referralService;

private String extension;
}
