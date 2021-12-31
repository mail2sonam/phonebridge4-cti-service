package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class ConnectionDTO implements Serializable {

	private static final long serialVersionUID = 5958277787984736233L;
	private String amiManagerIp;
	private String amiUserName;
	private String amiPassword;
	

}
