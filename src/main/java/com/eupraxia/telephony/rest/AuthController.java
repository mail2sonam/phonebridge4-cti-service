package com.eupraxia.telephony.rest;

import static org.springframework.http.ResponseEntity.ok;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.AuthBody;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.RefreshTokenRequest;
import com.eupraxia.telephony.Model.ExtensionDataModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.configuration.JwtTokenProvider;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.repositories.ExtensionDataRepository;
import com.eupraxia.telephony.repositories.Reports.UserRepository;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.security.CustomUserDetailsService;
import com.eupraxia.telephony.util.CommonUtil;
import com.fasterxml.uuid.Generators;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	UserRepository users;

	@Autowired
	private CustomUserDetailsService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CampaignService campaignService;
	
	@Autowired
	ActionHandler actionhandler;
	
	@Autowired
	ExtensionDataRepository extensionDataRepository;
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	private HashMap<String, String> refreshTokens = new HashMap<String, String>();
	
	@Value("${client.name}")
	private String clientName;

	
	@CrossOrigin()
	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthBody data) {
		String email="";
		String token="";
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		UUID refreshToken = null;
		UserModel userModel;
		try {								
			email = data.getEmail();	
			String startTime="";			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			startTime= dtf.format(now);
			userModel = userRepository.findByEmail(email);
System.out.println(email);
System.out.println(userModel);
			if (checkExtensionRegistered(userModel).equals("AlreadyRegistered")){
				map.put("responseCode", "400");
				map.put("responseMessage", "Extension Already Registered in ");
				map.put("responseType", "ExtensionDuplication");  
				map.put("model", userModel);
				return new ResponseEntity(map, HttpStatus.OK);
			}

			if(checkExtensionRegistered(userModel).equals("AlreadyNotRegistered")) {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
				token = jwtTokenProvider.createToken(email, this.users.findByEmail(email).getRoles());
				refreshToken = Generators.randomBasedGenerator().generate();
				refreshTokens.put(refreshToken.toString(), email);
				
				for (Map.Entry<String, String> entry : refreshTokens.entrySet()) {
				     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				}
				List<LoginModel> loginModel5=new ArrayList<>();
				loginModel5=loginService.findAllByExtensionAndStatus(userModel.getUserextension(),"login");
				if(loginModel5.isEmpty()) {
				LoginModel loginModel=new LoginModel();				
				loginModel.setAgentName(userModel.getUsername());
				loginModel.setExtension(userModel.getUserextension());
				loginModel.setStartTime(startTime);
				loginModel.setStatus("Ready");
				loginModel.setLoginStatus("login");
				loginModel.setStatusTime(startTime);
				loginService.saveOrUpdate(loginModel);
				}
			}		
//			quickService.submit(new Runnable() {
//				@Override
//				public void run() {
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
				//}
			//});	
		} catch (AuthenticationException e) {	
			map.put("responseCode", "400");
			map.put("responseMessage", "BadCredentialException");
			map.put("responseType", "Error"); 
			return ok(map);
			//throw new BadCredentialsException("Invalid email/password supplied");
		}	

		map.put("Email", email);
		map.put("token", token);
		map.put("clientName", clientName);
		map.put("refreshToken", refreshToken);
		map.put("model",userModel);
		map.put("responseCode", "200");
		map.put("responseMessage", "User logged in successfully");
		map.put("responseType", "Success"); 
		return ok(map);

	}
	
	
	
	private String  checkExtensionRegistered(UserModel userModel) {		
		boolean similarIP=true;			
		String startTime="";

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			startTime= dtf.format(now);
			List<LoginModel> loginModel5=new ArrayList<>();
			
			loginModel5=loginService.findAllByExtensionAndStatus(userModel.getUserextension(),"login");
			//LoginModel loginModel5=loginService.findByExtensionAndStatus(userModel.getUserextension(),"login");
			//if(loginModel5!=null) {
			for(LoginModel loginModel6:loginModel5) {
				loginModel6.setLoginStatus("logout");
				loginModel6.setEndTime(startTime);
				loginService.saveOrUpdate(loginModel6);
			}
			//}

			ExtensionDataModel extensionDataModel = new ExtensionDataModel();
			extensionDataModel = extensionDataRepository.findByExtension("SIP/"+userModel.getUserextension());
			String ip="";

			if(!CommonUtil.isNull(extensionDataModel)) {
				if(extensionDataModel.getAddress()!=null) {
					ip = extensionDataModel.getAddress().split(":")[0];						
					InetAddress localhost = InetAddress.getLocalHost();
					System.out.println(localhost.getHostAddress().trim()+" :: "+ip);
					similarIP = localhost.getHostAddress().trim().contentEquals(ip)?true:false;
				}

				if(!similarIP && extensionDataModel.getPeerStatus().contentEquals("Registered")) {						
					return "AlreadyRegistered";
				}
				else {
					return "AlreadyNotRegistered";
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "AlreadyNotRegistered";
	}
	
	@CrossOrigin()
	@SuppressWarnings("rawtypes")
	@PostMapping("/renew")
	public ResponseEntity renew(@RequestBody RefreshTokenRequest data) {

		
		Map<Object, Object> model = new HashMap<>();

		// get username
		// for now, pretend it is s
		String email = data.getEmail();
		String refreshToken = data.getRefreshToken();
		
		System.out.println("refreshToken "+refreshToken);
		System.out.println("From Map "+refreshTokens.get(refreshToken));
		
		for (Map.Entry<String, String> entry : refreshTokens.entrySet()) {
		     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}

		if (refreshTokens.get(refreshToken) == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		// check if the refreshToken is for this username
		if (refreshTokens.get(refreshToken).equals(email)) {
			// generate a new access token for this user
			String token = jwtTokenProvider.createToken(email, this.users.findByEmail(email).getRoles());

			model.put("token", token);
			return ok(model);
		}

		return new ResponseEntity(HttpStatus.UNAUTHORIZED);



	}

	
}
