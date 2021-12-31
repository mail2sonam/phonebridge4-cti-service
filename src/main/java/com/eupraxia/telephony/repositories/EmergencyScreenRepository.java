package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Disposition.CallDetailModel;
import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;

@Transactional
public interface EmergencyScreenRepository extends JpaRepository<EmergencyScreenModel, Integer> {

	EmergencyScreenModel findByCallId(String callId);
	
	EmergencyScreenModel findByCaseId(String caseId);
	
	@Query("SELECT e FROM EmergencyScreenModel e WHERE e.callId IN (:callIds)")     
    List<EmergencyScreenModel> findAllEmergencyByCallIds(@Param("callIds")List<String> callIds);

}
