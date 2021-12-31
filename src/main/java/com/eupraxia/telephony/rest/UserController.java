package com.eupraxia.telephony.rest;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.asteriskjava.live.AsteriskQueue;
import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.manager.action.ExtensionStateAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.BreakDTO;
import com.eupraxia.telephony.DTO.LoginDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.QueueDetailsDTO;
import com.eupraxia.telephony.DTO.ReportDTO;
import com.eupraxia.telephony.DTO.UserDTO;
import com.eupraxia.telephony.Model.ExtensionDataModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.MailStoreModel;
import com.eupraxia.telephony.Model.SmsModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.repositories.CallPropertiesRepository;
import com.eupraxia.telephony.repositories.ExtensionDataRepository;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.MailStoreService;
import com.eupraxia.telephony.service.SmsService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.Reports.UserService;
import com.eupraxia.telephony.util.AsteriskInit;
import com.eupraxia.telephony.util.CommonUtil;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	CampaignService campaignService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MailStoreService mailStoreService;
	
	@Autowired
	SmsService smsService;

	@Autowired
	ActionHandler actionhandler;
	
	@Autowired
	ExtensionDataRepository extensionDataRepository;
	
	@Autowired
	EntityManager entityManager;	
	
	@Autowired
	CallPropertiesRepository callPropertiesRepository;
	
	@Autowired
	private AsteriskInit asteriskInit;
	
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	private static volatile long loginCount = 0l;
	private static volatile long logoutCount = 0l;
	private static volatile long freeCount = 0l;
	private static volatile long talkCount = 0l;
	private static volatile long busyCount = 0l;
	private static volatile long notReadyCount = 0l;
	private static volatile long wrappingCount = 0l;
	private static volatile long callsInQueueCount = 0l;

	private static volatile List<MailStoreModel>  mailStoreUnseenList = new ArrayList<>();
	private static volatile List<MailStoreModel>  mailStoreAssignedList = new ArrayList<>();	
	private static volatile List<SmsModel>  smsModelUnseenList=new ArrayList<>();
	private static volatile List<SmsModel>  smsModelAssignedList=new ArrayList<>();
	
	
	@PostMapping(value = "/Save")
    public ResponseEntity<?> saveUsers(@RequestBody UserDTO userDTO) throws MalformedURLException, URISyntaxException {
		String test= userService.saveUser(userDTO);
		return new ResponseEntity<>(test, HttpStatus.OK); 
	}
	
	@PostMapping(value = "/getUsersByDepartment")
	public ResponseEntity<?> showAllUsersBydepartment(@RequestBody UserDTO userDTO) throws MalformedURLException, URISyntaxException {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userModels=userService.findByDepartmentcode(userDTO.getDepartmentcode());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/showAll")
	public ResponseEntity<?>  showAllUsers() throws MalformedURLException, URISyntaxException {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userModels= userService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/showAllTeamLeads")
	public ResponseEntity<?>  showAllTeamLeads() throws MalformedURLException, URISyntaxException {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userModels= userService.findByUsertype("Team Lead");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/deptshowAll")
	public ResponseEntity<?> showAllDeptUsers() throws MalformedURLException, URISyntaxException {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userModels= userService.findAllDepartments();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/id/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(name = "id") long id) throws MalformedURLException, URISyntaxException {
		UserModel userModel = new UserModel();
		userModel= userService.findById(id);
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModel); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/delete/{id}")
	public ResponseEntity<?> deleteUsers(@PathVariable(name = "id") long id) throws MalformedURLException, URISyntaxException {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userService.deleteUser(id);
		userModels= userService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	@GetMapping(value="/deptdelete/{id}")
	public ResponseEntity<?> deleteDeptUsers(@PathVariable(name = "id") long id) throws MalformedURLException, URISyntaxException {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userService.deleteUser(id);
		userModels= userService.findAllDepartments();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", userModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/username/{username}")
	 public ResponseEntity<?> getUserByUserName(@PathVariable(name = "username") String username) throws MalformedURLException, URISyntaxException {
		String test= userService.getUserByUserName(username);
		return new ResponseEntity<>(test, HttpStatus.OK); 
	}
	
	@PostMapping(value = "/login")
	 public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws MalformedURLException, URISyntaxException {
		System.out.println("Name "+userDTO.getUsername());
		System.out.println("Extension "+userDTO.getUserextension());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		boolean similarIP=true;
		System.out.println("inside login");
		map.put("responseCode", "200");
		map.put("responseMessage", "Success");
		map.put("responseType", "Success");   
		UserModel userModel=userService.checkUserUser(userDTO);
		
		if(userModel!=null) {
			String startTime="";
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				   LocalDateTime now = LocalDateTime.now();
				   startTime= dtf.format(now);
				  List<LoginModel> loginModel5=new ArrayList<>();
				  loginModel5=loginService.findAllByExtensionAndStatus(userModel.getUserextension(),"login");
					for(LoginModel loginModel1:loginModel5)	{
						loginModel1.setLoginStatus("logout");
						loginModel1.setEndTime(startTime);
						loginService.saveOrUpdate(loginModel1);
					}
					ExtensionDataModel extensionDataModel = new ExtensionDataModel();
					extensionDataModel = extensionDataRepository.findByExtension("SIP/"+userModel.getUserextension());
					String ip="";
					if(!CommonUtil.isNull(extensionDataModel)) {
						if(extensionDataModel.getAddress()!=null) {
						ip = extensionDataModel.getAddress().split(":")[0];						
						 InetAddress localhost = InetAddress.getLocalHost();
						 similarIP = localhost.getHostAddress().trim().contentEquals(ip)?true:false;
						}
						 if(!similarIP && extensionDataModel.getPeerStatus().contentEquals("Registered")) {
							 	map.put("responseCode", "400");
								map.put("responseMessage", "Extension Already Registered in "+ip);
								map.put("responseType", "ExtensionDuplication");  
								map.put("model", userModel);
								return new ResponseEntity(map, HttpStatus.OK);
						 }
						 
					}
					
			LoginModel loginModel=new LoginModel();
			System.out.println("userModel.getUsername() "+userModel.getUsername());
			System.out.println("userModel.getUserextension() "+userModel.getUserextension());
			loginModel.setAgentName(userModel.getUsername());
			loginModel.setExtension(userModel.getUserextension());
			loginModel.setStartTime(startTime);
			loginModel.setStatus("Ready");
			loginModel.setLoginStatus("login");
			loginModel.setStatusTime(startTime);
		
			
			loginService.saveOrUpdate(loginModel);
			map.put("model", userModel);
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				quickService.submit(new Runnable() {
					@Override
					public void run() {
						try {
							if(userModel.getUsertype().equalsIgnoreCase("agent")) {
						List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
						usercampaignModels=campaignService.findByUserextension(userModel.getUserextension());
						for(UserCampaignMappingModel ucm:usercampaignModels) {
							if(ucm.getQueue()!=null) {
						    QueueDTO queueDTO=new QueueDTO();
							queueDTO.setQueue(ucm.getQueue());
							queueDTO.setIface("Local/"+userModel.getUserextension()+"@from-queue");
							ManagerResponse queueAddResponse = actionhandler.queueAdd(queueDTO);
							}
						}
							}
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			map.put("responseCode", "404");
			map.put("responseMessage", "data not found");
			map.put("responseType", "Error");   
			map.put("model", new UserModel());
		
		}
	 return new ResponseEntity(map, HttpStatus.OK);
	
	}
	
	@PostMapping(value = "/logout")
	 public ResponseEntity<?> logoutUser(@RequestBody UserDTO userDTO) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		List<LoginModel> loginModel=new ArrayList<>();
		loginModel=loginService.findAllByExtensionAndStatus(userDTO.getUserextension(),"login");
		for(LoginModel loginModel4:loginModel) {
			String startTime="";
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				   LocalDateTime now = LocalDateTime.now();
				   startTime= dtf.format(now);
			}catch(Exception e) {}
			loginModel4.setLoginStatus("logout");
			loginModel4.setEndTime(startTime);
			loginService.saveOrUpdate(loginModel4);
			UserModel um=userService.findByUserextension(userDTO.getUserextension());
			if(um!=null) {
				um.setDiallerActive("0");
				userService.saveOrUpdate(um);
						}
		}
		try {
			quickService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						
						List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
						usercampaignModels=campaignService.findByUserextension(userDTO.getUserextension());
						for(UserCampaignMappingModel ucm:usercampaignModels) {
							if(ucm.getQueue()!=null) {
								QueueDTO queueDTO=new QueueDTO();
						queueDTO.setQueue(ucm.getQueue());
						queueDTO.setIface("Local/"+userDTO.getUserextension()+"@from-queue");
						ManagerResponse queueAddResponse = actionhandler.queueRemove(queueDTO);    
							}
						}
					}catch(Exception e) {}
				}
			});
		}catch(Exception e) {}
	 return new ResponseEntity(map, HttpStatus.OK);
	
	}
	
	@PostMapping(value = "/changeMode")
	public ResponseEntity<?> changeMode(@RequestBody BreakDTO userDTO) throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		LoginModel loginModel=loginService.findByExtensionAndStatus(userDTO.getUserextension(),"login");
		
		System.out.println(userDTO.getOnbreak() + ":: "+userDTO.getModeChangeReason());
		if(loginModel!=null) {
			String startTime="";
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				startTime= dtf.format(now);
			}catch(Exception e) {}
			if(userDTO.getOnbreak().equalsIgnoreCase("Available")) {
				loginModel.setStatus("Ready");
				loginModel.setReason(userDTO.getModeChangeReason());
				try {
					if(userDTO.getUserextension()!=null) {
						List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
						usercampaignModels=campaignService.findByUserextension(userDTO.getUserextension());
						for(UserCampaignMappingModel ucm:usercampaignModels) {
							if(ucm.getQueue()!=null) {
								QueueDTO queueDTO=new QueueDTO();
								queueDTO.setQueue(ucm.getQueue());
								queueDTO.setIface("Local/"+userDTO.getUserextension()+"@from-queue");
								ManagerResponse queueAddResponse = actionhandler.queueAdd(queueDTO);  
							}}
					}
				}catch(Exception e) {}

			}else {

				loginModel.setStatus("Not Ready");
				loginModel.setReason(userDTO.getModeChangeReason());
				try {
					List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
					usercampaignModels=campaignService.findByUserextension(userDTO.getUserextension());
					for(UserCampaignMappingModel ucm:usercampaignModels) {
						if(ucm.getQueue()!=null) {

							QueueDTO queueDTO=new QueueDTO();
							queueDTO.setQueue(ucm.getQueue());
							queueDTO.setIface("Local/"+userDTO.getUserextension()+"@from-queue");
							ManagerResponse queueAddResponse = actionhandler.queueRemove(queueDTO);    
						}}
				}catch(Exception e) {}

			}

			loginModel.setStatusTime(startTime);
			loginService.saveOrUpdate(loginModel);
		}

		return new ResponseEntity(map, HttpStatus.OK);

	}
	
	@GetMapping(value="/monitor")
	public ResponseEntity<?> monitorReport() throws MalformedURLException, URISyntaxException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();		
		ArrayList<LoginModel> loginModel=loginService.findByStatus("login");		
		if(!loginModel.isEmpty()) {
			map.put("responseCode", "200");
			map.put("responseMessage", "call Data");
			map.put("responseType", "Success");  
			for(int i=0;i<loginModel.size();i++) {

				LoginModel lm=loginModel.get(i);
				String startTime="";
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat formatter7=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

					LocalDateTime now = LocalDateTime.now();
					startTime= dtf.format(now);

					Date d1 = formatter7.parse(startTime);
					Date d2 = formatter7.parse(loginModel.get(i).getStatusTime()); 

					int queueDuration=(int)((d1.getTime() - d2.getTime())/1000);
					Date d = new Date(queueDuration * 1000L);
					SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
					df.setTimeZone(TimeZone.getTimeZone("GMT"));
					String time = df.format(d);
					lm.setDuration(time);
					loginModel.set(i,lm);
				}catch(Exception e) {}
			}
			List<LoginModel> loginModels = loginModel.stream().filter(e->!e.getAgentName().contentEquals("Admin")).collect(Collectors.toList());
			map.put("model",loginModels );
		}else {
			map.put("responseCode", "400");
			map.put("responseMessage", "Data found");
			map.put("responseType", "Error");  
			map.put("model",loginModel );
		}

		return new ResponseEntity(map, HttpStatus.OK);

	}
	
	@GetMapping(value="/monitor2")
	public ResponseEntity<?> monitorReport2() throws MalformedURLException, URISyntaxException {		
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		AsteriskServer asteriskServer = asteriskInit.getServer();
		//queueDetailsList.clear();
		
		quickService.submit(new Runnable() {
			@Override
			public void run() {				
				mailStoreUnseenList= mailStoreService.findAllByStatus("UNSEEN");				
				mailStoreAssignedList= mailStoreService.findAllByStatus("ASSIGNED");				
				smsModelUnseenList = smsService.findByStatus("UNSEEN");				
				smsModelAssignedList = smsService.findByStatus("ASSIGNED");
			}});
		
		quickService.submit(new Runnable() {
			@Override
			public void run() {

				Query query =  entityManager.createNativeQuery("SELECT  distinct(agent_name),extension,login_status,status from agent_login "
						+  "WHERE CAST(start_time AS DATE) = CURDATE() ");
				List<Object> loggedUserList = null;
				loggedUserList = query.getResultList();

				List<LoginDTO> loginDTOs=new ArrayList<>();
				if (CommonUtil.isListNotNullAndEmpty(loggedUserList)) {				
					Iterator loggedUserIterator = loggedUserList.iterator();			
					while (loggedUserIterator.hasNext()){
						try {
							Object[] objs = ( Object[]) loggedUserIterator.next();
							LoginDTO loginDTO=new LoginDTO();					
							loginDTO.setAgentName(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);					
							loginDTO.setExtension(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);					
							loginDTO.setLoginStatus(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);					
							loginDTO.setStatus(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);					
							loginDTOs.add(loginDTO);				
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
					loginCount = loginDTOs.stream().filter(e->e.getLoginStatus().contentEquals("login")).collect(Collectors.counting());
					logoutCount = loginDTOs.stream().filter(e->e.getLoginStatus().contentEquals("logout")).collect(Collectors.counting());
					freeCount = loginDTOs.stream().filter(e->e.getStatus().contentEquals("Ready")).filter(e->e.getLoginStatus().contentEquals("login")).collect(Collectors.counting());
					talkCount = loginDTOs.stream().filter(e->e.getStatus().contentEquals("On-Call")).filter(e->e.getLoginStatus().contentEquals("login")).collect(Collectors.counting());
					wrappingCount = loginDTOs.stream().filter(e->e.getStatus().contentEquals("Wrapping")).filter(e->e.getLoginStatus().contentEquals("login")).collect(Collectors.counting());
					notReadyCount = loginDTOs.stream().filter(e->e.getStatus().contentEquals("Not Ready")).filter(e->e.getLoginStatus().contentEquals("login")).collect(Collectors.counting());
					callsInQueueCount = 0l;
					busyCount = wrappingCount + notReadyCount + talkCount ;

				}
			}});
			
				List<QueueDetailsDTO> queueDetailsList = new ArrayList<>();
				for (AsteriskQueue asteriskQueue : asteriskServer.getQueues())  {
					QueueDetailsDTO queueDetailsDTO = new QueueDetailsDTO();
					queueDetailsDTO.setAbandonedCalls(asteriskQueue.getAbandoned());
					queueDetailsDTO.setCompletedCalls(asteriskQueue.getCompleted());
					queueDetailsDTO.setCurrentWaitingCalls(asteriskQueue.getWaiting());
					queueDetailsDTO.setHoldTime(asteriskQueue.getHoldTime());
					queueDetailsDTO.setQueueName(asteriskQueue.getName());
					queueDetailsDTO.setStrategy(asteriskQueue.getStrategy());
					queueDetailsDTO.setTalkTime(asteriskQueue.getTalkTime());
					queueDetailsDTO.setTotalCallsInQueue(asteriskQueue.getCalls());
					queueDetailsList.add(queueDetailsDTO);
				}
			
	
			
			map.put("responseCode", "200");
			map.put("responseMessage", "call Data");
			map.put("responseType", "Success"); 
			
			map.put("loginCount", loginCount);
			map.put("logoutCount", logoutCount);
			map.put("freeCount", freeCount);
			map.put("talkCount", talkCount);
			map.put("busyCount", busyCount);
			
			map.put("QueueDetails", queueDetailsList);
			map.put("totalMails",(mailStoreAssignedList.size()+mailStoreUnseenList.size()));
			map.put("mailsInQue",mailStoreAssignedList.size());
			map.put("totalSms",(smsModelAssignedList.size()+smsModelUnseenList.size()));
			map.put("smsInQue",smsModelUnseenList.size());
		
		

		return new ResponseEntity(map, HttpStatus.OK);

	}
	
	@GetMapping(value="/triggerDashboard")
	public ResponseEntity<?> triggerDashboard() throws MalformedURLException, URISyntaxException {		
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();		
		map.put("responseCode", "200");
		map.put("responseMessage", "Data Changed");
		map.put("responseType", "Success"); 
		map.put("count", callPropertiesRepository.count());

		return new ResponseEntity(map, HttpStatus.OK);

	}
}
