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
@Table(name = "extension_data")
public class ExtensionDataModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long Id;  
	
	@Column(name = "extension")
	private String extension;
	
	@Column(name = "peer_status")
	private String peerStatus;
	
	@Column(name = "address")
	private String address;
	
	
}
