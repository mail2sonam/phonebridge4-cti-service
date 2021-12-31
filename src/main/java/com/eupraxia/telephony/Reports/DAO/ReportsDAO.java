package com.eupraxia.telephony.Reports.DAO;

import java.util.ArrayList;
import java.util.Date;

import com.eupraxia.telephony.DTO.HistoryReport;
import com.eupraxia.telephony.Model.Reports.ReportsModel;

public interface ReportsDAO {

	ReportsModel save(ReportsModel reportsModel);

	ArrayList<ReportsModel> getData(Date startDate, Date endDate);

	ArrayList<ReportsModel> findByPhoneNo(String phoneNumber);

	ArrayList<ReportsModel> findByExtension(String extension);

	void disposeCall(String id);

	ArrayList<HistoryReport> findPhoneNoWithLimit(String phoneNumber);

	void disposeCall1(String id);

	

}
