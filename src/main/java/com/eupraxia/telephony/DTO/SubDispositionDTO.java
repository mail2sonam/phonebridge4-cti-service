package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SubDispositionDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3128524190636463468L;
	private int id;
	private int dispositionId;
	private String subDispositionName;
	private String subDispositionType;
}
