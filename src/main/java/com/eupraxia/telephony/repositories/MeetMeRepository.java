package com.eupraxia.telephony.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.eupraxia.telephony.Model.MeetMeModel;


import org.springframework.transaction.annotation.Transactional;

public interface MeetMeRepository extends MongoRepository<MeetMeModel,String>{

	MeetMeModel findByRoom(String meetMe);

	MeetMeModel findByStatus(String status);

	MeetMeModel findFirstByStatus(String status);

}
