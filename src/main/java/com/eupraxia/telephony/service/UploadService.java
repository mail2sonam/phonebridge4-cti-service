package com.eupraxia.telephony.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.UploadModel;
import com.eupraxia.telephony.Reports.DAO.UploadDAO;
@Service
public class UploadService {
	
	@Autowired 
	private UploadDAO uploadDAO;

	public UploadModel saveOrUpdate(UploadModel uploadModel) {
		
	return this.uploadDAO.saveOrUpdate(uploadModel)	;
	}

	public UploadModel NumberForDialler() {
		// TODO Auto-generated method stub
		return this.uploadDAO.findPhonenumber1()	;
	}

	

}
