package com.eupraxia.telephony.handler.impl;


import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CampaignModel;
import com.eupraxia.telephony.Model.ExtensionDataModel;
import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.MeetMeModel;
import com.eupraxia.telephony.Model.MissedCallModel;
import com.eupraxia.telephony.Model.QueueModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.components.TelephonyManagerConnection;
import com.eupraxia.telephony.DTO.CallPropertiesDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.Model.CallLogsModel;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.handler.EventsHandler;
import com.eupraxia.telephony.repositories.ExtensionDataRepository;
import com.eupraxia.telephony.repositories.Reports.CampaignRepository;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.IvrService;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.MeetMeService;
import com.eupraxia.telephony.service.MissedCallService;
import com.eupraxia.telephony.service.QueueService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.Reports.UserService;
import com.eupraxia.telephony.util.CommonUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.HangupAction;
import org.asteriskjava.manager.event.BridgeEvent;
import org.asteriskjava.manager.event.DialBeginEvent;
import org.asteriskjava.manager.event.DialEndEvent;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.JoinEvent;
import org.asteriskjava.manager.event.LeaveEvent;
import org.asteriskjava.manager.event.MeetMeJoinEvent;
import org.asteriskjava.manager.event.MeetMeLeaveEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.asteriskjava.manager.event.QueueCallerJoinEvent;
import org.asteriskjava.manager.event.QueueCallerLeaveEvent;
import org.asteriskjava.manager.response.ManagerResponse;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Service
public class EventsHandlerImpl implements EventsHandler { 	
	@Autowired
	UserService userService;
	
	@Autowired
	CampaignService campaignService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	private ActionHandler actionHandler;
	 
	@Autowired
	CallService callService;
	
	@Autowired
	private MeetMeService meetMeService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IvrService ivrService;
	
	@Autowired
	ExtensionDataRepository extensionDataRepository;
	
		
	@Autowired
	TelephonyManagerConnection telephonyManagerConnection;
	
	
	@Autowired
	private MissedCallService missedCallService;
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	
	
	
	@Autowired
	private QueueService queueService;
	
	@Autowired
	private ReportsDAO reportsDAO;
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	private LocalDateTime ringStartTime = LocalDateTime.now();
	
	private LocalDateTime ringEndTime = LocalDateTime.now();
	
	public void hangupEvent(HangupEvent event) {
		ZoneId zid = ZoneId.of("Asia/Kolkata"); 
		//System.out.println(LocalDateTime.now());
		
		if(event.getChannel()!=null) {
			CallPropertiesModel callPropertiesModel=callService.findByChannelsIn(event.getChannel());
			if(callPropertiesModel!=null) {
				
				if(callPropertiesModel.getSecondChannel()!=null&&callPropertiesModel.getSecondChannel().equals(event.getChannel())) {
					callPropertiesModel.setPopupStatus("Connected");
					callService.saveOrUpdate(callPropertiesModel);
				}else {
					try {
						if(callPropertiesModel.getCamName()!=null) {
						CampaignModel cm=campaignRepository.findByCampaignName(callPropertiesModel.getCamName());
						if(cm!=null) {
							int wraptime=Integer.valueOf(cm.getWrapUpTime());
							 Calendar calendar = Calendar.getInstance();
			    			    calendar.setTime(new Date());
			    			    calendar.add(Calendar.SECOND,+wraptime);
			    			    callPropertiesModel.setWrapuptime(calendar.getTime());  
						}
						}
						}catch(Exception e) {}
					
					
					 LocalDateTime now = LocalDateTime.now(zid);
					callPropertiesModel.setCallEndTime(now);
					
					if((callPropertiesModel.getSipChannel()!=null&&callPropertiesModel.getSipChannel().equals(event.getChannel())&&callPropertiesModel.getCallConnectTime()!=null)||(callPropertiesModel.getTrunkChannel()!=null&&callPropertiesModel.getTrunkChannel().equalsIgnoreCase(event.getChannel()))) {
						callPropertiesModel.setPopupStatus("Hangup");
						callService.saveOrUpdate(callPropertiesModel);
						
					}else {
						if(callPropertiesModel.getCallConnectTime()==null&&callPropertiesModel.getTrunkChannel()!=null&&callPropertiesModel.getTrunkChannel().equalsIgnoreCase(event.getChannel())) {
							callPropertiesModel.setPopupStatus("Hangup");
							callPropertiesModel.setCallStatus("NO ANSWER");
							callService.saveOrUpdate(callPropertiesModel);
						}
					}
					try {
						if(callPropertiesModel.getCamName()!=null&&callPropertiesModel.getCamName().equalsIgnoreCase("crmcall"))
						{
							reportsDAO.disposeCall(callPropertiesModel.getId());
								
						}
					}catch(Exception e) {}
					try {
						if("inbound".equalsIgnoreCase(callPropertiesModel.getCallDirection())) {
							if(callPropertiesModel.getTrunkChannel().equalsIgnoreCase(event.getChannel())) {
								if(callPropertiesModel.getCallConnectTime()==null) {
									try {
										
										reportsDAO.disposeCall(callPropertiesModel.getId());
									}catch(Exception e) {}
									String phoneNumber=callPropertiesModel.getPhoneNo();
									if(phoneNumber.length()>9) {
										phoneNumber= phoneNumber.substring(phoneNumber.length()-10,phoneNumber.length());
									}
									MissedCallModel missedCallModel1=missedCallService.findByPhoneNumberAndStatus(phoneNumber,"open");
									if(missedCallModel1==null) {
									MissedCallModel missedCallModel=new MissedCallModel();
									missedCallModel.setCallStatus("open");
									missedCallModel.setPhoneNumber(phoneNumber);
									missedCallService.insertMissedCallRequest(missedCallModel);
									}
								}
							}
						}
					}catch(Exception e) {}
					
				}
				
				if(callPropertiesModel.getCallConnectTime()!=null&&callPropertiesModel.getSipChannel()!=null&&callPropertiesModel.getSipChannel().equals(event.getChannel())) {
				//	reportsDAO.disposeCall(callPropertiesModel.getId());
					try {
					callPropertiesModel.setIsClosed("1");
					callService.saveOrUpdate(callPropertiesModel);
					
					//quickService.submit(new Runnable() {
					//	@Override
					//	public void run() {
					List<LoginModel> loginModel=new ArrayList<>();
					loginModel=loginService.findAllByExtensionAndStatus(callPropertiesModel.getExtension(),"login");
					for(LoginModel loginModel1: loginModel) {
						String startTime="";
						try {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
							LocalDateTime now = LocalDateTime.now(zid);
							startTime= dtf.format(now);
						}catch(Exception e) {}
						loginModel1.setStatus("Wrapping");

						loginModel1.setStatusTime(startTime);
						loginService.saveOrUpdate(loginModel1);
					}
					//	}});
					
					}catch(Exception e) {}
					
				}
				
			}		
			
		}
		//System.out.println(LocalDateTime.now());
	}
	
	public void dialEndEvent(DialEndEvent event) {	
		ZoneId zid = ZoneId.of("Asia/Kolkata");
		
		
		if(event.getChannel()!=null) {
			CallPropertiesModel callPropertiesModel=new CallPropertiesModel();
			if(ObjectId.isValid(event.getUniqueId())) {
			callPropertiesModel=callService.checkOutgoingOrNot(event.getUniqueId()==null?"NA":event.getUniqueId());	
			
			}
			if(callPropertiesModel.getPhoneNo()==null) {
				CallPropertiesModel callPropertiesModel1=callService.findByChannelsIn(event.getDestination());
				
				
				if(callPropertiesModel1!=null) {
					if(callPropertiesModel1.getSecondChannel()!=null&&callPropertiesModel1.getSecondChannel().equals(event.getDestination())) {						
						callPropertiesModel1.setPopupStatus("Connected");
						callService.saveOrUpdate(callPropertiesModel1);
					}else {	
						if(event.getDialStatus().equalsIgnoreCase("ANSWER")){
							 LocalDateTime now = LocalDateTime.now(zid);
							callPropertiesModel1.setCallConnectTime(now);
							callPropertiesModel1.setPopupStatus("Connected");
							callPropertiesModel1.setCallStatus(event.getDialStatus());
							try {
								IvrModel ivrmodel=new IvrModel();
								String pho=callPropertiesModel1.getPhoneNo();
								try {
									pho=pho.substring(pho.length()-10,pho.length());
					    		}catch(Exception e) {}
					    		try {
					    			ivrmodel = ivrService.findByPhoneNo(pho);
						    	}catch(Exception e) {
						    	}
								if(ivrmodel.getPhoneNo()!=null) {
									callPropertiesModel1.setIvrFlow(ivrmodel.getIvrFlow());
								}
							}catch(Exception e) {}
							callPropertiesModel1.setRingEndTime(ringEndTime);
							callService.saveOrUpdate(callPropertiesModel1);
							
							//quickService.submit(new Runnable() {
				    		//	@Override
				    		//	public void run() {
				    				try {
				    			
				    					List<LoginModel> loginModel=new ArrayList<>();
				    					loginModel=loginService.findAllByExtensionAndStatus(callPropertiesModel1.getExtension(),"login");
										for(LoginModel loginModel1: loginModel) {
											String startTime="";
									try {
										DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
										   //LocalDateTime now = LocalDateTime.now();
										   startTime= dtf.format(now);
									}catch(Exception e) {}
									loginModel1.setStatus("On-Call");
									loginModel1.setDirection("InBound");
									loginModel1.setStatusTime(startTime);
									loginService.saveOrUpdate(loginModel1);
								}
							}catch(Exception e) {}
							try {
								if(callPropertiesModel1.getExtension()!=null) {
									List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
									usercampaignModels=campaignService.findByUserextension(callPropertiesModel1.getExtension());
									for(UserCampaignMappingModel ucm:usercampaignModels) {
										if(ucm.getQueue()!=null) {
								QueueDTO queueDTO=new QueueDTO();
								queueDTO.setQueue(ucm.getQueue());
								queueDTO.setIface("Local/"+callPropertiesModel1.getExtension()+"@from-queue");
							
							quickService.submit(new Runnable() {
				    		@Override
				    			public void run() {
								try {
									actionHandler.queueRemove(queueDTO);
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
								}catch(Exception e) {}
				    			//}});
							}
							
					}
				}
				
			}else {
				
				String ext=callPropertiesModel.getExtension();
				if(event.getDialStatus().equalsIgnoreCase("ANSWER")){
					 LocalDateTime now = LocalDateTime.now(zid);
				callPropertiesModel.setCallConnectTime(now);
				callPropertiesModel.setPopupStatus("Connected");
				callPropertiesModel.setDisposition("Ongoing");
				callPropertiesModel.setCallStatus(event.getDialStatus());
				callPropertiesModel.setRingEndTime(ringEndTime);
				callService.saveOrUpdate(callPropertiesModel);
				String ext1=callPropertiesModel.getExtension();
				//quickService.submit(new Runnable() {
	    		//	@Override
	    		//	public void run() {
				try {
					
					try {
					//LoginModel loginModel=loginService.findByExtensionAndStatus(ext1,"login");
					//if(loginModel!=null) {
						List<LoginModel> loginModel=new ArrayList<>();
						loginModel=loginService.findAllByExtensionAndStatus(ext1,"login");
						for(LoginModel loginModel1: loginModel) {
						
						String startTime="";
						try {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
							   //LocalDateTime now = LocalDateTime.now();
							   startTime= dtf.format(now);
						}catch(Exception e) {}
						loginModel1.setStatus("On-Call");
						loginModel1.setDirection("OutBound");
						loginModel1.setStatusTime(startTime);
						loginService.saveOrUpdate(loginModel1);
					}
					}catch(Exception e) {}
					List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
					usercampaignModels=campaignService.findByUserextension(ext1);
					for(UserCampaignMappingModel ucm:usercampaignModels) {
						if(ucm.getQueue()!=null) {
					QueueDTO queueDTO=new QueueDTO();
					queueDTO.setQueue(ucm.getQueue());
					queueDTO.setIface("Local/"+ext+"@from-queue");
					quickService.submit(new Runnable() {
		    			@Override
		    			public void run() {
					try {
						actionHandler.queueRemove(queueDTO);
						
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
				}catch(Exception e) {}
	    			//}});
				}else {
					LocalDateTime now = LocalDateTime.now(zid);
				callPropertiesModel.setCallEndTime(now);
				callPropertiesModel.setPopupStatus("Hangup");
				callPropertiesModel.setCallStatus(event.getDialStatus());
				callService.saveOrUpdate(callPropertiesModel);
				}
			}
		}
System.out.println("end end");
	}

	public void dialBeginEvent(DialBeginEvent event) {
		ZoneId zid = ZoneId.of("Asia/Kolkata");
		System.out.println(event);
		LocalDateTime now = LocalDateTime.now(zid);
				
		if(event.getChannel()!=null) {
			CallPropertiesModel callPropertiesModel=new CallPropertiesModel();
			if(ObjectId.isValid(event.getUniqueId())) {
				callPropertiesModel=callService.checkOutgoingOrNot(event.getUniqueId()==null?"NA":event.getUniqueId());
				
				
			}
			ArrayList<String> channels1=new ArrayList<String>();
			if(event.getUniqueId().contains(";")) {
				String[] str=event.getUniqueId().split(";");
				event.setUniqueId(str[0]);
				if(ObjectId.isValid(event.getUniqueId())) {
					callPropertiesModel=callService.checkOutgoingOrNot(event.getUniqueId()==null?"NA":event.getUniqueId());
				}
			}
			if(callPropertiesModel.getPhoneNo()==null) {
				boolean incoming=false;
				if(event.getDialString()!=null) {
					if(event.getCallerIdNum()!=null) {
						CallPropertiesModel cm=callService.findByPhoneNoAndIsClosed(event.getCallerIdNum()==null?"NA":event.getCallerIdNum());
						if(cm!=null) {
							incoming=true;
						}
					}
					String dialString2 = event.getDialString();
					System.out.println("dialString2"+dialString2);
					
					if(incoming || dialString2.contains("from-queue")||!dialString2.contains("/")) {
						incoming=true;
					}
					if(!incoming){
						String[] dialString=event.getDialString().split("/");

						String dialString1=dialString[1];
						CallPropertiesModel callPropertiesModel1=new CallPropertiesModel();
						if(dialString1!=null&&dialString1.length()>10){
							dialString1=dialString1.substring(dialString1.length()-10,dialString1.length());
						}
						callPropertiesModel1=callService.findBySecondNumber(dialString1==null?"NA":dialString1);
						if(callPropertiesModel1.getPhoneNo()!=null) {
							callPropertiesModel1.setSecondChannel(event.getDestination());
							channels1.add(callPropertiesModel1.getSipChannel());
							channels1.add(callPropertiesModel1.getTrunkChannel());
							channels1.add(event.getDestination());
							callPropertiesModel1.setChannels(channels1);
							callService.saveOrUpdate(callPropertiesModel1);
						}
					}else {
						CallPropertiesModel callPropertiesModel2=callService.findByPhoneNoAndIsClosed(event.getCallerIdNum()==null?"NA":event.getCallerIdNum());
						if(callPropertiesModel2!=null) {
							CallPropertiesModel callPropertiesModel5=callService.findByChannelsIn(event.getChannel());
							if(callPropertiesModel5==null) {
								CallPropertiesModel callPropertiesModel6=callService.findByChannelsIn(event.getDestination());
								if(callPropertiesModel6==null) {
									ArrayList<String> channels=new ArrayList<String>();
									channels.add(event.getDestination());
									channels.add(callPropertiesModel2.getTrunkChannel());
									callPropertiesModel2.setSipChannel(event.getDestination());
									callPropertiesModel2.setPopupStatus("Dialing");
									callPropertiesModel2.setCallStartTime(now);
									callPropertiesModel2.setCallStatus("Dial");
									if(event.getDialString().contains("/")) {
										callPropertiesModel2.setExtension(CommonUtil.getExtensionFromChannelOrDestination(event.getChannel()));
									}else {
										if(!CommonUtil.isNullOrEmpty(callPropertiesModel2.getExtension())) {
									callPropertiesModel2.setExtension(event.getDialString());
										}
									}
									callPropertiesModel2.setRingStartTime(ringStartTime);
									
									callPropertiesModel2.setChannels(channels);
									callService.saveOrUpdate(callPropertiesModel2);
								}
							}
						}else {
							/*	CallPropertiesModel callPropertiesModel5=callService.findByChannelsIn(event.getChannel());
							if(callPropertiesModel5==null) {
							CallPropertiesDTO callPropertiesDTO = new CallPropertiesDTO();
							ArrayList<String> channels=new ArrayList<String>();
							channels.add(event.getChannel());
							ObjectId id=new ObjectId();
							callPropertiesDTO.setId(id);
							callPropertiesDTO.setPhoneNo(event.getCallerIdNum());
							callPropertiesDTO.setCallConnectTime(null);
							callPropertiesDTO.setCallDirection("inbound");
							callPropertiesDTO.setCallEndTime(null);
							callPropertiesDTO.setCallStartTime(new Date());
							callPropertiesDTO.setCallStatus("Dial");
							callPropertiesDTO.setExtension("");
							callPropertiesDTO.setIsClosed("0");
							callPropertiesDTO.setPopupStatus("dialing");
							callPropertiesDTO.setSecondChannel("");
							callPropertiesDTO.setSipChannel("");
							callPropertiesDTO.setTrunkChannel(event.getChannel());
							callPropertiesDTO.setDeleted("No");
							callPropertiesDTO.setChannels(channels);
							callService.initCallData(callPropertiesDTO);
							}
							 */
						}
					}
				}
			}else {
				if(callPropertiesModel.getExtension()!=null&&callPropertiesModel.getExtension().length()>=10) {
					ArrayList<String> channels=new ArrayList<String>();
					if(callPropertiesModel.getSipChannel().isEmpty()) {
						channels.add(event.getDestination());
						callPropertiesModel.setSipChannel(event.getDestination());
					}else {
						channels.add(callPropertiesModel.getSipChannel());
						channels.add(event.getDestination());
						callPropertiesModel.setTrunkChannel(event.getDestination());
						
					}
					callPropertiesModel.setPopupStatus("Dialing");
					LocalDateTime now1 = LocalDateTime.now(zid);
					callPropertiesModel.setCallStartTime(now1);
					System.out.println("Call Start Time :"+now1.toString());
					//callPropertiesModel.setTrunkChannel(event.getDestination());
					callPropertiesModel.setChannels(channels);
					callPropertiesModel.setDisposition("Ongoing");
					callPropertiesModel.setDisposition("Ongoing");
					callService.saveOrUpdate(callPropertiesModel);
				}else {
				ArrayList<String> channels=new ArrayList<String>();
				channels.add(event.getChannel());
				channels.add(event.getDestination());
				LocalDateTime now1 = LocalDateTime.now(zid);
				callPropertiesModel.setCallStartTime(now1);
				//System.out.println("Call Start Time :"+now.toString());
				callPropertiesModel.setSipChannel(event.getChannel());
				callPropertiesModel.setTrunkChannel(event.getDestination());
				callPropertiesModel.setChannels(channels);
				callPropertiesModel.setDisposition("Ongoing");
				
				callPropertiesModel.setRingStartTime(ringStartTime);
				callService.saveOrUpdate(callPropertiesModel);
				}
			}
		}	
	}
	
		
	/*public void BridgeEvent(BridgeEvent event) {
		if(event.getChannel1()!=null) {
			String channel2 = event.getChannel2();		
			String phoneNo = channel2.substring(channel2.indexOf('-')-10,channel2.indexOf('-'));		
			CallPropertiesModel callPropertiesModel1 = callService.checkPhoneNo(phoneNo);

			if(callPropertiesModel1.getPhoneNo().length()>4) {			
				callPropertiesModel1.setPhoneNo(callPropertiesModel1.getPhoneNo());
				callPropertiesModel1.setCallDirection(event.getCallerId2());
				callPropertiesModel1.setCallStatus(event.getChannel2());
				callPropertiesModel1.setExtension(event.getExten());
				callPropertiesModel1.setSipChannel(event.getCallerId2());
				callPropertiesModel1.setTrunkChannel(event.getCallerId2());
				callService.saveOrUpdate(callPropertiesModel1);
			
			}
		}	
		//System.out.println(event);
		
	}*/

	

	@Override
	public void queueCallerJoinEvent(QueueCallerJoinEvent event) {
		ZoneId zid = ZoneId.of("Asia/Kolkata");
		LocalDateTime now = LocalDateTime.now(zid);
		CallPropertiesModel callPropertiesModel=callService.findByChannelsIn(event.getChannel());
		if(callPropertiesModel==null) {
			
			//Re-Visit
			//CallPropertiesModel callPropertiesModelPrevious = callService.findByPhoneNo(event.getCallerIdNum());
			//if (!CommonUtil.isNull(callPropertiesModelPrevious)) {
			//	callService.deleteById(callPropertiesModelPrevious.getId());
			//}
			
		CallPropertiesDTO callPropertiesDTO = new CallPropertiesDTO();
		ArrayList<String> channels=new ArrayList<String>();
		channels.add(event.getChannel());
		ObjectId id=new ObjectId();
		
		callPropertiesDTO.setId(id);
		callPropertiesDTO.setPhoneNo(event.getCallerIdNum());
		callPropertiesDTO.setCallConnectTime(null);
		callPropertiesDTO.setCallDirection("inbound");
		callPropertiesDTO.setCallEndTime(null);
		callPropertiesDTO.setCallStartTime(now);
		callPropertiesDTO.setCallStatus("Dial");
		callPropertiesDTO.setExtension("");
		callPropertiesDTO.setIsClosed("0");
		callPropertiesDTO.setPopupStatus("In Queue");
		callPropertiesDTO.setSecondChannel("");
		callPropertiesDTO.setSipChannel("");
		callPropertiesDTO.setTrunkChannel(event.getChannel());
		callPropertiesDTO.setDeleted("No");
		callPropertiesDTO.setChannels(channels);
		callPropertiesDTO.setQueueJoinTime(now);
		callPropertiesDTO.setQueueRemoveTime(null);
		callPropertiesDTO.setQueue(event.getQueue());
		callPropertiesDTO.setDisposition("Ongoing");
		try {
			CampaignModel campaignModel=campaignService.findByCampaignName("Incoming");
			if(campaignModel!=null) {
				CallPropertiesModel callPropertiesModel21=callService.findById(id.toString());
				
				callPropertiesDTO.setCamName(campaignModel.getCampaignName());
				//callService.saveOrUpdate(callPropertiesModel2);
			}
		}catch(Exception e) {}
		callService.initCallData(callPropertiesDTO);
		try {
			CallPropertiesModel callPropertiesModel20=callService.findById(id.toString());
			callPropertiesModel20.setIvr(event.getUniqueId());
			callService.saveOrUpdate(callPropertiesModel20);
			
		}catch(Exception e) {}
		
		}
		
		try {
			IvrModel ivrModel=ivrService.findByUniqueId(event.getUniqueId());
			if(ivrModel!=null) {
				String startTime="";
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					   LocalDateTime now10 = LocalDateTime.now();
					   startTime= dtf.format(now);
				}catch(Exception e) {}
				ivrModel.setEndTime(startTime);
				ivrService.saveOrUpdate(ivrModel);
				
				
			}
			
		}catch(Exception e) {}
		QueueModel queueModel=queueService.findByQueue(event.getQueue());
		if(queueModel==null) {
			QueueModel queueModel1=new QueueModel();
			queueModel1.setCount(event.getCount());
			queueModel1.setQueue(event.getQueue());
		queueService.saveOrUpdate(queueModel1);
		}else {
			queueModel.setCount(event.getCount());
			queueService.saveOrUpdate(queueModel);
		}
	}

	@Override
	public void queueCallerLeaveEvent(QueueCallerLeaveEvent event) {
		ZoneId zid = ZoneId.of("Asia/Kolkata");
		CallPropertiesModel callPropertiesModel1=callService.findByChannelsIn(event.getChannel());
		if(callPropertiesModel1!=null) {
			LocalDateTime now = LocalDateTime.now(zid);
			callPropertiesModel1.setQueueRemoveTime(now);
			callPropertiesModel1.setExtension(event.getConnectedLineNum());
			//callPropertiesModel1.setCallStartTime(now);
			callService.saveOrUpdate(callPropertiesModel1);
		}
		
		QueueModel queueModel=queueService.findByQueue(event.getQueue());
		if(queueModel==null) {
			QueueModel queueModel1=new QueueModel();
			queueModel1.setCount(event.getCount());
			queueModel1.setQueue(event.getQueue());
		queueService.saveOrUpdate(queueModel1);
		}else {
			queueModel.setCount(event.getCount());
			queueService.saveOrUpdate(queueModel);
		}
	
	}

	@Override
	public void meetMeJoinEvent(MeetMeJoinEvent event) {
		MeetMeModel meetme=meetMeService.findByRoom(event.getMeetMe());
		if(meetme==null) {
			MeetMeModel meetme1=new MeetMeModel();
					List<String> channel=new ArrayList<String>();
			channel.add(event.getChannel());
			meetme1.setChannels(channel);
			meetme1.setStatus("busy");
			meetme1.setRoom(event.getMeetMe());
			meetMeService.saveOrUpdate(meetme1);
		}else {
			List<String> channel1=meetme.getChannels();
			if(!channel1.contains(event.getChannel())) {
			channel1.add(event.getChannel());
			}
			meetme.setChannels(channel1);
			meetme.setStatus("busy");
			meetMeService.saveOrUpdate(meetme);
		}
		
	}

	@Override
	public void meetMeLeaveEvent(MeetMeLeaveEvent event) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException{
		MeetMeModel meetme=meetMeService.findByRoom(event.getMeetMe());
		if(meetme!=null) {
			List<String> channel1=meetme.getChannels();
			channel1.remove(event.getChannel());
			if(channel1.isEmpty()) {
				meetme.setStatus("free");	
			}
			meetMeService.saveOrUpdate(meetme);
			
			if(channel1.size()==1) {
				HangupAction hangupAction=new HangupAction();
				hangupAction.setChannel(channel1.get(0));
				ManagerResponse HangupResponse = telephonyManagerConnection.getExisitingConnection().sendAction(hangupAction, 30000);		
			}
		}
	}
		
	@Override
	public void peerStatusEvent(PeerStatusEvent event) {
		ExtensionDataModel extensionDataModel = new ExtensionDataModel();
		extensionDataModel = extensionDataRepository.findByExtension(event.getPeer());
		if(CommonUtil.isNull(extensionDataModel)) {
			ExtensionDataModel extensionDataModel1 = new ExtensionDataModel();
			extensionDataModel1.setAddress(event.getAddress());
			extensionDataModel1.setPeerStatus(event.getPeerStatus());
			extensionDataModel1.setExtension(event.getPeer());		
			extensionDataModel1 = extensionDataRepository.save(extensionDataModel1);
		}
		else {
			extensionDataModel.setPeerStatus(event.getPeerStatus());
			extensionDataModel.setAddress(event.getAddress());
			extensionDataModel = extensionDataRepository.save(extensionDataModel);			
		}
	}
		
		@Override
		public void bridgeEvent(BridgeEvent event) {		
			if((event.getChannel1().contains("Local")||event.getChannel1().contains("DAHDI"))&& (event.getChannel2().contains("DAHDI")||event.getChannel1().contains("Local"))) {
				String userExtension="";
				String agentName="";				
				UserModel userModel = new UserModel();
				if(event.getBridgeState().contentEquals("Link")) {
					LoginModel loginModel=new LoginModel();
					if(event.getChannel1().contains("DAHDI")) userExtension = event.getChannel1();
					if(event.getChannel2().contains("DAHDI")) userExtension = event.getChannel2();
					if(userExtension.length()>5) {
						 int pos1 = userExtension.lastIndexOf("-");
					     int pos2 = userExtension.indexOf("/",userExtension.indexOf("/")+1);
					     userExtension = userExtension.substring(pos2+1,pos1);
					     userModel = userService.findByUserextension(userExtension);
					     if(userModel!=null) loginModel.setAgentName(userModel.getUsername());
					     else loginModel.setAgentName(userExtension);
					}
					loginModel.setExtension(userExtension);
					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat formatter7=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					LocalDateTime now = LocalDateTime.now();				
					loginModel.setStartTime(now.toString());
					loginModel.setStatus("On-Call");
					loginModel.setLoginStatus("login");
					loginModel.setDirection("In-Bound");
					loginModel.setStatusTime(LocalDateTime.now().toString());			
					loginService.saveOrUpdate(loginModel);
				}
			
				
				if(event.getBridgeState().contentEquals("Unlink")) {
					System.out.println("Passed unlink");
					if(event.getChannel1().contains("DAHDI")) userExtension = event.getChannel1();
					if(event.getChannel2().contains("DAHDI")) userExtension = event.getChannel2();
					if(userExtension.length()>5) {
						 int pos1 = userExtension.lastIndexOf("-");
					     int pos2 = userExtension.indexOf("/",userExtension.indexOf("/")+1);
					     userExtension = userExtension.substring(pos2+1,pos1);					    
					}
					 userModel = userService.findByUserextension(userExtension);
					 System.out.println("Extension  :"+userExtension);
				    System.out.println("User Name :"+userModel.getUsername());
					LoginModel loginModel=loginService.findByAgentName(userModel.getUsername());
					if(loginModel!=null) {
						 System.out.println("Id  :"+loginModel.getId());
						loginService.deleteById(loginModel.getId());
					}
				}
			}
		}
	
}
