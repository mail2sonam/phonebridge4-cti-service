package com.eupraxia.telephony.DTO;

import com.eupraxia.telephony.Model.CallPropertiesModel;

import lombok.Data;
@Data
public class PostDataDTO {
	private int payLoadId;
	private String source;
	private String destSubscriberId;
	private String topicName;
	private String accountId;
	private CallPropertiesModel message;
	private String msgPostedOn;
	
	
	
}
