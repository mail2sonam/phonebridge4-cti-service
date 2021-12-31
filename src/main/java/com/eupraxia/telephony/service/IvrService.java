package com.eupraxia.telephony.service;

import com.eupraxia.telephony.Model.IvrModel;

public interface IvrService {

	void saveOrUpdate(IvrModel ivrModel);

	IvrModel findByUniqueId(String uniqueId);
	
	IvrModel findByPhoneNo(String phoneNo);

}
