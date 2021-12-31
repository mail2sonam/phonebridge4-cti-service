package com.eupraxia.telephony.Model;

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

@Entity
@Table(name = "sakhi_ticket")
@Getter
@Setter
public class SakhiModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	@Column(name="caseid")
private String caseid;
	
	@Column(name="name")
private String name;
	
	@Column(name="phonenumber")
private String phonenumber;
	
	@Column(name="age")
private String age;

	@Column(name="education")
private String education;
	
	@Column(name="occupation")
private String occupation;
	
	@Column(name="aggreviedornot")
private String aggreviedornot;
	
	@Column(name="martialstatus")
private String martialstatus;
	
	@Column(name="address")
private String address;
	
	@Column(name="frequency")
private String frequency;
	
	@Column(name="typeofabuse")
private String typeofabuse;
	
	@Column(name="sakthiid")
private String sakthiid;
	
	@Column(name="personalidentity")
	private String personalidentity;
	
	@Column(name="incidentplace")
	private String incidentplace;
	
	@Column(name="generalisationcategory")
	private String generalisationcategory;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="perpetratordetails")
	private String perpetratordetails;
	
	@Column(name="incidentdate")
	private String incidentdate;
	
	@Column(name="incidenttime")
	private String incidentTime;
	
	@Column(name="extension")
	private String extension;
	
	@Column(name="call_id")
	private String callid;

}
