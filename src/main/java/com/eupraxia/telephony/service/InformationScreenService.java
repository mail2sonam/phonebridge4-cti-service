package com.eupraxia.telephony.service;

import java.util.List;

import com.eupraxia.telephony.Model.Disposition.GuidenceScreenModel;
import com.eupraxia.telephony.Model.Disposition.InformationScreenModel;

public interface InformationScreenService {

	void saveOrUpdate(InformationScreenModel informationScreenModel1);

	InformationScreenModel findByCallId(String callId);
	
	InformationScreenModel findByCaseId(String caseId);
	
	List<InformationScreenModel> findAllInformationByCallIds(List<String> callIds);

}
