package com.eupraxia.telephony.service;

import java.util.List;

import com.eupraxia.telephony.Model.Disposition.GuidenceScreenModel;

public interface GuidenceScreenService {

	void saveOrUpdate(GuidenceScreenModel guidenceScreenModel1);

	GuidenceScreenModel findByCallId(String callId);
	
	GuidenceScreenModel findByCaseId(String caseId);
	
	List<GuidenceScreenModel> findAllGuidenceByCallIds(List<String> callIds);

}
