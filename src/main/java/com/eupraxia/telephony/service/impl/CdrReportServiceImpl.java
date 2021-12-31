package com.eupraxia.telephony.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.CdrReportModel;
import com.eupraxia.telephony.repositories.CdrReportRepository;
import com.eupraxia.telephony.service.CdrReportService;

@Service
public class CdrReportServiceImpl implements  CdrReportService{

	@Autowired
	CdrReportRepository cdrReportRepository;
	
	@Override
	public void saveDisposition(CdrReportModel reportsModel) {
		cdrReportRepository.save(reportsModel);
		
	}

	@Override
	public ArrayList<CdrReportModel> getData(Date date1, Date date2) {
		
		return cdrReportRepository.findByCallDateBetween(date1,date2);
	}

	@Override
	public ArrayList<CdrReportModel> findAll() {
		// TODO Auto-generated method stub
		return (ArrayList<CdrReportModel>) cdrReportRepository.findAll();
	}

	public List<CdrReportModel> getAllCallsToday(String dayBegin, String dayEnd) {		
		return cdrReportRepository.findByCallStartTimeBetween(dayBegin, dayEnd);
	}

	


	
}
