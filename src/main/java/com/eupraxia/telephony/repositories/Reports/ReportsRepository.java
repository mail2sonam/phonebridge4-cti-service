package com.eupraxia.telephony.repositories.Reports;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Reports.ReportsModel;

@Transactional
public interface ReportsRepository extends JpaRepository<ReportsModel, Integer>{

	public ReportsModel save(ReportsModel reportsModel);
	
	@Query("SELECT e FROM ReportsModel e WHERE e.callStartTime Between :startDate and :endDate ")    
	public ArrayList<ReportsModel> findByStartDateBetween(Date startDate, Date endDate);

	
	public ArrayList<ReportsModel> findByPhoneNo(String phoneNumber);

	public ArrayList<ReportsModel> findByExtension(String extension);
	
	@Query("SELECT e FROM ReportsModel e WHERE e.callStartTime Between :startDate and :endDate ")
	public List<ReportsModel> findByCallStartTimeBetween(@Param("startDate") LocalDateTime dayBegin, @Param("endDate") LocalDateTime dayEnd);
	//public List<ReportsModel> findByCallStartTimeBetween(LocalDateTime dayBegin, LocalDateTime dayEnd);
	
	ReportsModel findByDocId(String docId);
	
	ReportsModel findByCaseId(String caseId);	
	
	ReportsModel findById(int id);
}
