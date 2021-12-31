package com.eupraxia.telephony.Reports.DAO;

import com.eupraxia.telephony.Model.UploadModel;

public interface UploadDAO {
	public UploadModel saveOrUpdate(UploadModel uploadModel);

	public UploadModel findPhonenumber1();

}
