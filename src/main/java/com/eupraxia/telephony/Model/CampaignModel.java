package com.eupraxia.telephony.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.annotation.Cacheable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Cacheable
@Getter
@Setter
@Table(name = "campaign_details")
public class CampaignModel {
	@Id
	@Column(name = "campaign_id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long campaignId;  
	
	@Column(name = "campaign_name")
	private String campaignName;
	
	@Column(name = "dial_method")
	private String dialMethod;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "wrapup_time")
	private String wrapUpTime;
	
	@Column(name = "is_default")
	private boolean isDefault;
	
	@Column(name = "campaign_created_on")
	private Date campaignCreatedOn;
	
	@Column(name = "did_number")
	private String didNumber;
	
	@Column(name = "campaign_Source")
	private String campaign_Source;
	
	@Column(name = "call_direction")
	private String callDirection;
	
	@Column(name = "resource_url")
	private String resourceURL;
	
	@Column(name = "queue_name")
	private String queueName;
	
	@Column(name = "trunk")
	private String trunk;
	
	@Column(name = "being_called")
	private long beingcalled;
	
	@Column(name = "department_code")
	private String departmentcode;
	
	
}
