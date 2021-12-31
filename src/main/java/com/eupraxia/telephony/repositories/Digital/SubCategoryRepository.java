package com.eupraxia.telephony.repositories.Digital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Digital.SubCategoryModel;


@Transactional
public interface SubCategoryRepository extends JpaRepository<SubCategoryModel, Integer>{

	List<SubCategoryModel> findByCategoryId(int categoryId); 
	
	SubCategoryModel findBySubCategoryName(String subCategoryName);

}
