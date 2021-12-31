package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String userextension;
	private String extensionstatus;	
	private String onbreak;
	private String popupstatus;
	private String usertype;
	private String password;
	private String context;
	private String callstatus;
	private String branchid;
	private String deleted;
	private String prefix;
	private String extensiontype;
	private String email;
	private String popupstatusupdatetime;
	private String agenttype;
	private boolean ivruser;
	private String department_name;
	private String departmentcode;
	//private String teamLead;
	
	
}