package com.eupraxia.telephony.Reports.DAOImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eupraxia.telephony.Model.UploadModel;
import com.eupraxia.telephony.Reports.DAO.UploadDAO;
import com.eupraxia.telephony.repositories.Reports.UploadRepository;
@Repository
public class UploadDAOImpl implements UploadDAO{
	
	@Autowired 
	private UploadRepository uploadRepository;

	@Override
	public UploadModel saveOrUpdate(UploadModel uploadModel) {
		// TODO Auto-generated method stub
		return uploadRepository.save(uploadModel);
	}

	@Override
	public UploadModel findPhonenumber1() {
		// TODO Auto-generated method stub
		return uploadRepository.findFirstByStatus("NotInitiated");
	}

	

}
