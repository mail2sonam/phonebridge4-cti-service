package com.eupraxia.telephony.service;

import java.util.List;

import com.eupraxia.telephony.Model.PrankModel;

public interface PrankService {

	void save(PrankModel prankModel);

	List<PrankModel> findAll();

	PrankModel findByPhoneNo(String phoneNo);

	void deleteByPhoneNo(String phoneNo);

	void deleteById(int id);

}
