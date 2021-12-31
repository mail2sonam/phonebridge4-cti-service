package com.eupraxia.telephony.Model.Reports;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "yaml_data_report")
public class YamlDataReportModel implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4996213363169999448L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;

	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="call_id")
	private String callId;	
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="call_time")
	private String callTime;
	
	@Column(name ="campaign")
	private String campaign; 
	
	@Column(name = "client")
	private String client;
	
	@Column(name = "main_dispo")
	private String mainDispo;
	
	@Column(name = "sub_dispo")
	private String subDispo;
	
	@Column(name = "sub_sub_dispo")
	private String subSubDispo;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	

}
