package com.eupraxia.telephony.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.MailStoreModel;
import com.eupraxia.telephony.Model.SmsModel;
import com.eupraxia.telephony.repositories.MailStoreRepository;
import com.eupraxia.telephony.service.MailStoreService;


@Service
public class MailStoreServiceImpl implements MailStoreService{
	
	@Autowired
	MailStoreRepository mailStoreRepository;
	
	
	public void saveOrUpdate(MailStoreModel mailStoreModel) {
		
		mailStoreRepository.save(mailStoreModel);
	}
	
	public List<MailStoreModel> findAllByStatus(String status) {		
		return mailStoreRepository.findByStatus(status);
	}

	@Override
	public MailStoreModel findById(String id) {
		// TODO Auto-generated method stub
		return mailStoreRepository.findById(new ObjectId(id));
	}
	
	@Override
	public MailStoreModel findByCaseId(String caseId) {
		// TODO Auto-generated method stub
		return mailStoreRepository.findByCaseId(caseId);
	}
	
	@Override
	public MailStoreModel findByFromAndSubjectAndSentDate(String from, String subject, String sentDate) {
		return mailStoreRepository.findByFromAndSubjectAndSentDate(from, subject, sentDate);
	}

	@Override
	public List<MailStoreModel> findAllByUserName(String userName) {
		// TODO Auto-generated method stub
		return mailStoreRepository.findByUserName(userName);
	}

	@Override
	public void deleteByCaseId(String caseId) {
		System.out.println("deletebycaseid : "+caseId);
		// TODO Auto-generated method stub
		mailStoreRepository.deleteByCaseId(caseId);
	}
	
	@Override
	public List<MailStoreModel> findByUserExtension(String userExtension) {
		// TODO Auto-generated method stub
		return mailStoreRepository.findByUserExtension(userExtension);
	}
}
