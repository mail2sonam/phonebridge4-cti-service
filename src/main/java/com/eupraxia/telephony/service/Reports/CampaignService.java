package com.eupraxia.telephony.service.Reports;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.DTO.CampaignDTO;
import com.eupraxia.telephony.DTO.UserCampaignMappingDTO;
import com.eupraxia.telephony.Model.CampaignModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Reports.DAO.CampaignDao;
import com.eupraxia.telephony.util.CommonUtil;



@Service
public class CampaignService {
	
	@Autowired
	CampaignDao campaignDao;
	
	@Autowired
	 private ModelMapper modelMapper;
	
	
	
	public List<CampaignModel> findAll() {
		List<CampaignModel> campaignModels = new ArrayList<CampaignModel>();
		campaignModels=campaignDao.findAll();
		return campaignModels;
		
	}
	public CampaignModel findByCampaignId(long id) {
		CampaignModel campaignModel=new CampaignModel();
		campaignModel=campaignDao.findByCampaignId(id);
		return campaignModel;
	}
	
	public UserCampaignMappingModel findMappingCampaignNameAndUserName(String campaignName, String userName) {
		UserCampaignMappingModel userCampaignMappingModel=new UserCampaignMappingModel();
		System.out.println("Service");
		System.out.println("campaignName "+campaignName);
		System.out.println("userName "+userName);
		userCampaignMappingModel=campaignDao.findMappingCampaignNameAndUserName(campaignName,userName);
		return userCampaignMappingModel;
	}
	
	public CampaignModel findByCampaignName(String campaignName) {
		CampaignModel campaignModel=new CampaignModel();
		campaignModel=campaignDao.findByCampaignName(campaignName);
		return campaignModel;
	}
	
	public String saveCampaign(CampaignDTO campaignDTO) {
		CampaignModel campaignModel = new CampaignModel();
		campaignModel = modelMapper.map(campaignDTO, CampaignModel.class);	
		CampaignModel campaignModel1 = campaignDao.saveOrUpdateCampaignDetails(campaignModel);
		if(CommonUtil.isNull(campaignModel1)) {
			return "Error in Saving Campaign";
		}
		else {
			return "Success";
		}	
	}
	public void deleteByCampaign(long id) {
		campaignDao.deleteByCampaignId(id);
	}
	public List<CampaignModel> findCampaignsByDepartment(String department_code) {
		List<CampaignModel> campaignModels = new ArrayList<CampaignModel>();
		campaignModels=campaignDao.findCampaignsByDepartment(department_code);
		return campaignModels;
	}
	public List<UserCampaignMappingModel> findUsersFromCampaignName(String camapignname) {
		List<UserCampaignMappingModel> data=new ArrayList<UserCampaignMappingModel>();
		data=campaignDao.findUsersFromCampaignName(camapignname);
		return data;
	}
	public String saveCampaignmapping(UserCampaignMappingModel userCampaignMappingModel) {
		//UserCampaignMappingModel ucm =new UserCampaignMappingModel();
		//ucm=modelMapper.map(campaignDTO, UserCampaignMappingModel.class);	
		UserCampaignMappingModel ucm1=campaignDao.saveOrUpdateCampaignMappingDetails(userCampaignMappingModel);
		if(CommonUtil.isNull(ucm1)) {
			return "Error in Saving Campaign";
		}
		else {
			return "Success";
		}	
	}
	public void deleteCampaignmapping(Long id) {
		campaignDao.deleteByMappingId(id);
	}
	
	public List<UserCampaignMappingModel> findByUserextension(String userextension) {		
		return campaignDao.findByUserextension(userextension);
	}
	
	public List<UserCampaignMappingModel> findByUserextensionAndDirection(String userextension, String direction) {
		// TODO Auto-generated method stub
		return campaignDao.findByUserextensionAndDirection(userextension,direction);
	}
	
	
}