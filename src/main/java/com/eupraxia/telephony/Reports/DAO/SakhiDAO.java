package com.eupraxia.telephony.Reports.DAO;

import java.util.List;

import com.eupraxia.telephony.Model.SakhiModel;

public interface SakhiDAO {

	public SakhiModel saveOrUpdateSakhiModelDetails(SakhiModel sakhiModel);

	public List<SakhiModel> findAllTickets(String phonenumber);

	public List<SakhiModel> findAllTicketsForExtension(String phonenumber, String extension);

	public List<SakhiModel> findAllGrievancesTicketsForOnlyExtension(String extension);
	
	

}
