package com.eupraxia.telephony.Model;

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
@Table(name = "user_campaign_mapping")
public class UserCampaignMappingModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long id;
	
	@Column(name = "campaign_name")
	private String campaignname;
	
	
	@Column(name = "user_id")
	private long userid;
	
	@Column(name = "user_name")
	private String username;
	
	@Column(name = "extension")
	private String userextension;
	
	@Column(name="queue")
	private String queue;
	
	@Column(name="direction")
	private String direction;
	
	@Column(name="dial_method")
	private String dialMethod;

}
