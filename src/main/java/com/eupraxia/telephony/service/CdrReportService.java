package com.eupraxia.telephony.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eupraxia.telephony.Model.CdrReportModel;

public interface CdrReportService {

	void saveDisposition(CdrReportModel reportsModel);

	ArrayList<CdrReportModel> getData(Date date1, Date date2);

	ArrayList<CdrReportModel> findAll();
	
	List<CdrReportModel> getAllCallsToday(String dayBegin, String dayEnd);

}
