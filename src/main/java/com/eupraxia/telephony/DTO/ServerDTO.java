package com.eupraxia.telephony.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerDTO {
	private Long id;
	private String hostName;
	private String hostIp;
	private String portNo;
	private String userName;
	private String password;
	private String amiUsername;
	private String amiPassword;
	private String amiPort;
	private String serverType;
	
}
