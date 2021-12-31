package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "disposition_report")
public class DispositionReportModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="case_id")
	private String caseid;
	
	@Column(name="call_type")
	private String callType;
	
	@Column(name="incidentdate")
	private String incidentdate;
	
	@Column(name="incidenttime")
	private String incidentTime;
	
	@Column(name="informationtype")
	private String informationType;
	
	@Column(name="caller_name")
	private String callerName;
	
	@Column(name="age")
	private String age;
	
	@Column(name="education")
	private String education;
	
	@Column(name="district")
	private String district;
	
	@Column(name="information_sought")
	private String informationSought;
	
	@Column(name="call_status")
	private String callStatus;
	
	@Column(name="service_offered")
	private String serviceOffered;
	
	@Column(name="person_name")
	private String personName;
	
	@Column(name="contact_number")
	private String contactNumber;
	
	@Column(name="agency")
	private String agency;
	
	@Column(name="name")
	private String name;
	
	@Column(name="aggreviedornot")
	private String aggreviedornot;
	
	@Column(name="person_age")
	private String personAge;
	
	@Column(name="person_education")
	private String personEducation;
	
	@Column(name="occupation")
	private String occupation;
	
	@Column(name="personalidentity")
	private String personalidentity;
	
	@Column(name="marital_status")
	private String maritalStatus;
	
	@Column(name="living_status")
	private String livingStatus;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="family_status")
	private String familyStatus;
	
	@Column(name="prior_redressel")
	private String priorRedressel;
	
	@Column(name="addiction")
	private String addiction;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(name="incident_status")
	private String incidentStatus;
	
	@Column(name="incident_place")
	private String incidentPlace;
	
	@Column(name="house_no")
	private String houseNo;
	
	@Column(name="street_no")
	private String streetNo;
	
	@Column(name="mandal_no")
	private String MandalNo;
	
	@Column(name="city_no")
	private String cityNo;
	
	@Column(name="state_no")
	private String stateNo;
	
	@Column(name="district_no")
	private String districtNo;
	
	@Column(name="pincode_no")
	private String pincodeNo;
	
	@Column(name="perpetrator_type")
	private String perpetratorType;
	
	@Column(name="perpetrator_name")
	private String perpetratorName;
	
	@Column(name="perpetrator_mobile")
	private String perpetratorMobile;
	
	@Column(name="perpetrator_address")
	private String perpetratorAddress;
	
	@Column(name="perpetrator_gender")
	private String perpetratorGender;
	
	@Column(name="perpetrator_profession")
	private String perpetratorProfession;
	
	@Column(name="perpetrator_pincode")
	private String perpetratorPincode;
	
	@Column(name="casecategory_one")
	private String casecategoryOne;
	
	@Column(name="subcasecategory_one")
	private String subcasecategoryOne;
	
	@Column(name="casecategory_two")
	private String casecategoryTwo;
	
	@Column(name="subcasecategory_two")
	private String subcasecategoryTwo;
	
	@Column(name="abuse_one")
	private String abuseOne;
	
	@Column(name="abuse_two")
	private String abuseTwo;
	
	@Column(name="remarks")
	private String remarks;

	@Column(name="call_id")
	private String callId;
	
	@Column(name="call_Date")
	private Date callDate;
	
	
	
	
	
}
