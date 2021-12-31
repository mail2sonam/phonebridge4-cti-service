package com.eupraxia.telephony.Model.Reports;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name = "war_room_report")
public class WarRoomModel implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4996213363169999448L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;

	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="caller_id")
	private String callerId;
	
	@Column(name="type_of_query")
	private String typeOfQuery;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="call_time")
	private LocalDateTime callTime;
	
	//No Checking
}
