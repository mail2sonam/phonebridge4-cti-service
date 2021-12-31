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
@Table(name = "yaml_data")
public class YamlDataModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long id;  
		
	@Column(name ="campaign")
	private String campaign; 
	
	@Column(name = "client")
	private String client;
	
	@Column(name = "main_dispo")
	private String mainDispo;
	
	@Column(name = "sub_dispo")
	private String subDispo;
	
	@Column(name = "sub_sub_dispo")
	private String subSubDispo;
	
	@Column(name = "remarks")
	private String remarks;
}
