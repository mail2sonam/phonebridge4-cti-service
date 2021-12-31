package com.eupraxia.telephony.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiallerDTO {
	private String extension;
	private int campaignId;
	private String campaignName;
	private String prefix;
	private String context;
	private String channel;
	private int priority;
	
	private String missedCampaignName;
}
