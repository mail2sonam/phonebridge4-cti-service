package com.eupraxia.telephony.Model.Digital;

import java.io.Serializable;

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
@Table(name = "digital_directory")
public class DigitalDirectoryModel implements Serializable{
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int Id;
	
	@Column(name="main_category")
	private String maincategory;
	
	@Column(name="sub_category")
	private String subCategory;
	
	@Column(name="district")
	private String district;
	
	@Column(name="town")
	private String town;
	
	@Column(name="address")
	private String address;
	
	@Column(name="pincode")
	private String pincode;
	
	@Column(name="contact_name")
	private String contactName;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="mail_id")
	private String mailId;
	
	@Column(name="remarks")
	private String remarks;
}
