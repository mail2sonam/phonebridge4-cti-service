package com.eupraxia.telephony.service.Digital;

import java.util.List;

import com.eupraxia.telephony.Model.Digital.ContactModel;



public interface ContactService {

	void saveOrUpdate(ContactModel contactModel);

	List<ContactModel> findByTownId(int townId);
	
	List<ContactModel> findByTownIdAndSubCategoryId(int townId, int subCategoryId);

}
