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
@Table(name = "information_case_details")
public class InformationScreenModel implements Serializable {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="call_id")
	private String callId;
	
	@Column(name="is_name")
	private String isName;
	
	@Column(name="is_age")
	private String isAge;
	
	@Column(name="is_age_group")
	private String isAgeGroup;
	
	@Column(name="is_education")
	private String isEducation;
	
	@Column(name="is_gender")
	private String isGender;
	
	@Column(name="is_houseno")
	private String isHouseno;
	
	@Column(name="is_street")
	private String isStreet;
	
	@Column(name="is_block")
	private String isBlock;
	
	@Column(name="is_village")
	private String isVillage;
	
	@Column(name="is_state")
	private String isState;
	
	@Column(name="is_district")
	private String isDistrict;
	
	@Column(name="is_pincode")
	private String isPincode;
	
	@Column(name="is_information_sought")
	private String isInformationSought;
	
	@Column(name="is_service_offered")
	private String isServiceoffered;
	
	@Column(name="is_add_obtain_info")
	private String isAddObtainInfo;
	
	@Column(name="is_agency")
	private String isAgency;
	
	@Column(name="is_nameof_person")
	private String isNameofPerson;
	
	@Column(name="is_contact_num")
	private String isContactNum;
	
	@Lob
	@Column(name="is_remarks")
	private String isRemarks;
	
	@Column(name="case_id")
	private String caseId;
	
	@Column(name="is_directory_main")
	private String isDirectoryMain;
	
	@Column(name="is_directory_submain")
	private String isDirectorySubMain;
	
	@Column(name="is_directory_district")
	private String isDirectoryDistrict;
	
	@Column(name="is_directory_town")
	private String isDirectoryTown;
	
	@Column(name="is_directory_contname")
	private String isDirectoryContName;
	
	@Column(name="is_directory_contnumber")
	private String isDirectoryContNumber;
	
	//@Column(name="is_DirectoryAddress")
	//private String isDirectoryAddress;
	
	@Column(name="is_directory_pincode")
	private String isDirectoryPincode;
	
	@Column(name="is_directory_mailid")
	private String isDirectoryMailId;
	
	@Column(name="is_directory_smsnumber")
	private String isDirectorySMSNumber;
	
	@Column(name="is_directory_wanumber")
	private String isDirectoryWAnumber;
	
	
	
	
}
