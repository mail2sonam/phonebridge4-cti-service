package com.eupraxia.telephony.service;

import java.util.ArrayList;
import java.util.List;

import com.eupraxia.telephony.DTO.CallPropertiesDTO;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CallLogsModel;

public interface CallService {
	
	void saveOrUpdate(CallPropertiesModel callPropertiesModel);
	
	void delete(CallPropertiesModel CallPropertiesModel);
	
	CallPropertiesModel initCallData(CallPropertiesDTO callPropertiesDTO);
	
	void initCallDataLog(CallPropertiesDTO callPropertiesDTO);
	
	CallPropertiesModel checkPhoneNo(String phoneNo);
	
	//Mono<CallLogsModel> findByPhoneNo(String phoneNo);
	
	//Flux<CallLogsModel> findAllCallLogs();
	
	List<CallPropertiesModel> findAllCallProperties();
	
	CallPropertiesModel findById(String id);
	
	CallPropertiesModel checkOutgoingOrNot(String uniqueId);

	CallPropertiesModel findByChannelsIn(String channel);

	CallPropertiesModel findBySecondNumber(String secondNumber);

	CallPropertiesModel findBySecondChannel(String sencondChannel);

	CallPropertiesModel findByPhoneNo(String phoneNumber);
	
	CallPropertiesModel findByPhoneNoAndIsClosed(String phoneNumber);

	CallPropertiesModel deleteById(String callId);

	CallPropertiesModel findByExtension(String extension);

	ArrayList<CallPropertiesModel> findByPopupStatus(String popupstatus);

	List<CallPropertiesModel> findByAll();

	List<CallPropertiesModel> findAllByExtension(String extension);

	//Mono<CallLogsModel> getById(String id);
	
	//Mono deleteLog(String id);
	
}
