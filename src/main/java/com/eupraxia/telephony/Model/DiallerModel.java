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
@Table(name = "dialler")
public class DiallerModel implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="campaign_id")
	private int campaignId;
	
	@Column(name="campaign_name")
	private String campaignName;
	
	@Column(name="insert_date")
	private Date insertDate;
	
	@Column(name="call_date")
	private Date callDate;
	
	@Column(name="name")
	private String name;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="status")
	private String status;
	
	
	@Column(name="extension")
	private String extension;
	

}
