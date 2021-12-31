package com.eupraxia.telephony.rest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asteriskjava.manager.response.ManagerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.DispositionDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.SakhiDTO;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.DispositionModel;
import com.eupraxia.telephony.Model.SakhiModel;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.CaseService;
import com.eupraxia.telephony.service.DispositionService;
import com.eupraxia.telephony.service.SakhiService;
import com.eupraxia.telephony.util.CommonUtil;



@CrossOrigin
@RestController
@RequestMapping("/api")
public class SakhiController {
	@Autowired 
	private SakhiService sakhiService;
	
	@Autowired
	 private ModelMapper modelMapper;
	
	@Autowired
	private CallService callService;
	
	@Autowired
	private ReportsDAO reportsDAO;
	
	@Autowired
	private CaseService caseService;
	
	 @RequestMapping(value="/addGrievancesTicket",method = RequestMethod.POST)
	    public ResponseEntity<?> addsakhiTicket(@RequestBody SakhiDTO sakhiDTO)throws InstantiationException, IllegalAccessException{
	    		
	    		Map<Object, Object> model = new HashMap<>(); 
	    		SakhiModel sakhiModel = new SakhiModel();
	    		
	    		sakhiModel = modelMapper.map(sakhiDTO, SakhiModel.class);
	    		String caseId="TN/WHL/";
    			
	    		try {
	    			Date date=new Date();
	    			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    			int datevalue=localDate.getDayOfMonth();
	    			int month = localDate.getMonthValue();
	    			int year=localDate.getYear();
	    			String yearString =Integer.toString(year);
	    			String datemonthyear=datevalue+"/"+month+"/"+year;
	    			CaseModel caseModel=caseService.findByDate(datemonthyear);
	    			String caseId1=null;
	    			String datepad=String.format("%02d", datevalue);
	    			String monthpad=String.format("%02d", month);
	    			String lastTwoDigits = yearString.substring(2); 
	    			
	    			if(caseModel==null) {
	    				caseId=caseId.concat(datepad+"/"+monthpad+"/"+lastTwoDigits+"/0001");
	    				CaseModel caseModel1=new CaseModel();
	    				caseModel1.setCount(1);
	    				caseModel1.setDate(datemonthyear);
	    				caseService.saveOrUpdate(caseModel1);
	    				//caseId1=String.format("%04d", 0);
	    			
	    			}else {
	    				caseId1=String.format("%04d", caseModel.getCount()+1);
	    				caseId=caseId.concat(datepad+"/"+monthpad+"/"+lastTwoDigits+"/"+caseId1);
	    			    caseModel.setCount(caseModel.getCount()+1);
	    				caseService.saveOrUpdate(caseModel);
	    			
	    			}

	    		}catch(Exception e) {}
	    		sakhiModel.setCaseid(caseId);
	    		
	    		 sakhiModel = sakhiService.saveOrUpdateSakhiModelDetails(sakhiModel);
	    		try {
	    			
	    			reportsDAO.disposeCall(sakhiModel.getCallid());
	    			
	    		// CallPropertiesModel callPropertiesModel=callService.findById(sakhiModel.getCallid());
	    		// if(callPropertiesModel!=null) {
	    		// callPropertiesModel.setDisposition("Updated");
	    		// callService.saveOrUpdate(callPropertiesModel);
	    		// }
	    		}catch(Exception e) {}
				model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("disposition", sakhiDTO);
	    		return new ResponseEntity(model, HttpStatus.OK);    
	    
	    }
	 
	   @RequestMapping(value="/showAllGrievancesTickets",method = RequestMethod.GET)
	    public ResponseEntity<?> showAllsakhiTickets(@RequestParam("phonenumber") String phonenumber )throws InstantiationException, IllegalAccessException{
	    	
	    	List<DispositionDTO> dispositionList = new ArrayList<>();
	    	SakhiDTO sakhiDTO = new SakhiDTO();
	    	Map<Object, Object> model = new HashMap<>();
	    	List<SakhiModel> disposition = sakhiService.findAllTickets(phonenumber);
	    	if(CommonUtil.isListNotNullAndEmpty(disposition)) {          
	    		dispositionList = CommonUtil.copyListBeanProperty(disposition, SakhiDTO.class);
	    	}
	    	
	    		model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("disposition", dispositionList);
	    		return new ResponseEntity(model, HttpStatus.OK);       
	    }
	   
	   
	   
	   @RequestMapping(value="/showAllGrievancesTicketsForExtension",method = RequestMethod.GET)
	    public ResponseEntity<?> showAllGrievancesTicketsForExtension(@RequestParam("phonenumber") String phonenumber,@RequestParam("extension") String extension )throws InstantiationException, IllegalAccessException{
	    	
	    	List<DispositionDTO> dispositionList = new ArrayList<>();
	    	SakhiDTO sakhiDTO = new SakhiDTO();
	    	Map<Object, Object> model = new HashMap<>();
	    	List<SakhiModel> disposition = sakhiService.findAllTicketsForExtension(phonenumber,extension);
	    	if(CommonUtil.isListNotNullAndEmpty(disposition)) {          
	    		dispositionList = CommonUtil.copyListBeanProperty(disposition, SakhiDTO.class);
	    	}
	    	
	    		model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("disposition", dispositionList);
	    		return new ResponseEntity(model, HttpStatus.OK);       
	    }
	   
	   @RequestMapping(value="/showAllGrievancesTicketsForOnlyExtension",method = RequestMethod.GET)
	    public ResponseEntity<?> showAllGrievancesTicketsForOnlyExtension(@RequestParam("extension") String extension )throws InstantiationException, IllegalAccessException{
	    	
	    	List<DispositionDTO> dispositionList = new ArrayList<>();
	    	SakhiDTO sakhiDTO = new SakhiDTO();
	    	Map<Object, Object> model = new HashMap<>();
	    	List<SakhiModel> disposition = sakhiService.findAllGrievancesTicketsForOnlyExtension(extension);
	    	if(CommonUtil.isListNotNullAndEmpty(disposition)) {          
	    		dispositionList = CommonUtil.copyListBeanProperty(disposition, SakhiDTO.class);
	    	}
	    	
	    		model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("disposition", dispositionList);
	    		return new ResponseEntity(model, HttpStatus.OK);       
	    }
	   
}
