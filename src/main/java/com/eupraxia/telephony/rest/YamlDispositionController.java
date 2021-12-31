package com.eupraxia.telephony.rest;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eupraxia.telephony.DTO.DispositionDTO;
import com.eupraxia.telephony.DTO.DispositionReportDTO;
import com.eupraxia.telephony.DTO.DispositionYamlDTO;
import com.eupraxia.telephony.DTO.MainDispositionYamlDTO;
import com.eupraxia.telephony.DTO.YamlDataDTO;
import com.eupraxia.telephony.DTO.YamlDataReportDTO;
import com.eupraxia.telephony.Model.DispoFromYaml;
import com.eupraxia.telephony.Model.DispositionModel;
import com.eupraxia.telephony.Model.DispositionReportModel;
import com.eupraxia.telephony.Model.YamlDataModel;
import com.eupraxia.telephony.Model.Reports.YamlDataReportModel;
import com.eupraxia.telephony.repositories.YamlDataRepository;
import com.eupraxia.telephony.repositories.Reports.YamlDataReportRepository;
import com.eupraxia.telephony.service.DispositionService;
import com.eupraxia.telephony.util.CommonUtil;
import com.eupraxia.telephony.util.YamlUtil;


@CrossOrigin
@RestController
@RequestMapping("/yml")
public class YamlDispositionController {
	
@Autowired 
private DispositionService dispositionService;

@Autowired
YamlDataRepository yamlDataRepository;

@Autowired
YamlDataReportRepository yamlDataReportRepository;
	

	
@RequestMapping(value="/findByClient",method = RequestMethod.POST)

public ResponseEntity<?> findByClient(@RequestBody YamlDataDTO yamlDataDTO){		
	List<YamlDataModel> yamlDataList = new ArrayList();
	yamlDataList = yamlDataRepository.findByClient(yamlDataDTO.getClient());
	Map<Object, Object> model = new HashMap<>();
	if(!yamlDataList.isEmpty()) {
		 Set<YamlDataModel> uniqueMainDispoSet = yamlDataList
	                .stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlDataModel::getMainDispo))));
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  		
		model.put("disposition", uniqueMainDispoSet);
	}
	else {
		model.put("responseCode", "400");
		model.put("responseMessage", "Not able to Retrieve Data");
		model.put("responseType", "Error"); 
		model.put("disposition", yamlDataList);

	}
	return new ResponseEntity(model, HttpStatus.OK);   
}

@RequestMapping(value="/findByMainDispo",method = RequestMethod.POST)
	public ResponseEntity<?> findByMainDispo(@RequestBody YamlDataDTO yamlDataDTO){
	List<YamlDataModel> yamlDataList = new ArrayList();
	yamlDataList = yamlDataRepository.findByMainDispo(yamlDataDTO.getMainDispo());
	Map<Object, Object> model = new HashMap<>();
	if(!yamlDataList.isEmpty()) {
		 Set<YamlDataModel> uniqueSubDispoSet = yamlDataList
	                .stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlDataModel::getSubDispo))));
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("disposition", uniqueSubDispoSet);	
	}
	else {
		model.put("responseCode", "400");
		model.put("responseMessage", "Not able to Retrieve Data");
		model.put("responseType", "Error"); 
		model.put("disposition", yamlDataList);	

	}
	return new ResponseEntity(model, HttpStatus.OK);   
}


@RequestMapping(value="/findByCampaign",method = RequestMethod.POST)
public ResponseEntity<?> findByCampaign(@RequestBody YamlDataDTO yamlDataDTO){

	List<YamlDataModel> yamlDataList = new ArrayList();
	yamlDataList = yamlDataRepository.findByCampaign(yamlDataDTO.getCampaign());
	Map<Object, Object> model = new HashMap<>();
	if(!yamlDataList.isEmpty()) {	
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("disposition", yamlDataList. stream().distinct().collect(Collectors.toList()));
	}
	else {
		model.put("responseCode", "400");
		model.put("responseMessage", "Not able to Retrieve Data");
		model.put("responseType", "Error"); 
		model.put("disposition", yamlDataList);	
	}
	return new ResponseEntity(model, HttpStatus.OK);   
}


@RequestMapping(value="/findBySubDispo",method = RequestMethod.POST)
public ResponseEntity<?> findBySubDispo(@RequestBody YamlDataDTO yamlDataDTO){
	List<YamlDataModel> yamlDataList = new ArrayList();
	yamlDataList = yamlDataRepository.findBySubDispo(yamlDataDTO.getSubDispo());
	Map<Object, Object> model = new HashMap<>();
	if(!yamlDataList.isEmpty()) {	
		 Set<YamlDataModel> uniqueSubSubDispoSet = yamlDataList
	                .stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlDataModel::getSubSubDispo))));
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("disposition", uniqueSubSubDispoSet);
	}
	else {
		model.put("responseCode", "400");
		model.put("responseMessage", "Not able to Retrieve Data");
		model.put("responseType", "Error"); 
		model.put("disposition", yamlDataList);

	}
	return new ResponseEntity(model, HttpStatus.OK);   
}

@RequestMapping(value="/findBySubSubDispo",method = RequestMethod.POST)
public ResponseEntity<?> findBySubSubDispo(@RequestBody YamlDataDTO yamlDataDTO){
	List<YamlDataModel> yamlDataList = new ArrayList();
	yamlDataList = yamlDataRepository.findBySubSubDispo(yamlDataDTO.getSubSubDispo());
	Map<Object, Object> model = new HashMap<>();
	if(!yamlDataList.isEmpty()) {		
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("disposition", yamlDataList. stream().distinct().collect(Collectors.toList()));
	}
	else {
		model.put("responseCode", "400");
		model.put("responseMessage", "Not able to Retrieve Data");
		model.put("responseType", "Error"); 
		model.put("disposition", yamlDataList);

	}
	return new ResponseEntity(model, HttpStatus.OK);   
}

@GetMapping(value="/findAllDisposByPhoneNo")
public ResponseEntity<?> findAllDisposByPhoneNo(@RequestParam("phoneNo") String phoneNo){
	List<YamlDataReportModel> yamlDataReportList = new ArrayList();
	 try {
		 phoneNo=phoneNo.substring(phoneNo.length()-10,phoneNo.length());
		
	 }catch(Exception e) {
		 
	 }
	
	yamlDataReportList = yamlDataReportRepository.findByPhoneNoOrderByCallTimeAsc(phoneNo);
	Map<Object, Object> model = new HashMap<>();
	if(!yamlDataReportList.isEmpty()) {		
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		//model.put("disposition", yamlDataReportList.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlDataReportModel::getCallTime)))));
		model.put("disposition", yamlDataReportList);
	}
	else {
		model.put("responseCode", "400");
		model.put("responseMessage", "Not able to Retrieve Data");
		model.put("responseType", "Error"); 
		model.put("disposition", yamlDataReportList);

	}
	return new ResponseEntity(model, HttpStatus.OK);   
}



@RequestMapping(value="/saveDispo",method = RequestMethod.POST)
   public ResponseEntity<?> saveDispo(@RequestBody YamlDataReportDTO yamlDataReportDTO){

	 Map<Object, Object> model = new HashMap<>(); 
	 YamlDataReportModel yamlDataReportModel=new YamlDataReportModel();
	 try {
		 String pho=yamlDataReportDTO.getPhoneNo();
		 pho=pho.substring(pho.length()-10,pho.length());
		 yamlDataReportModel.setPhoneNo(pho);
	 }catch(Exception e) {
		 yamlDataReportModel.setPhoneNo(yamlDataReportDTO.getPhoneNo());
	 }
	
	 yamlDataReportModel.setCallId(yamlDataReportDTO.getCallId());
	 yamlDataReportModel.setCallTime(LocalDateTime.now().toString());
	 yamlDataReportModel.setRemarks(yamlDataReportDTO.getRemarks());
	 yamlDataReportModel.setCampaign(yamlDataReportDTO.getCampaign());
	 yamlDataReportModel.setClient(yamlDataReportDTO.getClient());
	 yamlDataReportModel.setMainDispo(yamlDataReportDTO.getMainDispo());
	 yamlDataReportModel.setSubDispo(yamlDataReportDTO.getSubDispo());
	 yamlDataReportModel.setSubSubDispo(yamlDataReportDTO.getSubSubDispo());
	 yamlDataReportModel.setCustomerName(yamlDataReportDTO.getCustomerName());
	 yamlDataReportModel.setDistrict(yamlDataReportDTO.getDistrict());
	 yamlDataReportModel.setState(yamlDataReportDTO.getState());
	 yamlDataReportModel.setCountry(yamlDataReportDTO.getCountry());
	 yamlDataReportModel = yamlDataReportRepository.save(yamlDataReportModel);
	 
	 if(yamlDataReportModel != null) {
		 model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			model.put("data", yamlDataReportModel);
		}
		else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Not able to save Data");
			model.put("responseType", "Error"); 
			model.put("data", new YamlDataReportModel());

		}
		return new ResponseEntity(model, HttpStatus.OK);    
	 }
		
}