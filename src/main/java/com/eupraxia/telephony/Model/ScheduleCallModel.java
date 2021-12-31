package com.eupraxia.telephony.Model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "eupraxia_schedulecall")
public class ScheduleCallModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="phone_No")
	private String phoneNumber;
	
	@Column(name="call_status")
	private String callStatus;
	
	@Column(name="cam_name")
	private String campaignName;
	
	@CreatedDate
    @Field("insert_time")
    @JsonIgnore
    private Instant insertTime = Instant.now();
	
	@Column(name="call_back_time")
	private Date callBackTime;
	
	@Column(name="extension")
	private String extension;
}
