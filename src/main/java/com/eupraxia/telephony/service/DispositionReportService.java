package com.eupraxia.telephony.service;

import java.util.ArrayList;
import java.util.Date;

import com.eupraxia.telephony.Model.DispositionReportModel;

public interface DispositionReportService {

	DispositionReportModel save(DispositionReportModel drp);

	ArrayList<DispositionReportModel> getData(Date date1, Date date2);

}
