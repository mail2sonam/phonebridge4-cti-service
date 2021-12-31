package com.eupraxia.telephony.DTO;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CSECEDTO {
	private Long id;  	
	private String empId; 
	private String employeeName;	
	private String branch;	
	private String phoneNumber;
}