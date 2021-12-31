package com.eupraxia.telephony.rest;


import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.eupraxia.telephony.DTO.ConnectionDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.Digital.AMIListener;
import com.eupraxia.telephony.Listener.LiveAsteriskServerListener;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.MissedCallModel;
import com.eupraxia.telephony.components.TelephonyManagerConnection;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.handler.EventsHandler;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.MissedCallService;
import com.eupraxia.telephony.repositories.CallPropertiesRepository;

@CrossOrigin
@RestController
@RequestScope
@RequestMapping("/api")
public class ActionlController {
	
	@Autowired
	private MissedCallService missedCallService;
	
	
	@Autowired
	ActionHandler actionhandler;
	
	@Autowired
	AMIListener amiListener;
	
	
	
	@Autowired
	TelephonyManagerConnection telephonyManagerConnection;
	
	@Autowired
	EventsHandler eventsHandler;
		
	@Autowired
	CallService callService;	

	@Autowired
	CallPropertiesRepository callPropertiesRepository;
	
	@GetMapping("/testApi")
	public String testApi() {
		callService.postData(new CallPropertiesModel());
		return telephonyManagerConnection.getToken();
	}
	
	@GetMapping("/getStreamApis")
	public ResponseEntity<?> getStreamApis() {
		Map<Object, Object> map = new HashMap<>();
		map.put("StreamApi", "http://104.154.188.48:8081/stream/accid/topicname/abcd");
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");
		 return new ResponseEntity(map, HttpStatus.OK); 
	}
	
	@PostMapping("/line2")
	 public ResponseEntity<?> line2(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
  	Map<Object, Object> model = new HashMap<>();
  	ManagerResponse transferResponse = actionhandler.AttendedTransfer(originateDTO);    	
  	model.put("responseType", "Success");  
      return new ResponseEntity(model, HttpStatus.OK); 
  }
	@PostMapping("/unhold")
	 public ResponseEntity<?> unhold(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
 	Map<Object, Object> model = new HashMap<>();
 	ManagerResponse transferResponse = actionhandler.unHold(originateDTO);    	
 	model.put("responseType", "Success");  
     return new ResponseEntity(model, HttpStatus.OK); 
 }
	@PostMapping("/conference")	
   public ResponseEntity<?> conference(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
   	Map<Object, Object> model = new HashMap<>();
   	ManagerResponse transferResponse = actionhandler.conference(originateDTO);    	
   	if(transferResponse==null) {
   		model.put("responseType", "conference room not available");
   	}else {
   	model.put("responseType", "Success");  
   	}
       return new ResponseEntity(model, HttpStatus.OK); 
   }
	
	@PostMapping("/connect/asterisk")	
	public ResponseEntity<ManagerConnection> connectAsterisk(@RequestBody ConnectionDTO connectionDTO) throws URISyntaxException, IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {
		
		Map<Object, Object> map = new HashMap<>();	
		ManagerConnection managerConnection = telephonyManagerConnection.getManageConnection(connectionDTO);
		//asteriskServer = new DefaultAsteriskServer("192.168.10.210", "dev", "dev@123");
		managerConnection.addEventListener(amiListener);
		//asteriskServer.addAsteriskServerListener(liveAsteriskServerListener);		
		map.put("ConnectionStatus", managerConnection.getState());
		map.put("responseType", "Success");  
		return new ResponseEntity(map, HttpStatus.OK); 
	}
	
	@PostMapping("/Dial")	
	public ResponseEntity<?> dial(@RequestBody OriginateDTO originateDTO) throws URISyntaxException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {
		Map<Object, Object> map = new HashMap<>();			
		ManagerConnection managerConnection = telephonyManagerConnection.getExisitingConnection();			
		ManagerResponse managerResponse = actionhandler.originateCall(originateDTO);
		return new ResponseEntity(managerResponse, HttpStatus.OK); 
	}
	
	@PostMapping("/Spy")	
	public ResponseEntity<?> spy(@RequestBody OriginateDTO originateDTO) throws URISyntaxException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {
		Map<Object, Object> map = new HashMap<>();			
		ManagerConnection managerConnection = telephonyManagerConnection.getExisitingConnection();			
		ManagerResponse managerResponse = actionhandler.spyCall(originateDTO);
		return new ResponseEntity(managerResponse, HttpStatus.OK); 
	}
	
	@PostMapping("/DialMissedCall")	
	public ResponseEntity<?> dialMissedCall(@RequestBody OriginateDTO originateDTO) throws URISyntaxException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {
		MissedCallModel MissedCallModel=missedCallService.findById(Integer.parseInt(originateDTO.getId()));
		if(MissedCallModel!=null) {
			MissedCallModel.setCallStatus("close");
			missedCallService.insertMissedCallRequest(MissedCallModel);
		}
		Map<Object, Object> map = new HashMap<>();			
		ManagerConnection managerConnection = telephonyManagerConnection.getExisitingConnection();			
		ManagerResponse managerResponse = actionhandler.originateCall(originateDTO);
		return new ResponseEntity(managerResponse, HttpStatus.OK); 
	}
	
	

		@PostMapping("/Hangup")
		public ResponseEntity<?> hangup(@RequestBody OriginateDTO originateDTO) throws URISyntaxException, IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {   
			ManagerResponse managerResponse = actionhandler.hangUpCall(originateDTO);
			Map<Object, Object> map = new HashMap<>();	   	 
			map.put("responseType", "Success");  
			return new ResponseEntity(map, HttpStatus.OK); 
		}
		
		@PostMapping("/Hold")	
	    public ResponseEntity<?> hold(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	    	Map<Object, Object> model = new HashMap<>();
	    	ManagerResponse holdResponse = actionhandler.holdCall(originateDTO);    	
	    	model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
	    }
		
		@PostMapping("/transfer")	
	    public ResponseEntity<?> transfer(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	    	Map<Object, Object> model = new HashMap<>();
	    	ManagerResponse transferResponse = actionhandler.holdCall(originateDTO);    	
	    	model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
	    }
		
		@PostMapping("/redirect")	
	    public ResponseEntity<?> redirect(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	    	Map<Object, Object> model = new HashMap<>();
	    	ManagerResponse redirectResponse = actionhandler.redirectCall(originateDTO);    	
	    	model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
	    }
		
		@PostMapping("/queueAdd")	
	    public ResponseEntity<?> queueAdd(@RequestBody QueueDTO queueDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	    	Map<Object, Object> model = new HashMap<>();
	    	ManagerResponse queueAddResponse = actionhandler.queueAdd(queueDTO);    	
	    	model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
	    }
		
		@PostMapping("/queueRemove")	
	    public ResponseEntity<?> queueRemove(@RequestBody QueueDTO queueDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	    	Map<Object, Object> model = new HashMap<>();
	    	System.out.println("Iface "+queueDTO.getIface());
	    	System.out.println("Queue "+queueDTO.getQueue());
	    	ManagerResponse queueRemoveResponse = actionhandler.queueRemove(queueDTO);    	
	    	model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
	    }
		@PostMapping("/addQueue")	
	    public ResponseEntity<?> addQueue(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	    	Map<Object, Object> model = new HashMap<>();
	    	ManagerResponse queueAddResponse = actionhandler.addQueue(originateDTO.getExtension());    	
	    	model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
	    }
		
		@PostMapping("/removeQueue")	
	    public ResponseEntity<?> removeQueue(@RequestBody OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
	    	Map<Object, Object> model = new HashMap<>();
	    	ManagerResponse queueAddResponse = actionhandler.removeQueue(originateDTO.getExtension());    	
	    	model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
	    }
		@GetMapping("/click2call")
		public ResponseEntity<?> ClickACall(@RequestParam("customerNumber") String customerNumber,@RequestParam("AgentNumber") String AgentNumber,@RequestParam("uniqueId") String uniqueId,@RequestParam("prefix") String prefix)throws URISyntaxException, IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {
			List<CallPropertiesModel> callPropertiesModels=new ArrayList<>();
			callPropertiesModels=callService.findAllByExtension(AgentNumber);
			if(!callPropertiesModels.isEmpty()) {
			for(CallPropertiesModel cm:callPropertiesModels) {
				if(cm.getCallStartTime()!=null) {
					
					try {
				Calendar calendar = Calendar.getInstance(); 
			    Calendar calendar1 = Calendar.getInstance();
			    ZonedDateTime zonedDateTime = cm.getCallStartTime().atZone(ZoneId.systemDefault());
			   calendar1.setTime(Date.from(zonedDateTime.toInstant()));
		        calendar1.add(Calendar.SECOND, 10);
		        
		        if(calendar.before(calendar1)) {
		        	return new ResponseEntity("duplicate", HttpStatus.OK); 
		        }else {
		        	callService.deleteById(cm.getId());
		        }
					
					}catch(Exception e) {}
				}else {
					callService.deleteById(cm.getId());
				}
			}
			}
	        OriginateDTO originateDTO=new OriginateDTO();
	        originateDTO.setContext("from-internal");
	        originateDTO.setChannel("SIP/"+AgentNumber);
			originateDTO.setPriority(1);
			originateDTO.setPrefix(prefix);
			originateDTO.setPhoneNo(customerNumber);
			originateDTO.setDialMethod("crmcall");
			originateDTO.setExtension(AgentNumber);
			originateDTO.setDialMethod("crmcall");
			originateDTO.setCamName("crmcall");
			actionhandler.originateCall(originateDTO);
			Map<Object, Object> model = new HashMap<>();
			model.put("responseType", "Success");  
	        return new ResponseEntity(model, HttpStatus.OK); 
		}

}
