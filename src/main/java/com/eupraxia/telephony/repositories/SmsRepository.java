package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eupraxia.telephony.Model.SmsModel;

public interface SmsRepository extends MongoRepository<SmsModel,String>{

	List<SmsModel> findByNumber(Long number);

	List<SmsModel> findByIsNew(boolean new1);
	
	SmsModel findByCaseId(String caseId);

	List<SmsModel> findByStatus(String status);
	
	List<SmsModel> findByUserExtension(String userExtension);
	
	SmsModel findByMessageId(String messageId);
	
	void deleteByCaseId(String caseId);
	
}
