package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CallInformationModel implements Serializable{ /**
	 * 
	 */
	private static final long serialVersionUID = -7608692226429048756L;
	

	private String CallStatus;
	private Date CallStartTime;
	private Date CallWrapupTime;
	private String ExtensionStatus;

}
