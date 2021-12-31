package com.eupraxia.telephony.Model;

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
@Table(name = "agent_login")
public class LoginModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="agent_name")
	private String agentName;
	
	@Column(name="extension")
	private String extension;
	
	@Column(name="start_time")
	private String startTime;
	
	@Column(name="status_time")
	private String statusTime;
	
	@Column(name="login_status")
	private String loginStatus;
	
	@Column(name="end_time")
	private String endTime;
	
	@Column(name="status")
	private String status;	
	
	@Column(name="reason")
	private String reason;
	
	
	@Column(name="direction")
	private String direction;
	
	@Column(name="duration")
	private String duration;
	
	
	
	
	
	
	
	
	
}
