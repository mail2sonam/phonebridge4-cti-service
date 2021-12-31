package com.eupraxia.telephony.service.Digital;

import java.util.List;

import com.eupraxia.telephony.Model.Digital.TownModel;


public interface TownService {

	void saveOrUpdate(TownModel townModel);

	List<TownModel> findByDistrictId(int districtId);
	
	TownModel findByTownName(String townName);

	

}
