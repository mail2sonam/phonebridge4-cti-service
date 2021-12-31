package com.eupraxia.telephony.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eupraxia.telephony.Model.CdrReportModel;
import com.eupraxia.telephony.Model.MissedCallModel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MissedCallRepository extends JpaRepository<MissedCallModel, Integer> {
	
	
	@Query("SELECT e FROM MissedCallModel e WHERE e.phoneNumber = (:phoneNumber) and e.callStatus = (:status)")    
	MissedCallModel findByPhoneNumberAndStatus(String phoneNumber, String status);
	
	MissedCallModel findById(int Id);
	
	MissedCallModel findFirstByCallStatusOrderByInsertTimeDesc(String status);

	List<MissedCallModel> findAllByCallStatusOrderByInsertTimeDesc(String status);
	
	List<MissedCallModel> findByInsertTimeBetween(String dayBegin, String dayEnd);

	MissedCallModel findFirstByCallStatusAndMissedNameOrderByInsertTimeDesc(String string, String campaignname);

	List<MissedCallModel> findAllByMissedNameOrderByInsertTimeDesc(String missedCampaignName);

	List<MissedCallModel> findByMissedNameAndCallStatus(String missedCampaignName, String string);

}
