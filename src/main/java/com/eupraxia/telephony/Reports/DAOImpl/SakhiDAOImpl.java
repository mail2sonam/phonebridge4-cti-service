package com.eupraxia.telephony.Reports.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eupraxia.telephony.Model.SakhiModel;
import com.eupraxia.telephony.repositories.DispositionRepository;
import com.eupraxia.telephony.repositories.SakhiRepository;

import com.eupraxia.telephony.Reports.DAO.SakhiDAO;
@Repository
public class SakhiDAOImpl implements SakhiDAO{
	@Autowired
	SakhiRepository sakhiRepository;
	@Override
	public SakhiModel saveOrUpdateSakhiModelDetails(SakhiModel sakhiModel) {
		// TODO Auto-generated method stub
		return sakhiRepository.save(sakhiModel);
	}
	@Override
	public List<SakhiModel> findAllTickets(String phonenumber) {
		// TODO Auto-generated method stub
		return sakhiRepository.findByPhonenumber(phonenumber);
	}
	@Override
	public List<SakhiModel> findAllTicketsForExtension(String phonenumber, String extension) {
		// TODO Auto-generated method stub
		return sakhiRepository.findByPhonenumberAndExtension(phonenumber,extension);
	}
	@Override
	public List<SakhiModel> findAllGrievancesTicketsForOnlyExtension(String extension) {
		// TODO Auto-generated method stub
		return sakhiRepository.findByExtensionOrderByIncidentdateDesc(extension);
	}

}
