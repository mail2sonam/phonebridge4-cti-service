package com.eupraxia.telephony.Reports.DAOImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eupraxia.telephony.Model.CampaignModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Reports.DAO.CampaignDao;
import com.eupraxia.telephony.repositories.Reports.CampaignRepository;
import com.eupraxia.telephony.repositories.Reports.UserCampaignMappingRepository;


@Repository
public class CampaignDaoImpl implements CampaignDao {
	@Autowired
	CampaignRepository campaignRepository;
	
	@Autowired
	UserCampaignMappingRepository  campaignMappingRepository;
	
	
	@Override
	public CampaignModel findByCampaignId(long id) {
		return campaignRepository.findByCampaignId(id);
	}
	
	@Override
	public CampaignModel findByCampaignName(String campaignName) {
		return campaignRepository.findByCampaignName(campaignName);
	}
	
	@Override
	public UserCampaignMappingModel findMappingCampaignNameAndUserName(String campaignName, String userName) {
		System.out.println("Dao");
		System.out.println("campaignName "+campaignName);
		System.out.println("userName "+userName);
		return campaignMappingRepository.findByCampaignnameAndUsername(campaignName, userName);
	}
	@Override
	public void deleteByCampaignId(long id) {
		campaignRepository.deleteByCampaignId(id);
	}
	@Override
	public List<CampaignModel> findAll(){
		return campaignRepository.findAll();
	}
	@Override
	public CampaignModel saveOrUpdateCampaignDetails(CampaignModel campaignModel) {
		return campaignRepository.save(campaignModel);
	}

	@Override
	public List<CampaignModel> findCampaignsByDepartment(String department_code) {
		return campaignRepository.findByDepartmentcode(department_code);
	}

	@Override
	public List<UserCampaignMappingModel> findUsersFromCampaignName(String campaignname) {
		// TODO Auto-generated method stub
		return campaignMappingRepository.findByCampaignname(campaignname);
	}

	@Override
	public UserCampaignMappingModel saveOrUpdateCampaignMappingDetails(UserCampaignMappingModel ucm) {
		
		return campaignMappingRepository.save(ucm);
	}

	@Override
	public void deleteByMappingId(Long id) {
		campaignMappingRepository.deleteById(id);
		
	}

	@Override
	public List<UserCampaignMappingModel> findByUserextension(String userextension) {
		// TODO Auto-generated method stub
		return campaignMappingRepository.findByUserextension(userextension);
	}

	@Override
	public List<UserCampaignMappingModel> findByUserextensionAndDialMethod(String extension, String dialMethod) {
		// TODO Auto-generated method stub
		return campaignMappingRepository.findByUserextensionAndDialMethod(extension,dialMethod);
	}

	@Override
	public List<CampaignModel> findbyDialMethod(String dialMethod) {
		// TODO Auto-generated method stub
		return campaignRepository.findByDialMethod(dialMethod);
	}

	@Override
	public List<UserCampaignMappingModel> findByUserextensionAndDirection(String userextension, String direction) {
		// TODO Auto-generated method stub
		return campaignMappingRepository.findByUserextensionAndDirection(userextension,direction);
	}
}