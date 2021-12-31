package com.eupraxia.telephony.rest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.CampaignDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.UserCampaignMappingDTO;
import com.eupraxia.telephony.DTO.UserDTO;
import com.eupraxia.telephony.Model.CampaignModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.repositories.Reports.UserRepository;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.Reports.UserService;
import com.eupraxia.telephony.util.CommonUtil;

@CrossOrigin
@RestController
@RequestMapping("/campaign")
public class CampaignController {
	@Autowired
	CampaignService campaignService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService  userService;
	
	@Autowired
	ActionHandler actionhandler;
	
	@PostMapping(value = "/save")
    public ResponseEntity<?> saveCampaign(@RequestBody CampaignDTO campaignDTO) throws MalformedURLException, URISyntaxException {
		String result= campaignService.saveCampaign(campaignDTO);
		return new ResponseEntity<>(result, HttpStatus.OK); 
	}

	@PostMapping(value = "/saveOrUpdateUserMapping")
    public ResponseEntity<?> saveCampaignmapping(@RequestBody UserCampaignMappingDTO campaignDTO) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
		String result= "";
		String [] names =  campaignDTO.getUsername();
		for(String name:names) {
			UserModel userModel = new UserModel();
			CampaignModel campaignModel = new CampaignModel();
			UserCampaignMappingDTO UserCampaignMappingDTO = new UserCampaignMappingDTO();
			userModel = userService.findByUserName(name);
			if(!CommonUtil.isNull(userModel)) {
				List<UserCampaignMappingModel> userCampaignMappingModels1 = new ArrayList<UserCampaignMappingModel>();
				userCampaignMappingModels1=campaignService.findUsersFromCampaignName(campaignDTO.getCampaignname());
				List<String> usernames=new ArrayList<String>();
				for(UserCampaignMappingModel ucm:userCampaignMappingModels1) {
					usernames.add(ucm.getUsername());
				}
				if(!usernames.contains(name)) {
				UserCampaignMappingModel userCampaignMappingModel = new UserCampaignMappingModel();
				
				UserCampaignMappingDTO.setUserid(userModel.getId());
				campaignModel = campaignService.findByCampaignName(campaignDTO.getCampaignname());			
				userCampaignMappingModel.setCampaignname(campaignModel.getCampaignName());
				userCampaignMappingModel.setUserid(userModel.getId());
				userCampaignMappingModel.setUsername(userModel.getUsername());
				userCampaignMappingModel.setUserextension(userModel.getUserextension());
				userCampaignMappingModel.setQueue(campaignModel.getQueueName());
				userCampaignMappingModel.setDirection(campaignModel.getCallDirection());
				userCampaignMappingModel.setDialMethod(campaignModel.getDialMethod());
				
				result= campaignService.saveCampaignmapping(userCampaignMappingModel);	
				}
			}
		}
		//String result= campaignService.saveCampaignmapping(campaignDTO);
		//userModels=userService.findByDepartmentcode(depname);
		//userModels1=userModels;
		List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		usercampaignModels= campaignService.findUsersFromCampaignName(campaignDTO.getCampaignname());
		List<UserModel> userModelResult =new ArrayList<UserModel>();
		if(usercampaignModels.isEmpty()) {
			userModelResult=userService.findAll();
		}else {
		List<String> userExtensionList = usercampaignModels.stream().map(e->e.getUserextension()).collect(Collectors.toList());
		userModelResult = userRepository.findUsersNotMatchingCampaign(userExtensionList);
		}
		/*
		 * for (int J=0;J<userModels.size();J++) { for(int
		 * i=0;i<usercampaignModels.size();i++) {
		 * if(userModels1.get(J).getUserextension().equals(usercampaignModels.get(i).
		 * getUserextension())) { userModels1.remove(J); } } }
		 */
		
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModelResult); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		 return new ResponseEntity(map, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/getAllUsersNotInCampaign/{camname}")
	 public ResponseEntity<?> getUsersBasedOnInusers(@PathVariable(name = "camname") String camname) throws MalformedURLException, URISyntaxException {
		List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		usercampaignModels= campaignService.findUsersFromCampaignName(camname);
		List<UserModel> userModelResult =new ArrayList<UserModel>();
		if(usercampaignModels.isEmpty()) {
			userModelResult=userService.findAll();
		}else {
		List<String> userExtensionList = usercampaignModels.stream().map(e->e.getUserextension()).collect(Collectors.toList());
		userModelResult = userRepository.findUsersNotMatchingCampaign(userExtensionList);
		}
		/*
		 * for (int J=0;J<userModels.size();J++) { for(int
		 * i=0;i<usercampaignModels.size();i++) {
		 * if(userModels1.get(J).getUserextension().equals(usercampaignModels.get(i).
		 * getUserextension())) { userModels1.remove(J); } } }
		 */
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModelResult); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}

	
	@PostMapping(value = "/removemapping")
    public ResponseEntity<?> removeCampaignmapping(@RequestBody UserCampaignMappingDTO campaignDTO) throws MalformedURLException, URISyntaxException {
		//List<UserModel> userModels = new ArrayList<UserModel>();
		//List<UserModel> userModels1 = new ArrayList<UserModel>();		
		
		String [] names =  campaignDTO.getUsername();
		for(String name:names) {
			UserModel userModel = new UserModel();
			CampaignModel campaignModel = new CampaignModel();
			UserCampaignMappingDTO UserCampaignMappingDTO = new UserCampaignMappingDTO();
			userModel = userService.findByUserName(name);
			if(!CommonUtil.isNull(userModel)) {
				List<UserCampaignMappingModel> userCampaignMappingModel = new ArrayList<UserCampaignMappingModel>();
				userCampaignMappingModel=campaignService.findUsersFromCampaignName(campaignDTO.getCampaignname());
				for(UserCampaignMappingModel ucm:userCampaignMappingModel) {
					if(ucm.getUsername().contentEquals(userModel.getUsername())) {
						campaignService.deleteCampaignmapping(ucm.getId());
					}
				}
			}
		}
	
		//userModels=userService.findByDepartmentcode(depname);
		//userModels1=userModels;
		List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		usercampaignModels= campaignService.findUsersFromCampaignName(campaignDTO.getCampaignname());
		List<UserModel> userModelResult =new ArrayList<UserModel>();
		if(usercampaignModels.isEmpty()) {
			userModelResult=userService.findAll();
		}else {
		List<String> userExtensionList = usercampaignModels.stream().map(e->e.getUserextension()).collect(Collectors.toList());
		userModelResult = userRepository.findUsersNotMatchingCampaign(userExtensionList);
		}
		/*
		 * for (int J=0;J<userModels.size();J++) { for(int
		 * i=0;i<usercampaignModels.size();i++) {
		 * if(userModels1.get(J).getUserextension().equals(usercampaignModels.get(i).
		 * getUserextension())) { userModels1.remove(J); } } }
		 */
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModelResult); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping(value="/getAll")
	public ResponseEntity<?> getCamapaign() throws MalformedURLException, URISyntaxException {
		List<CampaignModel> campaignModels = new ArrayList<CampaignModel>();
		campaignModels= campaignService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", campaignModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/getCampaign")
	public ResponseEntity<?> getCampaignById(@RequestBody CampaignDTO campaignDTO ) throws MalformedURLException, URISyntaxException {
		CampaignModel campaignModel = new CampaignModel();
		campaignModel= campaignService.findByCampaignId(campaignDTO.getCampaignId());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", campaignModel); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/getCampaignByName")
	public ResponseEntity<?> getCampaignByName(@RequestBody CampaignDTO campaignDTO ) throws MalformedURLException, URISyntaxException {
		CampaignModel campaignModel = new CampaignModel();
		campaignModel= campaignService.findByCampaignName(campaignDTO.getCampaignName());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", campaignModel); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	
	@PostMapping(value="/deleteCampaign")
	public ResponseEntity<?> deleteByCampaign(@RequestBody CampaignDTO campaignDTO) throws MalformedURLException, URISyntaxException {	
		List<CampaignModel> campaignModels = new ArrayList<CampaignModel>();
		campaignService.deleteByCampaign(campaignDTO.getCampaignId());
		campaignModels= campaignService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", campaignModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllCampaigns")
	public ResponseEntity<?> getCampaignsByDepartment() throws MalformedURLException, URISyntaxException {	
		List<CampaignModel> campaignModels = new ArrayList<CampaignModel>();
		campaignModels= campaignService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", campaignModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/getAllUsersInCampaign")
	public ResponseEntity<?> getUserFromMapping(@RequestBody UserCampaignMappingDTO userCampaignMappingModelDTO) throws MalformedURLException, URISyntaxException {	
		List<UserCampaignMappingModel> campaignModels = new ArrayList<UserCampaignMappingModel>();
		campaignModels= campaignService.findUsersFromCampaignName(userCampaignMappingModelDTO.getCampaignname());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", campaignModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
		
	}
	
	@PostMapping(value="/showMappedDiallerCampaigns")
	public ResponseEntity<?> showAllDiallerCampaigns(@RequestBody UserDTO userDTO) throws MalformedURLException, URISyntaxException {	
		List<CampaignModel> campaignModels = new ArrayList<CampaignModel>();
		List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		
		usercampaignModels=campaignService.findByUserextension(userDTO.getUserextension());
		
		for(UserCampaignMappingModel userCampaignMappingModel1:usercampaignModels) {
			CampaignModel campaignModel=campaignService.findByCampaignName(userCampaignMappingModel1.getCampaignname());
			if(campaignModel!=null&&"Progresive".equalsIgnoreCase(campaignModel.getDialMethod())){
				campaignModels.add(campaignModel);
			}
		}
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", campaignModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/activateCampaign")
	public ResponseEntity<?> activateCampaign(@RequestBody CampaignDTO campaignDTO) throws MalformedURLException, URISyntaxException {	
		try {
		List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		usercampaignModels=campaignService.findUsersFromCampaignName(campaignDTO.getCampaignName());
		
		for(UserCampaignMappingModel userCampaignMappingModel1:usercampaignModels) {
			QueueDTO queueDTO=new QueueDTO();
			queueDTO.setQueue(userCampaignMappingModel1.getQueue());
			queueDTO.setIface("Local/"+userCampaignMappingModel1.getUserextension()+"@from-queue");
			ManagerResponse queueAddResponse = actionhandler.queueAdd(queueDTO);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");  
		return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/deActivateCampaign")
	public ResponseEntity<?> deActivateCampaign(@RequestBody CampaignDTO campaignDTO) throws MalformedURLException, URISyntaxException {	
		try {
		List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		usercampaignModels=campaignService.findUsersFromCampaignName(campaignDTO.getCampaignName());
		
		for(UserCampaignMappingModel userCampaignMappingModel1:usercampaignModels) {
			QueueDTO queueDTO=new QueueDTO();
			queueDTO.setQueue(userCampaignMappingModel1.getQueue());
			queueDTO.setIface("Local/"+userCampaignMappingModel1.getUserextension()+"@from-queue");
			ManagerResponse queueAddResponse = actionhandler.queueRemove(queueDTO);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");  
		return new ResponseEntity(map, HttpStatus.OK);
	}
	
}
