package com.eupraxia.telephony.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.OtpModel;
import com.eupraxia.telephony.repositories.MissedCallRepository;
import com.eupraxia.telephony.repositories.OtpRepository;
import com.eupraxia.telephony.service.OtpService;

@Service
public class OtpServiceImpl implements  OtpService{
	
	@Autowired
	OtpRepository otpRepository;

	@Override
	public OtpModel findByPhoneNo(String phoneNo) {
		// TODO Auto-generated method stub
		return otpRepository.findByPhoneNo(phoneNo);
	}

	@Override
	public void saveOrUpdate(OtpModel otp2) {
		otpRepository.save(otp2);
		
	}

	@Override
	public void deleteByPhoneNo(String phoneNo) {
		otpRepository.deleteByPhoneNo(phoneNo);
		
	}

}
