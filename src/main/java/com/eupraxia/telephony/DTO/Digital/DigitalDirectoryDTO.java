package com.eupraxia.telephony.DTO.Digital;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DigitalDirectoryDTO {
	
	private int Id;
	private String maincategory;
	private String subCategory;
	private String district;
	private String town;
	private String address;
	private String pincode;
	private String contactName;
	private String contactNo;
	private String mailId;
	private String remarks;
}
