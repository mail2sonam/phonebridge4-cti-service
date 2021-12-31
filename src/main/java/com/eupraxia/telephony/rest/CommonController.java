package com.eupraxia.telephony.rest;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import com.eupraxia.telephony.repositories.Reports.ReportsRepository;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.FavariteDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;

import com.eupraxia.telephony.Model.CallInformationModel;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.CdrReportModel;
import com.eupraxia.telephony.Model.FavoriteModel;
import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.IvrReportModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.MissedCallModel;
import com.eupraxia.telephony.Model.OtpModel;
import com.eupraxia.telephony.Model.QueueModel;
import com.eupraxia.telephony.Model.ScheduleCallModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;
import com.eupraxia.telephony.Reports.DAO.CampaignDao;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.Reports.DAO.UserDao;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.repositories.FavoriteRepository;
import com.eupraxia.telephony.repositories.IvrReportRepository;
import com.eupraxia.telephony.repositories.IvrRepository;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.CaseService;
import com.eupraxia.telephony.service.CdrReportService;
import com.eupraxia.telephony.service.IvrService;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.MissedCallService;
import com.eupraxia.telephony.service.OtpService;
import com.eupraxia.telephony.service.QueueService;
import com.eupraxia.telephony.service.ScheduleCallService;
import com.eupraxia.telephony.util.CommonUtil;
import com.eupraxia.telephony.util.QueryCriteria;

@CrossOrigin
@RestController
@RequestMapping("/call")
public class CommonController {
	@Autowired
	ReportsRepository reportsRepositoryho;
	@Autowired
	IvrRepository ivrRepository;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	private MissedCallService missedCallService;
	
	@Autowired
	private IvrService ivrService;
	
	@Autowired
	CallService callService;
	
	@Autowired
	OtpService otpService;
	
	@Autowired
	private QueueService queueService;
	
	@Autowired 
	private ScheduleCallService scheduleCallService;
	
	@Autowired
	private ActionHandler actionHandler;
	
	@Autowired 
	private CdrReportService cdrReportService;
	
	@Autowired
	private IvrReportRepository ivrReportRepository;
	
	@Autowired
	ReportsDAO reportsDAO; 
	
	@Autowired 
	QueryCriteria queryCriteria;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private CampaignDao campaignDao;
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	@Value("${asterisk.host}")
	private String asteriskHost;
	
	
	@PostMapping("/saveName")
	public ResponseEntity<?> saveName(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		CallPropertiesModel callPropertiesModel=callService.findById(originateDTO.getId());
		if(callPropertiesModel!=null) {
			callPropertiesModel.setName(originateDTO.getName());
			callService.saveOrUpdate(callPropertiesModel);
		}
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping("/callInfo")
	public ResponseEntity<?> callInfo(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		
		//System.out.println("Hai");
		Map<Object, Object> model = new HashMap<>();
	model.put("responseType", "Success"); 
 	CallPropertiesModel callPropertiesModel1=callService.findByExtension(originateDTO.getExtension());
 	
 	if(callPropertiesModel1==null) {
 		 try {
 			 UserModel userModel=new UserModel();
 			 userModel=userDao.findByUserextension(originateDTO.getExtension());
 			 
 		 if(userModel.getUsername()!=null) {
 				if(userModel.getInActive()==30) {
 					userModel.setInActive(0);
 					userDao.saveOrUpdateUserDetails(userModel);
 					MissedCallModel missedCallModel=null;
 					LoginModel loginModel=loginService.findByExtensionAndStatus(originateDTO.getExtension(),"login");
 					if(loginModel!=null&&loginModel.getStatus().equalsIgnoreCase("Ready")) {
 						List<UserCampaignMappingModel> ucm=new ArrayList<UserCampaignMappingModel>();
 						ucm=campaignDao.findByUserextensionAndDialMethod(originateDTO.getExtension(),"MissedCall");
 						for(UserCampaignMappingModel um:ucm) {
 							if(missedCallModel==null) {
 								missedCallModel=missedCallService.findByMissedName(um.getCampaignname());
 							}
 						}
 					if(missedCallModel==null) {
 							ArrayList<ScheduleCallModel> scheduleCallModel1=new ArrayList<ScheduleCallModel>();
 						scheduleCallModel1=scheduleCallService.getScheduleCallFinalData(originateDTO.getExtension());
 					 
 						if(!scheduleCallModel1.isEmpty()) {
 					 		ScheduleCallModel scheduleCallModel=new ScheduleCallModel();
 					 		scheduleCallModel=scheduleCallModel1.get(0);
 					 		originateDTO.setContext(userModel.getContext());
 	        				originateDTO.setChannel("SIP/".concat(userModel.getUserextension()));
 	        				originateDTO.setPriority(1);
 	        				originateDTO.setPrefix(userModel.getPrefix());
 					 		originateDTO.setPhoneNo(scheduleCallModel.getPhoneNumber());
 							originateDTO.setDialMethod("schedulecall");
 							originateDTO.setCamName(scheduleCallModel.getCampaignName());
 							actionHandler.originateCall(originateDTO);
 							scheduleCallModel.setCallStatus("close");
 					 		scheduleCallService.saveOrUpdate(scheduleCallModel);
 					 	}
 					}else {
 						originateDTO.setContext(userModel.getContext());
 	    				originateDTO.setChannel("SIP/".concat(userModel.getUserextension()));
 	    				originateDTO.setPriority(1);
 	    				originateDTO.setPrefix(userModel.getPrefix());
 						originateDTO.setPhoneNo(missedCallModel.getPhoneNumber());
 						originateDTO.setDialMethod("missedcall");
 						originateDTO.setCamName(missedCallModel.getCampaignName());
 						actionHandler.originateCall(originateDTO);
 						missedCallModel.setCallStatus("close");
 					 	missedCallService.insertMissedCallRequest(missedCallModel);
 					}
 					}
 				}else {
 					int count=userModel.getInActive()+1;
 					userModel.setInActive(count);
 					userDao.saveOrUpdateUserDetails(userModel);
 				}
 			 }
 		 }catch(Exception e) {
 			 e.printStackTrace();
 		 }
 		 map.put("result", "data not found");
 	 }else {
 	 	
 	 if(callPropertiesModel1.getWrapuptime()!=null&&new Date().after(callPropertiesModel1.getWrapuptime())) {
 		// reportsDAO.disposeCall1(callPropertiesModel1.getId());
 	 }
 	 	map.put("result", callPropertiesModel1);
 		
 	 }
 return new ResponseEntity(map, HttpStatus.OK);
 }
	
	
	@GetMapping("/ShowCallInfo")
	public ResponseEntity<?> ShowCallInfo() throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		
		
		Map<Object, Object> model = new HashMap<>();
	model.put("responseType", "Success"); 
 	List<CallPropertiesModel> callPropertiesModel1=new ArrayList<CallPropertiesModel>();
 	callPropertiesModel1=callService.findByAll();
 	
 if(callPropertiesModel1.isEmpty()) {
	 map.put("result", "data not found");
 }else {
 	
 
 	map.put("result", callPropertiesModel1);
	
 }
 return new ResponseEntity(map, HttpStatus.OK);
 }

	
	@PostMapping("/queuecount")
	public ResponseEntity<?> queueCount(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		
		
		Map<Object, Object> model = new HashMap<>();
	model.put("responseType", "Success"); 
	ArrayList<CallPropertiesModel> callPropertiesModel1=callService.findByPopupStatus("In Queue");
	map.put("count",callPropertiesModel1.size());
	map.put("result", callPropertiesModel1);

 return new ResponseEntity(map, HttpStatus.OK);
 }

	@PostMapping("/getMissedCall")
	 public ResponseEntity<?> getMissedCall(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	 	Map<Object, Object> model = new HashMap<>();
		model.put("responseType", "Success"); 
	 	MissedCallModel missedCallModel=missedCallService.getMissedCallData();
	 	if(missedCallModel!=null) {
	 		missedCallModel.setCallStatus("close");
	 		missedCallService.insertMissedCallRequest(missedCallModel);
	 	}
	model.put("data", missedCallModel);
	  return new ResponseEntity(model, HttpStatus.OK); 
	 }
	
	@PostMapping("/getScheduleCall")
	 public ResponseEntity<?> getScheduleCall(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	 	Map<Object, Object> model = new HashMap<>();
		model.put("responseType", "Success"); 
		ScheduleCallModel scheduleCallModel=scheduleCallService.getScheduleCallData(originateDTO.getExtension());
	 	if(scheduleCallModel!=null) {
	 		scheduleCallModel.setCallStatus("close");
	 		scheduleCallService.saveOrUpdate(scheduleCallModel);
	 	}
	model.put("data", scheduleCallModel);
	  return new ResponseEntity(model, HttpStatus.OK); 
	 }
	
	
	
	@PostMapping("/getScheduleCallList")
	 public ResponseEntity<?> getScheduleCallList(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	 	Map<Object, Object> map = new HashMap<>();
	 	List<ScheduleCallModel> scheduleList = new ArrayList<>();
	    scheduleList=scheduleCallService.getScheduleCallDataInList(originateDTO.getExtension());
		if (scheduleList.isEmpty()) {
		       map.put("responseCode", "400");
		        map.put("responseMessage", "Data Not Found");
		        map.put("responseType", "Error");       
		        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
		      }else {
		    	  map.put("responseType", "Success"); 
		    	  map.put("responseCode", "200");
		    	  map.put("responseMessage", "Success");
		    	  map.put("responseType", "Success");  
		    	  map.put("data", scheduleList);
		    	  return new ResponseEntity(map, HttpStatus.OK); 
		      }
	 }
	
	@PostMapping("/getMissedCallList")
	 public ResponseEntity<?> getMissedCallList(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	 	Map<Object, Object> map = new HashMap<>();
	 	List<MissedCallModel> missedCallList = new ArrayList<>();
		 missedCallList=missedCallService.getMissedCallDataInLIst();
	 	if (missedCallList.isEmpty()) {
		       map.put("responseCode", "400");
		        map.put("responseMessage", "Data Not Found");
		        map.put("responseType", "Error");       
		        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);   
		      }else {
		    	  map.put("responseType", "Success"); 
		    	  map.put("responseCode", "200");
		    	  map.put("responseMessage", "Success");
		    	  map.put("responseType", "Success");  
		    	  map.put("data", missedCallList);
		    	  return new ResponseEntity(map, HttpStatus.OK); 
		      }
	 }	
	
	@PostMapping("/checkCall")
	 public ResponseEntity<?> checkCall(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	 	Map<Object, Object> model = new HashMap<>();
	 
		CallPropertiesModel callPropertiesModel=callService.findByExtension(originateDTO.getExtension());
		if(callPropertiesModel==null) {
			MissedCallModel missedCallModel=missedCallService.getMissedCallData();
			if(missedCallModel==null) {
				ScheduleCallModel scheduleCallModel=scheduleCallService.getScheduleCallData(originateDTO.getExtension());
			 	if(scheduleCallModel!=null) {
			 		originateDTO.setPhoneNo(scheduleCallModel.getPhoneNumber());
					originateDTO.setDialMethod("schedulecall");
					actionHandler.originateCall(originateDTO);
					scheduleCallModel.setCallStatus("close");
			 		scheduleCallService.saveOrUpdate(scheduleCallModel);
			 	}
			}else {
				originateDTO.setPhoneNo(missedCallModel.getPhoneNumber());
				originateDTO.setDialMethod("missedcall");
				actionHandler.originateCall(originateDTO);
				missedCallModel.setCallStatus("close");
			 	missedCallService.insertMissedCallRequest(missedCallModel);
			}
		}
	
	  return new ResponseEntity(model, HttpStatus.OK); 
	 }	
	
	
	@PostMapping("/queue")
	 public ResponseEntity<?> getCount(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		QueueModel queueModel=queueService.findByQueue(originateDTO.getExtension());
		Map<Object, Object> model = new HashMap<>();
		model.put("responseType", "Success"); 
		if(queueModel==null) {
			model.put("count", 0);
		}else {
			model.put("count", queueModel.getCount());
		}
		 return new ResponseEntity(model, HttpStatus.OK); 
	}
	
	@GetMapping("/generateCaseId")
	 public ResponseEntity<?> generateCaseId() throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		Date date=new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int datevalue=localDate.getDayOfMonth();
		int month = localDate.getMonthValue();
		int year=localDate.getYear();
		String yearString =Integer.toString(year);
		String datemonthyear=datevalue+"/"+month+"/"+year;
		CaseModel caseModel=caseService.findByDate(datemonthyear);
		String caseId="TN/WHL/";
		Map<Object, Object> model = new HashMap<>();
		model.put("responseType", "Success"); 
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
			model.put("caseId", caseId);
		}else {
			caseId1=String.format("%04d", caseModel.getCount()+1);
			caseId=caseId.concat(datepad+"/"+monthpad+"/"+lastTwoDigits+"/"+caseId1);
		    caseModel.setCount(caseModel.getCount()+1);
			caseService.saveOrUpdate(caseModel);
			model.put("caseId", caseId);
		}
		 return new ResponseEntity(model, HttpStatus.OK); 
	}
	
		
	@PostMapping("/addFavorite")
	 public ResponseEntity<?> addFavorite(@RequestBody FavariteDTO favoriteDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	 	Map<Object, Object> model = new HashMap<>();
		
		if(favoriteDTO.getName()!=null&&!favoriteDTO.getName().isEmpty()&&favoriteDTO.getNumber()!=null&&!favoriteDTO.getNumber().isEmpty()) {
		FavoriteModel favoriteModel=new FavoriteModel();
		 favoriteModel.setName(favoriteDTO.getName());
		 favoriteModel.setNumber(favoriteDTO.getNumber());
		 favoriteRepository.save(favoriteModel);
		 model.put("responseType", "Success");
		}else {
			 model.put("responseType", "Error");
		}
	  return new ResponseEntity(model, HttpStatus.OK); 
	 }
	@GetMapping("/findAllFavorites")
	public ResponseEntity<?> findAllFavorites() throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		List<FavoriteModel> favoriteModels=new ArrayList<FavoriteModel>();
		favoriteModels=favoriteRepository.findAll();
		
		Map<Object, Object> model = new HashMap<>();
		
		if(!favoriteModels.isEmpty()) {
		 model.put("responseType", "Success");
		 model.put("responseCode", "200");
		 model.put("data", favoriteModels);
		}else {
			model.put("responseType", "Error");
			 model.put("responseCode", "400");
			 model.put("data", "data not found");
		}
		 return new ResponseEntity(model, HttpStatus.OK); 
	}
}
