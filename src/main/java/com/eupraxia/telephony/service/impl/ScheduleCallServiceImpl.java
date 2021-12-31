package com.eupraxia.telephony.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.ScheduleCallModel;
import com.eupraxia.telephony.repositories.MissedCallRepository;
import com.eupraxia.telephony.repositories.ScheduleCallRepository;
import com.eupraxia.telephony.service.ScheduleCallService;

@Service
public class ScheduleCallServiceImpl implements ScheduleCallService{

	@Autowired
	ScheduleCallRepository scheduleCallRepository;
	
	@Override
	public void saveOrUpdate(ScheduleCallModel scheduleCallModel) {
		scheduleCallRepository.save(scheduleCallModel);
	}

	@Override
	public ScheduleCallModel getScheduleCallData(String extension) {
		return scheduleCallRepository.findFirstByExtensionAndCallStatus(extension,"open",new Date());
		
	}

	@Override
	public List<ScheduleCallModel> getScheduleCallDataInList(String extension) {
		// TODO Auto-generated method stub
		return scheduleCallRepository.findAllByExtensionAndCallStatus(extension,"open");
	}

	@Override
	public ArrayList<ScheduleCallModel> getScheduleCallFinalData(String extension) {
		return scheduleCallRepository.findData(extension,"open",new Date());
		
	}

	@Override
	public List<ScheduleCallModel> findAll() {
		// TODO Auto-generated method stub
		return scheduleCallRepository.findAll();
	}

	@Override
	public List<ScheduleCallModel> findByCallStatus(String string) {
		// TODO Auto-generated method stub
		return scheduleCallRepository.findByCallStatus(string);
	}
	
}
