package com.eupraxia.telephony.service.Reports;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.DTO.BranchDTO;
import com.eupraxia.telephony.Model.BranchModel;
import com.eupraxia.telephony.Reports.DAO.BranchDao;
import com.eupraxia.telephony.util.CommonUtil;



@Service
public class BranchService {
	@Autowired
	BranchDao branchDao;
	
	@Autowired
	 private ModelMapper modelMapper;
	
	
	
	public List<BranchModel> findAll() {
		List<BranchModel> branchModels = new ArrayList<BranchModel>();
		branchModels=branchDao.findAll();
		return branchModels;
		
	}
	public BranchModel findById(long id) {
		BranchModel bm=new BranchModel();
		bm=branchDao.findById(id);
		return bm;
	}
	public String saveBranchs(BranchDTO branchDTO) {
		BranchModel branchModel = new BranchModel();
		branchModel = modelMapper.map(branchDTO, BranchModel.class);	
		BranchModel branchModel1 = branchDao.saveOrUpdateBranchDetails(branchModel);
		if(CommonUtil.isNull(branchModel1)) {
			return "Error in Saving Document";
		}
		else {
			return "Success";
		}	
	}
	public void deleteBranch(long id) {
		branchDao.deleteById(id);
	}
	public String getBranchByBranchName(String branchname) {
		BranchModel branchModel = new BranchModel();
		branchModel= branchDao.findByBranchName(branchname);
		if(CommonUtil.isNull(branchModel)) {
			return "200";
		}
		else {
			return "400";
		}	
	}
	
}