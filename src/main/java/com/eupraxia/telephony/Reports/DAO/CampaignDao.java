package com.eupraxia.telephony.Reports.DAO;

import java.util.List;

import com.eupraxia.telephony.Model.CampaignModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;

public interface CampaignDao {

	CampaignModel saveOrUpdateCampaignDetails(CampaignModel campaignModel);
	
	CampaignModel findByCampaignId(long id);
	
	CampaignModel findByCampaignName(String clientName);
	
	UserCampaignMappingModel findMappingCampaignNameAndUserName(String campaignName, String userName);
	
	void deleteByCampaignId(long id);
	
	List<CampaignModel> findAll();
	
	List<CampaignModel> findCampaignsByDepartment(String department_code);
	
	List<UserCampaignMappingModel> findUsersFromCampaignName(String camapignname);
	
	UserCampaignMappingModel saveOrUpdateCampaignMappingDetails(UserCampaignMappingModel ucm);
	
	void deleteByMappingId(Long id);
	
	List<UserCampaignMappingModel> findByUserextension(String userextension);
	
	List<UserCampaignMappingModel> findByUserextensionAndDialMethod(String extension, String string);
	List<CampaignModel> findbyDialMethod(String dialMethod);

	List<UserCampaignMappingModel> findByUserextensionAndDirection(String userextension, String direction);
}
