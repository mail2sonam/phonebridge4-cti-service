package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CallLogsDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5264771695933374877L;
	private String phoneNumber;
	private int agentId;
	private int campaignId;
	private String comments;
	private int dispositionId;
	private int subdispositionId;
	private Date callDate;
	private String extension;
	private String customerName;
}
