package com.eupraxia.telephony.service.Digital;

import java.util.List;

import com.eupraxia.telephony.Model.Digital.SubCategoryModel;



public interface SubCategoryService {

	void saveOrUpdate(SubCategoryModel subCategoryModel);

	List<SubCategoryModel> findByCategoryId(int categoryId);
	
	SubCategoryModel findBySubCategoryName(String subCategoryName);
}
