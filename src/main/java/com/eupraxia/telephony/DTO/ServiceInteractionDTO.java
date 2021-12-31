package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ServiceInteractionDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String feedback;
	private String goodFeedback;
	private String feedbackper;
	private String phoneNumber;
	private String month;
	private String totalcallsAcrossLanguages;
	private String callsAnsweredAcrossLanguages;
	private String callsAnsweredWithinThreshold;
	private String answeredPercentage;
	private String callsAbandonedAcrossLanguages;
	private String callsAbandonedWithinThreshold;
	private String abandonedPercentage;
	private String totalInteractions;
	private String totalInteractionsInCRM;
	private String totalintper;
	private String totalCalls;
	private String callsAnsWithin10Sec;
	private String callsAnsWithin20Sec;
	private String abandon;
	private String notAnsWithIn10Sec;
	private String notAnsWithIn20Sec;
	private String missed;
	private String servPercentage;
	private String abnPercentage;
	
	
	
	
	
}
