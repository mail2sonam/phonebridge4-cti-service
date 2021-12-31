package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;
import com.eupraxia.telephony.Model.Disposition.InformationScreenModel;

@Transactional
public interface InformationScreenRepository extends JpaRepository<InformationScreenModel, Integer> {

	InformationScreenModel findByCallId(String callId);	

	InformationScreenModel findByCaseId(String caseId);	
	
	@Query("SELECT e FROM InformationScreenModel e WHERE e.callId IN (:callIds)")     
    List<InformationScreenModel> findAllInformationByCallIds(@Param("callIds")List<String> callIds);

}
