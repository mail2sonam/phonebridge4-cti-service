package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuidenceScreenDTO implements Serializable {
    private int id;
	
	private String callId;
	

	private String gsInformationSought;

	private String gsRiskAsses;
	
	private String gsAggrieved;
	
	private String caseId;
	
	private String gsOtherAggName;
	
	private String gsOtherAggMobile;
	
	private String gsOtherAggAge;
	
	private String gsOtherAggGender;
	
	private String gsAge;
	
	private String gsOtherAggAddress;
	
	private String gsAgeGroup;
	
	private String gsEducation;
	
	private String gsOccupation;
	
	private String gsGender;
	
	private String gsPersonalIdent;	
	
	private String gsMaritalStatus;	
	
	private String gsLivingStatus;	
	
	private String gsFamilyStatus;	
	
	private String gsHouseno;	
	
	private String gsStreet;	
	
	private String gsBock;	
	
	private String gsVillage;	
	
	private String gsState;
	
	private String gsDistrict;
	
	private String gsPincode;	
	
	private String gsPlaceofInc;
	
	private String gsFrequency;
	
	private String gsStatusofInc;
	
	private String gsCaseCat1;
	
	private String gsSubCat;
	
	private String gsTypeofAbuse;
	
	private String gsPriorRedressal;	
	
	private String gsPerpetrator;
	
	private String gsServiceOffered;
	
	private String gsAddObtain;
	
	private String gsStatusofCase;
	
	private String gsAgency;
	
	private String gsNameofPerson;
	
	private String gsRemarks;
	
	private String gsPerpetratorName;
	
	private String gsPerpetratorAge;
	
	private String gsPerpetratorGender;
	
	private String gsPerpetratorMobile;
	
	private String gsPerpetratorOccup;
	
	private String gsPerpetratorAddition;
	
	private String gsOtherAggAgeGroup;
	
	private String gsCaseCat2;
	
	private String gsSubCat2;
	
	private String gsTypeofAbuse2;
	
	private String gsDirectoryMain;
	
	private String gsDirectorySubMain;
	
	private String gsDirectoryDistrict;
	
	private String gsDirectoryTown;
	
	private String gsDirectoryContName;
	
	private String gsDirectoryContNumber;
	
	private String gsDirectoryAddress;
	
	private String gsDirectoryPincode;
	
	private String gsDirectoryMailId;
	
	private String gsDirectorySMSNumber;
	
	private String gsDirectoryWAnumber;
	
	private String gsFollowSession;
	
	private String gsFollowDate;
}
