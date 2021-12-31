package com.eupraxia.telephony.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.SakhiModel;


import com.eupraxia.telephony.Reports.DAO.SakhiDAO;
@Service
public class SakhiService {
	@Autowired 
	SakhiDAO sakhiDAO; 

	public SakhiModel saveOrUpdateSakhiModelDetails(SakhiModel sakhiModel) {
		// TODO Auto-generated method stub
		return this.sakhiDAO.saveOrUpdateSakhiModelDetails(sakhiModel);
	}

	public List<SakhiModel> findAllTickets(String phonenumber) {
		// TODO Auto-generated method stub
		return this.sakhiDAO.findAllTickets(phonenumber);
	}

	public List<SakhiModel> findAllTicketsForExtension(String phonenumber, String extension) {
		// TODO Auto-generated method stub
		return this.sakhiDAO.findAllTicketsForExtension(phonenumber,extension);
	}

	public List<SakhiModel> findAllGrievancesTicketsForOnlyExtension(String extension) {
		// TODO Auto-generated method stub
		return this.sakhiDAO.findAllGrievancesTicketsForOnlyExtension(extension);
	}



}
