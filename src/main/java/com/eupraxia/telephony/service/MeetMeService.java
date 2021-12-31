package com.eupraxia.telephony.service;

import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.MeetMeModel;


public interface MeetMeService {

	MeetMeModel findByRoom(String meetMe);

	void saveOrUpdate(MeetMeModel meetme1);
	
	MeetMeModel findByStatus(String status);

}
