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
@Table(name = "guidence_case_details")
public class GuidenceScreenModel implements Serializable{
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="call_id")
	private String callId;
	
	@Column(name="case_id")
	private String caseId;

	@Column(name="gs_information_sought")
	private String gsInformationSought;

	@Column(name="gs_risk_asses")
	private String gsRiskAsses;
	
	@Column(name="gs_aggrieved")
	private String gsAggrieved;
	
	@Column(name="gs_other_agg_name")
	private String gsOtherAggName;
	
	@Column(name="gs_other_agg_mobile")
	private String gsOtherAggMobile;
	
	@Column(name="gs_other_agg_age")
	private String gsOtherAggAge;
	
	@Column(name="gs_other_agg_gender")
	private String gsOtherAggGender;
	
	@Column(name="gs_age")
	private String gsAge;
	
	@Column(name="gs_other_agg_address")
	private String gsOtherAggAddress;
	
	@Column(name="gs_age_group")
	private String gsAgeGroup;
	
	@Column(name="gs_education")
	private String gsEducation;
	
	@Column(name="gs_occupation")
	private String gsOccupation;
	
	@Column(name="gs_gender")
	private String gsGender;
	
	@Column(name="gs_personal_ident")
	private String gsPersonalIdent;
	
	
	@Column(name="gs_marital_status")
	private String gsMaritalStatus;
	
	
	@Column(name="gs_living_status")
	private String gsLivingStatus;
	
	
	@Column(name="gs_family_status")
	private String gsFamilyStatus;
	
	
	@Column(name="gs_houseno")
	private String gsHouseno;
	
	
	@Column(name="gs_street")
	private String gsStreet;
	
	
	@Column(name="gs_block")
	private String gsBock;
	
	
	@Column(name="gs_village")
	private String gsVillage;
	
	
	@Column(name="gs_state")
	private String gsState;
	
	@Column(name="gs_district")
	private String gsDistrict;
	
	@Column(name="gs_pincode")
	private String gsPincode;
	
	
	@Column(name="gs_placeof_inc")
	private String gsPlaceofInc;
	
	@Column(name="gs_frequency")
	private String gsFrequency;
	
	@Column(name="gs_statusof_inc")
	private String gsStatusofInc;
	
	@Column(name="gs_case_cat1")
	private String gsCaseCat1;
	
	@Column(name="gs_sub_cat")
	private String gsSubCat;
	
	@Column(name="gs_typeof_abuse")
	private String gsTypeofAbuse;
	
	@Column(name="gs_prior_redressal")
	private String gsPriorRedressal;
	
	
	@Column(name="gs_perpetrator")
	private String gsPerpetrator;
	
	@Column(name="gs_service_offered")
	private String gsServiceOffered;
	
	@Column(name="gs_add_obtain")
	private String gsAddObtain;
	
	@Column(name="gs_statusof_case")
	private String gsStatusofCase;
	
	@Column(name="gs_agency")
	private String gsAgency;
	
	@Column(name="gs_nameof_person")
	private String gsNameofPerson;
	
	@Lob
	@Column(name="gs_remarks")
	private String gsRemarks;
	
	@Column(name="gs_perpetrator_name")
	private String gsPerpetratorName;
	
	@Column(name="gs_perpetrator_age")
	private String gsPerpetratorAge;
	
	@Column(name="gs_perpetrator_gender")
	private String gsPerpetratorGender;
	
	@Column(name="gs_perpetrator_mobile")
	private String gsPerpetratorMobile;
	
	@Column(name="gs_perpetrator_occup")
	private String gsPerpetratorOccup;
	
	@Column(name="gs_perpetrator_addition")
	private String gsPerpetratorAddition;
	
	@Column(name="gs_other_aggagegroup")
	private String gsOtherAggAgeGroup;
	
	@Column(name="gs_casecat2")
	private String gsCaseCat2;
	
	@Column(name="gs_subcat2")
	private String gsSubCat2;
	
	@Column(name="gs_typeof_abuse2")
	private String gsTypeofAbuse2;
	
	@Column(name="gs_directory_main")
	private String gsDirectoryMain;
	
	@Column(name="gs_directory_submain")
	private String gsDirectorySubMain;
	
	@Column(name="gs_directory_district")
	private String gsDirectoryDistrict;
	
	@Column(name="gs_directory_town")
	private String gsDirectoryTown;
	
	@Column(name="gs_directory_contname")
	private String gsDirectoryContName;
	
	@Column(name="gs_directory_contnumber")
	private String gsDirectoryContNumber;
	
	@Column(name="gs_directory_address")
	private String gsDirectoryAddress;
	
	@Column(name="gs_directory_pincode")
	private String gsDirectoryPincode;
	
	@Column(name="gs_directory_mailid")
	private String gsDirectoryMailId;
	
	@Column(name="gs_directory_smsnumber")
	private String gsDirectorySMSNumber;
	
	@Column(name="gs_directory_wanumber")
	private String gsDirectoryWAnumber;
	
	@Column(name="gs_shift_timing")
	private String gsFollowSession;
	
	@Column(name="gs_followup_date ")
	private String gsFollowDate;
	
	
}
