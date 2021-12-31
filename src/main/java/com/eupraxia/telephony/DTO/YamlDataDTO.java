package com.eupraxia.telephony.DTO;

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


@Getter
@Setter

public class YamlDataDTO {
	
	private Long id;  
	
	private String campaign; 
	
	private String client;
	
	private String mainDispo;
	
	private String subDispo;	
	
	private String subSubDispo;
	
	private String remarks;
}
