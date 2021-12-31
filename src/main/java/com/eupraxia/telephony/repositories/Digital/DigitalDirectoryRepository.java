package com.eupraxia.telephony.repositories.Digital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Digital.DigitalDirectoryModel;
import com.eupraxia.telephony.Model.Digital.TownModel;



@Transactional
public interface DigitalDirectoryRepository extends JpaRepository<DigitalDirectoryModel, Integer>{

	@Query("SELECT DISTINCT maincategory FROM DigitalDirectoryModel")     
	List<String> findAllmainCategories();
		   
	List<DigitalDirectoryModel> findByMaincategory(String mainCat);
	
	List<DigitalDirectoryModel> findByMaincategoryAndSubCategory(String mainCat, String subCat);
	
	List<DigitalDirectoryModel> findByMaincategoryAndSubCategoryAndDistrict(String mainCat, String subCat, String district);
	
	List<DigitalDirectoryModel> findByMaincategoryAndSubCategoryAndDistrictAndTown(String mainCat, String subCat, String district, String town);
}
