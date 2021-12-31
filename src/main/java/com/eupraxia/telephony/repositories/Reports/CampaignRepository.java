package com.eupraxia.telephony.repositories.Reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.CampaignModel;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CampaignRepository extends JpaRepository<CampaignModel,Long>{
	public CampaignModel findByCampaignId(long id);
	public CampaignModel findByCampaignName(String branchname);
	public void deleteByCampaignId(long id);
	public List<CampaignModel> findAll();
	public List<CampaignModel> findByDepartmentcode(String department_code);
	public CampaignModel findByQueueNameAndDialMethod(String queue, String dialMethod);
	public List<CampaignModel> findByDialMethod(String dialMethod);
}
