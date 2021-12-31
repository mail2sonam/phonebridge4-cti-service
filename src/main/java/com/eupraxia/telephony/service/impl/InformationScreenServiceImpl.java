package com.eupraxia.telephony.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Disposition.GuidenceScreenModel;
import com.eupraxia.telephony.Model.Disposition.InformationScreenModel;
import com.eupraxia.telephony.repositories.CaseRepository;
import com.eupraxia.telephony.repositories.InformationScreenRepository;
import com.eupraxia.telephony.service.InformationScreenService;

@Service
public class InformationScreenServiceImpl  implements InformationScreenService{
	@Autowired
	private InformationScreenRepository informationScreenRepository;

	@Override
	public void saveOrUpdate(InformationScreenModel informationScreenModel1) {
		// TODO Auto-generated method stub
		informationScreenRepository.save(informationScreenModel1);
	}

	@Override
	public InformationScreenModel findByCallId(String callId) {
		// TODO Auto-generated method stub
		return informationScreenRepository.findByCallId(callId);
	}
	
	@Override
	public InformationScreenModel findByCaseId(String caseId) {
		// TODO Auto-generated method stub
		return informationScreenRepository.findByCaseId(caseId);
	}
	
	@Override
	public List<InformationScreenModel> findAllInformationByCallIds(List<String> callIds) {
		// TODO Auto-generated method stub
		return informationScreenRepository.findAllInformationByCallIds(callIds);
	}
}
