package com.eupraxia.telephony.DTO;

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

public class LoginDTO implements Serializable{	
	private int id;	
	private String agentName;	
	private String extension;	
	private String startTime;	
	private String statusTime;	
	private String loginStatus;	
	private String endTime;	
	private String status;	
	private String reason;	
	private String direction;	
	private String duration;
	
	
	
	
	
	
	
	
	
}
