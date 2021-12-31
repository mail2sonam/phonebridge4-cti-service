package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EmergencyScreenDTO implements Serializable {
private int id;
	private String feedback;
	private String callId;
	
	private String esIinformationSought;
	
	private String esRiskAsses;
	
	private String esAggrieved;
	
	private String caseId;
	
	private String esOtherAggName;
	
	private String esOtherAggMobile;
	
	private String esOtherAggGender;
	
	private String esOtherAggAge;
	
	private String esOtherAggAddress;
	
	private String esAge;
	
	private String esAgeGroup;
	
	private String esEducation;
	
	private String esOccupation;
	
	private String esGender;
	
	private String esPersonalIdent;
	
	private String esMaritalStatus;
	
	private String esLivingStatus;
	
	private String esFamilyStatus;
	
	private String esHouseno;
	
	private String esStreet;
	
	private String esBlock;
	
	private String esVillage;
	
	private String esState;
	
	private String esDistrict;	
	
	private String esPincode;
	
	private String esPlaceofInc;
	
	private String esFrequency;
	
	private String esStatusofInc;
	
	private String esCaseCat1;
	
	private String esSubCat;
	
	private String esTypeofAbuse;
	
	private String esPriorRedressal;
	
	private String esPerpetrator;
	
	private String esServiceOffered;
	
	private String esAddObtain;
	
	private String esStatusofCase;
	
	private String esAgency;
	
	private String esNameofPerson;
	
	private String esRemarks;
	
	private String esPerpetratorName;
	
	private String esPerpetratorAge;
	
	private String esPerpetratorGender;
	
	private String esPerpetratorMobile;
	
	private String esPerpetratorOccup;
	
	private String esPerpetratorAddition;

	private String esOtherAggAgeGroup;
	
	private String esCaseCat2;
	
	private String esSubCat2;
	
	private String esTypeofAbuse2;
	
	private String esDirectoryMain;
	
	private String esDirectorySubMain;
	
	private String esDirectoryDistrict;
	
	private String esDirectoryTown;
	
	private String esDirectoryContName;
	
	private String esDirectoryContNumber;
	
	private String esDirectoryAddress;
	
	private String esDirectoryPincode;
	
	private String esDirectoryMailId;
	
	private String esDirectorySMSNumber;
	
	private String esDirectoryWAnumber;
	
	private String esFollowSession;
	
	private String esFollowDate;
	
}
