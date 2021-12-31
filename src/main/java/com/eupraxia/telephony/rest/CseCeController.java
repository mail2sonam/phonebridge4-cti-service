package com.eupraxia.telephony.rest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.BranchDTO;
import com.eupraxia.telephony.DTO.CSECEDTO;
import com.eupraxia.telephony.Model.BranchModel;
import com.eupraxia.telephony.Model.CSECEModel;
import com.eupraxia.telephony.repositories.CseCeRepository;
import com.eupraxia.telephony.service.Reports.BranchService;


@CrossOrigin
@RestController
@RequestMapping("/ce")
public class CseCeController {
	@Autowired
	BranchService branchService;
	
	@Autowired
	CseCeRepository cseCeRepository;
	
	@Autowired
	ModelMapper mapper;

	@GetMapping(value="/getByPhoneNo")
	public ResponseEntity<?> getByPhoneNo(@RequestParam(name = "phoneNo") String phoneNo) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		CSECEModel CseCeModel = new CSECEModel();
		String parsedPhoneNo = parsePhoneNumber(phoneNo);
		CseCeModel = cseCeRepository.findByPhoneNumber(parsedPhoneNo);
		if(CseCeModel != null) {
		map.put("responseCode", "200");
		map.put("responseMessage", "Cse Data");
		map.put("responseType", "Success");   
		map.put("model", CseCeModel); 
		}
		else {
			map.put("responseCode", "500");
			map.put("responseMessage", "Not able to get data");
			map.put("responseType", "Error");   			
		}
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/saveDispoContact")
	public ResponseEntity<?> saveDispoContact(@RequestBody CSECEDTO cseCEDTO)  {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		CSECEModel CseCeModel = new CSECEModel();
		CseCeModel.setEmpId(cseCEDTO.getEmpId());
		CseCeModel.setEmployeeName(cseCEDTO.getEmployeeName());
		CseCeModel.setBranch(cseCEDTO.getBranch());
		CseCeModel.setPhoneNumber(parsePhoneNumber(cseCEDTO.getPhoneNumber()));
		CSECEModel CseCeModel1 = cseCeRepository.findByPhoneNumber(cseCEDTO.getPhoneNumber());
		if(CseCeModel1 == null) {
			CseCeModel = cseCeRepository.save(CseCeModel);
		map.put("responseCode", "200");
		map.put("responseMessage", "Cse Data");
		map.put("responseType", "Success");   
		map.put("model", CseCeModel); 
		}
		else {
			map.put("responseCode", "500");
			map.put("responseMessage", "Phone Number is already found");
			map.put("responseType", "Error");   
			 return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/getByEmpId")
	public ResponseEntity<?> getByEmpId(@RequestParam(name = "empId") String empId) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		CSECEModel CseCeModel = new CSECEModel();
		CseCeModel = cseCeRepository.findByEmpId(empId);
		if(CseCeModel != null) {
		map.put("responseCode", "200");
		map.put("responseMessage", "Cse Data");
		map.put("responseType", "Success");   
		map.put("model", CseCeModel); 
		}
		else {
			map.put("responseCode", "500");
			map.put("responseMessage", "Not able to get data");
			map.put("responseType", "Error");   			
		}
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/getAll")
	public ResponseEntity<?> getByEmpId() throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		List<CSECEModel> CseCeList = new ArrayList<>();
		CseCeList = cseCeRepository.findAll();
		if(CseCeList != null) {
		map.put("responseCode", "200");
		map.put("responseMessage", "Cse Data");
		map.put("responseType", "Success");   
		map.put("model", CseCeList); 
		}
		else {
			map.put("responseCode", "500");
			map.put("responseMessage", "Not able to get data");
			map.put("responseType", "Error");   			
		}
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	private String parsePhoneNumber(String phoneNumber) {
		
		String lastTenDigits = "";    		 
		if (phoneNumber.length() > 10) {
			lastTenDigits = phoneNumber.substring(phoneNumber.length() - 10);
		} 
		else {
			lastTenDigits = phoneNumber;
		}
		
		return lastTenDigits;
	}
	
	
}
