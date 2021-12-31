package com.eupraxia.telephony.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CallPropertiesTestingModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CallPropertiesTestingRepository extends MongoRepository<CallPropertiesTestingModel,String>{


	//CallPropertiesModel findFirstByPhoneNoAndIsClosedByOrderByCallStartTimeDesc(String phoneNumber, String isclosed);
	
	
	
}
