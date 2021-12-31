package com.eupraxia.telephony.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.LoginModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CallPropertiesRepository extends MongoRepository<CallPropertiesModel,String>{


	CallPropertiesModel findByPhoneNo(String phoneNo);
	CallPropertiesModel findById(ObjectId id);
	CallPropertiesModel findByChannelsIn(String channel);
	CallPropertiesModel findBySecondNumber(String secondnumber);
	CallPropertiesModel findBySecondChannel(String sencondChannel);
	CallPropertiesModel deleteById(ObjectId id);
	CallPropertiesModel findByExtension(String extension);
	ArrayList<CallPropertiesModel> findByPopupStatus(String popupstatus);
	CallPropertiesModel findByPhoneNoAndIsClosed(String phoneNumber, String isclosed);
	
	@Query("{ extension : ?0 }")   
	List<CallPropertiesModel> findAllByExtension(String extension);
	
	//CallPropertiesModel findFirstByPhoneNoAndIsClosedByOrderByCallStartTimeDesc(String phoneNumber, String isclosed);
	//it will work?
	
	
}
