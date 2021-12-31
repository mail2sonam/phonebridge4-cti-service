package com.eupraxia.telephony.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.CaseModel;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CaseRepository extends JpaRepository<CaseModel, Integer>{

	CaseModel findByDate(String date);

}
