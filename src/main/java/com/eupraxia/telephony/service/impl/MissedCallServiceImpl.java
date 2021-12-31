package com.eupraxia.telephony.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.CdrReportModel;
import com.eupraxia.telephony.Model.MissedCallModel;
import com.eupraxia.telephony.Model.ScheduleCallModel;
import com.eupraxia.telephony.repositories.CallPropertiesRepository;
import com.eupraxia.telephony.repositories.MissedCallRepository;
import com.eupraxia.telephony.service.MissedCallService;

@Service
public class MissedCallServiceImpl implements MissedCallService{

	@Autowired
	MissedCallRepository missedCallRepository;
	
	@Override
	public void insertMissedCallRequest(MissedCallModel missedCallModel) {
		
		missedCallRepository.save(missedCallModel);
	}

	@Override
	public MissedCallModel findByPhoneNumberAndStatus(String phoneNumber, String status) {
	return	missedCallRepository.findByPhoneNumberAndStatus(phoneNumber,status);
	}

	@Override
	public MissedCallModel getMissedCallData() {
		return	missedCallRepository.findFirstByCallStatusOrderByInsertTimeDesc("open");
	}
	

	@Override
	public List<MissedCallModel> getMissedCallDataInLIst() {
		// TODO Auto-generated method stub
		return	missedCallRepository.findAllByCallStatusOrderByInsertTimeDesc("open");
	}

	@Override
	public MissedCallModel findById(int id) {
		// TODO Auto-generated method stub
		return missedCallRepository.findById(id);
	}

	public List<MissedCallModel> findByInsertTimeBetween(String dayBegin, String dayEnd) {		
		return missedCallRepository.findByInsertTimeBetween(dayBegin, dayEnd);
	}

	@Override
	public MissedCallModel findByMissedName(String campaignname) {
		// TODO Auto-generated method stub
		return	missedCallRepository.findFirstByCallStatusAndMissedNameOrderByInsertTimeDesc("open",campaignname);
	}

	@Override
	public List<MissedCallModel> findByAllMissedName(String missedCampaignName) {
		// TODO Auto-generated method stub
		return missedCallRepository.findAllByMissedNameOrderByInsertTimeDesc(missedCampaignName);
	}

	@Override
	public List<MissedCallModel> findByMissedNameAndStatus(String missedCampaignName, String string) {
		// TODO Auto-generated method stub
		return missedCallRepository.findByMissedNameAndCallStatus(missedCampaignName,string);
	}
}
