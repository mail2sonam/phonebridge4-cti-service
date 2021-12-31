package com.eupraxia.telephony.service.impl.Digital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Digital.CategoryModel;
import com.eupraxia.telephony.repositories.Digital.CategoryRepository;
import com.eupraxia.telephony.service.Digital.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public void saveOrUpdate(CategoryModel categoryModel) {
		categoryRepository.save(categoryModel);
		
	}
	@Override
	public List<CategoryModel> findByAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}
	
	@Override
	public CategoryModel findByCategoryName(String categoryName) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryName(categoryName);
	}

}
