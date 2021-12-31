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
@Table(name = "case_report")
public class CaseDispositionModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="call_id")
	private String callId;
	
	@Column(name="call_type")
	private String callType;
	
	@Column(name="incident_date")
	private String incidentdate;
	
	@Column(name="incident_time")
	private String incidentTime;
	
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
	
	@Column(name="is_remarks")
	private String isRemarks;
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
}
