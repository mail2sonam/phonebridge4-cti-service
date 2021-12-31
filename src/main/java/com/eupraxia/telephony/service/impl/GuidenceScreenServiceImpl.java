package com.eupraxia.telephony.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;
import com.eupraxia.telephony.Model.Disposition.GuidenceScreenModel;
import com.eupraxia.telephony.repositories.CaseRepository;
import com.eupraxia.telephony.repositories.GuidenceScreenRepository;
import com.eupraxia.telephony.service.GuidenceScreenService;

@Service
public class GuidenceScreenServiceImpl implements GuidenceScreenService{
	@Autowired
	private GuidenceScreenRepository guidenceScreenRepository;

	@Override
	public void saveOrUpdate(GuidenceScreenModel guidenceScreenModel1) {
		guidenceScreenRepository.save(guidenceScreenModel1);
		
	}

	@Override
	public GuidenceScreenModel findByCallId(String callId) {
		// TODO Auto-generated method stub
		return guidenceScreenRepository.findByCallId(callId);
	}
	
	@Override
	public GuidenceScreenModel findByCaseId(String caseId) {
		// TODO Auto-generated method stub
		return guidenceScreenRepository.findByCaseId(caseId);
	}
	
	@Override
	public List<GuidenceScreenModel> findAllGuidenceByCallIds(List<String> callIds) {
		// TODO Auto-generated method stub
		return guidenceScreenRepository.findAllGuidenceByCallIds(callIds);
	}
}
