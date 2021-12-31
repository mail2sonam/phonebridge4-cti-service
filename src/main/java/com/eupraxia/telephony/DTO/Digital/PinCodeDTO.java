package com.eupraxia.telephony.DTO.Digital;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinCodeDTO {
	
	private Long id;
	private String pinCode;	
	private String taluk;
	private String town;	
	private String district;
	private String state;
	
	
}
