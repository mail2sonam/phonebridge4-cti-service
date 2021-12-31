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
@Table(name = "contact_details")
public class ContactModel implements Serializable{
	@Column(name="contact_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int contactId;
	
	@Column(name="address")
	private String address;
	
	@Column(name="contact_number")
	private String contactNumber;
	
	@Column(name="contact_name")
	private String contactName;
	
	@Column(name="email_id")
	private String emailId;
	
	@Column(name="sms_number")
	private String smsNumber;
	
	@Column(name="whatsup_number")
	private String whatsupNumber;
	
	@Column(name="town_id")
	private int townId;
	
	@Column(name="district_id")
	private int districtId;
	
	@Column(name="category_id")
	private int categoryId;
	
	@Column(name="sub_category_id")
	private int subCategoryId;
	
	
	
	
	
	
	
}
