package com.eupraxia.telephony.service.impl.Digital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Digital.SubCategoryModel;
import com.eupraxia.telephony.repositories.Digital.SubCategoryRepository;
import com.eupraxia.telephony.service.Digital.SubCategoryService;
@Service
public class SubCategoryServiceImpl implements SubCategoryService{

	@Autowired
    private SubCategoryRepository subCategoryRepository;
	
	@Override
	public void saveOrUpdate(SubCategoryModel subCategoryModel) {
		subCategoryRepository.save(subCategoryModel);
		
	}

	@Override
	public List<SubCategoryModel> findByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return subCategoryRepository.findByCategoryId(categoryId);
	}
	
	@Override
	public SubCategoryModel findBySubCategoryName(String subCategoryName) {
		// TODO Auto-generated method stub
		return subCategoryRepository.findBySubCategoryName(subCategoryName);
	}
	
	

}
