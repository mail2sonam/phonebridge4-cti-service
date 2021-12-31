package com.eupraxia.telephony.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eupraxia.telephony.DTO.CsvHelper;
import com.eupraxia.telephony.DTO.DiallerDTO;
import com.eupraxia.telephony.DTO.EmergencyScreenDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.Model.CampaignModel;
import com.eupraxia.telephony.Model.DiallerModel;
import com.eupraxia.telephony.Model.MissedCallModel;
import com.eupraxia.telephony.Model.ScheduleCallModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.service.DiallerService;
import com.eupraxia.telephony.service.MissedCallService;
import com.eupraxia.telephony.service.ScheduleCallService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.Reports.UserService;

import springfox.documentation.service.ResponseMessage;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class DiallerController {

@Autowired
private DiallerService fileService;

@Autowired
private UserService userService;


@Autowired
private CampaignService campaignService;


@Autowired
private MissedCallService missedCallService;

@Autowired
private ScheduleCallService scheduleCallService;


@Autowired
ActionHandler actionHandler;



@PostMapping("/upload")
public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("campaignId") Long campaignId,@RequestParam("campaignName") String campaignName) {
	Map<Object, Object> map = new HashMap<>();
 	

  if (CsvHelper.hasCSVFormat(file)) {
    try {
      fileService.save(file,campaignId,campaignName);
      map.put("responseType", "Success"); 
	  map.put("responseCode", "200");
	  map.put("responseMessage", "Success");
	  map.put("responseType", "Success");  
	 return new ResponseEntity(map, HttpStatus.OK); 
      } catch (Exception e) {
    	  map.put("responseCode", "400");
	        map.put("responseMessage", "Data Not Found");
	        map.put("responseType", "Error");       
	        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
	    
     }
  }else {

	  map.put("responseCode", "400");
      map.put("responseMessage", "Data Not Found");
      map.put("responseType", "Error");       
      return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
  
  }
}
@RequestMapping(value="/startDialler",method = RequestMethod.POST)
public ResponseEntity<?> startDialler(@RequestBody DiallerDTO diallerDTO)throws InstantiationException, IllegalAccessException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
	System.out.println("1");
	Map<Object, Object> map = new HashMap<>();
	try {
	DiallerModel diallerModel=fileService.findByStatusAndDate(diallerDTO.getCampaignId());
	if(diallerModel.getPhoneNo()!=null&&!diallerModel.getPhoneNo().isEmpty()) {
		System.out.println("2");
		CampaignModel campaignModel=campaignService.findByCampaignId(Long.valueOf(diallerDTO.getCampaignId()));
		
		OriginateDTO originateDTO=new OriginateDTO();
		originateDTO.setExtension(diallerDTO.getExtension());
		originateDTO.setPhoneNo(diallerModel.getPhoneNo());
		originateDTO.setContext(diallerDTO.getContext());
		originateDTO.setChannel(diallerDTO.getChannel());
		originateDTO.setPriority(diallerDTO.getPriority());
		originateDTO.setCamId(diallerDTO.getCampaignId());
		originateDTO.setCamName(campaignModel.getCampaignName());
		originateDTO.setDialMethod("autodialler");
		originateDTO.setPrefix(diallerDTO.getPrefix());
		originateDTO.setName(diallerModel.getName());
		try {
		actionHandler.originateCall(originateDTO);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		e.printStackTrace();
		}
		try {
		DiallerModel DiallerModel1=fileService.findById(diallerModel.getId());
		
		if(DiallerModel1!=null) {
			System.out.println("3");
			DiallerModel1.setStatus("close");
			DiallerModel1.setExtension(diallerDTO.getExtension());
			DiallerModel1.setCallDate(new Date());
			fileService.saveOrUpdate(DiallerModel1);
		}
		}catch(Exception e) {}
		UserModel userModel=userService.findByUserextension(diallerDTO.getExtension());
		if(userModel!=null) {
			System.out.println("4");
			userModel.setDiallerActive("1");
			userService.saveOrUpdate(userModel);
		}
		 map.put("responseType", "Success"); 
		  map.put("responseCode", "200");
		  map.put("responseMessage", "Success");
		  map.put("responseType", "Success");  
		 return new ResponseEntity(map, HttpStatus.OK); 
	}else {
	
		map.put("responseCode", "400");
	      map.put("responseMessage", "Data Not Found");
	      map.put("responseType", "Error");       
	      return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
	}
	}catch(Exception e) {e.printStackTrace();}
	 return new ResponseEntity(map, HttpStatus.OK);   	
}

@RequestMapping(value="/stopDialler",method = RequestMethod.POST)
public ResponseEntity<?> stopDialler(@RequestBody DiallerDTO diallerDTO)throws InstantiationException, IllegalAccessException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
	Map<Object, Object> map = new HashMap<>();
	UserModel userModel=userService.findByUserextension(diallerDTO.getExtension());
	if(userModel!=null) {
		userModel.setDiallerActive("0");
		userService.saveOrUpdate(userModel);
	}
	map.put("responseType", "Success"); 
		  map.put("responseCode", "200");
		  map.put("responseMessage", "Success");
		  map.put("responseType", "Success");  
		 return new ResponseEntity(map, HttpStatus.OK); 
	
	
}
@RequestMapping(value="/stopDiallerCampaign",method = RequestMethod.POST)
public ResponseEntity<?> stopDiallerCampaign(@RequestBody DiallerDTO diallerDTO)throws InstantiationException, IllegalAccessException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
	Map<Object, Object> map = new HashMap<>();
	List<UserCampaignMappingModel> campaignModel=campaignService.findUsersFromCampaignName(diallerDTO.getCampaignName());
	if(campaignModel!=null) {
		for(UserCampaignMappingModel userCampaignMappingModel:campaignModel) {
			UserModel userModel=userService.findByUserextension(userCampaignMappingModel.getUserextension());
			if(userModel!=null) {
				userModel.setDiallerActive("0");
				userService.saveOrUpdate(userModel);
			}
		}
	}
	map.put("responseType", "Success"); 
		  map.put("responseCode", "200");
		  map.put("responseMessage", "Success");
		  map.put("responseType", "Success");  
		 return new ResponseEntity(map, HttpStatus.OK); 
	
	
}

@RequestMapping(value="/diallerMonitor",method = RequestMethod.POST)
public ResponseEntity<?> diallerMonitor(@RequestBody DiallerDTO diallerDTO)throws InstantiationException, IllegalAccessException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
	Map<Object, Object> map = new HashMap<>();
	String total="";
	String open="";
	String close="";
	List<DiallerModel> diallerModels=new ArrayList<DiallerModel>();
	diallerModels=fileService.findAllByCampaignId(diallerDTO.getCampaignId());
	total=String.valueOf(diallerModels.size());
	List<DiallerModel> diallerModels1=new ArrayList<DiallerModel>();
	diallerModels1=fileService.findByCampaignIdAndStatus(diallerDTO.getCampaignId(),"open");
	open=String.valueOf(diallerModels1.size());
	List<DiallerModel> diallerModels2=new ArrayList<DiallerModel>();
	 diallerModels2=fileService.findByCampaignIdAndStatus(diallerDTO.getCampaignId(),"close");
	 close=String.valueOf(diallerModels2.size());
	
	if(!diallerModels.isEmpty()) {
		 map.put("responseType", "Success"); 
		  map.put("responseCode", "200");
		  map.put("responseMessage", "Success");
		  map.put("responseType", "Success");  
		  map.put("data",diallerModels);
		  map.put("total",total);
		  map.put("open",open);
		  map.put("close",close);
		  
		 return new ResponseEntity(map, HttpStatus.OK); 
	}else {
		map.put("responseCode", "400");
	      map.put("responseMessage", "Data Not Found");
	      map.put("responseType", "Error");       
	      return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
	}
}
@RequestMapping(value="/missedMonitor",method = RequestMethod.POST)
public ResponseEntity<?> missedMonitor(@RequestBody DiallerDTO diallerDTO)throws InstantiationException, IllegalAccessException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
	Map<Object, Object> map = new HashMap<>();
	String total="";
	String open="";
	String close="";
	List<MissedCallModel> diallerModels=new ArrayList<MissedCallModel>();
	diallerModels=missedCallService.findByAllMissedName(diallerDTO.getMissedCampaignName());
	total=String.valueOf(diallerModels.size());
	List<MissedCallModel> diallerModels1=new ArrayList<MissedCallModel>();
	diallerModels1=missedCallService.findByMissedNameAndStatus(diallerDTO.getMissedCampaignName(),"open");
	open=String.valueOf(diallerModels1.size());
	List<MissedCallModel> diallerModels2=new ArrayList<MissedCallModel>();
	 diallerModels2=missedCallService.findByMissedNameAndStatus(diallerDTO.getMissedCampaignName(),"close");
	 close=String.valueOf(diallerModels2.size());
	
	if(!diallerModels.isEmpty()) {
		 map.put("responseType", "Success"); 
		  map.put("responseCode", "200");
		  map.put("responseMessage", "Success");
		  map.put("responseType", "Success");  
		  map.put("data",diallerModels);
		  map.put("total",total);
		  map.put("open",open);
		  map.put("close",close);
		  
		 return new ResponseEntity(map, HttpStatus.OK); 
	}else {
		map.put("responseCode", "400");
	      map.put("responseMessage", "Data Not Found");
	      map.put("responseType", "Error");       
	      return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
	}
}

@RequestMapping(value="/scheduleMonitor",method = RequestMethod.GET)
public ResponseEntity<?> scheduleMonitor()throws InstantiationException, IllegalAccessException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
	Map<Object, Object> map = new HashMap<>();
	String total="";
	String open="";
	String close="";
	List<ScheduleCallModel> diallerModels=new ArrayList<ScheduleCallModel>();
	diallerModels=scheduleCallService.findAll();
	total=String.valueOf(diallerModels.size());
	List<ScheduleCallModel> diallerModels1=new ArrayList<ScheduleCallModel>();
	diallerModels1=scheduleCallService.findByCallStatus("open");
	open=String.valueOf(diallerModels1.size());
	List<ScheduleCallModel> diallerModels2=new ArrayList<ScheduleCallModel>();
	 diallerModels2=scheduleCallService.findByCallStatus("close");
	 close=String.valueOf(diallerModels2.size());
	
	if(!diallerModels.isEmpty()) {
		 map.put("responseType", "Success"); 
		  map.put("responseCode", "200");
		  map.put("responseMessage", "Success");
		  map.put("responseType", "Success");  
		  map.put("data",diallerModels);
		  map.put("total",total);
		  map.put("open",open);
		  map.put("close",close);
		  
		 return new ResponseEntity(map, HttpStatus.OK); 
	}else {
		map.put("responseCode", "400");
	      map.put("responseMessage", "Data Not Found");
	      map.put("responseType", "Error");       
	      return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
	}
}


}
