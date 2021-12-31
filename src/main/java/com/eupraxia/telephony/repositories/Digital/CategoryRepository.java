package com.eupraxia.telephony.repositories.Digital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Digital.CategoryModel;



@Transactional
public interface CategoryRepository  extends JpaRepository<CategoryModel, Integer>{
	
	CategoryModel findByCategoryName(String categoryName);

}
