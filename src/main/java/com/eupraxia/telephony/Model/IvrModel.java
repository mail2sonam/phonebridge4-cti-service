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
@Table(name = "ivr_capture")
public class IvrModel implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="start_time")
	private String startTime;
	
	@Column(name="end_time")
	private String endTime;
	
	@Column(name="ivr_flow")
	private String ivrFlow;
	
	@Column(name="unique_id")
	private String uniqueId;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="queue_vdn")
	private String queueVdn;
	
	@Column(name="rec_path")
	private String recPath;
	
	
	
	
	
	

}
