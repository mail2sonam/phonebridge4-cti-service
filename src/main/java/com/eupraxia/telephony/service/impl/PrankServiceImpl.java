package com.eupraxia.telephony.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.PrankModel;
import com.eupraxia.telephony.repositories.PrankRepository;
import com.eupraxia.telephony.service.PrankService;

@Service
public class PrankServiceImpl implements PrankService{

	@Autowired
	private PrankRepository prankRepository;

	@Override
	public void save(PrankModel prankModel) {
		prankRepository.save(prankModel);
	}

	@Override
	public List<PrankModel> findAll() {
		// TODO Auto-generated method stub
		return prankRepository.findAll();
	}

	@Override
	public PrankModel findByPhoneNo(String phoneNo) {
		// TODO Auto-generated method stub
		return prankRepository.findByPhoneNo(phoneNo);
	}

	@Override
	public void deleteByPhoneNo(String phoneNo) {
		// TODO Auto-generated method stub
		prankRepository.deleteByPhoneNo(phoneNo);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		prankRepository.deleteById(id);
	}
}
