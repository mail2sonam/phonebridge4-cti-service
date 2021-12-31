package com.eupraxia.telephony.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;
import com.eupraxia.telephony.repositories.CaseRepository;
import com.eupraxia.telephony.repositories.EmergencyScreenRepository;
import com.eupraxia.telephony.service.EmergencyScreenService;

@Service
public class EmergencyScreenServiceImpl implements EmergencyScreenService{
	@Autowired
	private EmergencyScreenRepository emergencyScreenRepository;

	@Override
	public EmergencyScreenModel findByCallId(String callId) {
		// TODO Auto-generated method stub
		return emergencyScreenRepository.findByCallId(callId);
	}

	@Override
	public EmergencyScreenModel findByCaseId(String caseId) {
		// TODO Auto-generated method stub
		return emergencyScreenRepository.findByCaseId(caseId);
	}

	@Override
	public void saveOrUpdate(EmergencyScreenModel emergencyScreenModel) {
		// TODO Auto-generated method stub
		emergencyScreenRepository.save(emergencyScreenModel);
	}
	
	@Override
	public List<EmergencyScreenModel> findAllEmergencyByCallIds(List<String> callIds) {
		// TODO Auto-generated method stub
		return emergencyScreenRepository.findAllEmergencyByCallIds(callIds);
	}
}
