package com.eupraxia.telephony.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.response.ManagerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.CallDetailDTO;
import com.eupraxia.telephony.DTO.CallPropertiesDTO;
import com.eupraxia.telephony.DTO.EmergencyScreenDTO;
import com.eupraxia.telephony.DTO.FollowupDTO;
import com.eupraxia.telephony.DTO.GuidenceScreenDTO;
import com.eupraxia.telephony.DTO.InformationScreenDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.ReportDispoDTO;
import com.eupraxia.telephony.DTO.TestDTO;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.DiallerModel;
import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.IvrReportModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.ScheduleCallModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Model.Disposition.CallDetailModel;
import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;
import com.eupraxia.telephony.Model.Disposition.GuidenceScreenModel;
import com.eupraxia.telephony.Model.Disposition.InformationScreenModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.repositories.IvrReportRepository;
import com.eupraxia.telephony.repositories.IvrRepository;
import com.eupraxia.telephony.repositories.Reports.ReportsRepository;
import com.eupraxia.telephony.service.CallDetailService;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.CaseService;
import com.eupraxia.telephony.service.DiallerService;
import com.eupraxia.telephony.service.EmergencyScreenService;
import com.eupraxia.telephony.service.GuidenceScreenService;
import com.eupraxia.telephony.service.InformationScreenService;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.MailStoreService;
import com.eupraxia.telephony.service.PrankService;
import com.eupraxia.telephony.service.ScheduleCallService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.Reports.ReportService;
import com.eupraxia.telephony.service.Reports.UserService;
import com.eupraxia.telephony.util.CommonUtil;

@CrossOrigin
@RestController
@RequestMapping("/caseDispo")
public class CaseDispositionController {
	
	@Autowired
	ActionHandler actionHandler; 
	
	@Autowired
	IvrReportRepository ivrReportRepository;
	
	@Autowired
	ReportsRepository reportsRepository;
	
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UserService userService; 
	
	@Autowired
	CallService callService;
	
	@Autowired
	ActionHandler actionhandler;
	
	@Autowired
	EntityManager entityManager;
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	@Autowired
	private PrankService prankservice; 	

	@Autowired
	private DiallerService fileService;

	
	@Autowired
	CampaignService campaignService;
	
	@Autowired
	private ReportsDAO reportsDAO;
	
	@Autowired
	private EmergencyScreenService emergencyScreenService;
	
	@Autowired
	private GuidenceScreenService guidenceScreenService;
	
	@Autowired
	private InformationScreenService informationScreenService;
	
	@Autowired
	private CallDetailService callDetailService;
	
	@Autowired
	 private ModelMapper modelMapper;
	
	@Autowired
	 private ScheduleCallService scheduleCallService;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	MailStoreService mailStoreService;
	
	@Autowired
	IvrRepository ivrRepository;
	
	@RequestMapping(value="/saveDisposition",method = RequestMethod.POST)
	public ResponseEntity<?> saveDisposition(@RequestBody ReportDispoDTO reportDispoDTO)throws InstantiationException, IllegalAccessException{
	 Map<Object, Object> model = new HashMap<>(); 	
	String callIdforSMS = reportDispoDTO.getCallId();
	String caseId = generateCaseId();
	

		 try {
			 CallPropertiesModel callPropertiesModel=callService.findById(reportDispoDTO.getCallId());
				if(callPropertiesModel!=null) {
					ReportsModel reportsModel = new ReportsModel();	
					reportsModel.setCaseId(caseId);
					try {
					reportsModel.setCallConnectTime(callPropertiesModel.getCallConnectTime());
					}catch(Exception e) {}
		    		reportsModel.setCallDirection(callPropertiesModel.getCallDirection());
		    		try {
		    		reportsModel.setCallStartTime(callPropertiesModel.getCallStartTime());
		    		}catch(Exception e) {}
		    		try {
		    			if(callPropertiesModel.getCallEndTime()!=null) {
		    		reportsModel.setCallEndTime(callPropertiesModel.getCallEndTime());
		    			}else {
		    				ZoneId zid = ZoneId.of("Asia/Kolkata");
		    				 LocalDateTime now = LocalDateTime.now(zid);
		    				reportsModel.setCallEndTime(now);
		    				callPropertiesModel.setCallEndTime(now);	    			}
		    		}catch(Exception e) {}
		    		if(callPropertiesModel.getCallDirection().equalsIgnoreCase("inbound")&&callPropertiesModel.getCallStatus().equalsIgnoreCase("Dial")) {
		    			reportsModel.setCallStatus("ANSWER");
		    		}else {
		    				reportsModel.setCallStatus(callPropertiesModel.getCallStatus());	
		    		}
		    		reportsModel.setDocId(callPropertiesModel.getId());
		    		reportsModel.setExtension(callPropertiesModel.getExtension());
		    		try {
		    		reportsModel.setHoldEndTime(callPropertiesModel.getHoldEndTime());
		    		}catch(Exception e) {}
		    		try {
		    		reportsModel.setHoldStartTime(callPropertiesModel.getHoldStartTime());
		    		}catch(Exception e) {}
		    		reportsModel.setIsClosed(callPropertiesModel.getIsClosed());
		    		reportsModel.setPhoneNo(callPropertiesModel.getPhoneNo());
		    		reportsModel.setSecondChannel(callPropertiesModel.getSecondChannel());
		    		reportsModel.setSecondNumber(callPropertiesModel.getSecondNumber());
		    		reportsModel.setSipChannel(callPropertiesModel.getSipChannel());
		    		reportsModel.setTrunkChannel(callPropertiesModel.getTrunkChannel());
		    		reportsModel.setComments("");
		    		reportsModel.setDisposition("");
		    		Date callbackdate=null;
		    		try {
		    			if(reportDispoDTO.getCallback()!=null&&!reportDispoDTO.getCallback().isEmpty()) {
		    			String stdt=reportDispoDTO.getCallback();
		    			stdt=stdt.replace("T", " ");
		    			 SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    			   Date date1=formatter6.parse(stdt);
		    			   Calendar calendar = Calendar.getInstance();
		    			    calendar.setTime(date1);
		    			    calendar.add(Calendar.HOUR,+5);
		    			    calendar.add(Calendar.MINUTE,+30);
		    			    callbackdate=calendar.getTime();
		    			   
		    		reportsModel.setCallbackDate(calendar.getTime());
		    		}
		    		}catch(Exception e) {}
		    		//reportsModel.setCallbackDate(null);	
		    		
		 	    		//reportsModel.setFeedback(String.valueOf(callPropertiesModel.getFeedback()));
		    		IvrModel ivrModel = new IvrModel();
		    		String pho=callPropertiesModel.getPhoneNo();
		    		try {
		    			pho=pho.substring(pho.length()-10,pho.length());
		    		}catch(Exception e) {}
		    		try {
			    		ivrModel = ivrRepository.findByPhoneNo(pho);
			    	}catch(Exception e) {
			    		ivrModel=ivrRepository.findFirstByPhoneNo(pho);
			    	}
		    		if(!CommonUtil.isNull(ivrModel)) {
		    			reportsModel.setRecPath(ivrModel.getRecPath());
		    			ivrRepository.deleteById(ivrModel.getId());
		    			try {
		    				ZoneId zid = ZoneId.of("Asia/Kolkata");
		    				LocalDateTime now5 = LocalDateTime.now(zid);
		    				
		    				IvrReportModel cdrReportModel=new IvrReportModel();
		    				cdrReportModel.setPhoneNo(reportsModel.getPhoneNo());
		    				cdrReportModel.setTraverse(ivrModel.getIvrFlow());
		    					SimpleDateFormat formatter7=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		    					SimpleDateFormat formatter8=new SimpleDateFormat("HH:mm:ss");
		    					
		    					try {
		    						Date d1 =formatter7.parse(ivrModel.getEndTime());
			    				       Date d2 =formatter7.parse(ivrModel.getStartTime());
		    			          
		    			            LocalDateTime ldt = LocalDateTime.ofInstant(d2.toInstant(),
		    	                            ZoneId.systemDefault());
		    			            
		    						long ivrduartion1=(int)((d1.getTime() - d2.getTime())/1000);
		    						cdrReportModel.setIvrDuration(String.valueOf(ivrduartion1));
		    						cdrReportModel.setIvrStartTime(formatter8.format(d2));
		    						cdrReportModel.setIvrEndTime(formatter8.format(d1));
		    						cdrReportModel.setDocId(reportDispoDTO.getCallId());
		    						
		    						cdrReportModel.setCallDate(convertToIST(now5));
		    						//cdrReportModel.setTraverse(ivrModel.getIvrFlow());
		    						ivrReportRepository.save(cdrReportModel);
		    			
		    			}catch(Exception e) {}
		    		
		    			}catch(Exception e) {}
		    		}
		    		
		    		try {
		    			reportsModel.setCamId(callPropertiesModel.getCamId());
		    			reportsModel.setCamName(callPropertiesModel.getCamName());		    			reportsModel.setDispo(reportDispoDTO.getDispo());
		    			reportsModel.setMaindispo(reportDispoDTO.getMaindispo());
		    			reportsModel.setSubdispo(reportDispoDTO.getSubdispo());
		    			reportsModel.setSubsubdispo(reportDispoDTO.getSubsubdispo());
		    			//reportsModel
		    			
		    		}catch(Exception e) {}
		    		reportsDAO.save(reportsModel);
		    		callService.deleteById(reportDispoDTO.getCallId());
		    		
		    		
		           
		    		
		    		//quickService.submit(new Runnable() {
		    		//	@Override
		    		//	public void run() {
		    				try {}catch(Exception e) {
		    					System.out.println("Exception :"+e);
		    				}
		    			//}
		    			//});
		    				Date callbackdate1=callbackdate;
		    	//	quickService.submit(new Runnable() {
		    	//		@Override
		    	//		public void run() {
		    				
		    				if(reportDispoDTO.getCallback()!=null&&!reportDispoDTO.getCallback().isEmpty()) {
		    	    			try {
		    	    				ScheduleCallModel scheduleCallModel=new ScheduleCallModel();
		    	    				String phone=callPropertiesModel.getPhoneNo();
		    	    				if(phone.length()>9) {
		    	    					phone= phone.substring(phone.length()-10,phone.length());
		    	 	    					
		    	    				}
		    	    				 
		    	    				scheduleCallModel.setExtension(callPropertiesModel.getExtension());
		    	    				scheduleCallModel.setPhoneNumber(phone);
		    	    				scheduleCallModel.setCallStatus("open");
		    	    				scheduleCallModel.setCallBackTime(callbackdate1);
		    	    				scheduleCallModel.setCampaignName(callPropertiesModel.getCamName());
		    	    				scheduleCallService.saveOrUpdate(scheduleCallModel);
		    	    			}catch(Exception e) {}
		    	    		}
		    				List<LoginModel> loginModel=new ArrayList<>();
		    				
		    				try {
		    				loginModel=loginService.findAllByExtensionAndStatus(callPropertiesModel.getExtension(),"login");
		    					for(LoginModel LoginModel1:loginModel) {
		    						String startTime="";
		    						try {
		    							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		    							   LocalDateTime now = LocalDateTime.now();
		    							   startTime= dtf.format(now);
		    						}catch(Exception e) {}
		    						
		    						LoginModel1.setStatus("Ready");
		    						LoginModel1.setDirection("");
		    						LoginModel1.setStatusTime(startTime);
		    						loginService.saveOrUpdate(LoginModel1);
		    					}
		    					UserModel userModel=userService.findByUserextension(callPropertiesModel.getExtension());
		    		            if(callPropertiesModel.getExtension()!=null) {
		    		            	 if(userModel.getDiallerActive()!=null&&"1".equalsIgnoreCase(userModel.getDiallerActive())&&callPropertiesModel.getDialMethod()!=null&&"autodialler".equalsIgnoreCase(callPropertiesModel.getDialMethod())){
		    		            	//	 System.out.println("login Status :: "+loginModel.getStatus());	
		    		             		if(userModel!=null) {
		    		             			DiallerModel diallerModel=fileService.findByStatusAndDate(callPropertiesModel.getCamId());
		    		             			if(diallerModel.getPhoneNo()!=null) {
		    		             				OriginateDTO originateDTO=new OriginateDTO();
		    		             				originateDTO.setExtension(userModel.getUserextension());
		    		             				originateDTO.setPhoneNo(diallerModel.getPhoneNo());
		    		             				originateDTO.setContext(userModel.getContext());
		    		             				originateDTO.setChannel("SIP/".concat(userModel.getUserextension()));
		    		             				originateDTO.setPriority(1);
		    		             				originateDTO.setCamId(callPropertiesModel.getCamId());
		    		             				originateDTO.setCamName(callPropertiesModel.getCamName());
		    		             				originateDTO.setDialMethod("autodialler");
		    		             				originateDTO.setPrefix(userModel.getPrefix());
		    		             				originateDTO.setName(diallerModel.getName());
		    		             				try {
		    		         						if(!callPropertiesModel.getPopupStatus().equalsIgnoreCase("hangup")) {
		    		         						OriginateDTO originateDTO1=new OriginateDTO();
		    		         						originateDTO1.setChannel(callPropertiesModel.getSipChannel());
		    		         						ManagerResponse HangupResponse = actionhandler.hangUpCall(originateDTO1);		
		    		         						}
		    		         					}catch(Exception e) {}
		    		             				actionhandler.originateCall(originateDTO);
		    		             				DiallerModel DiallerModel1=fileService.findById(diallerModel.getId());
		    		             				if(DiallerModel1!=null) {
		    		             					DiallerModel1.setStatus("close");
		    		             					DiallerModel1.setExtension(userModel.getUserextension());
		    		             					DiallerModel1.setCallDate(new Date());
		    		             					fileService.saveOrUpdate(DiallerModel1);
		    		             				}else {
		    		             					List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		    		             					usercampaignModels=campaignService.findByUserextensionAndDirection(callPropertiesModel.getExtension(),"Incoming");
		    		     							for(UserCampaignMappingModel ucm:usercampaignModels) {
		    		         							if(ucm.getQueue()!=null) {
		    		         		    					QueueDTO queueDTO=new QueueDTO();
		    		         		    					queueDTO.setQueue(ucm.getQueue());
		    		         		    					queueDTO.setIface("Local/"+callPropertiesModel.getExtension()+"@from-queue");
		    		         		    					quickService.submit(new Runnable() {
		    		         		   		    			@Override
		    		         		   		    			public void run() {
		    		         		    					try {
																actionhandler.queueAdd(queueDTO);
															} catch (IllegalArgumentException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IllegalStateException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IOException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (TimeoutException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (AuthenticationFailedException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} 
		    		         		   		    			}});
		    		         							}}
		    		             				}
		    		             				
		    		             			}
		    		             		}
		    		            			 
		    		            		 }else {
		    		            	List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
		    						usercampaignModels=campaignService.findByUserextension(callPropertiesModel.getExtension());
		    						for(UserCampaignMappingModel ucm:usercampaignModels) {
		    							if(ucm.getQueue()!=null) {
		    		    					QueueDTO queueDTO=new QueueDTO();
		    		    					queueDTO.setQueue(ucm.getQueue());
		    		    					queueDTO.setIface("Local/"+callPropertiesModel.getExtension()+"@from-queue");
		    		    					quickService.submit(new Runnable() {
		         		   		    			@Override
		         		   		    			public void run() {
		         		    					try {
													actionhandler.queueAdd(queueDTO);
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalStateException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (TimeoutException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (AuthenticationFailedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} 
		         		   		    			}});
		         			
		    							}}}}
		    		            		 
		    					
		    					
		    					try {
		    						if(!callPropertiesModel.getPopupStatus().equalsIgnoreCase("hangup")) {
		    						OriginateDTO originateDTO=new OriginateDTO();
		    						originateDTO.setChannel(callPropertiesModel.getSipChannel());
		    						ManagerResponse HangupResponse = actionhandler.hangUpCall(originateDTO);		
		    						}
		    					}catch(Exception e) {}
		    				}catch(Exception e) {}
		    		//	}
		    	//	});
		    		
		    		// Re-Visit
		    		//CallPropertiesModel callPropertiesModelPrevious = callService.findByPhoneNo(event.getCallerIdNum());
					//if (!CommonUtil.isNull(callPropertiesModelPrevious)) {
						callService.deleteById(callPropertiesModel.getId());
					//}
	    		
		    		 model.put("responseCode", "200");
		    			model.put("responseMessage", "Success");
		    			model.put("responseType", "Success");  
		    			return new ResponseEntity(model, HttpStatus.OK);   

				}
				else {
					 model.put("responseCode", "400");
						model.put("responseMessage", "Call Id Not Found");
						model.put("responseType", "Error");  
						return new ResponseEntity(model, HttpStatus.OK);   
					
				}

			 
		 }catch(Exception e) {}
		return null;
	
 }

	
	@RequestMapping(value="/addEmergencyScreen",method = RequestMethod.POST)
    public ResponseEntity<?> addEmergencyScreen(@RequestBody EmergencyScreenDTO emergencyScreenDTO)throws InstantiationException, IllegalAccessException{
	 Map<Object, Object> model = new HashMap<>(); 
	 
	 EmergencyScreenModel emergencyScreenModel=emergencyScreenService.findByCallId(emergencyScreenDTO.getCallId());
	 if(emergencyScreenModel==null) {
		 try {
			 System.out.println("null pos in emergency");
		 EmergencyScreenModel emergencyScreenModel1=new EmergencyScreenModel();
		 emergencyScreenModel1 = modelMapper.map(emergencyScreenDTO, EmergencyScreenModel.class);
		 emergencyScreenService.saveOrUpdate(emergencyScreenModel1);
		 }catch(Exception e) {
				e.printStackTrace();
			 }
	 }
	 else {
		 try {
			 System.out.println("not null pos in emergency");
		 emergencyScreenDTO.setId(emergencyScreenModel.getId());
		 emergencyScreenModel = modelMapper.map(emergencyScreenDTO, EmergencyScreenModel.class);
		 emergencyScreenService.saveOrUpdate(emergencyScreenModel);
		 }catch(Exception e) {
			e.printStackTrace();
		 }
	 }
	  
	 model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		return new ResponseEntity(model, HttpStatus.OK);   
 }


	
	@RequestMapping(value="/addGuidenceScreen",method = RequestMethod.POST)
    public ResponseEntity<?> addGuidenceScreen(@RequestBody GuidenceScreenDTO guidenceScreenDTO)throws InstantiationException, IllegalAccessException{
	 Map<Object, Object> model = new HashMap<>(); 
	 GuidenceScreenModel guidenceScreenModel=guidenceScreenService.findByCallId(guidenceScreenDTO.getCallId());
	 if(guidenceScreenModel==null) {
		 GuidenceScreenModel guidenceScreenModel1=new GuidenceScreenModel();
		 guidenceScreenModel1 = modelMapper.map(guidenceScreenDTO, GuidenceScreenModel.class);
		 guidenceScreenService.saveOrUpdate(guidenceScreenModel1);
	 }
	 
	 else {
		 guidenceScreenDTO.setId(guidenceScreenModel.getId());
		 guidenceScreenModel = modelMapper.map(guidenceScreenDTO, GuidenceScreenModel.class);
		 guidenceScreenService.saveOrUpdate(guidenceScreenModel);
	 }
	 
	 model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		return new ResponseEntity(model, HttpStatus.OK);   
 }

	
	@RequestMapping(value="/addInformationScreen",method = RequestMethod.POST)
	public ResponseEntity<?> addInformationScreen(@RequestBody InformationScreenDTO informationScreenDTO)throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		InformationScreenModel informationScreenModel=informationScreenService.findByCallId(informationScreenDTO.getCallId());
		if(informationScreenModel==null) {
			InformationScreenModel informationScreenModel1=new InformationScreenModel();
			informationScreenModel1 = modelMapper.map(informationScreenDTO, InformationScreenModel.class);
			informationScreenService.saveOrUpdate(informationScreenModel1);
		}
		else {
			informationScreenDTO.setId(informationScreenModel.getId());
			informationScreenModel = modelMapper.map(informationScreenDTO, InformationScreenModel.class);
			informationScreenService.saveOrUpdate(informationScreenModel);
		}

		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		return new ResponseEntity(model, HttpStatus.OK);   
	}

	
	 @RequestMapping(value="/addCallDetails",method = RequestMethod.POST)
	    public ResponseEntity<?> addCallDetails(@RequestBody CallDetailDTO callDetailDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 CallDetailModel callDetailModel=callDetailService.findByCallId(callDetailDTO.getCallId());
		
		 if(callDetailModel==null) {
			 CallDetailModel callDetailModel1=new CallDetailModel();
			 callDetailModel1 = modelMapper.map(callDetailDTO, CallDetailModel.class);
			 callDetailService.saveOrUpdate(callDetailModel1);
		 }
		 
		 else {
			 callDetailDTO.setId(callDetailModel.getId());
			callDetailModel = modelMapper.map(callDetailDTO, CallDetailModel.class);
			 callDetailService.saveOrUpdate(callDetailModel);
		 }
		 
		
		 
		 //Not Required by business
		 /*
		 try {
			 if(callDetailDTO.getCallType().equalsIgnoreCase("prank")) {
			 CallPropertiesModel call=callService.findById(callDetailDTO.getCallId());
		  String pho=call.getPhoneNo();
		  pho=pho.substring(pho.length()-10,pho.length());
			 PrankModel prankModel1=prankservice.findByPhoneNo(pho);
			if(prankModel1==null) {
				PrankModel prankModel=new PrankModel();
				prankModel.setPhoneNo(pho);
				prankservice.save(prankModel);
			}
			 }
		 }catch(Exception e) {}
		 */
		 
		 model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			return new ResponseEntity(model, HttpStatus.OK);   
	 }	 

	 
	 @RequestMapping(value="/getAllFollowUps",method = RequestMethod.GET)
	    public ResponseEntity<?> getAllFollowUps()throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		
		 Query query =  entityManager.createNativeQuery("select r.case_Id, r.extension, r.phone_no, is_name,g.call_id, gs_statusof_case, es_other_agg_name, gs_other_agg_name "
		 		+ "FROM eupraxia_report r "
		 		+ "LEFT JOIN  guidence_case_details g  ON r.doc_id = g.call_id "
				 + "LEFT JOIN information_case_details i on r.doc_id = i.call_id "		 		
		 		+ "LEFT JOIN emergency_case_details e  ON r.doc_id = e.call_id where gs_statusof_case='Follow Up'");
		 
		 List<Object> list = query.getResultList();
		 List<TestDTO> dispositionReportDTOs = new ArrayList<>();
			if (CommonUtil.isListNotNullAndEmpty(list)) {
				Iterator it = list.iterator();
				while (it.hasNext()){
					try {
					
						Object[] objs = ( Object[]) it.next();
						TestDTO dto=new TestDTO();
						dto.setCaseId(CommonUtil.isNull(objs[0])?null:(String)objs[0]);
						dto.setExtension(CommonUtil.isNull(objs[1])?null:(String)objs[1]);
						dto.setPhoneNo(CommonUtil.isNull(objs[2])?null:(String)objs[2]);
						dto.setIsName(CommonUtil.isNull(objs[3])?null:(String)objs[3]);
						dto.setEsOtherAggName(CommonUtil.isNull(objs[6])?null:(String)objs[6]);
						dto.setGsOtherAggName(CommonUtil.isNull(objs[7])?null:(String)objs[7]);
						dispositionReportDTOs.add(dto);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
		 
		 	model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			model.put("caseids", dispositionReportDTOs);
			return new ResponseEntity(model, HttpStatus.OK);   
			}
			else {
				model.put("responseCode", "400");
				model.put("responseMessage", "Data not Found");
				model.put("responseType", "Error");  
				return new ResponseEntity(model, HttpStatus.OK);   
			}
			
	 }	 
	 
	 @RequestMapping(value="/getAllFollowUpsBasedOnDateAndShift",method = RequestMethod.POST)
	    public ResponseEntity<?> getAllFollowUpsBasedOnDateAndShift(@RequestBody FollowupDTO followupDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		
		 Query query =  entityManager.createNativeQuery("select r.case_Id, r.extension, r.phone_no, is_name,g.call_id, gs_statusof_case, es_other_agg_name, gs_other_agg_name "
		 		+ "FROM eupraxia_report r "
		 		+ "LEFT JOIN  guidence_case_details g  ON r.doc_id = g.call_id "
				 + "LEFT JOIN information_case_details i on r.doc_id = i.call_id "		 		
		 		+ "LEFT JOIN emergency_case_details e  ON r.doc_id = e.call_id where g.gs_statusof_case='Follow Up' AND g.gs_shift_timing=:shift");
		 //+ "LEFT JOIN emergency_case_details e  ON r.doc_id = e.call_id where g.gs_statusof_case='Follow Up' AND g.gs_shift_timing=:shift AND g.gs_followup_date=:followdate");
		 query.setParameter("shift", followupDTO.getShiftTiming());
		// query.setParameter("followdate", followupDTO.getFollowupDate());
		 
		 List<Object> list = query.getResultList();
		 List<TestDTO> dispositionReportDTOs = new ArrayList<>();
			if (CommonUtil.isListNotNullAndEmpty(list)) {
				Iterator it = list.iterator();
				while (it.hasNext()){
					try {
					
						Object[] objs = ( Object[]) it.next();
						TestDTO dto=new TestDTO();
						dto.setCaseId(CommonUtil.isNull(objs[0])?null:(String)objs[0]);
						dto.setExtension(CommonUtil.isNull(objs[1])?null:(String)objs[1]);
						dto.setPhoneNo(CommonUtil.isNull(objs[2])?null:(String)objs[2]);
						dto.setIsName(CommonUtil.isNull(objs[3])?null:(String)objs[3]);
						dto.setEsOtherAggName(CommonUtil.isNull(objs[6])?null:(String)objs[6]);
						dto.setGsOtherAggName(CommonUtil.isNull(objs[7])?null:(String)objs[7]);
						dispositionReportDTOs.add(dto);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
		 
		 	model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			model.put("caseids", dispositionReportDTOs);
			return new ResponseEntity(model, HttpStatus.OK);   
			}
			else {
				model.put("responseCode", "400");
				model.put("responseMessage", "Data not Found");
				model.put("responseType", "Error");  
				model.put("caseids", new TestDTO());
				return new ResponseEntity(model, HttpStatus.OK);   
			}
			
	 }	 
	 

	 @RequestMapping(value="/updateFollowup",method = RequestMethod.POST)
	    public ResponseEntity<?> updateFollowup(@RequestBody FollowupDTO followupDTO) throws InstantiationException, IllegalAccessException, ParseException{
		
		 Map<Object, Object> model = new HashMap<>(); 
		 ReportsModel reportsModel = new ReportsModel();
		 System.out.println("followupDTO.getCaseId() : "+followupDTO.getCaseId());
		 reportsModel = reportService.findByCaseId(followupDTO.getCaseId());		 
		 GuidenceScreenModel guidenceScreenModel=guidenceScreenService.findByCallId(reportsModel.getDocId());
		 if(!CommonUtil.isNull(guidenceScreenModel)) {
			 guidenceScreenModel.setGsStatusofCase(followupDTO.getStatusofCase());
			 guidenceScreenModel.setGsRemarks(followupDTO.getRemarks());
			 guidenceScreenModel.setGsFollowSession(followupDTO.getShiftTiming());
			 guidenceScreenModel.setGsFollowDate(followupDTO.getFollowupDate());
			 guidenceScreenService.saveOrUpdate(guidenceScreenModel);
		 }
		 
		 EmergencyScreenModel emergencyScreenModel=emergencyScreenService.findByCallId(reportsModel.getDocId());
		 if(!CommonUtil.isNull(emergencyScreenModel)) {
			 emergencyScreenModel.setEsStatusofCase(followupDTO.getStatusofCase());
			 emergencyScreenModel.setEsRemarks(followupDTO.getStatusofCase());
			 emergencyScreenModel.setEsFollowSession(followupDTO.getShiftTiming());
			 emergencyScreenModel.setEsFollowDate(followupDTO.getFollowupDate());
			 emergencyScreenService.saveOrUpdate(emergencyScreenModel);
			 
		 }
		 
		 	model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success"); 			
			return new ResponseEntity(model, HttpStatus.OK);   
	 }

	 
	 @RequestMapping(value="/changeFeedbackFlag",method = RequestMethod.POST)
	    public ResponseEntity<?> updateFollowup(@RequestBody CallPropertiesDTO callPropertiesDTO) throws InstantiationException, IllegalAccessException, ParseException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		
		 Map<Object, Object> model = new HashMap<>(); 
		 CallPropertiesModel callPropertiesModel=callService.findById(callPropertiesDTO.getId().toString());		
		 if(!CommonUtil.isNull(callPropertiesModel)) {
			 callPropertiesModel.setFeedback(callPropertiesDTO.getFeedback());;			
			 callService.saveOrUpdate(callPropertiesModel);
			 actionhandler.redirectFeedBack(callPropertiesModel.getTrunkChannel(),callPropertiesModel.getCallDirection());
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success"); 			
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
		 model.put("responseCode", "400");
		 model.put("responseMessage", "ID not found");
		 model.put("responseType", "Error"); 			
		 return new ResponseEntity(model, HttpStatus.BAD_REQUEST);   
		 	
	 }

	 
	 private LocalDateTime convertToIST(LocalDateTime localDateTime) {
		LocalDateTime dateTimeHour=  localDateTime.plusHours(5);
		LocalDateTime dateTimeConverted =  dateTimeHour.plusMinutes(30);		 
		return dateTimeConverted;
		 
	 }
	 
	 private String sendSMS(String phoneNo, String content) {
		 String sResult="";
		 try {
			 // Construct data
			 String apiKey = "apikey=" + URLEncoder.encode("7ank/UwWQ4A-2kMKdE8D1PHQ7SeOCYAONxLYulRJWC", "UTF-8");
				String message = "&message=" + URLEncoder.encode(content, "UTF-8");
				String sender = "&sender=" + URLEncoder.encode("SWDWHL", "UTF-8");
				String numbers = "&numbers=" + URLEncoder.encode(phoneNo, "UTF-8");
				System.out.println(message);
				
				// Send data
				String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
				System.out.println(data);
				URL url = new URL(data);
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				
				while ((line = rd.readLine()) != null) {
				// Process line...
					System.out.println("line :"+line);
					System.out.println("line :"+line);
					System.out.println("line :"+line);
					System.out.println("line :"+line);
					sResult=sResult+line+" ";
				}
				rd.close();
				
		 } catch (Exception e) {
			 System.out.println("Error SMS "+e);
		 }
		 return sResult;
	 }
	 
	 @RequestMapping(value="/testSMS",method = RequestMethod.POST)
	    public ResponseEntity<?> testSMS(@RequestBody EmergencyScreenDTO emergencyScreenDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 //CallPropertiesModel callPropertiesModel=callService.findById(emergencyScreenDTO.getCallId());
		//	if(callPropertiesModel!=null) {
				try {
					String smsContent = "";
					String caseId1 = "TN/WHL/12/03/21/0003";
					String Address1 = "Forrest Ray 191-103 Integer Rd.";
					String Address2 = " ";
					String Address3 = " ";
					String pincode = "560024";
					String contactName = "VACANT (Krishnagiri DSWO (i/c)";
					String contactNo = "9788798465";
					String mailId = "testpriya@test.com";
				
					smsContent = "Ref Id : "+ caseId1
							+"\nAddress: "+Address1+Address2+Address3
							+"\nPincode: "+pincode
							+"\nContact Name: "+contactName
							+"\nContact No: "+mailId
							+"\nMail : "+mailId;
						
					
						/*
						smsContent = "Ref Id : "+ caseId1
						+"\r\nAddress : "+Address1+Address2+Address3
						+"\r\nPincode : "+pincode
						+"\r\nContact Name : "+contactName
						+"\r\nContact No : "+mailId
						+"\r\nMail : "+mailId;
					*/
						
						
					
					sendSMS(contactNo,smsContent);
					System.out.println(smsContent);
				}catch(Exception e) {
					
				}
			//}
			model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			
			return new ResponseEntity(model, HttpStatus.OK);   
			}
	 
	 
	 private String makeTextToThirty(String str) {
		
		 if(str==null) {
			 return " "+StringUtils.rightPad(str, 27," ");
		 }
		 if(str.length()>29){
			 return str.substring(0,27);
		 }
		 if(str.length()<29){
			 return " "+StringUtils.rightPad(str, 27," ");
		 }
		 
		 return str;
	 }
	 
	 private String generateCaseId() {
		
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
						caseId=caseId.concat(datepad+"/"+monthpad+"/"+lastTwoDigits+"/000001");
						CaseModel caseModel1=new CaseModel();
						caseModel1.setCount(1);
						caseModel1.setDate(datemonthyear);
						caseService.saveOrUpdate(caseModel1);
						//caseId1=String.format("%04d", 0);
					
					}else {
						caseId1=String.format("%06d", caseModel.getCount()+1);
						//caseId=caseId.concat(datepad+"/"+monthpad+"/"+lastTwoDigits+"/"+caseId1);
						caseId=caseId.concat(datepad+monthpad+lastTwoDigits+"/"+caseId1);
					    caseModel.setCount(caseModel.getCount()+1);
						caseService.saveOrUpdate(caseModel);
					
					}

				}catch(Exception e) {
					e.printStackTrace();
				}
					return caseId;
	 }
	 
	 private String findDistrict(String districtName) {
		 System.out.println("districtName :"+districtName);		 
		 HashMap<String, String> map = new HashMap<>();
		 map.put("Ariyalur", "ARI");
		 map.put("Chennai", "CHE");
		 map.put("Coimbatore", "COI");
		 map.put("Cuddalore", "CUD");
		 map.put("Dharmapuri", "DHA");
		 map.put("Dindigul", "DIN");
		 map.put("Erode", "ERO");
		 map.put("Kanchipuram", "KAN");
		 map.put("Kanyakumari", "KYK");
		 map.put("Karur", "KAR");
		 map.put("Krishnagiri", "KRI");
		 map.put("Madurai", "MAD");
		 map.put("Nagapattinam", "NAG");
		 map.put("Namakkal", "NAM");
		 map.put("Perambalur", "PER");
		 map.put("Pudukkottai", "PUD");
		 map.put("Ramanathapuram", "RAM");
		 map.put("Salem", "SAL");
		 map.put("Sivagangai", "SIV");
		 map.put("Thanjavur", "THA");
		 map.put("Nilgiris", "NIL");
		 map.put("Theni", "THE");
		 map.put("Thiruvallur", "THI");
		 map.put("Thiruvarur", "TRR");
		 map.put("Tiruchirappalli", "TRY");
		 map.put("Tirunelveli", "TVL");
		 map.put("Tiruppur", "TUR");
		 map.put("Tiruvannamalai", "TVI");
		 map.put("Tuticorin", "TUT");
		 map.put("Vellore", "VEL");
		 map.put("Villupuram", "VIL");
		 map.put("Virudhunagar", "VIR");
		 map.put("Mayiladuthurai", "MAY");
		 map.put("Chengalpet", "CGL");
		 map.put("Ranipet", "RAN");
		 map.put("Tenkasi", "TEN");
		 map.put("Kallakurichi", "KAL");
		 map.put("Tirupathur", "TPR");
		 map.put("Pondicherry", "PON");
		 map.put("OTHERS", "OTR");
		 
		 if (map.containsKey(districtName)) {	            
			 return map.get(districtName);
		 }
		 return "OTR";
		
		 /*
		 String districtValue = map.entrySet().stream().filter(e -> 
	        StringUtils.startsWithIgnoreCase(e.getKey(), districtName)).findFirst().get()
	                .getValue();
		 
		if(districtValue ==null || districtValue.contentEquals(""))
		 return "OTR";
		else 
			return districtValue;
			*/
	 }
	 
}
