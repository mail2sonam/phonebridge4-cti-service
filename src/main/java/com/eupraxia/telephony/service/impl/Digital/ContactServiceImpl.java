package com.eupraxia.telephony.service.impl.Digital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Digital.ContactModel;
import com.eupraxia.telephony.repositories.Digital.ContactRepository;
import com.eupraxia.telephony.service.Digital.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public void saveOrUpdate(ContactModel contactModel) {
		// TODO Auto-generated method stub
		contactRepository.save(contactModel);
	}

	@Override
	public List<ContactModel> findByTownId(int townId) {
		// TODO Auto-generated method stub
		return contactRepository.findByTownId(townId);
	}
	
	@Override
	public List<ContactModel> findByTownIdAndSubCategoryId(int townId, int subCategoryId) {
		// TODO Auto-generated method stub
		return contactRepository.findByTownIdAndSubCategoryId(townId, subCategoryId);
	}
	
}
