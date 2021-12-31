package com.eupraxia.telephony.rest.Digital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.eupraxia.telephony.DTO.Digital.CategoryDTO;
import com.eupraxia.telephony.DTO.Digital.ContactDTO;
import com.eupraxia.telephony.DTO.Digital.DistrictDTO;
import com.eupraxia.telephony.DTO.Digital.PinCodeDTO;
import com.eupraxia.telephony.DTO.Digital.SubCategoryDTO;
import com.eupraxia.telephony.DTO.Digital.TownDTO;
import com.eupraxia.telephony.Model.Digital.CategoryModel;
import com.eupraxia.telephony.Model.Digital.ContactModel;
import com.eupraxia.telephony.Model.Digital.DistrictModel;
import com.eupraxia.telephony.Model.Digital.PinCodeModel;
import com.eupraxia.telephony.Model.Digital.SubCategoryModel;
import com.eupraxia.telephony.Model.Digital.TownModel;
import com.eupraxia.telephony.service.Digital.CategoryService;
import com.eupraxia.telephony.service.Digital.ContactService;
import com.eupraxia.telephony.service.Digital.DistrictService;
import com.eupraxia.telephony.service.Digital.PinCodeService;
import com.eupraxia.telephony.service.Digital.SubCategoryService;
import com.eupraxia.telephony.service.Digital.TownService;
import com.eupraxia.telephony.util.CommonUtil;

@CrossOrigin
@RestController
@RequestScope
@RequestMapping("/digital")
public class DigitalController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private TownService townService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	PinCodeService pinCodeService;
	
	
	
	@PostMapping("/saveCategory")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO  categoryDTO)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		CategoryModel categoryModel=new CategoryModel();
		//categoryModel.setCategoryId(categoryDTO.getCategoryId());
		categoryModel.setCategoryName(categoryDTO.getCategoryName());
		categoryService.saveOrUpdate(categoryModel);
		model.put("responseCode", "200");
		model.put("responseMessage", "Category Inserted");
		model.put("responseType", "Success");
		  return new ResponseEntity(model, HttpStatus.OK); 
	}
	
	@PostMapping("/saveSubCategory")
	public ResponseEntity<?> saveSubCategory(@RequestBody SubCategoryDTO  subCategoryDTO)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		SubCategoryModel subCategoryModel=new SubCategoryModel();
		//subCategoryModel.setSubCategoryId(subCategoryDTO.getSubCategoryId());
		subCategoryModel.setSubCategoryName(subCategoryDTO.getSubCategoryName());
		subCategoryModel.setCategoryId(subCategoryDTO.getCategoryId());
		subCategoryService.saveOrUpdate(subCategoryModel);
		model.put("responseCode", "200");
		model.put("responseMessage", "Sub Category Added");
		model.put("responseType", "Success");
		  return new ResponseEntity(model, HttpStatus.OK); 
	}
	
	@PostMapping("/saveDistrict")
	public ResponseEntity<?> saveDistrict(@RequestBody DistrictDTO  districtDTO)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		DistrictModel districtModel=new DistrictModel();
		//districtModel.setDistrictId(districtDTO.getDistrictId());
		districtModel.setDistrictName(districtDTO.getDistrictName());
		districtService.saveOrUpdate(districtModel);
		model.put("responseCode", "200");
		model.put("responseMessage", "District Added");
		model.put("responseType", "Success");
		  return new ResponseEntity(model, HttpStatus.OK); 
	}
	
	@PostMapping("/saveTown")
	public ResponseEntity<?> saveTown(@RequestBody TownDTO  townDTO)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		TownModel townModel=new TownModel();
		//townModel.setTownId(townDTO.getTownId());
		townModel.setTownName(townDTO.getTownName());
		townModel.setDistrictId(townDTO.getDistrictId());
		townService.saveOrUpdate(townModel);
		model.put("responseCode", "200");
		model.put("responseMessage", "Town Added");
		model.put("responseType", "Success");
		  return new ResponseEntity(model, HttpStatus.OK); 
	}
	
	@PostMapping("/saveContacts")
	public ResponseEntity<?> saveContacts(@RequestBody ContactDTO  contactDTO)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		ContactModel contactModel=new ContactModel();
		//contactModel.setContactId(contactDTO.getContactId());
		contactModel.setContactNumber(contactDTO.getContactNumber());
		contactModel.setSmsNumber(contactDTO.getSmsNumber());
		contactModel.setWhatsupNumber(contactDTO.getWhatsupNumber());
		contactModel.setTownId(contactDTO.getTownId());
		contactModel.setDistrictId(contactDTO.getDistrictId());
		contactModel.setCategoryId(contactDTO.getCategoryId());
		contactModel.setSubCategoryId(contactDTO.getSubCategoryId());
		contactService.saveOrUpdate(contactModel);
		model.put("responseCode", "200");
		model.put("responseMessage", "Contact Added");
		model.put("responseType", "Success");
		  return new ResponseEntity(model, HttpStatus.OK); 
	}
	
	
	
	
	@GetMapping("/ShowAllCategories")
	public ResponseEntity<?> showAllCategories() throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<CategoryModel> CategoryModels=new ArrayList<>();
		CategoryModels=categoryService.findByAll();
		if(CommonUtil.isListNotNullAndEmpty(CategoryModels)) {
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",CategoryModels );
			 return new ResponseEntity(model, HttpStatus.OK); 
			 
		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
		        return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@PostMapping("/ShowAllSubCategories")
	public ResponseEntity<?> showAllCategories(@RequestBody CategoryDTO  categoryDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<SubCategoryModel> CategoryModels=new ArrayList<>();
		CategoryModel categoryModel = new CategoryModel();
		categoryModel = categoryService.findByCategoryName(categoryDTO.getCategoryName());
		CategoryModels=subCategoryService.findByCategoryId(categoryModel.getCategoryId());
		if(CommonUtil.isListNotNullAndEmpty(CategoryModels)) {
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",CategoryModels );
			 return new ResponseEntity(model, HttpStatus.OK); 
			 
		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
		        return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@GetMapping("/ShowAllDistricts")
	public ResponseEntity<?> showAllDistricts() throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		List<DistrictModel> districtModels=new ArrayList<>();
		districtModels=districtService.findAll();
		if(CommonUtil.isListNotNullAndEmpty(districtModels)) {
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",districtModels );
			 return new ResponseEntity(model, HttpStatus.OK); 
			 
		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
		        return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@PostMapping("/ShowAllTowns")
	public ResponseEntity<?> showAllTowns(@RequestBody DistrictDTO  districtDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		DistrictModel districtModel = new DistrictModel();
		districtModel = districtService.findByDistrictName(districtDTO.getDistrictName());
		List<TownModel> townModels=new ArrayList<>();
		townModels=townService.findByDistrictId(districtModel.getDistrictId());
		if(CommonUtil.isListNotNullAndEmpty(townModels)) {
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",townModels );
			 return new ResponseEntity(model, HttpStatus.OK); 
			 
		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
		        return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@PostMapping("/ShowContacts")
	public ResponseEntity<?> showContacts(@RequestBody ContactDTO  contactDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		
		TownModel townModel = new TownModel();
		townModel = townService.findByTownName(contactDTO.getTownName());
		
		SubCategoryModel subCategoryModel = new SubCategoryModel();
		subCategoryModel =subCategoryService.findBySubCategoryName(contactDTO.getSubCategoryName());
		
		List<ContactModel> contactModels=new ArrayList<>();
		List<ContactModel> contactModelsFinal=new ArrayList<>();
		contactModels=contactService.findByTownIdAndSubCategoryId(townModel.getTownId(),subCategoryModel.getSubCategoryId());
		
		for(ContactModel contacts:contactModels) { 
			
			ContactModel contactModel = new ContactModel();
			contactModel.setAddress(contacts.getAddress()==null?" ":contacts.getAddress());
			contactModel.setContactName(contacts.getContactName()==null?" ":contacts.getContactName());
			contactModel.setContactNumber(contacts.getContactNumber()==null?" ":contacts.getContactNumber());
			contactModel.setSmsNumber(contacts.getSmsNumber()==null?" ":contacts.getSmsNumber());
			contactModel.setWhatsupNumber(contacts.getWhatsupNumber()==null?" ":contacts.getWhatsupNumber());
			contactModelsFinal.add(contactModel);
		}
		
		if(CommonUtil.isListNotNullAndEmpty(contactModelsFinal)) {		
			
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",contactModelsFinal );
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	 
	
	@PostMapping("/ShowPinCodes")
	public ResponseEntity<?> showPinCodes(@RequestBody PinCodeDTO  pinCodeDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		
		List<PinCodeModel> pinCodes = new ArrayList<>();
		pinCodes = pinCodeService.findByDistrictContainingAndTownContaining(pinCodeDTO.getDistrict(),pinCodeDTO.getTown());		
	
		if(CommonUtil.isListNotNullAndEmpty(pinCodes)) {			
			
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",pinCodes );
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	
	@GetMapping("/ShowDistrictFromPinCode")
	public ResponseEntity<?> ShowDistrictFromPinCode() throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		
		List<String> pinCodes = new ArrayList<>();
		List<PinCodeDTO> resultDistricts = new ArrayList<>();
		pinCodes = pinCodeService.findAllDistrictFromPinCode();		
	
		if(CommonUtil.isListNotNullAndEmpty(pinCodes)) {			
			for(String district:pinCodes) {
				PinCodeDTO pinCodeDTO = new PinCodeDTO();
				pinCodeDTO.setDistrict(district);
				resultDistricts.add(pinCodeDTO);
			}
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",resultDistricts);
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@PostMapping("/ShowTaluksFromPinCode")
	public ResponseEntity<?> ShowTaluksFromPinCode(@RequestBody PinCodeDTO  pinCodeDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		
		List<PinCodeModel> pinCodes = new ArrayList<>();
		List<PinCodeModel> taluksList = new ArrayList<>();
		List<String> taluks = new ArrayList<>();
		pinCodes = pinCodeService.findAllTaluksFromPinCode(pinCodeDTO.getDistrict());		
	
		
		
		for(PinCodeModel pincode:pinCodes) {
			if(pincode.getTaluk()!=null) {
			taluks.add(pincode.getTaluk());
			}
		}
		
		List<String> listWithoutDuplicates = taluks.stream().distinct().collect(Collectors.toList());
		
		//listWithoutDuplicates.forEach(taluk->System.out.println(taluk));
		
		
		for(String listBuild:listWithoutDuplicates) {
		
			PinCodeModel pinCodeModel = new PinCodeModel();
			pinCodeModel.setTaluk(listBuild);
			taluksList.add(pinCodeModel);
            
        }
       
		
		if(CommonUtil.isListNotNullAndEmpty(taluksList)) {			
			
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",taluksList);
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@PostMapping("/ShowTownsFromPinCode")
	public ResponseEntity<?> ShowTownsFromPinCode(@RequestBody PinCodeDTO  pinCodeDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		
		List<PinCodeModel> pinCodes = new ArrayList<>();
		List<PinCodeModel> townsList = new ArrayList<>();
		List<String> towns = new ArrayList<>();
		pinCodes = pinCodeService.findAllTownsFromPinCode(pinCodeDTO.getTaluk());		
	
		
		//for(PinCodeModel pincode:pinCodes) {
		//	towns.add(pincode.getTown());
		//}
		
		//List<String> listWithoutDuplicates = towns.stream().distinct().collect(Collectors.toList());
		
		/*
		
		Set<String> uniqueTowns = new LinkedHashSet<String>(towns);  
		Iterator<String> setIterator = uniqueTowns.iterator();
		while(setIterator.hasNext()){
			PinCodeModel pinCodeModel = new PinCodeModel();
			pinCodeModel.setTown(setIterator.next());
			townsList.add(pinCodeModel);
            
        }
        */
		if(CommonUtil.isListNotNullAndEmpty(pinCodes)) {			
			
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",pinCodes.stream().distinct().collect(Collectors.toList()));
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	@PostMapping("/ShowPinsFromPinCode")
	public ResponseEntity<?> ShowPinsFromPinCode(@RequestBody PinCodeDTO  pinCodeDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		Map<Object, Object> model = new HashMap<>();
		
		List<PinCodeModel> pinCodes = new ArrayList<>();
		List<String> pins = new ArrayList<>();
		pinCodes = pinCodeService.findAllPinsFromPinCode(pinCodeDTO.getTown());	
	
		//for(PinCodeModel pincode:pinCodes) {
		//	pins.add(String.valueOf(pincode.getPinCode()));
		//}
		
		//List<String> listWithoutDuplicates = pins.stream().distinct().collect(Collectors.toList());
		
		/*
		Set<String> uniquePins = new LinkedHashSet<String>(pins);  
		Iterator<String> setIterator = uniquePins.iterator();
		while(setIterator.hasNext()){
			PinCodeModel pinCodeModel = new PinCodeModel();
			pinCodeModel.setTown(setIterator.next());
			pinCodes.add(pinCodeModel);
            
        }
		 
		 */
		if(CommonUtil.isListNotNullAndEmpty(pinCodes)) {			
			
			model.put("responseCode", "200");
			model.put("responseMessage", "Data");
			model.put("responseType", "Success");
			model.put("data",pinCodes.stream().distinct().collect(Collectors.toList()));
			return new ResponseEntity(model, HttpStatus.OK); 

		}else {
			model.put("responseCode", "400");
			model.put("responseMessage", "Data Not Found");
			model.put("responseType", "Error");       
			return new ResponseEntity(model, HttpStatus.BAD_REQUEST);  
		}
	}
	
	
}
