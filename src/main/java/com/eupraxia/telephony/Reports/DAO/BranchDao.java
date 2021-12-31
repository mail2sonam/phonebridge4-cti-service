package com.eupraxia.telephony.Reports.DAO;

import java.util.List;

import com.eupraxia.telephony.Model.BranchModel;




public interface BranchDao {

	BranchModel saveOrUpdateBranchDetails(BranchModel branchModel);
	BranchModel findById(long id);
	BranchModel findByBranchName(String branchname);
	void deleteById(long id);
	List<BranchModel> findAll();
}
