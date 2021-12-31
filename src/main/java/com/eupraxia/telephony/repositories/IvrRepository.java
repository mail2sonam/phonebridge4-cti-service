package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.LoginModel;

public interface IvrRepository extends JpaRepository<IvrModel, Integer>{

	IvrModel findByUniqueId(String uniqueId);
	
	IvrModel findByPhoneNo(String phoneNo);
	
	IvrModel findFirstByPhoneNo(String phoneNo);

}
