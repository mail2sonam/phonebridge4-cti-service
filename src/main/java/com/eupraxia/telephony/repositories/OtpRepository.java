package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.OtpModel;



@Transactional
public interface OtpRepository extends JpaRepository<OtpModel, Integer> {

	OtpModel findByPhoneNo(String phoneNo);

	void deleteByPhoneNo(String phoneNo);

}
