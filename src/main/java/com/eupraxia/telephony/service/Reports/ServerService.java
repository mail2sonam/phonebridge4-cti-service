package com.eupraxia.telephony.service.Reports;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.DTO.ServerDTO;
import com.eupraxia.telephony.Model.ServerModel;
import com.eupraxia.telephony.Reports.DAO.ServerDao;
import com.eupraxia.telephony.util.CommonUtil;

@Service
public class ServerService {
	@Autowired
	ServerDao serverDao;
	
	
	@Autowired
	 private ModelMapper modelMapper;

	public String saveServer(ServerDTO serverDTO) {
		ServerModel branchModel = new ServerModel();
		branchModel = modelMapper.map(serverDTO, ServerModel.class);	
		ServerModel branchModel1 = serverDao.saveOrUpdateServerDetails(branchModel);
		if(CommonUtil.isNull(branchModel1)) {
			return "Error in Saving Document";
		}
		else {
			return "Success";
		}	
	}

	public List<ServerModel> findAll() {
		List<ServerModel> serverModels = new ArrayList<ServerModel>();
		serverModels=serverDao.findAll();
		return serverModels;
	}

	public ServerModel findById(Long id) {
		ServerModel bm=new ServerModel();
		bm=serverDao.findById(id);
		return bm;
	}

	public void deleteServer(Long id) {
		serverDao.deleteById(id);
	}

	public String findByHostName(String hostName) {
		ServerModel branchModel = new ServerModel();
		System.out.println("hostName: :"+hostName);
		branchModel= serverDao.findByHostName(hostName);
		if(CommonUtil.isNull(branchModel)) {
			System.out.println("200");
			return "200";
		}
		else {
			System.out.println("400");
			return "400";
		}	
	}
	
	
}
