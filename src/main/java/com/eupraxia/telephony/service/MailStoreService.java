package com.eupraxia.telephony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eupraxia.telephony.DTO.CallPropertiesDTO;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.MailStoreModel;
import com.eupraxia.telephony.Model.SmsModel;
import com.eupraxia.telephony.Model.CallLogsModel;

public interface MailStoreService {
	
	void saveOrUpdate(MailStoreModel mailStoreModel);
	
	List<MailStoreModel> findAllByStatus(String status);

	MailStoreModel findById(String id);	
	
	MailStoreModel findByCaseId(String caseId);	
	
	MailStoreModel findByFromAndSubjectAndSentDate(String from, String subject, String sentDate);	

	List<MailStoreModel> findAllByUserName(String userName);

	void deleteByCaseId(String caseId);
	
	List<MailStoreModel> findByUserExtension(String userExtension);
	
	
	

	
	
}
