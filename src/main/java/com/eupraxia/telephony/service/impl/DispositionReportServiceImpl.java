package com.eupraxia.telephony.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.DispositionReportModel;
import com.eupraxia.telephony.repositories.DispositionReportRepository;
import com.eupraxia.telephony.service.DispositionReportService;

@Service
public class DispositionReportServiceImpl implements DispositionReportService{

	@Autowired
	private DispositionReportRepository dispositionReportRepository;
	
	@Override
	public DispositionReportModel save(DispositionReportModel drp) {
		
		return dispositionReportRepository.save(drp);
	}

	@Override
	public ArrayList<DispositionReportModel> getData(Date date1, Date date2) {
		// TODO Auto-generated method stub
		return dispositionReportRepository.getData(date1,date2);
	}

}
