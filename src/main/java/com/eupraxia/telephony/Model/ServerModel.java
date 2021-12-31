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
@Table(name = "server_details")
public class ServerModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long id;  
	
	@Column(name ="host_name")
	private String hostName;
	
	@Column(name ="host_ip")
	private String hostIp;
	
	@Column(name ="port_no")
	private String portNo;
	
	
	@Column(name ="user_name")
	private String userName;
	
	@Column(name ="password")
	private String password;
	
	@Column(name ="ami_username")
	private String amiUsername;
	

	@Column(name ="ami_password")
	private String amiPassword;
	
	@Column(name ="ami_port")
	private String amiPort;
	
	@Column(name="server_type")
	private String serverType;
	
	

}
