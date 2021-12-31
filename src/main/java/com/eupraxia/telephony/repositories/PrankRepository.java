package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.PrankModel;

public interface PrankRepository extends JpaRepository<PrankModel, Integer>{

	PrankModel findByPhoneNo(String phoneNo);

	void deleteByPhoneNo(String phoneNo);

}
