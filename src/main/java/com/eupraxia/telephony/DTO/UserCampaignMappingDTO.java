package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCampaignMappingDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3258170178819753930L;
	private Long id;
	private String campaignname;
	private Long campaignId;
	private long userid;
	private String[] username;
	
	//private String userextension;
}

