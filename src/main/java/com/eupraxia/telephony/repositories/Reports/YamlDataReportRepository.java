package com.eupraxia.telephony.repositories.Reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Reports.YamlDataReportModel;

@Transactional
public interface YamlDataReportRepository extends JpaRepository<YamlDataReportModel, Integer>{
	@Query(value = "select * from yaml_data_report where call_time >= ?1 and call_time <= ?2", nativeQuery = true)	
	List<YamlDataReportModel> findByCallTimeBetween(String startDate, String endDate);
	
	List<YamlDataReportModel> findByPhoneNo(String phoneNo);
	
	List<YamlDataReportModel> findByPhoneNoOrderByCallTimeAsc(String phoneNo);
	
}
