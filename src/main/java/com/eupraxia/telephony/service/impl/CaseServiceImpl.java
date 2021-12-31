package com.eupraxia.telephony.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.repositories.CaseRepository;
import com.eupraxia.telephony.service.CaseService;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	private CaseRepository caseRepository;
	@Override
	public CaseModel findByDate(String date) {
		// TODO Auto-generated method stub
		return caseRepository.findByDate(date);
	}

	@Override
	public void saveOrUpdate(CaseModel caseModel) {
		// TODO Auto-generated method stub
		caseRepository.save(caseModel);
	}

}
