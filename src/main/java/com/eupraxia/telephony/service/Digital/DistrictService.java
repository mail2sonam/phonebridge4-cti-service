package com.eupraxia.telephony.service.Digital;

import java.util.List;

import com.eupraxia.telephony.Model.Digital.DistrictModel;
import com.eupraxia.telephony.Model.Digital.TownModel;



public interface DistrictService {

	void saveOrUpdate(DistrictModel districtModel);

	List<DistrictModel> findAll();
	
	DistrictModel findByDistrictName(String districtName);

}
