package com.eupraxia.telephony.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.MailStoreModel;
import com.eupraxia.telephony.Model.SmsModel;
import com.eupraxia.telephony.repositories.SmsRepository;
import com.eupraxia.telephony.service.SmsService;
@Service
public class SmsServiceImpl implements SmsService{

	@Autowired
	SmsRepository smsRepository;
	
	@Override
	public void saveOrUpdate(SmsModel smsModel) {
		
		smsRepository.save(smsModel);
	}

	@Override
	public List<SmsModel> findAll() {
		// TODO Auto-generated method stub
		return smsRepository.findAll();
	}

	@Override
	public List<SmsModel> findByNumber(Long number) {
		// TODO Auto-generated method stub
		return smsRepository.findByNumber(number);
	}
	
	@Override
	public SmsModel findByCaseId(String caseId) {
		// TODO Auto-generated method stub
		return smsRepository.findByCaseId(caseId);
	}
	

	@Override
	public List<SmsModel> findByIsNew(boolean new1) {
		// TODO Auto-generated method stub
		return smsRepository.findByIsNew(new1);
	}

	@Override
	public List<SmsModel> findByStatus(String status) {
		// TODO Auto-generated method stub
		return smsRepository.findByStatus(status);
	}
	
	
	@Override
	public Optional<SmsModel> findById(String id) {
		// TODO Auto-generated method stub
		return smsRepository.findById(id);
	}
	
	@Override
	public SmsModel findByMessageId(String messageId) {
		// TODO Auto-generated method stub
		return smsRepository.findByMessageId(messageId);
	}
	
	@Override
	public List<SmsModel> findByUserExtension(String userExtension) {
		// TODO Auto-generated method stub
		return smsRepository.findByUserExtension(userExtension);
	}
	
	@Override
	public void deleteByCaseId(String caseId) {
		System.out.println("deletebycaseid : "+caseId);
		// TODO Auto-generated method stub
		smsRepository.deleteByCaseId(caseId);
	}


}
