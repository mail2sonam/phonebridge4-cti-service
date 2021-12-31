package com.eupraxia.telephony.service;

import java.util.List;
import java.util.Optional;

import com.eupraxia.telephony.Model.SmsModel;

public interface SmsService {

	void saveOrUpdate(SmsModel smsModel);

	List<SmsModel> findAll();
	
	SmsModel findByCaseId(String caseId);	

	List<SmsModel> findByNumber(Long number);
	
	SmsModel findByMessageId(String messageId);

	List<SmsModel> findByIsNew(boolean new1);
	
	List<SmsModel> findByStatus(String status);
	
	List<SmsModel> findByUserExtension(String userExtension);

	Optional<SmsModel> findById(String id);
	
	void deleteByCaseId(String caseId);

}
