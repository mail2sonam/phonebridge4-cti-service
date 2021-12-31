package com.eupraxia.telephony.service.impl.Digital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Digital.DigitalDirectoryModel;
import com.eupraxia.telephony.repositories.Digital.DigitalDirectoryRepository;
import com.eupraxia.telephony.service.Digital.DigitalDirectoryService;
@Service
public class DigitalDirecotryServiceImpl implements DigitalDirectoryService{

@Autowired
private DigitalDirectoryRepository digitalDirectoryRepository;
	
@Override
	public void saveOrUpdate(DigitalDirectoryModel digitalDirectoryModel) {
		digitalDirectoryRepository.save(digitalDirectoryModel);		
	}
	
	@Override
	public List<String> findAllMainCategories() {
		// TODO Auto-generated method stub
		return digitalDirectoryRepository.findAllmainCategories();
	}
	
	@Override
	public List<DigitalDirectoryModel> findByMaincategory(String mainCat) {
		// TODO Auto-generated method stub
		return digitalDirectoryRepository.findByMaincategory(mainCat);
	}

	
	@Override
	public List<DigitalDirectoryModel> findAllDistricts(String mainCat, String subCat) {
		// TODO Auto-generated method stub
		return digitalDirectoryRepository.findByMaincategoryAndSubCategory(mainCat, subCat);
	}
	
	@Override
	public List<DigitalDirectoryModel> findAllTowns(String mainCat, String subCat, String district) {
		// TODO Auto-generated method stub
		return digitalDirectoryRepository.findByMaincategoryAndSubCategoryAndDistrict(mainCat, subCat, district);
	}
	
	@Override
	public List<DigitalDirectoryModel> findFinalDetails(String mainCat, String subCat, String district, String town) {
		// TODO Auto-generated method stub
		return digitalDirectoryRepository.findByMaincategoryAndSubCategoryAndDistrictAndTown(mainCat, subCat, district,town);
	}
	


}
