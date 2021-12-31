package com.eupraxia.telephony.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.CdrReportModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;

@Transactional
public interface CdrReportRepository extends JpaRepository<CdrReportModel, Integer> {

	@Query("SELECT e FROM CdrReportModel e WHERE e.callDate Between :startDate and :endDate ")    
	public ArrayList<CdrReportModel> findByCallDateBetween(Date startDate, Date endDate);
	
	List<CdrReportModel> findByCallStartTimeBetween(String dayBegin, String dayEnd);
	
	//List<CdrReportModel> getAllCallsToday(LocalDateTime dayBegin, LocalDateTime dayEnd);


}
