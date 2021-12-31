package com.eupraxia.telephony.rest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.eupraxia.telephony.DTO.BranchDTO;
import com.eupraxia.telephony.Model.BranchModel;
import com.eupraxia.telephony.service.Reports.BranchService;


@CrossOrigin
@RestController
@RequestMapping("/branch")
public class BranchController {
	@Autowired
	BranchService branchService;
	
	@PostMapping(value = "/save")
    public ResponseEntity<?> saveBranchs(@RequestBody BranchDTO branchDTO) throws MalformedURLException, URISyntaxException {
		String test= branchService.saveBranchs(branchDTO);
		return new ResponseEntity<>(test, HttpStatus.OK); 
	}

	@GetMapping(value="/branches")
	public ResponseEntity<?> getBranches() throws MalformedURLException, URISyntaxException {
		List<BranchModel> brancheModels = new ArrayList<BranchModel>();
		brancheModels= branchService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", brancheModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/id/{id}")
	public ResponseEntity<?> getBranchesById(@PathVariable(name = "id") long id) throws MalformedURLException, URISyntaxException {
		BranchModel brancheModel = new BranchModel();
		brancheModel= branchService.findById(id);
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", brancheModel); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/delete/{id}")
	public ResponseEntity<?> deleteBranchs(@PathVariable(name = "id") long id) throws MalformedURLException, URISyntaxException {
		List<BranchModel> brancheModels = new ArrayList<BranchModel>();
		branchService.deleteBranch(id);
		brancheModels= branchService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", brancheModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	@GetMapping(value="/branchname/{branchname}")
	 public ResponseEntity<?> getBranchByBranchName(@PathVariable(name = "branchname") String branchname) throws MalformedURLException, URISyntaxException {
		String test= branchService.getBranchByBranchName(branchname);
		return new ResponseEntity<>(test, HttpStatus.OK); 
	}
	
	/*
	@GetMapping(value="/openFire")
	public ResponseEntity<?> getOpenFire() throws MalformedURLException, URISyntaxException {
		
		AuthenticationToken authenticationToken = new AuthenticationToken("admin", "etpl@123");
		RestApiClient restApiClient = new RestApiClient("http://192.168.10.210", 9090, authenticationToken);
		//RestApiClient restApiClient = new RestApiClient("localhost", 9090, authenticationToken);
		UserEntity userEntity = new UserEntity("testUsername", "testName", "test@email.com", "p4ssw0rd");
		restApiClient.createUser(userEntity);
		  
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		//map.put("Users", restApiClient.getUsers());   
		//map.put("Users", restApiClient);   
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	*/
}
