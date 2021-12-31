package com.eupraxia.telephony.rest.Digital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.eupraxia.telephony.DTO.Digital.DigitalDirectoryDTO;
import com.eupraxia.telephony.DTO.Digital.PinCodeDTO;
import com.eupraxia.telephony.Model.Digital.DigitalDirectoryModel;
import com.eupraxia.telephony.Model.Digital.PinCodeModel;
import com.eupraxia.telephony.util.CommonUtil;
import com.eupraxia.telephony.service.Digital.DigitalDirectoryService;

@CrossOrigin
@RestController
@RequestScope
@RequestMapping("/directory")
public class DigitalDirectoryController {
	
	@Autowired
	private DigitalDirectoryService digitalDirectoryService;
	
	
	@GetMapping("/ShowAllMainCategories")
	public ResponseEntity<?> ShowAllMainCategories() throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<String> mainCategories=new ArrayList<>();
		List<DigitalDirectoryDTO> mainCategoriesDTO=new ArrayList<>();
		mainCategories=digitalDirectoryService.findAllMainCategories();
		
		if(CommonUtil.isListNotNullAndEmpty(mainCategories)) {			
			for(String mainCat:mainCategories) {
				DigitalDirectoryDTO digitalDirectory = new DigitalDirectoryDTO();
				digitalDirectory.setMaincategory(mainCat);				
				mainCategoriesDTO.add(digitalDirectory);
			}
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",mainCategoriesDTO);
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
		
	
	@PostMapping("/ShowAllSubCategories")
	public ResponseEntity<?> ShowAllSubCategories(@RequestBody DigitalDirectoryDTO  digitalDirectoryDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<DigitalDirectoryModel> subCategories=new ArrayList<>();
		List<DigitalDirectoryModel> subCategoriesList=new ArrayList<>();
		List<DigitalDirectoryDTO> mainCategoriesDTO=new ArrayList<>();
		subCategories=digitalDirectoryService.findByMaincategory(digitalDirectoryDTO.getMaincategory());
		
	
		List<String> subCategoriestexts = new ArrayList<>();
			
	
		for(DigitalDirectoryModel subCat:subCategories) {
			subCategoriestexts.add(subCat.getSubCategory());
		}
		
		Set<String> uniqueSubCategories = new LinkedHashSet<String>(subCategoriestexts);  
		Iterator<String> setIterator = uniqueSubCategories.iterator();
		while(setIterator.hasNext()){
			DigitalDirectoryModel digitalDirectoryModel = new DigitalDirectoryModel();
			digitalDirectoryModel.setSubCategory(setIterator.next());
			subCategoriesList.add(digitalDirectoryModel);            
        }
		
		
		if(CommonUtil.isListNotNullAndEmpty(subCategoriesList)) {			
		System.out.println("ShowAllSubCategories");
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",subCategoriesList);
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	
	@PostMapping("/ShowAllDistricts")
	public ResponseEntity<?> ShowAllDistricts(@RequestBody DigitalDirectoryDTO  digitalDirectoryDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<DigitalDirectoryModel> districts=new ArrayList<>();
		List<DigitalDirectoryModel> districtsList=new ArrayList<>();
		List<DigitalDirectoryDTO> districtsDTO=new ArrayList<>();
		districts=digitalDirectoryService.findAllDistricts(digitalDirectoryDTO.getMaincategory(),digitalDirectoryDTO.getSubCategory());
		
		List<String> districtTexts = new ArrayList<>();
		
		
		for(DigitalDirectoryModel district:districts) {
			districtTexts.add(district.getDistrict());
		}
		
		Set<String> uniqueDistricts = new LinkedHashSet<String>(districtTexts);  
		Iterator<String> setIterator = uniqueDistricts.iterator();
		while(setIterator.hasNext()){
			DigitalDirectoryModel digitalDirectoryModel = new DigitalDirectoryModel();
			digitalDirectoryModel.setDistrict(setIterator.next());
			districtsList.add(digitalDirectoryModel);
            
        }
		
		
		if(CommonUtil.isListNotNullAndEmpty(districtsList)) {			
			System.out.println("ShowAllDistricts");
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",districtsList);
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	
	@PostMapping("/ShowAllTowns")
	public ResponseEntity<?> ShowAllTowns(@RequestBody DigitalDirectoryDTO  digitalDirectoryDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<DigitalDirectoryModel> towns=new ArrayList<>();
		List<DigitalDirectoryModel> townsList=new ArrayList<>();
		List<DigitalDirectoryDTO> townsDTO=new ArrayList<>();
		towns=digitalDirectoryService.findAllTowns(digitalDirectoryDTO.getMaincategory(),digitalDirectoryDTO.getSubCategory(),digitalDirectoryDTO.getDistrict() );
		
		
		List<String> townTexts = new ArrayList<>();
		
		
		for(DigitalDirectoryModel town:towns) {
			townTexts.add(town.getTown());
		}
		
		Set<String> uniqueTowns = new LinkedHashSet<String>(townTexts);  
		Iterator<String> setIterator = uniqueTowns.iterator();
		while(setIterator.hasNext()){
			DigitalDirectoryModel digitalDirectoryModel = new DigitalDirectoryModel();
			digitalDirectoryModel.setTown(setIterator.next());
			townsList.add(digitalDirectoryModel);
            
        }
		if(CommonUtil.isListNotNullAndEmpty(townsList)) {			
			System.out.println("ShowAllTowns");
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",townsList);
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@PostMapping("/ShowAllFinalDetails")
	public ResponseEntity<?> ShowAllFinalDetails(@RequestBody DigitalDirectoryDTO  digitalDirectoryDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<DigitalDirectoryModel> finalDetails=new ArrayList<>();
		List<DigitalDirectoryDTO> finalDetailsDTO=new ArrayList<>();
		finalDetails=digitalDirectoryService.findFinalDetails(digitalDirectoryDTO.getMaincategory(),digitalDirectoryDTO.getSubCategory(),digitalDirectoryDTO.getDistrict(),digitalDirectoryDTO.getTown() );
		
		if(CommonUtil.isListNotNullAndEmpty(finalDetails)) {			
			System.out.println("ShowAllFinalDetails");
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",finalDetails);
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
}
