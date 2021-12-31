package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import org.asteriskjava.manager.ManagerConnection;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class IncomingCallDTO implements Serializable {

	private static final long serialVersionUID = 9115893963340112067L;
	
	private String message;
	private String phoneNo;

	
	
}
