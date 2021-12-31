package com.eupraxia.telephony.service;

import java.util.ArrayList;
import java.util.List;

import com.eupraxia.telephony.Model.ScheduleCallModel;

public interface ScheduleCallService {

	void saveOrUpdate(ScheduleCallModel scheduleCallModel);

	ScheduleCallModel getScheduleCallData(String extension);

	List<ScheduleCallModel> getScheduleCallDataInList(String extension);
	
	List<ScheduleCallModel> findAll();

	ArrayList<ScheduleCallModel> getScheduleCallFinalData(String extension);

	List<ScheduleCallModel> findByCallStatus(String string);


}
