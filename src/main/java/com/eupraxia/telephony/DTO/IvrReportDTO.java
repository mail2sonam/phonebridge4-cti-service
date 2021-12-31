package com.eupraxia.telephony.DTO;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class IvrReportDTO {
	private String callDate;
	
	private String docId;
	
	private String phoneNo;

	private String IvrStartTime;
	
	private String IvrEndTime;
	
	private String ivrDuration;
	
	private String traverse;

}
