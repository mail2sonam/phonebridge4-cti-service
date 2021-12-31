package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.CSECEModel;

@Transactional
public interface CseCeRepository extends JpaRepository<CSECEModel, Integer> {

	CSECEModel findByPhoneNumber(String phoneNo);
	
	CSECEModel findByEmpId(String empId);
}
