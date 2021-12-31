package com.eupraxia.telephony.service.Reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.DTO.HistoryReport;
import com.eupraxia.telephony.Model.CdrReportModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.repositories.Reports.ReportsRepository;



@Service
public class ReportService {
	@Autowired 
	ReportsDAO reportsDAO; 
	
	@Autowired
	ReportsRepository reportsRepository;
	
	public ReportsModel saveDisposition(ReportsModel reportsModel) {

		return this.reportsDAO.save(reportsModel);
	}


	public ArrayList<ReportsModel> getData(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return this.reportsDAO.getData(startDate,endDate);
	}


	public ArrayList<ReportsModel> findByPhoneNo(String phoneNumber) {
		// TODO Auto-generated method stub
		return reportsDAO.findByPhoneNo(phoneNumber);
	}


	public ArrayList<ReportsModel> findByExtension(String extension) {
		// TODO Auto-generated method stub
		return reportsDAO.findByExtension(extension);
	}
	
	public List<ReportsModel> getAllCallsToday(LocalDateTime dayBegin, LocalDateTime dayEnd) throws ParseException {	
				 
		return reportsRepository.findByCallStartTimeBetween(dayBegin, dayEnd);
	}


	public ArrayList<HistoryReport> findHistoryByPhoneNo(String phoneNumber) {
		// TODO Auto-generated method stub
		return reportsDAO.findPhoneNoWithLimit(phoneNumber);
	}
	
	
	public ReportsModel findByDocId(String docId) throws ParseException {	
		 
		return reportsRepository.findByDocId(docId);
	}
	
	public ReportsModel findByCaseId(String caseId) throws ParseException {	
		 
		return reportsRepository.findByCaseId(caseId);
	}
}
