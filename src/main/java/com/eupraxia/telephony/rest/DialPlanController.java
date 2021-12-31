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
import com.eupraxia.telephony.Model.PrankModel;
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
import com.eupraxia.telephony.service.PrankService;
import com.eupraxia.telephony.service.QueueService;
import com.eupraxia.telephony.service.ScheduleCallService;
import com.eupraxia.telephony.util.CommonUtil;
import com.eupraxia.telephony.util.QueryCriteria;

@CrossOrigin
@RestController
@RequestMapping("/dialPlan")
public class DialPlanController {
	@Autowired
	ReportsRepository reportsRepository;
	@Autowired
	IvrRepository ivrRepository;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	private IvrService ivrService;
	
	@Autowired
	CallService callService;
	
	@Autowired
	OtpService otpService;
	
	@Autowired
	private IvrReportRepository ivrReportRepository;
	
	@Autowired
	ReportsDAO reportsDAO; 
	
	@Autowired 
	QueryCriteria queryCriteria;
	
	@Autowired
	private PrankService prankservice;
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
	
	@Value("${asterisk.host}")
	private String asteriskHost;
	
	@GetMapping("/IvrStartTime")
	public String IvrStartTime(@RequestParam("phoneNo") String phoneNo ,@RequestParam("uniqueId") String uniqueId) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		System.out.println("praveena start :: "+phoneNo+" :: "+uniqueId);
		IvrModel ivrModel=ivrService.findByUniqueId(uniqueId);
		try {
			phoneNo=phoneNo.substring(phoneNo.length()-10,phoneNo.length());
		}catch(Exception e) {}
		String startTime="";
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			   LocalDateTime now = LocalDateTime.now();
			   startTime= dtf.format(now);
		}catch(Exception e) {}
		if(ivrModel.getEndTime()!=null) {
			return "1";
		}
	ivrModel.setStartTime(startTime);
		ivrModel.setUniqueId(uniqueId);
		ivrModel.setPhoneNo(phoneNo);
		ivrService.saveOrUpdate(ivrModel);
		return "1";
	}
	
	@GetMapping("/IvrEndTime")
	public String IvrEndTime(@RequestParam("phoneNo") String phoneNo ,@RequestParam("uniqueId") String uniqueId) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		System.out.println("praveena end :: "+phoneNo+" :: "+uniqueId);
		IvrModel ivrModel=ivrService.findByUniqueId(uniqueId);
		ZoneId zid = ZoneId.of("Asia/Kolkata");
		LocalDateTime now1 = LocalDateTime.now(zid);
		
		if(ivrModel!=null) {
			if(ivrModel.getEndTime()!=null) {
				return "1";
			}
			String startTime="";
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				   LocalDateTime now = LocalDateTime.now();
				   startTime= dtf.format(now);
			}catch(Exception e) {}
			ivrModel.setEndTime(startTime);
			ivrService.saveOrUpdate(ivrModel);
			IvrModel ivrModel1=ivrService.findByUniqueId(uniqueId);
			IvrReportModel cdrReportModel=new IvrReportModel();
			cdrReportModel.setPhoneNo(phoneNo);
			cdrReportModel.setTraverse(ivrModel1.getIvrFlow());
			if(ivrModel!=null) {
				SimpleDateFormat formatter7=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				SimpleDateFormat formatter8=new SimpleDateFormat("HH:mm:ss");
				
				try {
					Date d1 =formatter7.parse(ivrModel1.getEndTime());
		            Date d2 =formatter7.parse(ivrModel1.getStartTime());
		          
		            LocalDateTime.ofInstant(d2.toInstant(),
                            ZoneId.systemDefault());
		            
					long ivrduartion1=(int)((d1.getTime() - d2.getTime())/1000);
					cdrReportModel.setIvrDuration(String.valueOf(ivrduartion1));
					cdrReportModel.setIvrStartTime(formatter8.format(d2));
					cdrReportModel.setIvrEndTime(formatter8.format(d1));
					now1=now1.plusHours(5);
					now1=now1.plusMinutes(30);
					cdrReportModel.setCallDate(now1);
					//cdrReportModel.setTraverse(ivrModel.getIvrFlow());
					ivrReportRepository.save(cdrReportModel);
					ivrRepository.deleteById(ivrModel.getId());
				}catch(Exception e) {}
			}
			
			
			
		}
		return "1";
	}
	
	@GetMapping("/IvrLevel")
	public String IvrLevel(@RequestParam("phoneNo") String phoneNo ,@RequestParam("uniqueId") String uniqueId,@RequestParam("Level") String Level) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		System.out.println("praveena Level :: "+phoneNo+" :: "+uniqueId+"  :: "+Level);
		IvrModel ivrModel=ivrService.findByUniqueId(uniqueId);
		if(ivrModel!=null) {
			if(ivrModel.getEndTime()!=null) {
				return "1";
			}
			if(Level.equalsIgnoreCase("Transfer_Agent")) {
				String startTime="";
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					   LocalDateTime now = LocalDateTime.now();
					   startTime= dtf.format(now);
				}catch(Exception e) {}
				ivrModel.setEndTime(startTime);
			}
			String ivrLevel=ivrModel.getIvrFlow();
			if(ivrLevel != null && !ivrLevel.isEmpty()) {
				ivrLevel=ivrLevel.concat(Level).concat(", ");
			}else {
				ivrLevel=Level.concat(", ");
			}
			ivrModel.setIvrFlow(ivrLevel);
			ivrService.saveOrUpdate(ivrModel);
		}
		return "1";
	}
	
	@GetMapping("/queueVdn")
	public String queueVdn(@RequestParam("phoneNo") String phoneNo ,@RequestParam("uniqueId") String uniqueId,@RequestParam("queueName") String  queueName) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		System.out.println("praveena queue vdn :: "+phoneNo+" :: "+uniqueId+"  :: "+queueName);
		IvrModel ivrModel=ivrService.findByUniqueId(uniqueId);
		if(ivrModel!=null) {
			ivrModel.setQueueVdn(queueName);
			ivrService.saveOrUpdate(ivrModel);
		}
		return "1";
	}
	
	
	@GetMapping("/saveRecording")
	public String saveRecording(@RequestParam("phoneNo") String phoneNo ,@RequestParam("uniqueId") String uniqueId,@RequestParam("fileName") String  fileName) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException { 
		System.out.println("phoneNo :: "+phoneNo+"  fileName :: "+fileName);
		IvrModel ivrModel=ivrService.findByUniqueId(uniqueId);
		String hostname="";
        try {
        	InetAddress.getLocalHost();
       // 	hostname = "https://"+ip.getHostAddress()+"/recordings/";
        	hostname = "http://"+asteriskHost+"/recordings/";
        	System.out.println("hostname :: "+hostname);
        	      
        } catch (UnknownHostException e) {        	
        	e.printStackTrace();
        }

		if(ivrModel!=null) {
			ivrModel.setRecPath(hostname+fileName);
			ivrService.saveOrUpdate(ivrModel);
		}else if(phoneNo !=null && phoneNo.length()>=10){
			phoneNo=phoneNo.substring(phoneNo.length()-10,phoneNo.length());
			IvrModel ivrModel1=new IvrModel();
			ivrModel1.setRecPath(hostname+fileName);  
			ivrModel1.setPhoneNo(phoneNo);
			ivrModel1.setUniqueId(uniqueId);
			ivrService.saveOrUpdate(ivrModel1);
		}
		/*IvrModel ivrModel2=ivrService.findByPhoneNo(phoneNo);
		if(ivrModel2==null) {
			IvrModel ivrModel1=new IvrModel();
			ivrModel1.setRecPath(fileName);  
			ivrModel1.setPhoneNo(phoneNo);
			ivrModel1.setUniqueId(uniqueId);
			ivrService.saveOrUpdate(ivrModel1);
		}else {			
			ivrModel2.setRecPath(fileName);
			ivrService.saveOrUpdate(ivrModel2);
		}*/
		return "1";
	}
	

	/*
	@GetMapping("/saveRecording")
	public String saveRecording(@RequestParam("phoneNo") String phoneNo ,@RequestParam("uniqueId") String uniqueId,@RequestParam("fileName") String  fileName) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		System.out.println("praveena queue vdn :: "+phoneNo+" :: "+uniqueId+"  :: "+fileName);
		IvrModel ivrModel=ivrService.findByUniqueId(uniqueId);
		if(ivrModel!=null) {
			ivrModel.setRecPath("https://192.168.10.155/recordings/"+fileName);
			ivrService.saveOrUpdate(ivrModel);
		}
		return "1";
	}
	*/
	
	@GetMapping("/ValidateOtp")
	public String ValidateOtp(@RequestParam("phoneNo") String phoneNo ,@RequestParam("otp") String otp) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		OtpModel  otp1=otpService.findByPhoneNo(phoneNo);
		if(otp1==null) {
			return "0";
		}else {
			if(otp.equalsIgnoreCase(otp1.getOtp())) {
				//otpService.deleteByPhoneNo(phoneNo);
				return "1";
			}else {
				return "0";
			}
		}
	}
	
	@GetMapping("/saveFeedback")
	public String saveFeedback(@RequestParam("phoneNo") String phoneNo ,@RequestParam("dtmf") String dtmf) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		System.out.println(phoneNo +" : : "+ dtmf );
		if(phoneNo.contains("/")) {
			String[] arr=phoneNo.split("/");
			phoneNo=arr[1];
			phoneNo=phoneNo.substring(phoneNo.length()-10,phoneNo.length());
			System.out.println(phoneNo);
		}
		Query query=queryCriteria.queryForIdPhoneNumberLimit();
		query.setParameter("phoneNumber",phoneNo );
		query.setMaxResults(1);
		 List<Object> list= query.getResultList();
		 
		 Object obj=list.get(0);
		 System.out.println(obj);
		 int ids=0;
		 if(obj!=null) {
			 ids=(int) obj;
         }
		 ReportsModel reportModel=reportsRepository.findById(ids);
		 if(reportModel!=null) {
			// reportModel.setFeedback(dtmf);
			 reportsRepository.save(reportModel);
		 }
		 
		return "1";
	}
	
	
	@GetMapping("/ValidatePhoneNumber")
	public String ValidatePhoneNumber(@RequestParam("phoneNo") String phoneNo ,@RequestParam("uniqueId") String uniqueId) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		
		
		System.out.println(phoneNo);
		
		if(phoneNo.equals("918639541313")||phoneNo.equals("919677760017") ||phoneNo.equals("919940102222")||phoneNo.equals("917708099789")||phoneNo.equals("919840015963")) {		
			String phoneNo1 = phoneNo.substring(2);
			System.out.println(phoneNo1);
			//Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}"); 
			//Matcher m = p.matcher(phoneNo);
			//if(m.find() && m.group().equals(phoneNo)) {
				quickService.submit(new Runnable() {
	    			@Override
	    			public void run() {
	    				try {
	    					String numbers = "0123456789"; 
	    					Random rndm_method = new Random(); 
	    					  
	    			        char[] otp = new char[6]; 
	    			  
	    			        for (int i = 0; i < 6; i++) 
	    			        { 
	    			            // Use of charAt() method : to get character value 
	    			            // Use of nextInt() as it is scanning the value as int 
	    			            otp[i] = 
	    			             numbers.charAt(rndm_method.nextInt(numbers.length())); 
	    			        } 
	    			        String str = new String(otp);
	    			        
	    			       
	    			        OtpModel  otp1=otpService.findByPhoneNo(phoneNo1);
	    			        if(otp1==null) {
	    			        	 OtpModel otp2=new OtpModel();
	    			        	 otp2.setPhoneNo(phoneNo1);
	    			        	 otp2.setOtp(str);
	    			        	 otpService.saveOrUpdate(otp2);
	    			        }else {
	    			        	otp1.setOtp(str);
	    			        	otpService.saveOrUpdate(otp1);
	    			        }
	    			        
	    			        try {

	    				        try{
	    				            String smsAgent="Your OTP "+str;
	    				           
	    				           
	    				           String smsUrl ="http://192.168.10.103:80/sendsms?username=admin&password=admin&phonenumber="+phoneNo1+"&message=";
	    				           String tempURL = smsUrl+URLEncoder.encode(smsAgent, "UTF-8");//sms.getSmsContext();
	    				            URL url = new URL(tempURL);
	    				            
	    				           
	    				            System.out.println("SMS URL : "+url);
	    				            URLConnection urlConn = url.openConnection();
	    				            
	    				            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	    				            String inputLine;
	    				            String smsResponse = "";
	    				            while((inputLine = bufferedReader.readLine())!=null){
	    				                if(inputLine.contains("<messageid>")){
	    				                    Pattern ptr = Pattern.compile("(?<=\\>)\\d+(?=\\<)");
	    				                    Matcher mch = ptr.matcher(inputLine);
	    				                    if(mch.find()) {
											}
	    				                }
	    				                smsResponse = smsResponse.concat(inputLine).concat(" ");
	    				            }
	    				           
	    				               }
	    				        catch(Exception exe){
	    				            exe.getMessage();
	    				        }      
	    			        }catch(Exception e) {}
	    			        		
	    			        
	    				}catch(Exception e) {
	    					
	    				}
	    			}		
			
		});
				return "1";
			}else {
				return "0";
			}
	}
		
		@GetMapping("/checkPhoneNumber")
		public String checkPhoneNumber(@RequestParam("phoneNo") String phoneNo) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException {		
		if(phoneNo.length()>=10) {
			phoneNo=phoneNo.substring(phoneNo.length()-10,phoneNo.length());
		}	
			PrankModel ivrModel=prankservice.findByPhoneNo(phoneNo);
			if(ivrModel!=null) {
				return "1";
			}else {
				return "0";
			}
			
		}
	
	
	
	
}
