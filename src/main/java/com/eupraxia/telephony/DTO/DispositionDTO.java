package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DispositionDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1281535867902358799L;
	private int id;
	private int parentId;
	private String disposition;
	private String callId;
	private String comments;
	private String callbackDate;

}
