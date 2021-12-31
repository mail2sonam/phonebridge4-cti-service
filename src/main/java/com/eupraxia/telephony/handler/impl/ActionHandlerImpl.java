package com.eupraxia.telephony.handler.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.AtxferAction;
import org.asteriskjava.manager.action.BridgeAction;
import org.asteriskjava.manager.action.HangupAction;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.eupraxia.telephony.DTO.CallPropertiesDTO;
import com.eupraxia.telephony.DTO.ConnectionDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.Digital.AMIListener;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CampaignModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.MeetMeModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.components.TelephonyManagerConnection;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.MeetMeService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.util.CommonUtil;

import org.asteriskjava.manager.action.QueueAddAction;
import org.asteriskjava.manager.action.QueueRemoveAction;
import org.asteriskjava.manager.action.RedirectAction;

@Service

public class ActionHandlerImpl implements ActionHandler { 		
	@Autowired
	AMIListener amiListener;
	
	@Autowired
	CampaignService campaignService;

	@Autowired
	TelephonyManagerConnection telephonyManagerConnection;
	
	@Autowired
	private MeetMeService meetMeService;
	
	@Autowired
	CallService callService;
	
	@Autowired
	LoginService loginService;
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	
	
	public ManagerConnection getManageConnection(ConnectionDTO connectionDTO) throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {
		ManagerConnectionFactory factory = new ManagerConnectionFactory(connectionDTO.getAmiManagerIp(),connectionDTO.getAmiUserName(),connectionDTO.getAmiPassword());    	
		ManagerConnection managerConnection = factory.createManagerConnection();
		managerConnection.login();	
		amiListener.registerListener(managerConnection);
		return managerConnection;
	}
	
	@Override
	public ManagerResponse originateCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {
		OriginateAction originateAction = new OriginateAction();	
		ManagerResponse originateResponse = null;
		CallPropertiesModel callPropertiesModel=addCallProperties(originateDTO);
		try {
		
		if(originateDTO.getExtension()!=null&&!originateDTO.getExtension().isEmpty()&&originateDTO.getExtension().length()>=10) {
			originateAction.setChannel("Local/"+originateDTO.getExtension()+"@from-internal");
		}else {
			originateAction.setChannel(originateDTO.getChannel());
		}
		
		originateAction.setContext(originateDTO.getContext());		
		originateAction.setExten(originateDTO.getPrefix()+originateDTO.getPhoneNo());		
		originateAction.setPriority(originateDTO.getPriority());		
				} catch(Exception e) {
			originateResponse.setMessage("Originate failed");	
			return originateResponse;
		}
		originateAction.setAsync(true);
		originateAction.setChannelId(callPropertiesModel.getId().toString());
		originateAction.setCallerId(callPropertiesModel.getId().toString());
		originateResponse = telephonyManagerConnection.getExisitingConnection().sendAction(originateAction,30000);	
	System.out.println(originateResponse.getMessage());
		if(originateResponse.getMessage().contentEquals("Originate failed")) {
			callService.deleteById(callPropertiesModel.getId().toString());
		}
		
		return originateResponse;		
	}
	
	
	public ManagerResponse spyCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {
		OriginateAction originateAction = new OriginateAction();
		
		System.out.println("Inside SpyCall");
		originateAction.setChannel("SIP/"+originateDTO.getChannel());
		
		if(originateDTO.getSpyType().equalsIgnoreCase("listen")) {
			originateAction.setContext("ext-local-custom");
			originateAction.setExten("*222"+originateDTO.getExten());	
		}
		if(originateDTO.getSpyType().equalsIgnoreCase("whisper")) {
			originateAction.setContext("ext-local-custom-1");
			originateAction.setExten("*223"+originateDTO.getExten());	
		}
		if(originateDTO.getSpyType().equalsIgnoreCase("barge")) {
			originateAction.setContext("ext-local-custom-2");
			originateAction.setExten("*224"+originateDTO.getExten());	
		}
			
		originateAction.setPriority(1);		
		originateAction.setChannelId("Spying");
		originateAction.setCallerId("Spying");
		System.out.println("originateAction.getChannel() :"+originateAction.getChannel());
		System.out.println("originateAction.getContext() :"+originateAction.getContext());
		System.out.println("originateAction.getContext() :"+originateAction.getExten());
		
		ManagerResponse originateResponse = telephonyManagerConnection.getExisitingConnection().sendAction(originateAction,30000);	
	
		String events = originateResponse.getEvents();
		//System.out.println(events);
		return originateResponse;		
	}
	
	public ManagerResponse hangUpCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		HangupAction hangupAction=new HangupAction();
		hangupAction.setChannel(originateDTO.getChannel());
		ManagerResponse HangupResponse = telephonyManagerConnection.getExisitingConnection().sendAction(hangupAction, 30000);		
		return HangupResponse;		
	}
	
	public ManagerResponse holdCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {
		ZoneId zid = ZoneId.of("Asia/Kolkata"); 
		System.out.println(LocalDateTime.now());
		RedirectAction redirectAction =new RedirectAction();
		redirectAction.setChannel(originateDTO.getChannel());
		redirectAction.setExtraChannel(originateDTO.getExtraChannel());
		redirectAction.setExten("70");
		redirectAction.setContext("enter-moh");
		redirectAction.setPriority(1);
		
		try {
			List<LoginModel> loginModel=new ArrayList<>();
			 loginModel=loginService.findAllByExtensionAndStatus(originateDTO.getExtension(),"login");
			for(LoginModel loginModel1: loginModel) {
				String startTime="";
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					   LocalDateTime now = LocalDateTime.now(zid);
					   startTime= dtf.format(now);
				}catch(Exception e) {}
				loginModel1.setStatus("Hold");
				loginModel1.setStatusTime(startTime);
				loginService.saveOrUpdate(loginModel1);
			}
		}catch(Exception e) {}
		
		//ParkAction parkAction=new ParkAction();
		//parkAction.setChannel("SIP/".concat(originateDTO.getChannel()));
		//if(originateDTO.getManagerConnection().getState().DISCONNECTED !=null)
		//originateDTO.setManagerConnection(getManageConnection(originateDTO.getConnectionDTO()));
		ManagerResponse holdResponse = telephonyManagerConnection.getExisitingConnection().sendAction(redirectAction, 30000);	
		CallPropertiesModel callPropertiesModel=callService.findByChannelsIn(originateDTO.getChannel());
		if(callPropertiesModel!=null) {
			callPropertiesModel.setPopupStatus("Hold");
			LocalDateTime holdStartTime =LocalDateTime.now(zid);
			callPropertiesModel.setHoldStartTime(holdStartTime);
			callService.saveOrUpdate(callPropertiesModel);
		}
		System.out.println(LocalDateTime.now());
		return holdResponse;		
	}
	public ManagerResponse transferCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		AtxferAction atxferAction=new AtxferAction();
		atxferAction.setChannel("SIP/".concat(originateDTO.getChannel()));
		atxferAction.setExten("9".concat(originateDTO.getPhoneNo()));
		atxferAction.setContext(originateDTO.getContext());
		atxferAction.setPriority(originateDTO.getPriority());
		if(originateDTO.getManagerConnection().getState().DISCONNECTED !=null)
		originateDTO.setManagerConnection(getManageConnection(originateDTO.getConnectionDTO()));
		ManagerResponse transferResponse = originateDTO.getManagerConnection().sendAction(atxferAction, 30000);	
		return transferResponse;		
	}

	@Override
	public ManagerResponse redirectCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {	
		RedirectAction redirectAction =new RedirectAction();
		redirectAction.setChannel("SIP/".concat(originateDTO.getChannel()));
		redirectAction.setExten(originateDTO.getExten());
		redirectAction.setContext(originateDTO.getContext());
		redirectAction.setPriority(originateDTO.getPriority());    	 
		redirectAction.setExtraChannel(originateDTO.getExtraChannel());
		redirectAction.setExtraExten(originateDTO.getExtraExten());
		redirectAction.setExtraContext(originateDTO.getExtraContext());
		redirectAction.setExtraPriority(originateDTO.getExtraPriority());  
		if(originateDTO.getManagerConnection().getState().DISCONNECTED !=null)
		originateDTO.setManagerConnection(getManageConnection(originateDTO.getConnectionDTO()));
		ManagerResponse redirectResponse = originateDTO.getManagerConnection().sendAction(redirectAction, 30000);	
		return redirectResponse;		
	}
	
	public ManagerResponse queueAdd(QueueDTO queueDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {	
		QueueAddAction queueAddAction=new QueueAddAction();
		queueAddAction.setQueue(queueDTO.getQueue());	 
		queueAddAction.setInterface(queueDTO.getIface());
		ManagerResponse queueAddResponse = telephonyManagerConnection.getExisitingConnection().sendAction(queueAddAction, 30000);		
		return queueAddResponse;		
	}
	
	public ManagerResponse queueRemove(QueueDTO queueDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {	
		QueueRemoveAction queueRemoveAction=new QueueRemoveAction();
		queueRemoveAction.setQueue(queueDTO.getQueue());	 
		queueRemoveAction.setInterface(queueDTO.getIface());
		ManagerResponse queueRemoveResponse = telephonyManagerConnection.getExisitingConnection().sendAction(queueRemoveAction, 30000);		
		return queueRemoveResponse;		
	}


	@Override
	public ManagerResponse AttendedTransfer(OriginateDTO originateDTO) throws IllegalArgumentException,
			IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {
		AtxferAction atxferAction=new AtxferAction();
   	 atxferAction.setChannel(originateDTO.getChannel());
   	//if(originateDTO.getPhoneNo().length()==10) {
   		atxferAction.setExten(originateDTO.getPrefix().concat(originateDTO.getPhoneNo()));
      //	 }else {
      //		atxferAction.setExten(originateDTO.getPhoneNo());
      //	   	 }
   		System.out.println("");
   	 atxferAction.setContext(originateDTO.getContext());
   	 atxferAction.setPriority(1);
   	 CallPropertiesModel callPropertiesModel=callService.findByChannelsIn(originateDTO.getChannel());
		if(callPropertiesModel!=null) {
			callPropertiesModel.setPopupStatus("Dialing");
			callPropertiesModel.setSecondNumber(originateDTO.getPhoneNo());
			callService.saveOrUpdate(callPropertiesModel);
		}
		
		
   	 ManagerResponse originateResponse = telephonyManagerConnection.getExisitingConnection().sendAction(atxferAction,30000);	
	    String events = originateResponse.getEvents();
	    //System.out.println(events);
		return originateResponse;	
	
	}


	@Override
	public ManagerResponse unHold(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException,
			IOException, TimeoutException, AuthenticationFailedException {
		ZoneId zid = ZoneId.of("Asia/Kolkata"); 
		BridgeAction bridgeAction=new BridgeAction();
		bridgeAction.setChannel1(originateDTO.getChannel());
		bridgeAction.setChannel2(originateDTO.getExtraChannel());
		
		try {
			List<LoginModel> loginModel=new ArrayList<>();
			 loginModel=loginService.findAllByExtensionAndStatus(originateDTO.getExtension(),"login");
			for(LoginModel loginModel1: loginModel) {
				String startTime="";
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					   LocalDateTime now = LocalDateTime.now(zid);
					   startTime= dtf.format(now);
				}catch(Exception e) {}
				loginModel1.setStatus("On-Call");
				loginModel1.setStatusTime(startTime);
				loginService.saveOrUpdate(loginModel1);
			}
		}catch(Exception e) {}
		
		
		
		ManagerResponse originateResponse = telephonyManagerConnection.getExisitingConnection().sendAction(bridgeAction,30000);	
	    String events = originateResponse.getEvents();
	    //System.out.println(events);
	    CallPropertiesModel callPropertiesModel=callService.findByChannelsIn(originateDTO.getChannel());
		if(callPropertiesModel!=null) {
			callPropertiesModel.setPopupStatus("Connected");
			 LocalDateTime now = LocalDateTime.now(zid);
			callPropertiesModel.setHoldEndTime(now);
			callService.saveOrUpdate(callPropertiesModel);
		}
		
   	 
		return originateResponse;	
	}


	@Override
	public ManagerResponse conference(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException,
			IOException, TimeoutException, AuthenticationFailedException {
		MeetMeModel meetMeModel=meetMeService.findByStatus("free");
		ManagerResponse originateResponse=null;
		if(meetMeModel!=null) {
		RedirectAction redirectAction =new RedirectAction();
   	 redirectAction.setChannel(originateDTO.getChannel());
   	 redirectAction.setExten(meetMeModel.getRoom());
   	 redirectAction.setContext("from-internal");
   	 redirectAction.setPriority(1);
   	 RedirectAction redirectAction1 =new RedirectAction();
	 redirectAction1.setChannel(originateDTO.getExtraChannel());
	 redirectAction1.setExtraChannel(originateDTO.getSipChannel());
	 redirectAction1.setExten(meetMeModel.getRoom());
	 redirectAction1.setContext("from-internal");
	 redirectAction1.setPriority(1);
		
		originateResponse = telephonyManagerConnection.getExisitingConnection().sendAction(redirectAction,30000);	
		 ManagerResponse originateResponse1 = telephonyManagerConnection.getExisitingConnection().sendAction(redirectAction1,30000);	
		 String events = originateResponse.getEvents();
			
		}
		  return originateResponse;
	}
	@Override
	public ManagerResponse addQueue(String extension) throws IllegalArgumentException, IllegalStateException,
			IOException, TimeoutException, AuthenticationFailedException {
		try {
			//quickService.submit(new Runnable() {
			//	@Override
			//	public void run() {
					List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
					usercampaignModels=campaignService.findByUserextension(extension);
					for(UserCampaignMappingModel ucm:usercampaignModels) {
						if(ucm.getQueue()!=null) {
					    QueueDTO queueDTO=new QueueDTO();
						queueDTO.setQueue(ucm.getQueue());
						queueDTO.setIface("Local/"+extension+"@from-queue");
						QueueAddAction queueAddAction=new QueueAddAction();
						queueAddAction.setQueue(queueDTO.getQueue());	 
						queueAddAction.setInterface(queueDTO.getIface());
						try {
							ManagerResponse queueAddResponse = telephonyManagerConnection.getExisitingConnection().sendAction(queueAddAction, 30000);
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
						}		
						
						}
					}
			//	}
		//	});
		}catch(Exception e) {}
		return null;
	}


	@Override
	public ManagerResponse removeQueue(String extension) throws IllegalArgumentException, IllegalStateException,
			IOException, TimeoutException, AuthenticationFailedException {
		ManagerResponse queueAddResponse1 =null;
		try {
			//quickService.submit(new Runnable() {
			//	@Override
			//	public void run() {
					List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
					usercampaignModels=campaignService.findByUserextension(extension);
					for(UserCampaignMappingModel ucm:usercampaignModels) {
						if(ucm.getQueue()!=null) {
					    QueueDTO queueDTO=new QueueDTO();
						queueDTO.setQueue(ucm.getQueue());
						queueDTO.setIface("Local/"+extension+"@from-queue");
						QueueRemoveAction queueAddAction=new QueueRemoveAction();
						queueAddAction.setQueue(queueDTO.getQueue());	 
						queueAddAction.setInterface(queueDTO.getIface());
						try {
							ManagerResponse	queueAddResponse= telephonyManagerConnection.getExisitingConnection().sendAction(queueAddAction, 30000);
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
						}		
						
						}
					}
			//	}
			//});
		}catch(Exception e) {}
		return queueAddResponse1;

	}


	@Override
	public void redirectFeedBack(String channel,String direction) throws IllegalArgumentException, IllegalStateException,
			IOException, TimeoutException, AuthenticationFailedException {
		RedirectAction redirectAction =new RedirectAction();
		redirectAction =new RedirectAction();
	    redirectAction.setChannel(channel);
	    redirectAction.setExten("s");
	    //if("inbound".equalsIgnoreCase(direction)) {
	    //redirectAction.setContext("feedback-ivr");
	    //}else {
	    	 redirectAction.setContext("feedback-ivr-out");
	    //}
	    redirectAction.setPriority(1);
		ManagerResponse holdResponse = telephonyManagerConnection.getExisitingConnection().sendAction(redirectAction, 30000);	
	System.out.println(channel +" :: "+holdResponse.getMessage());
		//return holdResponse;
	}
	
	private CallPropertiesModel addCallProperties(OriginateDTO originateDTO) {
	
		CallPropertiesDTO callPropertiesDTO = new CallPropertiesDTO();
		try {
	/*	List<CallPropertiesModel> callPropertiesModelPrevious=new ArrayList<>();

		
		try {
		callPropertiesModelPrevious = callService.findAllByExtension(originateDTO.getExtension());
		
		
		for(CallPropertiesModel cpm:callPropertiesModelPrevious) {
			callService.deleteById(cpm.getId());
		}
				*/
		
		List<CallPropertiesModel> callPropertiesModelPrevious=new ArrayList<>();
		callPropertiesModelPrevious=callService.findAllByExtension(originateDTO.getExtension());
		if(!callPropertiesModelPrevious.isEmpty()) {
		for(CallPropertiesModel cm:callPropertiesModelPrevious) {
			if(cm.getCallStartTime()!=null) {
				Calendar calendar = Calendar.getInstance(); 
			    Calendar calendar1 = Calendar.getInstance();
			    ZonedDateTime zonedDateTime = cm.getCallStartTime().atZone(ZoneId.systemDefault());
			   calendar1.setTime(Date.from(zonedDateTime.toInstant()));
		        calendar1.add(Calendar.SECOND, 10);
		        
		        if(calendar.before(calendar1)) {
		        
		        	return null; 
		        }else {
		        	callService.deleteById(cm.getId());
		        }
					
			}else {
				callService.deleteById(cm.getId());
			}
			}
		}
		ObjectId id=new ObjectId();
		callPropertiesDTO.setId(id);
		callPropertiesDTO.setPhoneNo(originateDTO.getPhoneNo());
		callPropertiesDTO.setCallConnectTime(null);
		callPropertiesDTO.setCallDirection("OutBound");
		callPropertiesDTO.setCallEndTime(null);
		callPropertiesDTO.setCallStartTime(null);
		callPropertiesDTO.setCallStatus("Initiating");
		callPropertiesDTO.setExtension(originateDTO.getExtension());
		callPropertiesDTO.setIsClosed("0");
		callPropertiesDTO.setPopupStatus("Dialing");
		callPropertiesDTO.setSecondChannel("");
		callPropertiesDTO.setSipChannel("");
		callPropertiesDTO.setTrunkChannel("");
		callPropertiesDTO.setDeleted("No");
		callPropertiesDTO.setDialMethod(originateDTO.getDialMethod());
		callPropertiesDTO.setName(originateDTO.getName());
		
		if(originateDTO.getCamName()!=null&&!originateDTO.getCamName().isEmpty()) {
			callPropertiesDTO.setCamId(originateDTO.getCamId());
			callPropertiesDTO.setCamName(originateDTO.getCamName());
		}else {
			callPropertiesDTO.setCamName("OGDefault");
		}


		return callService.initCallData(callPropertiesDTO);
		} catch(Exception e) {
		}
	return new CallPropertiesModel();

	}

}
