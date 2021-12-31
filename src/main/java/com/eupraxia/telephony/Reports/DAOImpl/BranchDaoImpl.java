package com.eupraxia.telephony.Reports.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eupraxia.telephony.Model.BranchModel;
import com.eupraxia.telephony.Reports.DAO.BranchDao;
import com.eupraxia.telephony.repositories.Reports.BranchRepository;


@Repository
public class BranchDaoImpl implements BranchDao {
	@Autowired
	BranchRepository branchRepository;
	
	
	
	@Override
	public BranchModel findById(long id) {
		return branchRepository.findById(id);
	}
	
	@Override
	public BranchModel findByBranchName(String branchName) {
		return branchRepository.findByBranchName(branchName);
	}
	@Override
	public void deleteById(long id) {
		branchRepository.deleteById(id);
	}
	@Override
	public List<BranchModel> findAll(){
		return branchRepository.findAll();
	}
	@Override
	public BranchModel saveOrUpdateBranchDetails(BranchModel branchModel) {
		return branchRepository.save(branchModel);
	}
}