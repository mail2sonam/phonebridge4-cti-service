package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.Disposition.CallDetailModel;

@Transactional
public interface CallDetailsRepository  extends JpaRepository<CallDetailModel, Integer> {

	CallDetailModel findByCallId(String callId);
	
	CallDetailModel findByCaseId(String caseId);

}
