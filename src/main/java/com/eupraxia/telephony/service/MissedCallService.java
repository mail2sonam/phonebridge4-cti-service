package com.eupraxia.telephony.service;

import java.util.List;

import com.eupraxia.telephony.Model.MissedCallModel;
import com.eupraxia.telephony.Model.ScheduleCallModel;

public interface MissedCallService {

	void insertMissedCallRequest(MissedCallModel missedCallModel);

	MissedCallModel findByPhoneNumberAndStatus(String phoneNumber, String status);

	MissedCallModel getMissedCallData();

	List<MissedCallModel> getMissedCallDataInLIst();

	MissedCallModel findById(int id);
	
	List<MissedCallModel> findByInsertTimeBetween(String dayBegin, String dayEnd);
	
	MissedCallModel findByMissedName(String campaignname);

	List<MissedCallModel> findByAllMissedName(String missedCampaignName);

	List<MissedCallModel> findByMissedNameAndStatus(String missedCampaignName, String string);
	
}
