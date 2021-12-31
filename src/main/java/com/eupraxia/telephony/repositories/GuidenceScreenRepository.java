package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Disposition.GuidenceScreenModel;

@Transactional
public interface GuidenceScreenRepository extends JpaRepository<GuidenceScreenModel, Integer> {

	GuidenceScreenModel findByCallId(String callId);
	
	GuidenceScreenModel findByCaseId(String caseId);
	
	@Query("SELECT e FROM GuidenceScreenModel e WHERE e.callId IN (:callIds)")     
    List<GuidenceScreenModel> findAllGuidenceByCallIds(@Param("callIds")List<String> callIds);


}
