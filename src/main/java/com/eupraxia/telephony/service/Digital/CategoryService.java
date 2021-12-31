package com.eupraxia.telephony.service.Digital;

import java.util.List;

import com.eupraxia.telephony.Model.Digital.CategoryModel;

public interface CategoryService {

	void saveOrUpdate(CategoryModel categoryModel);

	List<CategoryModel> findByAll();
	
	CategoryModel findByCategoryName(String categoryName);

}
