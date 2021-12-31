package com.eupraxia.telephony.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDispoDTO {
	private String callId;
	 private String dispo;
	   private String maindispo;
	   private String subdispo;  
	   private String subsubdispo;
	   private String callback;
	   private String remark;
	  
}
