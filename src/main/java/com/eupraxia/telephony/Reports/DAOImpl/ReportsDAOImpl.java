package com.eupraxia.telephony.Reports.DAOImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.persistence.Query;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.HangupAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eupraxia.telephony.DTO.HistoryReport;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.DiallerModel;
import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.IvrReportModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.repositories.IvrReportRepository;
import com.eupraxia.telephony.repositories.IvrRepository;
import com.eupraxia.telephony.repositories.Reports.ReportsRepository;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.CaseService;
import com.eupraxia.telephony.service.DiallerService;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.Reports.ReportService;
import com.eupraxia.telephony.service.Reports.UserService;
import com.eupraxia.telephony.util.CommonUtil;
import com.eupraxia.telephony.util.QueryCriteria;
@Repository
public class ReportsDAOImpl implements ReportsDAO{
	@Autowired
	private DiallerService fileService;

	@Autowired 
	QueryCriteria queryCriteria;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	IvrRepository ivrRepository;
	
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CampaignService campaignService;
	
	@Autowired
	ReportsRepository reportsRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CallService callService;
	
	@Autowired
	ActionHandler actionhandler;
	
	@Autowired
	private IvrReportRepository ivrReportRepository;
	
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	@Override
	public ReportsModel save(ReportsModel reportsModel) {
		
		return reportsRepository.save(reportsModel);
	}
	@Override
	public ArrayList<ReportsModel> getData(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return reportsRepository.findByStartDateBetween(startDate,endDate);
	}
	@Override
	public ArrayList<ReportsModel> findByPhoneNo(String phoneNumber) {
		// TODO Auto-generated method stub
		return reportsRepository.findByPhoneNo(phoneNumber);
	}
	@Override
	public ArrayList<ReportsModel> findByExtension(String extension) {
		// TODO Auto-generated method stub
		return reportsRepository.findByExtension(extension);
	}
	@Override
	public void disposeCall(String id) {
		//CallPropertiesModel callPropertiesModel=callService.findById(id);
		
	/*	 EmergencyScreenModel emergencyScreenModel=emergencyScreenService.findByCallId(emergencyScreenDTO.getCallId());
		 if(emergencyScreenModel!=null) {
			 emergencyScreenModel.setCaseId(caseId);
			 emergencyScreenService.saveOrUpdate(emergencyScreenModel);
		 }
		 
		 GuidenceScreenModel guidenceScreenModel=guidenceScreenService.findByCallId(emergencyScreenDTO.getCallId());
		 if(guidenceScreenModel!=null) {
			 guidenceScreenModel.setCaseId(caseId);
			 guidenceScreenService.saveOrUpdate(guidenceScreenModel);
		 }
		 
		 InformationScreenModel informationScreenModel=informationScreenService.findByCallId(emergencyScreenDTO.getCallId());
		 if(informationScreenModel!=null) {
			 informationScreenModel.setCaseId(caseId);
			 informationScreenService.saveOrUpdate(informationScreenModel);
		 }
		 
		 CallDetailModel callDetailModel=callDetailService.findByCallId(emergencyScreenDTO.getCallId());
		 if(callDetailModel!=null) {
			 callDetailModel.setCaseId(caseId);
			 callDetailService.saveOrUpdate(callDetailModel);
		 }*/
		 try {
			 CallPropertiesModel callPropertiesModel=callService.findById(id);
				if(callPropertiesModel!=null) {
					ReportsModel reportsModel = new ReportsModel();	
				//	reportsModel.setDocId(caseId);
					try {
					reportsModel.setCallConnectTime(callPropertiesModel.getCallConnectTime());
					}catch(Exception e) {}
		    		reportsModel.setCallDirection(callPropertiesModel.getCallDirection());
		    		try {
		    		reportsModel.setCallStartTime(callPropertiesModel.getCallStartTime());
		    		}catch(Exception e) {}
		    		try {
		    		reportsModel.setCallEndTime(callPropertiesModel.getCallEndTime());
		    		}catch(Exception e) {}
		    		reportsModel.setCallStatus(callPropertiesModel.getCallStatus());
		    		//reportsModel.setComments(callPropertiesModel.getComments());
		    		//reportsModel.setDisposition(callPropertiesModel.getDisposition());
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
		    		reportsModel.setCallbackDate(null);
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
		    				LocalDateTime now5= LocalDateTime.now(zid);
		    				
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
		    						cdrReportModel.setDocId(id);
		    						
		    						cdrReportModel.setCallDate(convertToIST(now5));
		    						//cdrReportModel.setTraverse(ivrModel.getIvrFlow());
		    						ivrReportRepository.save(cdrReportModel);
		    			
		    			}catch(Exception e) {
		    		System.out.println(e.getMessage());
		    			}
		    		
		    			}catch(Exception e) {
		    				System.out.println(e.getMessage());
		    			}
		    		}
		    	
                 
                  
		 		    		this.save(reportsModel);
		    		callService.deleteById(id);
		    		
		    	//	quickService.submit(new Runnable() {
		    		//	@Override
		    			//public void run() {
		    				try {	try {
	    						List<LoginModel> loginModel=loginService.findAllByExtensionAndStatus(callPropertiesModel.getExtension(),"login");
	    						for(LoginModel loginModel1: loginModel) {
	    							String startTime="";
	    						try {
	    							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    							   LocalDateTime now = LocalDateTime.now();
	    							   startTime= dtf.format(now);
	    						}catch(Exception e) {}
	    						
	    						loginModel1.setStatus("Ready");
	    						loginModel1.setDirection("");
	    						loginModel1.setStatusTime(startTime);
	    						loginService.saveOrUpdate(loginModel1);
	    					}
	    					}catch(Exception e) {}
		    					UserModel userModel=userService.findByUserextension(callPropertiesModel.getExtension());
		    		            if(callPropertiesModel.getExtension()!=null) {
		    		            	 if(userModel.getDiallerActive()!=null&&"1".equalsIgnoreCase(userModel.getDiallerActive())&&callPropertiesModel.getDialMethod()!=null&&"autodialler".equalsIgnoreCase(callPropertiesModel.getDialMethod())){
		    		            		// System.out.println("login Status :: "+loginModel1.getStatus());	
		    		             		if(userModel!=null) {
		    		             			DiallerModel diallerModel=fileService.findByStatusAndDate(callPropertiesModel.getCamId());
		    		             			if(diallerModel!=null) {
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
		    		         					    actionhandler.hangUpCall(originateDTO1);		
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
		    			//}
		    		///});
				
				}
		 }catch(Exception e) {}
	}
	
	
	@Override
	public void disposeCall1(String id) {
		//CallPropertiesModel callPropertiesModel=callService.findById(id);
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
		
	/*	 EmergencyScreenModel emergencyScreenModel=emergencyScreenService.findByCallId(emergencyScreenDTO.getCallId());
		 if(emergencyScreenModel!=null) {
			 emergencyScreenModel.setCaseId(caseId);
			 emergencyScreenService.saveOrUpdate(emergencyScreenModel);
		 }
		 
		 GuidenceScreenModel guidenceScreenModel=guidenceScreenService.findByCallId(emergencyScreenDTO.getCallId());
		 if(guidenceScreenModel!=null) {
			 guidenceScreenModel.setCaseId(caseId);
			 guidenceScreenService.saveOrUpdate(guidenceScreenModel);
		 }
		 
		 InformationScreenModel informationScreenModel=informationScreenService.findByCallId(emergencyScreenDTO.getCallId());
		 if(informationScreenModel!=null) {
			 informationScreenModel.setCaseId(caseId);
			 informationScreenService.saveOrUpdate(informationScreenModel);
		 }
		 
		 CallDetailModel callDetailModel=callDetailService.findByCallId(emergencyScreenDTO.getCallId());
		 if(callDetailModel!=null) {
			 callDetailModel.setCaseId(caseId);
			 callDetailService.saveOrUpdate(callDetailModel);
		 }*/
		 try {
			 CallPropertiesModel callPropertiesModel=callService.findById(id);
				if(callPropertiesModel!=null) {
					ReportsModel reportsModel = new ReportsModel();	
					reportsModel.setDocId(caseId);
					try {
					reportsModel.setCallConnectTime(callPropertiesModel.getCallConnectTime());
					}catch(Exception e) {}
		    		reportsModel.setCallDirection(callPropertiesModel.getCallDirection());
		    		try {
		    		reportsModel.setCallStartTime(callPropertiesModel.getCallStartTime());
		    		}catch(Exception e) {}
		    		try {
		    		reportsModel.setCallEndTime(callPropertiesModel.getCallEndTime());
		    		}catch(Exception e) {}
		    		reportsModel.setCallStatus(callPropertiesModel.getCallStatus());
		    		//reportsModel.setComments(callPropertiesModel.getComments());
		    		//reportsModel.setDisposition(callPropertiesModel.getDisposition());
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
		    		reportsModel.setCallbackDate(null);
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
		    						cdrReportModel.setDocId(id);
		    						
		    						cdrReportModel.setCallDate(ldt);
		    						//cdrReportModel.setTraverse(ivrModel.getIvrFlow());
		    						ivrReportRepository.save(cdrReportModel);
		    			
		    			}catch(Exception e) {
		    		System.out.println(e.getMessage());
		    			}
		    		
		    			}catch(Exception e) {
		    				System.out.println(e.getMessage());
		    			}
		    		}
		    		
		    		this.save(reportsModel);
		    		callService.deleteById(id);
		    		
		    		//quickService.submit(new Runnable() {
		    			//@Override
		    			//public void run() {
		    			
		    				try {	try {
	    						List<LoginModel> loginModel=loginService.findAllByExtensionAndStatus(callPropertiesModel.getExtension(),"login");
	    						for(LoginModel loginModel1: loginModel) {
	    							String startTime="";
	    						try {
	    							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    							   LocalDateTime now = LocalDateTime.now();
	    							   startTime= dtf.format(now);
	    						}catch(Exception e) {}
	    						
	    						loginModel1.setStatus("Ready");
	    						loginModel1.setDirection("");
	    						loginModel1.setStatusTime(startTime);
	    						loginService.saveOrUpdate(loginModel1);
	    					}
	    					}catch(Exception e) {}
		    					UserModel userModel=userService.findByUserextension(callPropertiesModel.getExtension());
		            if(callPropertiesModel.getExtension()!=null) {
		            	 if(userModel.getDiallerActive()!=null&&"1".equalsIgnoreCase(userModel.getDiallerActive())&&callPropertiesModel.getDialMethod()!=null&&"autodialler".equalsIgnoreCase(callPropertiesModel.getDialMethod())){
		            	//	 System.out.println("login Status :: "+loginModel1.getStatus());	
		             		if(userModel!=null) {
		             			DiallerModel diallerModel=fileService.findByStatusAndDate(callPropertiesModel.getCamId());
		             			if(diallerModel!=null) {
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
							}}
		}
		    					
		    					try {
		    						if(!callPropertiesModel.getPopupStatus().equalsIgnoreCase("hangup")) {
		    						OriginateDTO originateDTO=new OriginateDTO();
		    						originateDTO.setChannel(callPropertiesModel.getSipChannel());
		    						ManagerResponse HangupResponse = actionhandler.hangUpCall(originateDTO);		
		    						}
		    					}catch(Exception e) {}
		            }
		    				}catch(Exception e) {}
		    			//}
		    		//});
				
				}
		 }catch(Exception e) {}
	}
	
	

	
	
	private LocalDateTime convertToIST(LocalDateTime localDateTime) {
		LocalDateTime dateTimeHour=  localDateTime.plusHours(5);
		LocalDateTime dateTimeConverted =  dateTimeHour.plusMinutes(30);		 
		return dateTimeConverted;
		 
	 }
	@Override
	public ArrayList<HistoryReport> findPhoneNoWithLimit(String phoneNumber) {
		ArrayList<HistoryReport> res=new ArrayList<HistoryReport>();
		Query query = null;
		 List<Object> list = null;
		 query =  queryCriteria.queryForPhoneNumberLimit();		 
		 query.setParameter("phoneNumber", phoneNumber);
		 query.setMaxResults(3);
		 list = query.getResultList();
		 res=queryCriteria.phoneNumberLimitResultSet(list);
		
		return res;
	}
	
}
