package com.eupraxia.telephony.service;

import java.util.Date;

import com.eupraxia.telephony.Model.CaseModel;

public interface CaseService {
	CaseModel findByDate(String date);
	void saveOrUpdate(CaseModel caseModel);
	
}
