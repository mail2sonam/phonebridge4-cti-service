package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.eupraxia.telephony.Model.SakhiModel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SakhiRepository extends JpaRepository<SakhiModel, Integer>{

	List<SakhiModel> findByPhonenumber(String phonenumber);

	List<SakhiModel> findByPhonenumberAndExtension(String phonenumber, String extension);

	List<SakhiModel> findByExtensionOrderByIncidentdateDesc(String extension);
}
