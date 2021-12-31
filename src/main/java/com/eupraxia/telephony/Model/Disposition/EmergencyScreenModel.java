package com.eupraxia.telephony.Model.Disposition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "emergency_case_details")
public class EmergencyScreenModel implements Serializable{
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="case_id")
	private String caseId;
	
	@Column(name="call_id")
	private String callId;
	
	@Column(name="es_information_sought")
	private String esIinformationSought;
	
	@Column(name="es_risk_asses")
	private String esRiskAsses;
	
	@Column(name="es_aggrieved")
	private String esAggrieved;
	
	@Column(name="es_other_agg_name")
	private String esOtherAggName;
	
	@Column(name="es_other_agg_mobile")
	private String esOtherAggMobile;
	
	@Column(name="es_other_agg_gender")
	private String esOtherAggGender;
	
	@Column(name="es_other_agg_age")
	private String esOtherAggAge;
	
	@Column(name="es_other_agg_address")
	private String esOtherAggAddress;
	
	@Column(name="es_age")
	private String esAge;
	
	@Column(name="es_age_group")
	private String esAgeGroup;
	
	@Column(name="es_education")
	private String esEducation;
	
	@Column(name="es_occupation")
	private String esOccupation;
	
	@Column(name="es_gender")
	private String esGender;
	
	@Column(name="es_person_alident")
	private String esPersonalIdent;
	
	@Column(name="es_marital_status")
	private String esMaritalStatus;
	
	@Column(name="es_living_status")
	private String esLivingStatus;
	
	@Column(name="es_family_status")
	private String esFamilyStatus;
	
	@Column(name="es_houseno")
	private String esHouseno;
	
	@Column(name="es_street")
	private String esStreet;
	
	@Column(name="es_block")
	private String esBlock;
	
	@Column(name="es_village")
	private String esVillage;
	
	@Column(name="es_state")
	private String esState;
	
	@Column(name="es_district")
	private String esDistrict;	
	
	@Column(name="es_pincode")
	private String esPincode;
	
	@Column(name="es_placeof_inc")
	private String esPlaceofInc;
	
	@Column(name="es_frequency")
	private String esFrequency;
	
	@Column(name="es_statusof_inc")
	private String esStatusofInc;
	
	@Column(name="es_casecat1")
	private String esCaseCat1;
	
	@Column(name="es_subcat")
	private String esSubCat;
	
	@Column(name="es_typeof_abuse")
	private String esTypeofAbuse;
	
	@Column(name="es_priorred_ressal")
	private String esPriorRedressal;
	
	@Column(name="es_perpetrator")
	private String esPerpetrator;
	
	@Column(name="es_service_offered")
	private String esServiceOffered;
	
	@Column(name="es_addobtain")
	private String esAddObtain;
	
	@Column(name="es_statusof_case")
	private String esStatusofCase;
	
	@Column(name="es_agency")
	private String esAgency;
	
	@Column(name="es_nameof_Person")
	private String esNameofPerson;
	
	@Lob
	@Column(name="es_remarks")
	private String esRemarks;
	
	@Column(name="es_perpetrator_name")
	private String esPerpetratorName;
	
	@Column(name="es_perpetrator_age")
	private String esPerpetratorAge;
	
	@Column(name="es_perpetrator_gender")
	private String esPerpetratorGender;
	
	@Column(name="es_perpetrator_mobile")
	private String esPerpetratorMobile;
	
	@Column(name="es_perpetrator_occup")
	private String esPerpetratorOccup;
	
	@Column(name="es_perpetrator_addition")
	private String esPerpetratorAddition;
	
	@Column(name="es_other_aggagegroup")
	private String esOtherAggAgeGroup;
	
	@Column(name="es_casecat2")
	private String esCaseCat2;
	
	@Column(name="es_subcat2")
	private String esSubCat2;
	
	@Column(name="es_typeof_abuse2")
	private String esTypeofAbuse2;	

	@Column(name="es_directory_main")
	private String esDirectoryMain;
	
	@Column(name="es_directory_submain")
	private String esDirectorySubMain;
	
	@Column(name="es_directory_district")
	private String esDirectoryDistrict;
	
	@Column(name="es_directory_town")
	private String esDirectoryTown;
	
	@Column(name="es_directory_contname")
	private String esDirectoryContName;
	
	@Column(name="es_directory_contnumber")
	private String esDirectoryContNumber;
	
	@Column(name="es_directory_address")
	private String esDirectoryAddress;
	
	@Column(name="es_directory_pincode")
	private String esDirectoryPincode;
	
	@Column(name="es_directory_mailid")
	private String esDirectoryMailId;
	
	@Column(name="es_directory_smsnumber")
	private String esDirectorySMSNumber;
	
	@Column(name="es_directory_wanumber")
	private String esDirectoryWAnumber;
	
	@Column(name="es_shift_timing")
	private String esFollowSession;
	
	@Column(name="es_followup_date ")
	private String esFollowDate;
}
