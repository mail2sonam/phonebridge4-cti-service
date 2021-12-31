package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class HistoryReport implements Serializable {
private String caseId;
private String name;
private String age;
private String phoneNo;
private String incidentDate;
private String abuseType;
private String aggrieved;
}
