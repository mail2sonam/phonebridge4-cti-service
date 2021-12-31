package com.eupraxia.telephony.service.Digital;

import java.util.List;

import com.eupraxia.telephony.Model.Digital.DigitalDirectoryModel;


public interface DigitalDirectoryService {

	void saveOrUpdate(DigitalDirectoryModel DigitalDirectoryModel);

	List<DigitalDirectoryModel> findByMaincategory(String mainCat);
	
	List<DigitalDirectoryModel> findAllDistricts(String mainCat, String subCat);
	
	List<DigitalDirectoryModel> findAllTowns(String mainCat, String subCat, String district);
	
	List<DigitalDirectoryModel> findFinalDetails(String mainCat, String subCat, String district, String town);

	List<String> findAllMainCategories();
	

}
