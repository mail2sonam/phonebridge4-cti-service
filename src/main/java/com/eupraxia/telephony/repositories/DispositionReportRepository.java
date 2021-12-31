package com.eupraxia.telephony.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eupraxia.telephony.Model.DispositionReportModel;


public interface DispositionReportRepository extends JpaRepository<DispositionReportModel, Integer>{

	@Query("SELECT e FROM DispositionReportModel e WHERE e.callDate Between :startDate and :endDate ")  
	ArrayList<DispositionReportModel> getData(Date startDate, Date endDate);

}
