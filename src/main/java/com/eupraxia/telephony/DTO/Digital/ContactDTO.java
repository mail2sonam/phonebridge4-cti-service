package com.eupraxia.telephony.DTO.Digital;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDTO {
	
	private int contactId;	
	private String address;	
	private String contactNumber;
	private String contactName;
	private String emailId;
	private String smsNumber;
	private String whatsupNumber;
	private int townId;	
	private int districtId;	
	private int categoryId;	
	private int subCategoryId;
	private String townName;
	private String subCategoryName;
	
}
