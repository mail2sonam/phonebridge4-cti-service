package com.eupraxia.telephony.repositories.Digital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Digital.DistrictModel;

@Transactional
public interface DistrictRepository extends JpaRepository<DistrictModel, Integer>{
	
	DistrictModel findByDistrictName(String districtName);
	


}
