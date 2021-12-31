package com.eupraxia.telephony.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.repositories.IvrRepository;
import com.eupraxia.telephony.service.IvrService;
@Service
public class IvrServiceImpl implements IvrService{

	@Autowired
	IvrRepository ivrRepository;
	
	@Override
	public void saveOrUpdate(IvrModel ivrModel) {
		// TODO Auto-generated method stub
		ivrRepository.save(ivrModel);
		
	}

	@Override
	public IvrModel findByUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return ivrRepository.findByUniqueId(uniqueId);
	}

	@Override
	public IvrModel findByPhoneNo(String phoneNo) {
		// TODO Auto-generated method stub
		return ivrRepository.findByPhoneNo(phoneNo);
	}
	

}
