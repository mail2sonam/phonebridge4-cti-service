package com.eupraxia.telephony.repositories.Reports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eupraxia.telephony.Model.UploadModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UploadRepository extends JpaRepository<UploadModel, Integer>{

	public UploadModel save(UploadModel uploadModel);
	
 
	public UploadModel findFirstByStatus(String status);
	
}
