package com.eupraxia.telephony.service;

import java.util.List;

import com.eupraxia.telephony.Model.Disposition.CallDetailModel;


public interface CallDetailService {

	CallDetailModel findByCallId(String callId);
	
	CallDetailModel findByCaseId(String caseId);

	void saveOrUpdate(CallDetailModel callDetailModel);

	List<Object> findAllReports();

}
