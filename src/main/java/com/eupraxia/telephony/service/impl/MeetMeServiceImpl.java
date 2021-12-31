package com.eupraxia.telephony.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.MeetMeModel;
import com.eupraxia.telephony.repositories.MeetMeRepository;
import com.eupraxia.telephony.service.MeetMeService;

@Service
public class MeetMeServiceImpl implements MeetMeService {

	@Autowired
	MeetMeRepository meetMeRepository;
	
	@Override
	public MeetMeModel findByRoom(String meetMe) {
		return meetMeRepository.findByRoom(meetMe);
	}

	@Override
	public void saveOrUpdate(MeetMeModel meetme1) {
		meetMeRepository.save(meetme1);
	}

	@Override
	public MeetMeModel findByStatus(String status) {
		
		return meetMeRepository.findFirstByStatus(status);
	}

}

