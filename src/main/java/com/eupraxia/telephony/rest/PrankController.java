package com.eupraxia.telephony.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.eupraxia.telephony.DTO.BranchDTO;
import com.eupraxia.telephony.DTO.PrankDTO;
import com.eupraxia.telephony.Model.BranchModel;
import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.PrankModel;
import com.eupraxia.telephony.service.PrankService;

@CrossOrigin
@RestController
@RequestScope
@RequestMapping("/prank")
public class PrankController {
	@Autowired
	private PrankService prankservice;
	
	@PostMapping(value = "/save")
    public ResponseEntity<?> savePrank(@RequestBody PrankDTO prankDTO) throws MalformedURLException, URISyntaxException {
		PrankModel prankModel1=prankservice.findByPhoneNo(prankDTO.getPhoneNo());
		if(prankModel1==null) {
			PrankModel prankModel=new PrankModel();
			prankModel.setId(prankDTO.getId());
			prankModel.setPhoneNo(prankDTO.getPhoneNo());
			prankservice.save(prankModel);
		}
		
		Map<Object, Object> map = new HashMap<>();
		  map.put("responseType", "Success"); 
    	  map.put("responseCode", "200");
    	  map.put("responseMessage", "Success");
    	  map.put("responseType", "Success");  
    	  return new ResponseEntity(map, HttpStatus.OK); 
	}
	
	@GetMapping("/checkPhoneNumber")
	public String checkPhoneNumber(@RequestParam("phoneNo") String phoneNo) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	if(phoneNo.length()>=10) {
		phoneNo=phoneNo.substring(phoneNo.length()-10,phoneNo.length());
	}	
		PrankModel ivrModel=prankservice.findByPhoneNo(phoneNo);
		if(ivrModel!=null) {
			return "1";
		}else {
			return "0";
		}
		
	}
	
	@GetMapping(value="/showPranks")
	public ResponseEntity<?> showPranks() throws MalformedURLException, URISyntaxException {
		List<PrankModel> prankModel = new ArrayList<PrankModel>();
		prankModel= prankservice.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", prankModel); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/findByPhoneNumber")
	public ResponseEntity<?> findByPhoneNumber(@RequestBody PrankDTO prankDTO) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		PrankModel prankModel=prankservice.findByPhoneNo(prankDTO.getPhoneNo());
		if (prankModel==null) {
		       map.put("responseCode", "400");
		        map.put("responseMessage", "Data Not Found");
		        map.put("responseType", "Error");       
		        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
		      }else {
		    	  map.put("responseType", "Success"); 
		    	  map.put("responseCode", "200");
		    	  map.put("responseMessage", "Success");
		    	  map.put("responseType", "Success");  
		    	  map.put("model", prankModel);
		    	  return new ResponseEntity(map, HttpStatus.OK); 
		      }
	}
	
	@PostMapping(value = "/deleteByPhoneNumber")
	public ResponseEntity<?> deleteByPhoneNumber(@RequestBody PrankDTO prankDTO) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		prankservice.deleteByPhoneNo(prankDTO.getPhoneNo());
		    	  map.put("responseType", "Success"); 
		    	  map.put("responseCode", "200");
		    	  map.put("responseMessage", "Success");
		    	  map.put("responseType", "Success");  
		    	 return new ResponseEntity(map, HttpStatus.OK); 
		      }
	
	@PostMapping(value = "/deleteById")
	public ResponseEntity<?> deleteById(@RequestBody PrankDTO prankDTO) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		prankservice.deleteById(prankDTO.getId());
		    	  map.put("responseType", "Success"); 
		    	  map.put("responseCode", "200");
		    	  map.put("responseMessage", "Success");
		    	  map.put("responseType", "Success");  
		    	 return new ResponseEntity(map, HttpStatus.OK); 
		      }
	}
	

