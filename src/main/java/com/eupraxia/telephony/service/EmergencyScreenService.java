package com.eupraxia.telephony.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;

public interface EmergencyScreenService {

	EmergencyScreenModel findByCallId(String callId);
	
	EmergencyScreenModel findByCaseId(String caseId);

	void saveOrUpdate(EmergencyScreenModel emergencyScreenModel);
	
	List<EmergencyScreenModel> findAllEmergencyByCallIds(List<String> callIds);

}
