package com.eupraxia.telephony.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Disposition.CallDetailModel;
import com.eupraxia.telephony.repositories.CallDetailsRepository;
import com.eupraxia.telephony.repositories.CaseRepository;
import com.eupraxia.telephony.service.CallDetailService;


@Service
public class CallDetailServiceImpl implements   CallDetailService{

	@Autowired
	private CallDetailsRepository callDetailRepository;

	@Override
	public CallDetailModel findByCallId(String callId) {
		// TODO Auto-generated method stub
		return callDetailRepository.findByCallId(callId);
	}
	
	@Override
	public CallDetailModel findByCaseId(String caseId) {
		// TODO Auto-generated method stub
		return callDetailRepository.findByCaseId(caseId);
	}

	@Override
	public void saveOrUpdate(CallDetailModel callDetailModel) {
		// TODO Auto-generated method stub
		callDetailRepository.save(callDetailModel);
	}

	@Override
	public List<Object> findAllReports() {
		// TODO Auto-generated method stub
		//use this query
		  String query ="SELECT * FROM eupraxia_report r LEFT JOIN  guidence_case_details g ON r.doc_Id = g.call_id  LEFT JOIN information_case_details i  ON r.doc_Id = i.call_id LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id LEFT JOIN emergency_case_details e  ON r.doc_Id =e.call_id";
		     
		return null;
	}
}
