package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InformationScreenDTO implements Serializable{

	private int id;
	
	private String callId;
	
	private String isName;
	
	private String isAge;	
	
	private String caseId;	
	
	private String isAgeGroup;
	
	private String isEducation;
	
	private String isGender;
	
	private String isHouseno;
	
	private String isStreet;
	
	private String isBlock;
	
	private String isVillage;
	
	private String isState;
	
	private String isDistrict;
	
	private String isPincode;
	
	private String isInformationSought;
	
	private String isServiceoffered;
	
	private String isAddObtainInfo;
	
	private String isAgency;
	
	private String isNameofPerson;
	
	private String isContactNum;	
	
	private String isRemarks;
	
	private String isDirectoryMain;
	
	private String isDirectorySubMain;
	
	private String isDirectoryDistrict;
	
	private String isDirectoryTown;
	
	private String isDirectoryContName;
	
	private String isDirectoryContNumber;
	
	private String isDirectoryAddress;
	
	private String isDirectoryPincode;
	
	private String isDirectoryMailId;
	
	private String isDirectorySMSNumber;
	
	private String isDirectoryWAnumber;
}
