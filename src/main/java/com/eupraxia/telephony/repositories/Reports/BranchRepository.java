package com.eupraxia.telephony.repositories.Reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.BranchModel;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BranchRepository extends JpaRepository<BranchModel, Long> {
	public BranchModel findById(long id);
	public BranchModel findByBranchName(String branchname);
	public Long deleteById(long id);
	public List<BranchModel> findAll();
	
}