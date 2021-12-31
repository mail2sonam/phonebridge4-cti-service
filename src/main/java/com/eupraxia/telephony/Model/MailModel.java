package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "handled_mails_details")
public class MailModel implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	
	@Column(name="from_mail")
	private String from;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="sent_date")
	private String sentDate;
	
	@Column(name="body")
	private String body;
	
	@Column(name="status")
	private String status;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="enter_date")
	private Date enteredDate;
	
	@Column(name="last_update_date")
	private Date lastUpdateDate;
	
	@Column(name="caseId")
	private String caseId;
	
	
	
	
}
