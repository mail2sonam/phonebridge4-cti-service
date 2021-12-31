package com.eupraxia.telephony.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.ExtensionDataModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExtensionDataRepository extends JpaRepository<ExtensionDataModel, Integer>{
	
	ExtensionDataModel findByExtension(String extension);
	
}
