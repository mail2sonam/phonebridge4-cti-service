package com.eupraxia.telephony.repositories.Digital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Digital.ContactModel;



@Transactional
public interface ContactRepository  extends JpaRepository<ContactModel, Integer>{

	List<ContactModel> findByTownId(int townId);
	
	List<ContactModel> findByTownIdAndSubCategoryId(int townId, int subCategoryId);

}
