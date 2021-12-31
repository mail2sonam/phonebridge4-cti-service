package com.eupraxia.telephony.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eupraxia.telephony.Model.MissedCallModel;
import com.eupraxia.telephony.Model.ScheduleCallModel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ScheduleCallRepository extends JpaRepository<ScheduleCallModel, Integer>{

	@Query("SELECT e FROM ScheduleCallModel e WHERE e.extension = (:extension) and e.callStatus = (:callStatus) and e.callBackTime < (:datestart)") 
	ScheduleCallModel findFirstByExtensionAndCallStatus(String extension, String callStatus,Date datestart);
	
	//@Query("SELECT e FROM ScheduleCallModel e WHERE e.extension = (:extension) and e.callStatus = (:callStatus) and e.callBackTime < CURRENT_DATE") 
	List<ScheduleCallModel> findAllByExtensionAndCallStatus(String extension, String callStatus);

	@Query("SELECT e FROM ScheduleCallModel e WHERE e.extension = (:extension) and e.callStatus = (:callStatus) and e.callBackTime < (:datestart)") 
	ArrayList<ScheduleCallModel> findData(String extension, String callStatus, Date datestart);

	
	List<ScheduleCallModel> findByCallStatus(String string);
	
}
