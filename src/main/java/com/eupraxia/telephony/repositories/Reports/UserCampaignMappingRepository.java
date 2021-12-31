package com.eupraxia.telephony.repositories.Reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.UserCampaignMappingModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCampaignMappingRepository extends JpaRepository<UserCampaignMappingModel,Long> {

	List<UserCampaignMappingModel> findByCampaignname(String camapignname);
	
	UserCampaignMappingModel findByCampaignnameAndUsername(String camapignname, String username);
	
	public UserCampaignMappingModel findById(long id);

	List<UserCampaignMappingModel> findByUserextension(String userextension);
	
	List<UserCampaignMappingModel> findByUserextensionAndDialMethod(String extension, String dialMethod);

	List<UserCampaignMappingModel> findByUserextensionAndDirection(String userextension, String direction);

	
}
