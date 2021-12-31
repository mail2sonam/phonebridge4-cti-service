package com.eupraxia.telephony.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.DispositionReportDTO;
import com.eupraxia.telephony.DTO.ReportDTO;
import com.eupraxia.telephony.DTO.SakhiDTO;
import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.DispositionReportModel;
import com.eupraxia.telephony.Model.SakhiModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.service.CaseService;
import com.eupraxia.telephony.service.DispositionReportService;

@CrossOrigin
@RestController
@RequestMapping("/rep")
public class DispositionReportController {
	
	@Autowired
	 private ModelMapper modelMapper;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private DispositionReportService dispositionReportService;
	
	@Autowired
	private ReportsDAO reportsDAO;
	
	
	 @RequestMapping(value="/addGrievancesTickets",method = RequestMethod.POST)
	    public ResponseEntity<?> addGrievancesTickets(@RequestBody DispositionReportDTO dispositionReportDTO)throws InstantiationException, IllegalAccessException{
	 
		 Map<Object, Object> model = new HashMap<>(); 
		 DispositionReportModel drp=new DispositionReportModel();
		 dispositionReportDTO.setCallDate(new Date());
		 drp=modelMapper.map(dispositionReportDTO, DispositionReportModel.class);
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

 		}catch(Exception e) {
 			e.printStackTrace();
 		}
 		drp.setCaseid(caseId);
 		drp = dispositionReportService.save(drp);
		try {
			
			reportsDAO.disposeCall(drp.getCallId());
			
		// CallPropertiesModel callPropertiesModel=callService.findById(sakhiModel.getCallid());
		// if(callPropertiesModel!=null) {
		// callPropertiesModel.setDisposition("Updated");
		// callService.saveOrUpdate(callPropertiesModel);
		// }
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("disposition", dispositionReportDTO);
		return new ResponseEntity(model, HttpStatus.OK);    

	 }

		@RequestMapping(value="/callReports",method = RequestMethod.POST)
		 public ResponseEntity<?> callReports(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{
			Map<Object, Object> model = new HashMap<>(); 
			ArrayList<DispositionReportModel> ReportsModels=new ArrayList<DispositionReportModel>();
			  SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    Date  date1 = null; 
			    Date date2 = null;
				try {
					date1=formatter6.parse(reportDTO.getStartDate());
					date2 = formatter6.parse(reportDTO.getEndDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			ReportsModels=dispositionReportService.getData(date1,date2);
			model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			model.put("data", ReportsModels);
			return new ResponseEntity(model, HttpStatus.OK);   
		}
	 
	    		
	    	
}
