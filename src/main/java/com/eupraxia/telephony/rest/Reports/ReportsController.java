package com.eupraxia.telephony.rest.Reports;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.AgentWiseDTO;
import com.eupraxia.telephony.DTO.AgentWiseDataDTO;
import com.eupraxia.telephony.DTO.DispositionDTO;
import com.eupraxia.telephony.DTO.HistoryReport;
import com.eupraxia.telephony.DTO.IvrReportDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.DTO.QueueDTO;
import com.eupraxia.telephony.DTO.ReportDTO;
import com.eupraxia.telephony.DTO.ReportDispoDTO;
import com.eupraxia.telephony.DTO.SeniorCitizenReportDTO;
import com.eupraxia.telephony.DTO.ServiceInteractionDTO;
import com.eupraxia.telephony.DTO.TestDTO;
import com.eupraxia.telephony.DTO.WarRoomDTO;
import com.eupraxia.telephony.DTO.WarRoomReportDTO;
import com.eupraxia.telephony.DTO.YamlDataReportDTO;
import com.eupraxia.telephony.Model.CSECEModel;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CdrReportModel;
import com.eupraxia.telephony.Model.DiallerModel;
import com.eupraxia.telephony.Model.IvrModel;
import com.eupraxia.telephony.Model.IvrReportModel;
import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.Model.MailModel;
import com.eupraxia.telephony.Model.ScheduleCallModel;
import com.eupraxia.telephony.Model.SmsModel;
import com.eupraxia.telephony.Model.UserCampaignMappingModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;
import com.eupraxia.telephony.Model.Reports.WarRoomModel;
import com.eupraxia.telephony.Model.Reports.YamlDataReportModel;
import com.eupraxia.telephony.Reports.DAO.ReportsDAO;
import com.eupraxia.telephony.handler.ActionHandler;
import com.eupraxia.telephony.repositories.CseCeRepository;
import com.eupraxia.telephony.repositories.IvrRepository;
import com.eupraxia.telephony.repositories.LoginRepository;
import com.eupraxia.telephony.repositories.MailRepository;
import com.eupraxia.telephony.repositories.SmsRepository;
import com.eupraxia.telephony.repositories.Reports.ReportsRepository;
import com.eupraxia.telephony.repositories.Reports.UserRepository;
import com.eupraxia.telephony.repositories.Reports.WarRoomRepository;
import com.eupraxia.telephony.repositories.Reports.YamlDataReportRepository;
import com.eupraxia.telephony.response.WarRoomReportResponse;
import com.eupraxia.telephony.service.CallService;
import com.eupraxia.telephony.service.CdrReportService;
import com.eupraxia.telephony.service.DiallerService;
import com.eupraxia.telephony.service.EmergencyScreenService;
import com.eupraxia.telephony.service.GuidenceScreenService;
import com.eupraxia.telephony.service.InformationScreenService;
import com.eupraxia.telephony.service.IvrService;
import com.eupraxia.telephony.service.LoginService;
import com.eupraxia.telephony.service.MissedCallService;
import com.eupraxia.telephony.service.ScheduleCallService;
import com.eupraxia.telephony.service.Digital.DistrictService;
import com.eupraxia.telephony.service.Reports.CampaignService;
import com.eupraxia.telephony.service.Reports.ReportService;
import com.eupraxia.telephony.service.Reports.UserService;
import com.eupraxia.telephony.util.CommonUtil;
import com.eupraxia.telephony.util.QueryCriteria;


@CrossOrigin
@RestController
@RequestMapping("/report")
public class ReportsController {
	@Autowired
	CampaignService campaignService;
	
	

@Autowired
private DiallerService fileService;	

@Autowired
MailRepository mailRepository;

	@Autowired 
	private ReportService reportService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private IvrService ivrService;
	  
	@Autowired 
	private CallService CallService;
	
	
	@Autowired 
	private CdrReportService cdrReportService;	
	
	
	@Autowired
	DistrictService districtService;
	
	@Autowired 
	private ScheduleCallService scheduleCallService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MissedCallService missedCallService;
	
	@Autowired
	EmergencyScreenService emergencyScreenService;
	
	@Autowired
	SmsRepository smsRepository;	
	
	@Autowired
	ActionHandler actionhandler;
	
	@Autowired
	EntityManager entityManager;	
	
	@Autowired 
	QueryCriteria queryCriteria;
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
    IvrRepository ivrRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GuidenceScreenService guidenceScreenService;
	
	@Autowired
	InformationScreenService informationScreenService;
	
	@Autowired
	WarRoomRepository warRoomRepository;
	
	@Autowired
	ReportsRepository reportsRepository;
	
	@Autowired
	YamlDataReportRepository yamlDataReportRepository;
	
	@Autowired
	CseCeRepository cseCeRepository;
	
	@Autowired
	private ReportsDAO reportsDAO;
	
	public static int noOfQuickServiceThreads = 20;

	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);	
	
	
	@RequestMapping(value="/getAllMails",method = RequestMethod.GET)
	 public ResponseEntity<?> getAllMails()throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		List<MailModel> ReportsModels=new ArrayList<>();

       ReportsModels=mailRepository.findAll();
       model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success"); 
		if(ReportsModels.isEmpty()) {
		model.put("history", "data not found");
		}else{
			model.put("history", ReportsModels);
		}
		return new ResponseEntity(model, HttpStatus.OK);   
	}
	@RequestMapping(value="/getAllSms",method = RequestMethod.GET)
	 public ResponseEntity<?> getAllSms()throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		List<SmsModel> ReportsModels=new ArrayList<>();

      ReportsModels=smsRepository.findAll();
      model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success"); 
		if(ReportsModels.isEmpty()) {
		model.put("history", "data not found");
		}else{
			model.put("history", ReportsModels);
		}
		return new ResponseEntity(model, HttpStatus.OK);   
	}
	
	@RequestMapping(value="/callHistoryByPhoneNo",method = RequestMethod.POST)
	 public ResponseEntity<?> callHistoryByPhoneNo(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		ArrayList<ReportsModel> ReportsModels=new ArrayList<ReportsModel>();

       ReportsModels=reportService.findByPhoneNo(reportDTO.getPhoneNumber());
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		if(ReportsModels.isEmpty()) {
			model.put("history", "data not found");
			}else{
				model.put("history", ReportsModels);
			}
		return new ResponseEntity(model, HttpStatus.OK);   
		/*Map<Object, Object> model = new HashMap<>(); 
		ArrayList<HistoryReport> ReportsModels=new ArrayList<HistoryReport>();

        ReportsModels=reportService.findHistoryByPhoneNo(reportDTO.getPhoneNumber());
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success"); 
		if(ReportsModels.isEmpty()) {
		model.put("history", "data not found");
		}else{
			model.put("history", ReportsModels);
		}
		return new ResponseEntity(model, HttpStatus.OK);   */
	}
	
	@RequestMapping(value="/callHistoryByExtension",method = RequestMethod.POST)
	 public ResponseEntity<?> callHistoryByExtension(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		ArrayList<ReportsModel> ReportsModels=new ArrayList<ReportsModel>();

       ReportsModels=reportService.findByExtension(reportDTO.getExtension());
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		if(ReportsModels.isEmpty()) {
			model.put("history", "data not found");
			}else{
				model.put("history", ReportsModels);
			}
		return new ResponseEntity(model, HttpStatus.OK);   
	}
	@GetMapping("/AllCdrReports")
	public ResponseEntity<?> callCdrReports()throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		ArrayList<CdrReportModel> ReportsModels=new ArrayList<CdrReportModel>();
		ReportsModels=cdrReportService.findAll();
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("data", ReportsModels);
		return new ResponseEntity(model, HttpStatus.OK);   
		
	}
	
	
	@RequestMapping(value="/callCdrReports",method = RequestMethod.POST)
	 public ResponseEntity<?> callCdrReports(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		ArrayList<CdrReportModel> ReportsModels=new ArrayList<CdrReportModel>();
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
		ReportsModels=cdrReportService.getData(date1,date2);
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("data", ReportsModels);
		return new ResponseEntity(model, HttpStatus.OK);   
	}	
	
	
	@RequestMapping(value="/todayCallsCdrReports",method = RequestMethod.GET)
	 public ResponseEntity<?> todayCallCdrReports()throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 
		
		LocalDateTime now = LocalDateTime.now();
		LocalDate todayDate = now.toLocalDate();
		String todayDatewithStartTime = todayDate.toString()+" 00:00:00";		
		String todayDatewithEndTime = todayDate.toString()+" 23:59:00";		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		LocalDateTime startDateTimeFormatted = LocalDateTime.parse(todayDatewithStartTime, formatter);
		LocalDateTime endDateTimeFormatted = LocalDateTime.parse(todayDatewithEndTime, formatter);
		//LocalDateTime startDateTimeFormatted = LocalDateTime.parse(todayDatewithStartTime);		
		//LocalDateTime endDateTimeFormatted = LocalDateTime.parse(todayDatewithEndTime);
		
		LocalDate startDate = startDateTimeFormatted.toLocalDate();
		LocalDate endDate = endDateTimeFormatted.toLocalDate();	
		
		Map<Object, Object> model1 = new HashMap<>();
		
		 
		Query answered =  entityManager.createNativeQuery("SELECT  count(*) FROM eupraxia_report "
				 + "WHERE call_status='ANSWER' and call_direction='inbound' and CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");			
	
		Query ivrAbandoned =  entityManager.createNativeQuery("SELECT  count(*) as abandonedCalls "
				 + "FROM ivr_report "
				 + "WHERE doc_id is null and CAST(call_date AS DATE) BETWEEN :startDate AND :endDate");
		
		//Query missed = entityManager.createNativeQuery("SELECT  count(*) FROM eupraxia_report "
		//		 + "WHERE call_status !='ANSWER' and call_direction='inbound' and extension is not null and CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");
		
		//Query abandoned = entityManager.createNativeQuery("SELECT  count(*) FROM eupraxia_report "
		//		 + "WHERE call_status !='ANSWER' and call_direction='inbound' and extension is null and CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");
	
		Query abandoned = entityManager.createNativeQuery("SELECT  count(*) FROM eupraxia_report "
				 + "WHERE call_status !='ANSWER' and call_direction='inbound' and extension is not null and  extension <>'' "
				 + "and CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");		
	
		answered.setParameter("startDate", startDate);
		answered.setParameter("endDate", endDate);
		
		ivrAbandoned.setParameter("startDate", startDate);
		ivrAbandoned.setParameter("endDate", endDate);
		
		//missed.setParameter("startDate", startDate);
		//missed.setParameter("endDate", endDate);
		
		abandoned.setParameter("startDate", startDate);
		abandoned.setParameter("endDate", endDate);
		
		String answeredCount = answered.getSingleResult().toString();
		String ivrAbandonedCount = ivrAbandoned.getSingleResult().toString();
		//String missedCount = missed.getSingleResult().toString();
		String abandonedCount = abandoned.getSingleResult().toString();
		//String totalCalls =String.valueOf(Integer.parseInt(answeredCount)+ Integer.parseInt(ivrAbandonedCount)+Integer.parseInt(missedCount)+Integer.parseInt(abandonedCount));
		String totalCalls =String.valueOf(Integer.parseInt(answeredCount)+ Integer.parseInt(ivrAbandonedCount)+Integer.parseInt("0")+Integer.parseInt(abandonedCount));
		model.put("answeredCount", answeredCount);
		model.put("ivrAbandonedCount", ivrAbandonedCount);
		//model.put("missedCount", missedCount);
		model.put("missedCount", "0");
		model.put("abandonedCount", abandonedCount);
		model.put("totalCalls", totalCalls);
		return new ResponseEntity(model, HttpStatus.OK);  
	}
		
	@RequestMapping(value="/todayCallsCdrReportsDetails",method = RequestMethod.POST)
	 public ResponseEntity<?> todayCallsCdrReportsDetails(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 
		
		System.out.println("reportDTO.getStartDate() :"+reportDTO.getStartDate());
		System.out.println("reportDTO.getEndDate() :"+reportDTO.getEndDate());
		
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
		 Date date1 = inputFormat.parse(reportDTO.getStartDate());
		 Date date2 = inputFormat.parse(reportDTO.getEndDate());
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	    
		 LocalDate startDate = LocalDate.parse(formatter.format(date1));
		 LocalDate endDate = LocalDate.parse(formatter.format(date2));	
		
		 System.out.println("startDate :"+startDate);
		 System.out.println("endDate :"+endDate);
		
		Map<Object, Object> model1 = new HashMap<>();
		
		 
		Query answered =  entityManager.createNativeQuery("SELECT  extension,call_Start_Time,call_End_Time,phone_No FROM eupraxia_report "
				 + "WHERE call_status='ANSWER' and call_direction='inbound' and CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");			
	
		Query ivrAbandoned =  entityManager.createNativeQuery("SELECT  doc_Id,Ivr_Start_Time,Ivr_End_time,phone_No,Ivr_Duration "
				 + "FROM ivr_report  "	
				 + "WHERE doc_id is null and CAST(call_date AS DATE) BETWEEN :startDate AND :endDate");
		
		//Query abandoned = entityManager.createNativeQuery("SELECT  extension,call_Start_Time,call_End_Time,phone_No FROM eupraxia_report "					
		//		 + "WHERE call_status !='ANSWER' and call_direction='inbound' and extension is not null and CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");
		
		Query abandoned = entityManager.createNativeQuery("SELECT  extension,call_Start_Time,call_End_Time,phone_No FROM eupraxia_report "					
						 + "WHERE call_status !='ANSWER' and call_direction='inbound' and (CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate)");
				
		
		answered.setParameter("startDate", startDate);
		answered.setParameter("endDate", endDate);
		
		ivrAbandoned.setParameter("startDate", startDate);
		ivrAbandoned.setParameter("endDate", endDate);
		
		//missed.setParameter("startDate", startDate);
		//missed.setParameter("endDate", endDate);
		
		abandoned.setParameter("startDate", startDate);
		abandoned.setParameter("endDate", endDate);
		
		List<Object> answeredList = null;	
		List<Object> ivrAbandonedList = null;
		List<Object> missedList = null;
		List<Object> abandonedList = null;
		
		answeredList = answered.getResultList();		
		ivrAbandonedList = ivrAbandoned.getResultList();
		//missedList = missed.getResultList();
		abandonedList = abandoned.getResultList();
		
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
	     DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	     String time1="";
	     LocalDateTime startDateTime=null;
	     LocalDateTime endDateTime=null;
	     
		List<ReportDTO> answeredDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(answeredList)) {			
			Iterator answeredIterator = answeredList.iterator();			
			while (answeredIterator.hasNext()){
				try {
					Object[] objs = ( Object[]) answeredIterator.next();
					ReportDTO reportDTO1=new ReportDTO();					
					reportDTO1.setExtension(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);	
					
					if(!CommonUtil.isNull(objs[1])){
						time1 = String.valueOf(objs[1]).substring(0,19);						
						LocalDateTime datetime = LocalDateTime.parse(time1,dateTimeFormat);
						datetime = datetime.minusHours(5);		
						startDateTime = datetime.minusMinutes(30);
						reportDTO1.setStartDate(startDateTime.format(dateTimeFormat));
					}
					if(!CommonUtil.isNull(objs[2])){
						time1 = String.valueOf(objs[2]).substring(0,19);						
						LocalDateTime datetime = LocalDateTime.parse(time1,dateTimeFormat);
						datetime = datetime.minusHours(5);	
						endDateTime = datetime.minusMinutes(30);
						reportDTO1.setEndDate(endDateTime.format(dateTimeFormat));
					}
					Duration duration = Duration.between(startDateTime, endDateTime);
					//System.out.println(duration.getSeconds());
					reportDTO1.setDuration(duration.getSeconds());
					reportDTO1.setPhoneNumber(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);					
					answeredDTOs.add(reportDTO1);				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		List<ReportDTO> ivrAbandonedDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(ivrAbandonedList)) {
			Iterator ivrAbandonedIterator = ivrAbandonedList.iterator();			
			while (ivrAbandonedIterator.hasNext()){
				
				try {
					Object[] objs = ( Object[]) ivrAbandonedIterator.next();
					ReportDTO reportDTO1=new ReportDTO();					
					reportDTO1.setExtension(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);
					reportDTO1.setStartDate(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
					reportDTO1.setEndDate(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);
					reportDTO1.setPhoneNumber(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);	
					reportDTO1.setDuration(CommonUtil.isNull(objs[4])?0:Long.parseLong((String)objs[4]));	
                    ivrAbandonedDTOs.add(reportDTO1);				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		List<ReportDTO> missedDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(missedList)) {
			Iterator missedIterator = missedList.iterator();			
			while (missedIterator.hasNext()){
				try {
					Object[] objs = ( Object[]) missedIterator.next();
					ReportDTO reportDTO1=new ReportDTO();					
					reportDTO1.setExtension(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);
					if(!CommonUtil.isNull(objs[1])){
						time1 = String.valueOf(objs[1]).substring(0,19);
						
						LocalDateTime datetime = LocalDateTime.parse(time1,dateTimeFormat);
						datetime = datetime.minusHours(5);
						reportDTO1.setStartDate(datetime.minusMinutes(30).toString());
					}
					if(!CommonUtil.isNull(objs[2])){
						time1 = String.valueOf(objs[2]).substring(0,19);						
						LocalDateTime datetime = LocalDateTime.parse(time1,dateTimeFormat);
						datetime = datetime.minusHours(5);
						reportDTO1.setEndDate(datetime.minusMinutes(30).toString());
					}
					reportDTO1.setPhoneNumber(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
					missedDTOs.add(reportDTO1);				
				}catch(Exception e) {}
			}
		}
		
		List<ReportDTO> abandonedDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(abandonedList)) {
			Iterator abandonedIterator = abandonedList.iterator();			
			while (abandonedIterator.hasNext()){
				try {
					Object[] objs = ( Object[]) abandonedIterator.next();
					ReportDTO reportDTO1=new ReportDTO();					
					reportDTO1.setExtension(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);					
					if(!CommonUtil.isNull(objs[1])){
						time1 = String.valueOf(objs[1]).substring(0,19);						
						LocalDateTime datetime = LocalDateTime.parse(time1,dateTimeFormat);
						datetime = datetime.minusHours(5);
						startDateTime = datetime.minusMinutes(30);
						reportDTO1.setStartDate(startDateTime.format(dateTimeFormat));						
					}
					if(!CommonUtil.isNull(objs[2])){
						time1 = String.valueOf(objs[2]).substring(0,19);						
						LocalDateTime datetime = LocalDateTime.parse(time1,dateTimeFormat);
						datetime = datetime.minusHours(5);
						endDateTime = datetime.minusMinutes(30);
						reportDTO1.setEndDate(endDateTime.format(dateTimeFormat));
					}
					
					if(CommonUtil.isNull(objs[2])){
						time1 = String.valueOf(objs[1]).substring(0,19);						
						LocalDateTime datetime = LocalDateTime.parse(time1,dateTimeFormat);
						datetime = datetime.minusHours(5);
						endDateTime = datetime.minusMinutes(30);
						reportDTO1.setEndDate(endDateTime.format(dateTimeFormat));
					}
					try {
					 Duration duration = Duration.between(startDateTime, endDateTime);					 
					reportDTO1.setDuration(duration.getSeconds());
					}catch(Exception e) {}
					//System.out.println("reportDTO1.getEndDate() :" +dateFormat.format(reportDTO1.getEndDate()));
					reportDTO1.setPhoneNumber(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
					abandonedDTOs.add(reportDTO1);				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
			
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("answeredList", answeredDTOs);
		model.put("ivrAbandonedList", ivrAbandonedDTOs);
		model.put("missedList", missedDTOs);
		//model.put("abandonedList", abandonedDTOs.stream().filter(e->e.getExtension()!=null && e.getExtension().length()>1).collect(Collectors.toList()));
		model.put("abandonedList", abandonedDTOs);
		
		return new ResponseEntity(model, HttpStatus.OK);  
	}

	@RequestMapping(value="/ivrReport",method = RequestMethod.POST)
	 public ResponseEntity<?> ivrReport(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 
		
		System.out.println("reportDTO.getStartDate() :"+reportDTO.getStartDate());
		System.out.println("reportDTO.getEndDate() :"+reportDTO.getEndDate());
		
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
		 Date date1 = inputFormat.parse(reportDTO.getStartDate());
		 Date date2 = inputFormat.parse(reportDTO.getEndDate());
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	    
		 LocalDate startDate = LocalDate.parse(formatter.format(date1));
		 LocalDate endDate = LocalDate.parse(formatter.format(date2));	
		
		 System.out.println("startDate :"+startDate);
		 System.out.println("endDate :"+endDate);
		
		Map<Object, Object> model1 = new HashMap<>();
		
	
		Query ivrAbandoned =  entityManager.createNativeQuery("SELECT  doc_Id,Ivr_Start_Time,Ivr_End_time,phone_No,Ivr_Duration,call_date,traverse "
				 + "FROM ivr_report  "	
				 + "WHERE CAST(call_date AS DATE) BETWEEN :startDate AND :endDate");
		
			
		
		ivrAbandoned.setParameter("startDate", startDate);
		ivrAbandoned.setParameter("endDate", endDate);
		
		//missed.setParameter("startDate", startDate);
		//missed.setParameter("endDate", endDate);
		
	
		List<Object> ivrAbandonedList = null;
	
		
		ivrAbandonedList = ivrAbandoned.getResultList();
		//missedList = missed.getResultList();
		
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
	     DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	     
	     String time1="";
	     LocalDateTime startDateTime=null;
	     LocalDateTime endDateTime=null;
	     
	
		List<IvrReportDTO> ivrAbandonedDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(ivrAbandonedList)) {
			Iterator ivrAbandonedIterator = ivrAbandonedList.iterator();			
			while (ivrAbandonedIterator.hasNext()){
				LocalDateTime startDateTimeDataConverted1=null;
				LocalDateTime startDateTimeDataConverted=null;
				
				try {
					Object[] objs = ( Object[]) ivrAbandonedIterator.next();
					IvrReportDTO reportDTO1=new IvrReportDTO();					
					reportDTO1.setDocId(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);
					reportDTO1.setIvrStartTime(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
					reportDTO1.setIvrEndTime(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);
					reportDTO1.setPhoneNo(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);	
					reportDTO1.setIvrDuration(CommonUtil.isNull(objs[4])?"":(String)objs[4]);
					try {
					if(!CommonUtil.isNull(objs[5])) {
						startDateTimeDataConverted1 = LocalDateTime.parse(objs[5].toString().substring(0,19), dateTimeFormat);
					//	startDateTimeDataConverted = startDateTimeDataConverted1.plusMinutes(30);
						reportDTO1.setCallDate(startDateTimeDataConverted1.toLocalDate().toString());
					}
					}catch(Exception e) {}
					reportDTO1.setTraverse(CommonUtil.isNull(objs[6])?"":(String)objs[6]);	
					ivrAbandonedDTOs.add(reportDTO1);				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		List<ReportDTO> abandonedDTOs=new ArrayList<>();
			
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("data", ivrAbandonedDTOs);
		
		return new ResponseEntity(model, HttpStatus.OK);  
	}

	
	@RequestMapping(value="/todayRingStatusReportsDetails",method = RequestMethod.POST)
	 public ResponseEntity<?> todayRingStatusReportsDetails(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 	
		System.out.println(reportDTO.getRingStartSecond());
		System.out.println(reportDTO.getRingEndSecond());
		long ringStartSecond = Long.valueOf(reportDTO.getRingStartSecond());
		long ringEndSecond = Long.valueOf(reportDTO.getRingEndSecond());
		

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
		 DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 Date date1 = inputFormat.parse(reportDTO.getStartDate());
		 Date date2 = inputFormat.parse(reportDTO.getEndDate());
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	    
		 LocalDate startDate = LocalDate.parse(formatter.format(date1));
		 LocalDate endDate = LocalDate.parse(formatter.format(date2));	
				
		Map<Object, Object> model1 = new HashMap<>();		
		 
		Query result =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");		
	
	
		result.setParameter("startDate", startDate);
		result.setParameter("endDate", endDate);		
		
		
		List<Object> resultList = null;			
		resultList = result.getResultList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String startDateTime="";
		String endDateTime="";
		List<ReportDTO> resultDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(resultList)) {
			System.out.println("resultList :"+resultList);
			Iterator resultIterator = resultList.iterator();			
			while (resultIterator.hasNext()){
				try {
					Object[] objs = ( Object[]) resultIterator.next();
					ReportDTO reportDTO1=new ReportDTO();	
					reportDTO1.setExtension(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);					
					reportDTO1.setStartDate(CommonUtil.isNull(objs[1])?" ":objs[2].toString());					
					reportDTO1.setEndDate(CommonUtil.isNull(objs[2])?" ":objs[1].toString());					
					reportDTO1.setPhoneNumber(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);	
					
					
					if(reportDTO1.getStartDate()!=null) {
						startDateTime = reportDTO1.getStartDate().substring(0,reportDTO1.getStartDate().length()-2);
						endDateTime = reportDTO1.getEndDate().substring(0,reportDTO1.getEndDate().length()-2);					
						LocalDateTime localDateTime1 = LocalDateTime.parse(startDateTime,dateTimeFormat);						
						LocalDateTime localDateTime2 = LocalDateTime.parse(endDateTime,dateTimeFormat);							
						Duration duration = Duration.between(localDateTime1,localDateTime2);
						reportDTO1.setDuration(duration.getSeconds());
						resultDTOs.add(reportDTO1);	
					}
					
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}		
		
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("answeredList", resultDTOs.stream().sorted(Comparator.comparing(ReportDTO::getStartDate).reversed()).filter(e->e.getDuration()>=ringStartSecond&&e.getDuration()<=ringEndSecond).collect(Collectors.toList()));
		return new ResponseEntity(model, HttpStatus.OK);  
	}
	
	@RequestMapping(value="/monthlyRingStatusReportsDetails",method = RequestMethod.GET)
	 public ResponseEntity<?> monthlyRingStatusReportsDetails()throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 	
		List<ServiceInteractionDTO> serviceInteractionDTOs = new ArrayList<>();
		
		/*
		System.out.println(reportDTO.getRingStartSecond());
		System.out.println(reportDTO.getRingEndSecond());
		long ringStartSecond = Long.valueOf(reportDTO.getRingStartSecond());
		long ringEndSecond = Long.valueOf(reportDTO.getRingEndSecond());

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
		 DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 Date date1 = inputFormat.parse(reportDTO.getStartDate());
		 Date date2 = inputFormat.parse(reportDTO.getEndDate());
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	    */
		 DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 YearMonth yearMonth = YearMonth.now(); 			
		 int startMonth = yearMonth.minus(Period.ofMonths(yearMonth.getMonthValue()-1)).getMonthValue();
		 int currentMonth = yearMonth.getMonthValue();
		 for (int i = startMonth; i <= currentMonth; i++) {
			 LocalDate startDate = LocalDate.of(yearMonth.getYear(), i, 1);		  
			 LocalDate endDate = LocalDate.of(yearMonth.getYear(), i, startDate.lengthOfMonth());	
			 ServiceInteractionDTO serviceInteractionDTO = new ServiceInteractionDTO();
			 System.out.println(startDate);
			 System.out.println(endDate);
			 serviceInteractionDTO.setMonth(startDate.getMonth().toString()+"-"+startDate.getYear());
		Query result =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");		
	
		result.setParameter("startDate", startDate);
		result.setParameter("endDate", endDate);	
		
		List<Object> resultList = null;			
		resultList = result.getResultList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String startDateTime="";
		String endDateTime="";
		List<ReportDTO> resultDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(resultList)) {
			System.out.println("resultList :"+resultList);
			Iterator resultIterator = resultList.iterator();			
			while (resultIterator.hasNext()){
				try {
					Object[] objs = ( Object[]) resultIterator.next();
					ReportDTO reportDTO1=new ReportDTO();							
					reportDTO1.setStartDate(CommonUtil.isNull(objs[1])?" ":objs[1].toString());					
					reportDTO1.setEndDate(CommonUtil.isNull(objs[2])?" ":objs[2].toString());					
					reportDTO1.setCallStatus(CommonUtil.isNull(objs[4])?" ":objs[4].toString());
					if(reportDTO1.getStartDate()!=null) {
						if(reportDTO1.getStartDate()!=null && reportDTO1.getStartDate().length()>5) {
						startDateTime = reportDTO1.getStartDate().substring(0,reportDTO1.getStartDate().length()-2);
						}
						if(reportDTO1.getEndDate()!=null && reportDTO1.getEndDate().length()>5) {
						endDateTime = reportDTO1.getEndDate().substring(0,reportDTO1.getEndDate().length()-2);							
						LocalDateTime localDateTime1 = LocalDateTime.parse(startDateTime,dateTimeFormat);						
						LocalDateTime localDateTime2 = LocalDateTime.parse(endDateTime,dateTimeFormat);							
						Duration duration = Duration.between(localDateTime1,localDateTime2);
						reportDTO1.setDuration(duration.getSeconds());
						}
						resultDTOs.add(reportDTO1);	
					}
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}						
		}
		int totalcount=resultDTOs.size();
		serviceInteractionDTO.setTotalCalls(String.valueOf(totalcount));
		long answithin10sec=resultDTOs.stream().filter(e->e.getDuration()>=0&&e.getDuration()<=10&&e.getCallStatus().equalsIgnoreCase("ANSWER")).count();
		serviceInteractionDTO.setCallsAnsWithin10Sec(String.valueOf(answithin10sec));
		long answithin20sec=resultDTOs.stream().filter(e->e.getDuration()>=10&&e.getDuration()<=20&&e.getCallStatus().equalsIgnoreCase("ANSWER")).count();
		serviceInteractionDTO.setCallsAnsWithin20Sec(String.valueOf(answithin20sec));
		
		long notanswithin10sec=resultDTOs.stream().filter(e->e.getDuration()>=0&&e.getDuration()<=10&&!e.getCallStatus().equalsIgnoreCase("ANSWER")).count();
		serviceInteractionDTO.setNotAnsWithIn10Sec(String.valueOf(notanswithin10sec));;
		long notanswithin20sec=resultDTOs.stream().filter(e->e.getDuration()>=10&&e.getDuration()<=20&&!e.getCallStatus().equalsIgnoreCase("ANSWER")).count();
		serviceInteractionDTO.setNotAnsWithIn20Sec(String.valueOf(notanswithin20sec));
		
		Query abandoned = entityManager.createNativeQuery("SELECT  count(*) FROM eupraxia_report "
				 + "WHERE call_status !='ANSWER' and call_direction='inbound' and extension is not null and  extension <>'' "
				 + "and CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate");		
		abandoned.setParameter("startDate", startDate);
		abandoned.setParameter("endDate", endDate);		
		
		serviceInteractionDTO.setAbandon(abandoned.getSingleResult().toString());
		
		Query ivrAbandoned =  entityManager.createNativeQuery("SELECT  count(*) as abandonedCalls "
				 + "FROM ivr_report "
				 + "WHERE doc_id is null and CAST(call_date AS DATE) BETWEEN :startDate AND :endDate");	
		ivrAbandoned.setParameter("startDate", startDate);
		ivrAbandoned.setParameter("endDate", endDate);		
		serviceInteractionDTO.setMissed(ivrAbandoned.getSingleResult().toString());
		
			try {
		//	serviceInteractionDTO.setAnsweredPercentage(String.valueOf((totalCallsCount-answeredCountWithinThreshHold)/totalCallsCount));
	      } catch (ArithmeticException e) {
	         //e.printStackTrace();
	         //System.out.println("We are just printing the stack trace.\n"+ "ArithmeticException is handled. But take care of the variable \"c\"");
	      }
		
		
		serviceInteractionDTOs.add(serviceInteractionDTO);
	}
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("serviceInteraction", serviceInteractionDTOs);
		return new ResponseEntity(model, HttpStatus.OK);  
	}
	@RequestMapping(value="/abnLevel",method = RequestMethod.POST)
	 public ResponseEntity<?> abnLevel(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 	
		
		 String startDate1= CommonUtil.convertStringToDate(reportDTO.getStartDate());
		 String endtDate1= CommonUtil.convertStringToDate(reportDTO.getEndDate());
		
		 String startTimeHour= CommonUtil.convertStringToHour(reportDTO.getStartDate());
		 String startTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getStartDate());
		 
		 String endTimeHour= CommonUtil.convertStringToHour(reportDTO.getEndDate());
		 String endTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getEndDate());
		 	 
		  
		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		 LocalDateTime startDateTimeFormatted = LocalDateTime.parse(startDateTime, formatter);
		 
		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		 LocalDateTime endDateTimeFormatted = LocalDateTime.parse(endDateTime, formatter);
		 //LocalDateTime endDateTime1 =  endDateTimeFormatted.minusHours(5);
		 //LocalDateTime endDateTimeConverted =  endDateTime1.minusMinutes(30);
		
		 
	
		 //LocalDate startDate = LocalDate.parse(startDate1);		
		 //LocalDate endDate = LocalDate.parse(startDate1);
		 
		LocalDate startDate = startDateTimeFormatted.toLocalDate();
		 LocalDate endDate = endDateTimeFormatted.toLocalDate();		 

		List<ServiceInteractionDTO> serviceInteractionDTOs = new ArrayList<>();
		
		
		 ServiceInteractionDTO serviceInteractionDTO = new ServiceInteractionDTO();
			 System.out.println(startDate);
			 System.out.println(endDate);
			 serviceInteractionDTO.setMonth(startDate.getMonth().toString()+"-"+startDate.getYear());
		Query result =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound'");		
	
		result.setParameter("startDate", startDate);
		result.setParameter("endDate", endDate);
		
		List<Object> resultList = null;			
		resultList = result.getResultList();
		 DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startDateTime1="";
		String endDateTime1="";
		List<ReportDTO> resultDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(resultList)) {
			System.out.println("resultList :"+resultList);
			Iterator resultIterator = resultList.iterator();			
			Query query1 =  queryCriteria.reportQueryAll();		 
			 query1.setParameter("startDate", startDate);
			 query1.setParameter("endDate", endDate);
			List<Object> list1 = query1.getResultList();
		
//			Query ivrAbandoned =  entityManager.createNativeQuery("SELECT  doc_Id,Ivr_Start_Time,Ivr_End_time,phone_No "
//					 + "FROM ivr_report  "	
//					 + "WHERE doc_id is null and CAST(call_date AS DATE) BETWEEN :startDate AND :endDate");
//			ivrAbandoned.setParameter("startDate", startDate);
//			ivrAbandoned.setParameter("endDate", endDate);
//			List<Object> ivrAbandonedList = ivrAbandoned.getResultList();
//			
		int totalcount=list1.size();
		
		
		serviceInteractionDTO.setTotalCalls(String.valueOf(totalcount));
		
		Query result1 =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound' AND call_status !='ANSWER' AND ring_duration<=10");		
	
		result1.setParameter("startDate", startDate);
		result1.setParameter("endDate", endDate);
		
		
		
		List<Object> resultList1 = null;			
		resultList1 = result1.getResultList();
		int notanswithin10sec=resultList1.size();
		int nawt=(int)notanswithin10sec;
		serviceInteractionDTO.setNotAnsWithIn10Sec(String.valueOf(notanswithin10sec));
		Query result2 =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound' AND  call_Status!='ANSWER'");		
	
		result2.setParameter("startDate", startDate);
		result2.setParameter("endDate", endDate);
		
		List<Object> resultList2 = null;			
		resultList2 = result2.getResultList();
		int totalabn=resultList2.size();
		int totalabn1=(int)totalabn;
		
		serviceInteractionDTO.setAbandon(String.valueOf(totalabn));
		
	
			
			try {
				int sss=totalabn1 - nawt;
				int sss1=totalcount-nawt;
				
				
				float i = (float) sss;
				float j = (float)sss1;
				float k=(i/j)*100;
				String per=String.valueOf(k);
				if(per.length()>5) {
					per = per.substring(0, 5);
				}
				serviceInteractionDTO.setAbnPercentage(per);
				      
			} catch (ArithmeticException e) {
		         e.printStackTrace();
		         //System.out.println("We are just printing the stack trace.\n"+ "ArithmeticException is handled. But take care of the variable \"c\"");
		      }
		
		
		serviceInteractionDTOs.add(serviceInteractionDTO);
	}
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("serviceInteraction", serviceInteractionDTOs);
		return new ResponseEntity(model, HttpStatus.OK);  
	}
	@RequestMapping(value="/monthlyAbandonReportsDetails",method = RequestMethod.POST)
	 public ResponseEntity<?> monthlyAbandonReportsDetails(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 	
		
		 String startDate1= CommonUtil.convertStringToDate(reportDTO.getStartDate());
		 String endtDate1= CommonUtil.convertStringToDate(reportDTO.getEndDate());
		
		 String startTimeHour= CommonUtil.convertStringToHour(reportDTO.getStartDate());
		 String startTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getStartDate());
		 
		 String endTimeHour= CommonUtil.convertStringToHour(reportDTO.getEndDate());
		 String endTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getEndDate());
		 	 
		  
		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		 LocalDateTime startDateTimeFormatted = LocalDateTime.parse(startDateTime, formatter);
		 
		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		 LocalDateTime endDateTimeFormatted = LocalDateTime.parse(endDateTime, formatter);
		 //LocalDateTime endDateTime1 =  endDateTimeFormatted.minusHours(5);
		 //LocalDateTime endDateTimeConverted =  endDateTime1.minusMinutes(30);
		
		 
	
		 //LocalDate startDate = LocalDate.parse(startDate1);		
		 //LocalDate endDate = LocalDate.parse(startDate1);
		 
		LocalDate startDate = startDateTimeFormatted.toLocalDate();
		 LocalDate endDate = endDateTimeFormatted.toLocalDate();		 

		List<ServiceInteractionDTO> serviceInteractionDTOs = new ArrayList<>();
		
		
		 ServiceInteractionDTO serviceInteractionDTO = new ServiceInteractionDTO();
			 System.out.println(startDate);
			 System.out.println(endDate);
			 serviceInteractionDTO.setMonth(startDate.getMonth().toString()+"-"+startDate.getYear());
		Query result =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound'");		
	
		result.setParameter("startDate", startDate);
		result.setParameter("endDate", endDate);
		
		List<Object> resultList = null;			
		resultList = result.getResultList();
		 DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
	String startDateTime1="";
		String endDateTime1="";
	List<ReportDTO> resultDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(resultList)) {
			
		
		List<MailModel> mm=new ArrayList<MailModel>();
		mm=mailRepository.findAll();
		DateFormat formatter2 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int count=0;
			int count1=0;
		for(MailModel mailModel:mm) {
			System.out.println("hi email");
			String stdate=mailModel.getSentDate();
			try {
			Date stdate1 = (Date)formatter2.parse(stdate);
			if(stdate1.after(java.util.Date.from(startDate.atStartOfDay(ZoneId.of( "America/Montreal" ))                              .toInstant())
					)&&stdate1.before(java.util.Date.from(endDate.atStartOfDay(ZoneId.of( "America/Montreal" ))                              .toInstant())
							)) {
				count1=count1+1;
				if(mailModel.getCaseId()!=null) {
				count=count+1;
				}
			}
			
			}catch(Exception e) {}
			
		}
		List<SmsModel> sm=new ArrayList<>();
		sm=smsRepository.findAll();
		for(SmsModel sms:sm) {
			if(sms.getCaseId()!=null) {
			System.out.println("hi sms");
			String stdate=sms.getDate();
			try {
			Date stdate1 = (Date)formatter1.parse(stdate);
			if(stdate1.after(java.util.Date.from(startDate.atStartOfDay(ZoneId.of( "America/Montreal" )).toInstant())
					)&&stdate1.before(java.util.Date.from(endDate.atStartOfDay(ZoneId.of( "America/Montreal" )).toInstant())
							)) {
				count1=count1+1;
				if(sms.getCaseId()!=null) {
				count=count+1;
				}
			}
			
			}catch(Exception e) {}
			}
		}
		
		Query query1 =  queryCriteria.reportQueryAll();		 
		 query1.setParameter("startDate", startDate);
		 query1.setParameter("endDate", endDate);
		List<Object> list1 = query1.getResultList();
	
			Query query2 =  queryCriteria.reportQueryAllANS();		 
		 query2.setParameter("startDate", startDate);
		 query2.setParameter("endDate", endDate);
		List<Object> list2 = query2.getResultList();
		int totalcount=list2.size()+count;
		
		int tota1=list2.size()+count;
		
		serviceInteractionDTO.setTotalInteractionsInCRM(String.valueOf(tota1));
		
		serviceInteractionDTO.setTotalInteractions(String.valueOf(totalcount));
		try {
			int minuc=totalcount-tota1;
			serviceInteractionDTO.setTotalintper("100");
		}catch(Exception e) {}
			
		serviceInteractionDTOs.add(serviceInteractionDTO);
	}
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("serviceInteraction", serviceInteractionDTOs);
		return new ResponseEntity(model, HttpStatus.OK);  
	}
	
	@RequestMapping(value="/feedbackLevel",method = RequestMethod.POST)
	 public ResponseEntity<?> feedbackLevel(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 	
		
		 String startDate1= CommonUtil.convertStringToDate(reportDTO.getStartDate());
		 String endtDate1= CommonUtil.convertStringToDate(reportDTO.getEndDate());
		
		 String startTimeHour= CommonUtil.convertStringToHour(reportDTO.getStartDate());
		 String startTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getStartDate());
		 
		 String endTimeHour= CommonUtil.convertStringToHour(reportDTO.getEndDate());
		 String endTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getEndDate());
		 	 
		  
		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		 LocalDateTime startDateTimeFormatted = LocalDateTime.parse(startDateTime, formatter);
		 
		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		 LocalDateTime endDateTimeFormatted = LocalDateTime.parse(endDateTime, formatter);
		 //LocalDateTime endDateTime1 =  endDateTimeFormatted.minusHours(5);
		 //LocalDateTime endDateTimeConverted =  endDateTime1.minusMinutes(30);
		
		 
	
		 //LocalDate startDate = LocalDate.parse(startDate1);		
		 //LocalDate endDate = LocalDate.parse(startDate1);
		 
		LocalDate startDate = startDateTimeFormatted.toLocalDate();
		 LocalDate endDate = endDateTimeFormatted.toLocalDate();		 

		List<ServiceInteractionDTO> serviceInteractionDTOs = new ArrayList<>();
		
		
		 ServiceInteractionDTO serviceInteractionDTO = new ServiceInteractionDTO();
			 System.out.println(startDate);
			 System.out.println(endDate);
			 serviceInteractionDTO.setMonth(startDate.getMonth().toString()+"-"+startDate.getYear());
		Query result =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound' AND (feedback='1' OR feedback='2' OR feedback='3' OR feedback='4' OR feedback='5') ");		
	
		result.setParameter("startDate", startDate);
		result.setParameter("endDate", endDate);
		
		List<Object> resultList = null;			
		resultList = result.getResultList();
		
		Query result1 =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound' AND (feedback='4' OR feedback='5')");		
	
		result1.setParameter("startDate", startDate);
		result1.setParameter("endDate", endDate);
		
		List<Object> resultList1 = null;			
		resultList1 = result1.getResultList();
		 DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String startDateTime1="";
		String endDateTime1="";
		List<ReportDTO> resultDTOs=new ArrayList<>();
		if (CommonUtil.isListNotNullAndEmpty(resultList)) {
			System.out.println("resultList :"+resultList);
			Iterator resultIterator = resultList.iterator();			
	
		
		int totalfeedback=resultList.size();
		serviceInteractionDTO.setFeedback(String.valueOf(totalfeedback));
		int totfeed=totalfeedback;
		int totalfeedback1=resultList1.size();
		serviceInteractionDTO.setGoodFeedback(String.valueOf(totalfeedback1));
		int totfeed1=totalfeedback1;
		try {
			int minuc=totfeed-totfeed1;
			serviceInteractionDTO.setFeedbackper(String.valueOf((float)((minuc*100)/totfeed)));
		}catch(Exception e) {}
			
		serviceInteractionDTOs.add(serviceInteractionDTO);
	}
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("serviceInteraction", serviceInteractionDTOs);
		return new ResponseEntity(model, HttpStatus.OK);  
	}
	
	
	
	@RequestMapping(value="/serviceLevel",method = RequestMethod.POST)
	 public ResponseEntity<?> serviceLevel(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException, ParseException{
		Map<Object, Object> model = new HashMap<>(); 	
		
		 String startDate1= CommonUtil.convertStringToDate(reportDTO.getStartDate());
		 String endtDate1= CommonUtil.convertStringToDate(reportDTO.getEndDate());
		
		 String startTimeHour= CommonUtil.convertStringToHour(reportDTO.getStartDate());
		 String startTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getStartDate());
		 
		 String endTimeHour= CommonUtil.convertStringToHour(reportDTO.getEndDate());
		 String endTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getEndDate());
		 	 
		  
		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		 LocalDateTime startDateTimeFormatted = LocalDateTime.parse(startDateTime, formatter);
		 
		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		 LocalDateTime endDateTimeFormatted = LocalDateTime.parse(endDateTime, formatter);
		 //LocalDateTime endDateTime1 =  endDateTimeFormatted.minusHours(5);
		 //LocalDateTime endDateTimeConverted =  endDateTime1.minusMinutes(30);
		
		 
	
		 //LocalDate startDate = LocalDate.parse(startDate1);		
		 //LocalDate endDate = LocalDate.parse(startDate1);
		 
		LocalDate startDate = startDateTimeFormatted.toLocalDate();
		 LocalDate endDate = endDateTimeFormatted.toLocalDate();		 

		List<ServiceInteractionDTO> serviceInteractionDTOs = new ArrayList<>();
		
		
		 ServiceInteractionDTO serviceInteractionDTO = new ServiceInteractionDTO();
			 System.out.println(startDate);
			 System.out.println(endDate);
			 serviceInteractionDTO.setMonth(startDate.getMonth().toString()+"-"+startDate.getYear());
		Query result =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound' AND ring_duration<=20 AND call_Status='ANSWER'");		
	
		result.setParameter("startDate", startDate);
		result.setParameter("endDate", endDate);	
		
		
		Query result5 =  entityManager.createNativeQuery("SELECT  extension,ring_Start_Time,ring_End_Time,phone_No,call_status,feedback,case_Id FROM eupraxia_report "
				 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND call_direction='inbound' AND ring_duration<=20 AND call_Status!='ANSWER'");		
	
		result5.setParameter("startDate", startDate);
		result5.setParameter("endDate", endDate);	
		List<Object> list5 = result5.getResultList();
		
		int notanswithin20=list5.size();
		serviceInteractionDTO.setNotAnsWithIn20Sec(String.valueOf(notanswithin20));
		Query query1 =  queryCriteria.reportQueryAll();		 
		 query1.setParameter("startDate", startDate);
		 query1.setParameter("endDate", endDate);
		List<Object> list1 = query1.getResultList();
		List<Object> resultList = null;			
		resultList = result.getResultList();
		 DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startDateTime1="";
		String endDateTime1="";
		List<ReportDTO> resultDTOs=new ArrayList<>();
		
		int totalcount=list1.size();
		
		serviceInteractionDTO.setTotalCalls(String.valueOf(totalcount));
		
		long awt=resultList.size();
		serviceInteractionDTO.setCallsAnsWithin20Sec(String.valueOf(awt));
		
		
			try {
				
				float i = (float) (awt);
				float j = (float) (totalcount-notanswithin20);
				float k=(i/j)*100;
				String per=String.valueOf(k);
				if(per.length()>5) {
					per = per.substring(0, 5);
				}
			serviceInteractionDTO.setServPercentage(per);
	     
				//	serviceInteractionDTO.setServPercentage(String.valueOf((float)((awt*100)/(totalcount-notanswithin20))));
	     
			} catch (ArithmeticException e) {
	         e.printStackTrace();
	         //System.out.println("We are just printing the stack trace.\n"+ "ArithmeticException is handled. But take care of the variable \"c\"");
	      }
				serviceInteractionDTOs.add(serviceInteractionDTO);
	
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("serviceInteraction", serviceInteractionDTOs);
		return new ResponseEntity(model, HttpStatus.OK);  
	}
	
	@RequestMapping(value="/callReports",method = RequestMethod.POST)
	 public ResponseEntity<?> callReports(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{
		Map<Object, Object> model = new HashMap<>(); 
		ArrayList<ReportsModel> ReportsModels=new ArrayList<ReportsModel>();
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
		ReportsModels=reportService.getData(date1,date2);
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("data", ReportsModels);
		return new ResponseEntity(model, HttpStatus.OK);   
	}
	
	@RequestMapping(value="/saveDispo",method = RequestMethod.POST)
	 public ResponseEntity<?> saveDispo(@RequestBody DispositionDTO dispositionDTO)throws InstantiationException, IllegalAccessException{
		
		Map<Object, Object> model = new HashMap<>(); 
		CdrReportModel reportsModel = new CdrReportModel();	
		
		CallPropertiesModel callPropertiesModel=CallService.findById(dispositionDTO.getCallId());
/*
 * callPropertiesModel.setId(dispositionDTO.getCallId());
 * callPropertiesModel.setComments(dispositionDTO.getComments());
 * callPropertiesModel.setDisposition(dispositionDTO.getDisposition());
 * callPropertiesModel.setCallbackDate(dispositionDTO.getCallbackDate());
 */
		//CallService.saveOrUpdate(callPropertiesModel);
		try {
			//SimpleDateFormat formatter6=new SimpleDateFormat("HH:mm:ss");
			
			//SimpleDateFormat sdf 
            //= new SimpleDateFormat( 
            //    "dd-MM-yyyy HH:mm:ss"); 
			//int callDuration=0;
			//int queueDuration=0;
			//int holdduration=0;
			try {
				LocalDateTime d1 = callPropertiesModel.getCallEndTime(); 
				LocalDateTime d2 = callPropertiesModel.getCallConnectTime(); 
	           
				
				//long difference_In_Time = d1.getTime() - d2.getTime(); 
	            
	            //long diff1=difference_In_Time/1000;
				 Duration callDuration = Duration.between(d1, d2);
	            
	           // callDuration=(int)diff1;  
			reportsModel.setAgentDuration(String.valueOf(callDuration));
			reportsModel.setDuration(String.valueOf(callDuration));
			
			}catch(Exception e) {}
			try {
				LocalDateTime d1 = callPropertiesModel.getQueueRemoveTime(); 
				LocalDateTime d2 = callPropertiesModel.getQueueJoinTime(); 
	          
				 Duration queueDuration1 = Duration.between(d1, d2);	
				//queueDuration=(int)((d1. - d2.getTime())/1000);
				reportsModel.setQueueDuration(String.valueOf(queueDuration1));
			}catch(Exception e) {}
			
			try {
				LocalDateTime d1 = callPropertiesModel.getHoldEndTime(); 
				LocalDateTime d2 = callPropertiesModel.getHoldStartTime(); 
	          
				//long holdduartion1=(int)((d1.getTime() - d2.getTime())/1000);
				Duration holdduartion1 = Duration.between(d1, d2);
				reportsModel.setHoldDuration(String.valueOf(holdduartion1));
			}catch(Exception e) {}
			
			
			reportsModel.setCallDate(callPropertiesModel.getCallStartTime());
			try {
			reportsModel.setCallConnectTime(callPropertiesModel.getCallConnectTime().toString());
			}catch(Exception e) {}
			try {
			reportsModel.setCallStartTime(callPropertiesModel.getCallStartTime().toString());
			}catch(Exception e) {}
			try {
				reportsModel.setAgentStartTime(callPropertiesModel.getCallStartTime().toString());
				}catch(Exception e) {}
			try {
				reportsModel.setAgentEndTime(callPropertiesModel.getCallEndTime().toString());
				}catch(Exception e) {}
			
			try {
			reportsModel.setCallEndTime(callPropertiesModel.getCallEndTime().toString());
		}catch(Exception e) {}
			try {
			reportsModel.setHoldEndTime(callPropertiesModel.getHoldEndTime().toString());
	}catch(Exception e) {}
			try {
			reportsModel.setHoldStartTime(callPropertiesModel.getHoldStartTime().toString());
}catch(Exception e) {}
			
			try {
				reportsModel.setQueueStartTime(callPropertiesModel.getQueueJoinTime().toString());
			}catch(Exception e) {}
			try {
				reportsModel.setQueueEndTime(callPropertiesModel.getQueueRemoveTime().toString());
			}catch(Exception e) {}
		}catch(Exception e) {}		
		
		//reportsModel.setCallbackDate(callPropertiesModel.getCallbackDate());
		
		try {
			IvrModel ivrModel=ivrService.findByUniqueId(callPropertiesModel.getIvr());
			if(ivrModel!=null) {
				SimpleDateFormat formatter7=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				SimpleDateFormat formatter8=new SimpleDateFormat("HH:mm:ss");
				
				try {
					Date d1 =formatter7.parse(ivrModel.getEndTime());
		            Date d2 =formatter7.parse(ivrModel.getStartTime());
		          
					long ivrduartion1=(int)((d1.getTime() - d2.getTime())/1000);
					reportsModel.setIvrDuration(String.valueOf(ivrduartion1));
					reportsModel.setIvrStartTime(formatter8.format(d2));
					reportsModel.setIvrEndTime(formatter8.format(d1));
					reportsModel.setTraverse(ivrModel.getIvrFlow());
					reportsModel.setQueueVdn(ivrModel.getQueueVdn());
					reportsModel.setRecPath(ivrModel.getRecPath());
				}catch(Exception e) {}
			}
			
		}catch(Exception e) {}
		
		reportsModel.setCallDirection(callPropertiesModel.getCallDirection());
		
		String ext=callPropertiesModel.getExtension();
		reportsModel.setCallStatus(callPropertiesModel.getCallStatus());
		//reportsModel.setComments(callPropertiesModel.getComments());
		//reportsModel.setDisposition(callPropertiesModel.getDisposition());
		reportsModel.setDocId(callPropertiesModel.getId());
		reportsModel.setExtension(callPropertiesModel.getExtension());
		
		
		reportsModel.setIsClosed(callPropertiesModel.getIsClosed());
		reportsModel.setPhoneNo(callPropertiesModel.getPhoneNo());
		reportsModel.setSecondChannel(callPropertiesModel.getSecondChannel());
		reportsModel.setSecondNumber(callPropertiesModel.getSecondNumber());
		reportsModel.setSipChannel(callPropertiesModel.getSipChannel());
		reportsModel.setTrunkChannel(callPropertiesModel.getTrunkChannel());
		reportsModel.setComments(dispositionDTO.getComments());
		reportsModel.setDisposition(callPropertiesModel.getCallStatus());
		
		cdrReportService.saveDisposition(reportsModel);
		CallService.deleteById(dispositionDTO.getCallId());
		LoginModel loginModel=loginService.findByExtensionAndStatus(callPropertiesModel.getExtension(),"login");
		if(loginModel!=null) {
			String startTime="";
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				   LocalDateTime now = LocalDateTime.now();
				   startTime= dtf.format(now);
			}catch(Exception e) {}
			
				loginModel.setStatus("Ready");
				loginModel.setDirection("");
			loginModel.setStatusTime(startTime);
			loginService.saveOrUpdate(loginModel);
		}
		quickService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					if(callPropertiesModel.getExtension()!=null) {
						List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
						usercampaignModels=campaignService.findByUserextension(callPropertiesModel.getExtension());
						for(UserCampaignMappingModel ucm:usercampaignModels) {
							if(ucm.getQueue()!=null) {
					QueueDTO queueDTO=new QueueDTO();
				queueDTO.setQueue(ucm.getQueue());
					queueDTO.setIface("Local/"+callPropertiesModel.getExtension()+"@from-queue");
					ManagerResponse queueAddResponse = actionhandler.queueAdd(queueDTO);    
							}}
					}
					
						if(!callPropertiesModel.getPopupStatus().equalsIgnoreCase("hangup")) {
						OriginateDTO originateDTO=new OriginateDTO();
						originateDTO.setChannel(callPropertiesModel.getSipChannel());
						ManagerResponse HangupResponse = actionhandler.hangUpCall(originateDTO);		
						}
						
						
					
				}catch(Exception e) {}
			}
		});
		if(dispositionDTO.getCallbackDate()!=null) {
			try {
				ScheduleCallModel scheduleCallModel=new ScheduleCallModel();
				String phone=callPropertiesModel.getPhoneNo();
				if(phone.length()>9) {
					phone= phone.substring(phone.length()-10,phone.length());
	    					
				}
				 
				scheduleCallModel.setExtension(callPropertiesModel.getExtension());
				scheduleCallModel.setPhoneNumber(phone);
				scheduleCallModel.setCallStatus("open");
				//scheduleCallModel.setCallBackTime(dispositionDTO.getCallbackDate());
				scheduleCallService.saveOrUpdate(scheduleCallModel);
			}catch(Exception e) {}
		}		

		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("reportsModel", reportsModel);
		return new ResponseEntity(model, HttpStatus.OK);    

}


	
	 @RequestMapping(value="/saveDisposition",method = RequestMethod.POST)
	    public ResponseEntity<?> saveDisposition(@RequestBody DispositionDTO dispositionDTO)throws InstantiationException, IllegalAccessException{
			Date callbackdate=null;
	    		Map<Object, Object> model = new HashMap<>(); 
	    		ReportsModel reportsModel = new ReportsModel();	
	    		ReportsModel reportsModel1 = new ReportsModel();	
	    	
	    		
		/*
		 * callPropertiesModel.setId(dispositionDTO.getCallId());
		 * callPropertiesModel.setComments(dispositionDTO.getComments());
		 * callPropertiesModel.setDisposition(dispositionDTO.getDisposition());
		 * callPropertiesModel.setCallbackDate(dispositionDTO.getCallbackDate());
		 */
	    		//CallService.saveOrUpdate(callPropertiesModel);
	    	final 	CallPropertiesModel callPropertiesModel=CallService.findById(dispositionDTO.getCallId());
	    		//reportsModel.setCallbackDate(callPropertiesModel.getCallbackDate());
	    		reportsModel.setCallConnectTime(callPropertiesModel.getCallConnectTime());
	    		reportsModel.setCallDirection(callPropertiesModel.getCallDirection());
	    		reportsModel.setCallStartTime(callPropertiesModel.getCallStartTime());
	    		reportsModel.setCallEndTime(callPropertiesModel.getCallEndTime());
	    		reportsModel.setCallStatus(callPropertiesModel.getCallStatus());
	    		//reportsModel.setComments(callPropertiesModel.getComments());
	    		//reportsModel.setDisposition(callPropertiesModel.getDisposition());
	    		reportsModel.setDocId(callPropertiesModel.getId());
	    		reportsModel.setExtension(callPropertiesModel.getExtension());
	    		reportsModel.setHoldEndTime(callPropertiesModel.getHoldEndTime());
	    		reportsModel.setHoldStartTime(callPropertiesModel.getHoldStartTime());
	    		reportsModel.setIsClosed(callPropertiesModel.getIsClosed());
	    		reportsModel.setPhoneNo(callPropertiesModel.getPhoneNo());
	    		reportsModel.setSecondChannel(callPropertiesModel.getSecondChannel());
	    		reportsModel.setSecondNumber(callPropertiesModel.getSecondNumber());
	    		reportsModel.setSipChannel(callPropertiesModel.getSipChannel());
	    		reportsModel.setTrunkChannel(callPropertiesModel.getTrunkChannel());
	    		reportsModel.setComments(dispositionDTO.getComments());
	    		reportsModel.setDisposition(dispositionDTO.getDisposition());
	    		reportsModel.setCusName(callPropertiesModel.getName());
	    		reportsModel.setCamName(callPropertiesModel.getCamName());
	    		//reportsModel.setCallbackDate(dispositionDTO.getCallbackDate());
	    		try {
	    			if(dispositionDTO.getCallbackDate()!=null&&!dispositionDTO.getCallbackDate().isEmpty()) {
	    			String stdt=dispositionDTO.getCallbackDate();
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
	    		}
	    		
	    		
	    		reportService.saveDisposition(reportsModel);
	    		CallService.deleteById(dispositionDTO.getCallId());
	    		if(dispositionDTO.getCallbackDate()!=null&&!dispositionDTO.getCallbackDate().isEmpty()) {
	    			try {
	    				ScheduleCallModel scheduleCallModel=new ScheduleCallModel();
	    				String phone=callPropertiesModel.getPhoneNo();
	    				if(phone.length()>9) {
	    					phone= phone.substring(phone.length()-10,phone.length());
	 	    					
	    				}
	    				 
	    				scheduleCallModel.setExtension(callPropertiesModel.getExtension());
	    				scheduleCallModel.setPhoneNumber(phone);
	    				scheduleCallModel.setCallStatus("open");
	    				scheduleCallModel.setCallBackTime(callbackdate);
	    				scheduleCallModel.setCampaignName(callPropertiesModel.getCamName());
	    				scheduleCallService.saveOrUpdate(scheduleCallModel);
	    			}catch(Exception e) {}
	    		}
	    		quickService.submit(new Runnable() {
	    			@Override
	    			public void run() {
	    				try {
    						if(!callPropertiesModel.getPopupStatus().equalsIgnoreCase("hangup")) {
    						OriginateDTO originateDTO=new OriginateDTO();
    						originateDTO.setChannel(callPropertiesModel.getSipChannel());
    						ManagerResponse HangupResponse = actionhandler.hangUpCall(originateDTO);		
    						}
    					}catch(Exception e) {}

	    				
	    				

	    				LoginModel loginModel=new LoginModel();
	    				LoginModel loginModel1=new LoginModel();
	    				try {
	    				loginModel=loginService.findByExtensionAndStatus(callPropertiesModel.getExtension(),"login");
	    				loginModel1=loginModel;	
	    				if(loginModel.getAgentName()!=null) {
	    						String startTime="";
	    						try {
	    							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    							   LocalDateTime now = LocalDateTime.now();
	    							   startTime= dtf.format(now);
	    						}catch(Exception e) {}
	    						
	    							loginModel.setStatus("Ready");
	    							loginModel.setDirection("");
	    						loginModel.setStatusTime(startTime);
	    						loginService.saveOrUpdate(loginModel);
	    					}
	    					
	            if(callPropertiesModel.getExtension()!=null) {
if(loginModel1.getStatus()!=null&&!loginModel1.getStatus().equalsIgnoreCase("Not Ready")&&callPropertiesModel.getDialMethod()!=null&&"autodialler".equalsIgnoreCase(callPropertiesModel.getDialMethod())){
	            		
	            		UserModel userModel=userService.findByUserextension(callPropertiesModel.getExtension());
	            		if(userModel!=null&&!userModel.getDiallerActive().contentEquals("0")) {
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
	            				actionhandler.originateCall(originateDTO);
	            				DiallerModel DiallerModel1=fileService.findById(diallerModel.getId());
	            				if(DiallerModel1!=null) {
	            					DiallerModel1.setStatus("close");
	            					DiallerModel1.setExtension(userModel.getUserextension());
	            					DiallerModel1.setCallDate(new Date());
	            					fileService.saveOrUpdate(DiallerModel1);
	            				}else {
	            					List<UserCampaignMappingModel> usercampaignModels = new ArrayList<UserCampaignMappingModel>();
	        						usercampaignModels=campaignService.findByUserextension(callPropertiesModel.getExtension());
	        						for(UserCampaignMappingModel ucm:usercampaignModels) {
	        							if(ucm.getQueue()!=null) {
	        		    					QueueDTO queueDTO=new QueueDTO();
	        		    					queueDTO.setQueue(ucm.getQueue());
	        		    					queueDTO.setIface("Local/"+callPropertiesModel.getExtension()+"@from-queue");
	        		    					ManagerResponse queueAddResponse = actionhandler.queueAdd(queueDTO); 
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
	    					ManagerResponse queueAddResponse = actionhandler.queueAdd(queueDTO); 
						}}}
	}
	    					
	    					
	    				}catch(Exception e) {}
	    			}
	    		});
				model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("reportsModel", reportsModel);
	    		return new ResponseEntity(model, HttpStatus.OK);    
	    
	    }

	 @RequestMapping(value="/SeniorCitizenReports",method = RequestMethod.POST)
	 public ResponseEntity<?> seniorCitizenReports(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{

		 String startDate1= CommonUtil.convertStringToDate(reportDTO.getStartDate());
		 String endtDate1= CommonUtil.convertStringToDate(reportDTO.getEndDate());
		
		 String startTimeHour= CommonUtil.convertStringToHour(reportDTO.getStartDate());
		 String startTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getStartDate());
		 
		 String endTimeHour= CommonUtil.convertStringToHour(reportDTO.getEndDate());
		 String endTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getEndDate());
		 	 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		 
		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;		
		 LocalDateTime startDateTimeFormatted = LocalDateTime.parse(startDateTime, formatter);
		 
		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		 LocalDateTime endDateTimeFormatted = LocalDateTime.parse(endDateTime, formatter);
		 //LocalDateTime endDateTime1 =  endDateTimeFormatted.minusHours(5);
		 //LocalDateTime endDateTimeConverted =  endDateTime1.minusMinutes(30);
		
		 
		LocalTime startDataTime = null;
		LocalTime endDataTime = null;
		
		 //LocalDate startDate = LocalDate.parse(startDate1);		
		 //LocalDate endDate = LocalDate.parse(startDate1);
		 
		LocalDate startDate = startDateTimeFormatted.toLocalDate();
		 LocalDate endDate = endDateTimeFormatted.toLocalDate();		 
		 
		
		 List<SeniorCitizenReportDTO> dispositionReportDTOs = new ArrayList<>();	
		 Map<Object, Object> model = new HashMap<>();

		 List<Object> list = null;
		 Query query = null;
		 
		 query =  queryCriteria.reportQuerySeniorCitizen();		 
		 query.setParameter("startDate", startDate);
		 query.setParameter("endDate", endDate);
		 list = query.getResultList();
		 dispositionReportDTOs = queryCriteria.resultSetSeniorCitizen(list);

				 
			 if(CommonUtil.isListNotNullAndEmpty(dispositionReportDTOs)) {			 
				 
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 //model.put("reports", dispositionReportDTOs);
				 model.put("reports", dispositionReportDTOs);
				 //model.put("reports", dispositionwithTimeFilterReportDTOs);
				 return new ResponseEntity(model, HttpStatus.OK);       
			 }
			 else {
				 model.put("responseCode", "400");
				 model.put("responseMessage", "No Results Found");
				 model.put("responseType", "Error");  
				 return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
			 }   
			

	 }
			
	 @RequestMapping(value="/ShowReportsByDate",method = RequestMethod.POST)
	 public ResponseEntity<?> dispoReports(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{

		
		 String startDate1= CommonUtil.convertStringToDate(reportDTO.getStartDate());
		 String endtDate1= CommonUtil.convertStringToDate(reportDTO.getEndDate());
		
		 String startTimeHour= CommonUtil.convertStringToHour(reportDTO.getStartDate());
		 String startTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getStartDate());
		 
		 String endTimeHour= CommonUtil.convertStringToHour(reportDTO.getEndDate());
		 String endTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getEndDate());
		 	 
		  
		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		 LocalDateTime startDateTimeFormatted = LocalDateTime.parse(startDateTime, formatter);
		 
		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		 LocalDateTime endDateTimeFormatted = LocalDateTime.parse(endDateTime, formatter);
		 //LocalDateTime endDateTime1 =  endDateTimeFormatted.minusHours(5);
		 //LocalDateTime endDateTimeConverted =  endDateTime1.minusMinutes(30);
		
		 
		LocalTime startDataTime = null;
		LocalTime endDataTime = null;
		
		 //LocalDate startDate = LocalDate.parse(startDate1);		
		 //LocalDate endDate = LocalDate.parse(startDate1);
		 
		LocalDate startDate = startDateTimeFormatted.toLocalDate();
		 LocalDate endDate = endDateTimeFormatted.toLocalDate();		 
		 
		
		 List<TestDTO> dispositionReportDTOs = new ArrayList<>();	
		 Map<Object, Object> model = new HashMap<>();

		 List<Object> list = null;
		 Query query = null;
		 List<UserModel> um=new ArrayList<UserModel>();
		 um=userService.findAll();
		
		 
		 if(reportDTO.getCallStatus().contentEquals("All")) {
			 if(reportDTO.getCallDirection().contentEquals("inbound")) {
			 query =  queryCriteria.reportQueryAll();	
			 }else {
				 query =  queryCriteria.reportQueryAllForOutgoing();
			 }
			 query.setParameter("startDate", startDate);
			 query.setParameter("endDate", endDate);
			 list = query.getResultList();
			// dispositionReportDTOs = queryCriteria.resultSetAll(list,um);
			 System.out.println("dispositionReportDTOs All :"+dispositionReportDTOs.size());
		 }
		 
		 if(reportDTO.getCallStatus().contentEquals("report")) {
			 List<UserModel> um1=new ArrayList<UserModel>();
			 um1=userService.findAll();
			
			 query =  queryCriteria.reportQueryAllReport(reportDTO.getStatus(),reportDTO.getPhoneNumber(),reportDTO.getCallDirection(),reportDTO.getExtension(),reportDTO.getAgentName(),reportDTO.getCampaignName());		 
			 query.setParameter("startDate", startDate);
			 query.setParameter("endDate", endDate);
			
			 if(CommonUtil.isNullOrEmpty(reportDTO.getExtension())) {
				 query.setParameter("extension",reportDTO.getExtension());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getCallDirection())) {
				 query.setParameter("direction",reportDTO.getCallDirection());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getCampaignName())) {
				 query.setParameter("campaignName",reportDTO.getCampaignName());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getPhoneNumber())) {
				 query.setParameter("phoneNo",reportDTO.getPhoneNumber());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getAgentName())) {
				UserModel userModel=userService.findByUserName(reportDTO.getAgentName());
				 query.setParameter("extension",userModel.getUserextension());
			 }
			 
			 list = query.getResultList();
			
			 dispositionReportDTOs = queryCriteria.resultReportSetAll(list,um1,"report");
			 
		 }	
		
		 if(reportDTO.getCallStatus().contentEquals("war")) {
			 List<UserModel> um1=new ArrayList<UserModel>();
			 um1=userService.findAll();
			
			 query =  queryCriteria.reportQueryAllReport(reportDTO.getStatus(),reportDTO.getPhoneNumber(),reportDTO.getCallDirection(),reportDTO.getExtension(),reportDTO.getAgentName(),reportDTO.getCampaignName());		 
			 query.setParameter("startDate", startDate);
			 query.setParameter("endDate", endDate);
			
			 if(CommonUtil.isNullOrEmpty(reportDTO.getExtension())) {
				 query.setParameter("extension",reportDTO.getExtension());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getCallDirection())) {
				 query.setParameter("direction",reportDTO.getCallDirection());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getCampaignName())) {
				 query.setParameter("campaignName",reportDTO.getCampaignName());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getPhoneNumber())) {
				 query.setParameter("phoneNo",reportDTO.getPhoneNumber());
			 }
			 if(CommonUtil.isNullOrEmpty(reportDTO.getAgentName())) {
				UserModel userModel=userService.findByUserName(reportDTO.getAgentName());
				 query.setParameter("extension",userModel.getUserextension());
			 }
			 
			 list = query.getResultList();
			
			 dispositionReportDTOs = queryCriteria.resultReportSetAll(list,um1,"war");
			 
		 }	
		
		 
		 if(reportDTO.getCallStatus().contentEquals("Information")) {
			 if(reportDTO.getCallDirection().contentEquals("inbound")) {
			 query =  queryCriteria.reportQueryInformation();	
			 }else {
				 query =  queryCriteria.reportQueryInformationForOutgoing();
			 }
			 query.setParameter("startDate", startDate);
			 query.setParameter("endDate", endDate);
			 list = query.getResultList();
			 dispositionReportDTOs = queryCriteria.resultSetInformation(list,um);
			 System.out.println("dispositionReportDTOs Information :"+dispositionReportDTOs.size());
		 }
		 
		 if(reportDTO.getCallStatus().contentEquals("Guidance")) {
			 if(reportDTO.getCallDirection().contentEquals("inbound")) {
					query =  queryCriteria.reportQueryGuidance();	
			 }else {
				 query =  queryCriteria.reportQueryGuidanceForOutgoing();	
			 }
			 query.setParameter("startDate", startDate);
			 query.setParameter("endDate", endDate);
			 list = query.getResultList();
			// dispositionReportDTOs = queryCriteria.resultSetGuidance(list,um);
			 System.out.println("dispositionReportDTOs Guidance :"+dispositionReportDTOs.size());
		 }
		 
		 if(reportDTO.getCallStatus().contentEquals("Emergency")) {
			 if(reportDTO.getCallDirection().contentEquals("inbound")) {
					 query =  queryCriteria.reportQueryEmergency();	
			 }else {
				 query =  queryCriteria.reportQueryEmergencyForOutgoing();	
			 }
			 query.setParameter("startDate", startDate);
			 query.setParameter("endDate", endDate);
			 list = query.getResultList();
			// dispositionReportDTOs = queryCriteria.resultSetEmergency(list,um);
			 System.out.println("dispositionReportDTOs Emergency :"+dispositionReportDTOs.size());
		 }
		 //query.setParameter("startDateTimeFormatted", startDateTimeFormatted);
		 //query.setParameter("endDateTimeFormatted", endDateTimeFormatted);
		 
	
		
				 
				 //List<TestDTO> dispositionwithTimeFilterReportDTOs = new ArrayList<>();
				 //dispositionwithTimeFilterReportDTOs = dispositionwithAgentNameReportDTOs.stream()
				//		 .filter(e->Integer.parseInt(e.getCallStartTime().substring(11,12))>=Integer.parseInt(startTimeHour) 
				//		 && Integer.parseInt(e.getCallEndTime().substring(11,12))<=Integer.parseInt(endTimeHour)).collect(Collectors.toList());
				 //List<TestDTO> dispositionwithFilteredTimeReportDTOs = new ArrayList<>();
			 
			 if(CommonUtil.isListNotNullAndEmpty(dispositionReportDTOs)) {			 
				 
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 //model.put("reports", dispositionReportDTOs);
				 model.put("reports", dispositionReportDTOs);
				 //model.put("reports", dispositionwithTimeFilterReportDTOs);
				 return new ResponseEntity(model, HttpStatus.OK);       
			 }
			 else {
				 model.put("responseCode", "400");
				 model.put("responseMessage", "No Results Found");
				 model.put("responseType", "Error");  
				 return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
			 }   
			
	}
	 @RequestMapping(value="/agentWiseCalls",method = RequestMethod.POST)
	 public ResponseEntity<?> agentWiseCalls(@RequestBody ReportDTO reportDTO)throws InstantiationException, IllegalAccessException{
		
		 String startDate1= CommonUtil.convertStringToDate(reportDTO.getStartDate());
		 String endtDate1= CommonUtil.convertStringToDate(reportDTO.getEndDate());

		 String startTimeHour= CommonUtil.convertStringToHour(reportDTO.getStartDate());
		 String startTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getStartDate());

		 String endTimeHour= CommonUtil.convertStringToHour(reportDTO.getEndDate());
		 String endTimeMinute= CommonUtil.convertStringToMinute(reportDTO.getEndDate());


		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		 LocalDateTime startDateTimeFormatted = LocalDateTime.parse(startDateTime, formatter);

		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		 LocalDateTime endDateTimeFormatted = LocalDateTime.parse(endDateTime, formatter);		

		 LocalDate startDate = startDateTimeFormatted.toLocalDate();
		 LocalDate endDate = endDateTimeFormatted.toLocalDate();
		 
			
		 List<AgentWiseDTO> dispositionReportDTOs = new ArrayList<>();	
		 Map<Object, Object> model = new HashMap<>();

		 List<Object> list = null;
		 Query query = null;
		 
		 List<AgentWiseDataDTO> subawd=new ArrayList<AgentWiseDataDTO>();
		 query =  queryCriteria.reportQueryForAgent();		 
		 query.setParameter("startDate", startDate);
		 query.setParameter("endDate", endDate);
		 list = query.getResultList();
		 subawd = queryCriteria.resultSetAgent(list);
		 Map<String,  List<AgentWiseDataDTO>> counting = subawd.stream().collect(
	                Collectors.groupingBy(AgentWiseDataDTO::getExtension, Collectors.toList()));
		 for (Entry<String, List<AgentWiseDataDTO>> entry : counting.entrySet())  {  
			 AgentWiseDTO agentWiseDTO=new AgentWiseDTO();
			 if(entry.getKey()!=null&&!entry.getKey().isEmpty()) {
				 UserModel um=new UserModel();
				 try {
		 um=userService.findByUserextension(entry.getKey());
				 }catch(Exception e) {
					um= userService.findFirstByUserextension(entry.getKey());
				 }
			 if(um.getUsername()!=null) {
				 agentWiseDTO.setAgentName(um.getUsername());
				 agentWiseDTO.setData(entry.getValue());
				 dispositionReportDTOs.add(agentWiseDTO);
			 }
			 }
		 }
		 
		 	 if(CommonUtil.isListNotNullAndEmpty(dispositionReportDTOs)) {			 
				 
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 //model.put("reports", dispositionReportDTOs);
				 model.put("reports", dispositionReportDTOs);
				 //model.put("reports", dispositionwithTimeFilterReportDTOs);
				 return new ResponseEntity(model, HttpStatus.OK);       
			 }
			 else {
				 model.put("responseCode", "400");
				 model.put("responseMessage", "No Results Found");
				 model.put("responseType", "Error");  
				 return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
			 } 
	 }
	 
	 
	 @RequestMapping(value="/saveWarRoom",method = RequestMethod.POST)
		public ResponseEntity<?> saveWarRoom(@RequestBody WarRoomDTO warRoomDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 WarRoomModel warRoomModel = new WarRoomModel();
		 String status = "";
		 String phonNo=warRoomDTO.getPhoneNo();
		 try {
			 phonNo= phonNo.substring(phonNo.length()-10,phonNo.length());
		 }catch(Exception e) {}
		 try {		 
		 warRoomModel.setCallerId(warRoomDTO.getCallerId());
		 warRoomModel.setPhoneNo(phonNo);
		 warRoomModel.setTypeOfQuery(warRoomDTO.getTypeOfQuery());
		 warRoomModel.setRemarks(warRoomDTO.getRemarks());
		 warRoomModel.setCallTime(LocalDateTime.now());
		 warRoomModel = warRoomRepository.save(warRoomModel);
		 //status = saveDispo(warRoomDTO);
		 }catch(Exception ex) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in inserting war room data");
		 }	
		 if(status.equalsIgnoreCase("Success")) {
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");
			 model.put("data", warRoomModel);		
			 return new ResponseEntity(model, HttpStatus.OK);  
		 }
		 else {
			 model.put("responseCode", "500");
			 model.put("responseMessage", "Error in savind Dispo");
			 model.put("responseType", "Error");				
			 return new ResponseEntity(model, HttpStatus.INTERNAL_SERVER_ERROR);  
		 }
	 }
	 
	 //@Cacheable(value="cacheWarRoomInfo")  
	 @RequestMapping(value="/getWarRoomReport",method = RequestMethod.GET)
		//public ResponseEntity<?> getWarRoomReport(@RequestParam(name = "startDate") String startDate,
		//										@RequestParam(name = "endDate") String endDate)throws InstantiationException, IllegalAccessException{
	 public List<WarRoomReportDTO> getWarRoomReport(@RequestParam(name = "startDate") String startDate,
									@RequestParam(name = "endDate") String endDate)throws InstantiationException, IllegalAccessException{
		 
		 WarRoomReportResponse warRoomReportResponse = new WarRoomReportResponse();
		 String startDate1= CommonUtil.convertStringToDate(startDate);
		 String endtDate1= CommonUtil.convertStringToDate(endDate);
		
		 String startTimeHour= CommonUtil.convertStringToHour(startDate);
		 String startTimeMinute= CommonUtil.convertStringToMinute(startDate);
		 
		 String endTimeHour= CommonUtil.convertStringToHour(endDate);
		 String endTimeMinute= CommonUtil.convertStringToMinute(endDate);
		 	 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 		 
		 String startDateTime = startDate1+" "+startTimeHour+":"+startTimeMinute;	
		 String endDateTime = endtDate1+" "+endTimeHour+":"+endTimeMinute;		
		// String startDateTime1 = startDate1+" "+startTimeHour+":"+startTimeMinute+":"+"00";	
		// String endDateTime1 = endtDate1+" "+endTimeHour+":"+endTimeMinute+":"+"00";		
		 
		 
		 System.out.println(startDate1);
		 System.out.println(endtDate1);
		 
		 Map<Object, Object> model = new HashMap<>(); 
		 List<WarRoomModel> warRoomList = new ArrayList<>();	
		 List<WarRoomReportDTO> WarRoomReportList = new ArrayList<>();
		// DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// LocalDateTime joiningDate = LocalDateTime.parse(startDateTime1, formatter1);
		//  LocalDateTime zeroHour = LocalDateTime.parse(endDateTime1, formatter1);

		 try {	
			 //warRoomList = warRoomRepository.findByCallTimeBetween(startDateTime,endDateTime );
		 warRoomList = warRoomRepository.findByCallTimeBetween(startDate1,endtDate1 );
			 
			// warRoomList = warRoomRepository.findByCallTimeBetween1(joiningDate,zeroHour );
			 System.out.println("warRoomList :: "+warRoomList.size());
			 if(!warRoomList.isEmpty()) {
				
				 for(WarRoomModel warRoom:warRoomList) {
					try {					
					WarRoomReportDTO warRoomReport = new WarRoomReportDTO();
					ReportsModel reportsModel = new ReportsModel();	
					CSECEModel cseCEModel = new CSECEModel();
					
					warRoomReport.setRemarks(warRoom.getRemarks()); 
					warRoomReport.setTypeOfQuery(warRoom.getTypeOfQuery());	
					warRoomReport.setAgentName("");	
					warRoomReport.setDuration(0);
					
					cseCEModel = cseCeRepository.findByPhoneNumber(parsePhoneNumber(warRoom.getPhoneNo()));
					if(null != cseCEModel) {
					warRoomReport.setBranch(cseCEModel.getBranch());
					warRoomReport.setPhoneNo(warRoom.getPhoneNo());
					warRoomReport.setEmployeeName(cseCEModel.getEmployeeName());
					warRoomReport.setEmpCode(cseCEModel.getEmpId());
					}
					
					reportsModel = reportsRepository.findByDocId(warRoom.getCallerId());
					if(null != reportsModel) {
					warRoomReport.setCallDirection(reportsModel.getCallDirection());
					warRoomReport.setCallStatus(reportsModel.getCallStatus());
					warRoomReport.setCampaignName(reportsModel.getCamName());
					warRoomReport.setEndDate(reportsModel.getCallEndTime().toString());
					warRoomReport.setExtension(reportsModel.getExtension());
					warRoomReport.setHoldEndTime(reportsModel.getHoldEndTime());
					warRoomReport.setHoldStartTime(reportsModel.getHoldStartTime());
					warRoomReport.setRecPath(reportsModel.getRecPath());					
					warRoomReport.setStartDate(reportsModel.getCallStartTime().toString());
					warRoomReport.setCallDroped(reportsModel.getCallDroped()==null?"":reportsModel.getCallDroped());
					}
					
					if(reportsModel.getExtension()!=null) {
						LoginModel loginModel = new LoginModel();
						loginModel = loginRepository.findFirstByExtensionOrderByIdDesc(reportsModel.getExtension());
						if(loginModel != null) warRoomReport.setAgentName(loginModel.getAgentName());
					}
					
					WarRoomReportList.add(warRoomReport);
					}catch(Exception e) {}
				}
			 }
		 }catch(Exception ex) {
			 ex.printStackTrace();
			// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in retrieving war room data");
		 }	
		 if(false != WarRoomReportList.isEmpty()) {
			 
		 warRoomReportResponse.setData(WarRoomReportList);
		 warRoomReportResponse.setResponseCode("200");
		 warRoomReportResponse.setResponseType("Success");
		 warRoomReportResponse.setResponseMessage("Success");		 
		 return WarRoomReportList;
		 }
		 else {			
			 warRoomReportResponse.setResponseCode("500");
			 warRoomReportResponse.setResponseType("Error");
			 warRoomReportResponse.setResponseMessage("Error in retrieving Data");		 
			 return WarRoomReportList;
		 }
	 }
	 
	 @RequestMapping(value="/getYamlDispoReport",method = RequestMethod.GET)	
	 public List<YamlDataReportDTO> getYamlDispoReport(@RequestParam(name = "startDate") String startDate,
			 @RequestParam(name = "endDate") String endDate)throws InstantiationException, IllegalAccessException{		 

		 String startDate1= CommonUtil.convertStringToDate(startDate);
		 String endtDate1= CommonUtil.convertStringToDate(endDate);

		 String startTimeHour= CommonUtil.convertStringToHour(startDate);
		 String startTimeMinute= CommonUtil.convertStringToMinute(startDate);
		 String startTimeSeconds= CommonUtil.convertStringToSeconds(startDate);
		 
		 String endTimeHour= CommonUtil.convertStringToHour(endDate);
		 String endTimeMinute= CommonUtil.convertStringToMinute(endDate);
		 String endTimeSeconds= CommonUtil.convertStringToSeconds(endDate);
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 		 
		 String startDateTime = startDate1+"T"+startTimeHour+":"+startTimeMinute+":"+startTimeSeconds;	
		 String endDateTime = endtDate1+"T"+endTimeHour+":"+endTimeMinute+":"+endTimeSeconds;		
		
		 
		 System.out.println("startDateTime :"+startDateTime);
		 System.out.println("endDateTime :"+endDateTime);
		 
		 Map<Object, Object> model = new HashMap<>(); 
		 List<YamlDataReportModel> yamlDataReportModel = new ArrayList<>();	
		 ReportsModel reportsModel = new ReportsModel();	
		 List<YamlDataReportDTO> yamlDataReportList = new ArrayList<>();

		 try {
		
			 Query result2 =  entityManager.createNativeQuery("SELECT  phone_no,call_id,remarks, call_time,campaign, client,main_dispo,sub_dispo,"
			 		+ "sub_sub_dispo,customer_name,district,state,country FROM yaml_data_report "
					 + "WHERE call_time >=:startDateTime and call_time <=:endDateTime ORDER BY call_time DESC");		
		
			result2.setParameter("startDateTime", startDateTime);
			result2.setParameter("endDateTime", endDateTime);
			
			List<Object> resultList = null;			
			resultList = result2.getResultList();
			System.out.println("Size : "+resultList.size());
			if (CommonUtil.isListNotNullAndEmpty(resultList)) {
				Iterator it = resultList.iterator();
				while (it.hasNext()){
					Object[] objs = ( Object[]) it.next();
					 YamlDataReportDTO yamlDataReportDTO = new YamlDataReportDTO();
					 yamlDataReportDTO.setPhoneNo(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);
					 yamlDataReportDTO.setCallId(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
					 yamlDataReportDTO.setCallTime(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
					 yamlDataReportDTO.setCampaign(CommonUtil.isNull(objs[4])?" ":(String)objs[4]);
					 yamlDataReportDTO.setClient(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);
					 yamlDataReportDTO.setMainDispo(CommonUtil.isNull(objs[6])?" ":(String)objs[6]);					
					 yamlDataReportDTO.setSubDispo(CommonUtil.isNull(objs[7])?" ":(String)objs[7]);
					 yamlDataReportDTO.setSubSubDispo(CommonUtil.isNull(objs[8])?" ":(String)objs[8]);
					 yamlDataReportDTO.setRemarks(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);						 
					 yamlDataReportDTO.setCustomerName(CommonUtil.isNull(objs[9])?" ":(String)objs[9]);	
					 yamlDataReportDTO.setDistrict(CommonUtil.isNull(objs[10])?" ":(String)objs[10]);
					 yamlDataReportDTO.setState(CommonUtil.isNull(objs[11])?" ":(String)objs[11]);
					 yamlDataReportDTO.setCountry(CommonUtil.isNull(objs[12])?" ":(String)objs[12]);
					 reportsModel = reportsRepository.findByDocId(yamlDataReportDTO.getCallId().replaceAll("^[ \t]+|[ \t]+$", ""));					
					 if(null != reportsModel) {
						 yamlDataReportDTO.setCallDirection(reportsModel.getCallDirection());
						 yamlDataReportDTO.setCallStatus(reportsModel.getCallStatus());

						 yamlDataReportDTO.setExtension(reportsModel.getExtension());
						 yamlDataReportDTO.setHoldEndTime(reportsModel.getHoldEndTime());
						 yamlDataReportDTO.setCallStartTime(reportsModel.getCallStartTime());
						 yamlDataReportDTO.setCallEndTime(reportsModel.getCallEndTime());
						 if(reportsModel.getCallStartTime()!=null && reportsModel.getCallEndTime()!=null) {
							 Duration duration = Duration.between(reportsModel.getCallStartTime(), reportsModel.getCallEndTime());						 
							 yamlDataReportDTO.setDuration(String.valueOf(duration.getSeconds()));
						 }
						 yamlDataReportDTO.setHoldStartTime(reportsModel.getHoldStartTime());
						 yamlDataReportDTO.setRecPath(reportsModel.getRecPath());					
						 yamlDataReportDTO.setCallDroped(reportsModel.getCallDroped()==null?"":reportsModel.getCallDroped());							

						 if(reportsModel.getExtension()!=null) {
							 LoginModel loginModel = new LoginModel();
							 loginModel = loginRepository.findFirstByExtensionOrderByIdDesc(reportsModel.getExtension());
							 if(loginModel != null) yamlDataReportDTO.setAgentName(loginModel.getAgentName());
						 }
					 }
						
						yamlDataReportList.add(yamlDataReportDTO);	
				 }
				}
		 }catch(Exception ex) {
			 ex.printStackTrace();		
		 }			
			 return yamlDataReportList;		
	 }
	 
	private String saveDispo(WarRoomDTO warRoomDTO) {	
		try {
			CallPropertiesModel callPropertiesModel=CallService.findById(warRoomDTO.getCallerId());
			ReportsModel reportsModelCheck = new ReportsModel();	
			reportsModelCheck = reportsRepository.findByDocId(warRoomDTO.getCallerId());
			if(null != null) reportsRepository.delete(reportsModelCheck);
			if(callPropertiesModel!=null) {
				ReportsModel reportsModel = new ReportsModel();	
				reportsModel.setCallConnectTime(callPropertiesModel.getCallConnectTime());					
				reportsModel.setCallDirection(callPropertiesModel.getCallDirection());		    		
				reportsModel.setCallStartTime(callPropertiesModel.getCallStartTime());		    		
				if(callPropertiesModel.getCallEndTime()!=null) {
					reportsModel.setCallEndTime(callPropertiesModel.getCallEndTime());
				}else {
					ZoneId zid = ZoneId.of("Asia/Kolkata");		    		
					LocalDateTime now = LocalDateTime.now(zid);
					reportsModel.setCallEndTime(now);
					callPropertiesModel.setCallEndTime(now);
				}
				if(callPropertiesModel.getCallDirection().equalsIgnoreCase("inbound")&&callPropertiesModel.getCallStatus().equalsIgnoreCase("Dial")) {
					reportsModel.setCallStatus("ANSWER");
				}else {
					reportsModel.setCallStatus(callPropertiesModel.getCallStatus());	
				}
				reportsModel.setDocId(callPropertiesModel.getId());
				reportsModel.setExtension(callPropertiesModel.getExtension());		    		
				reportsModel.setHoldEndTime(callPropertiesModel.getHoldEndTime());		    		
				reportsModel.setHoldStartTime(callPropertiesModel.getHoldStartTime());		    		
				reportsModel.setIsClosed(callPropertiesModel.getIsClosed());
				reportsModel.setPhoneNo(callPropertiesModel.getPhoneNo());
				reportsModel.setSecondChannel(callPropertiesModel.getSecondChannel());
				reportsModel.setSecondNumber(callPropertiesModel.getSecondNumber());
				reportsModel.setSipChannel(callPropertiesModel.getSipChannel());
				reportsModel.setTrunkChannel(callPropertiesModel.getTrunkChannel());
				reportsModel.setComments("");
				reportsModel.setDisposition("");
				reportsModel.setCamId(callPropertiesModel.getCamId());
				reportsModel.setCamName(callPropertiesModel.getCamName());		    			
				reportsDAO.save(reportsModel);
				CallService.deleteById(warRoomDTO.getCallerId());

				List<LoginModel> loginModel=new ArrayList<>();
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
					}
				}
				try {
					if(!callPropertiesModel.getPopupStatus().equalsIgnoreCase("hangup")) {
						OriginateDTO originateDTO=new OriginateDTO();
						originateDTO.setChannel(callPropertiesModel.getSipChannel());
						ManagerResponse HangupResponse = actionhandler.hangUpCall(originateDTO);		
					}
				}catch(Exception e) {}

				CallService.deleteById(callPropertiesModel.getId());
				return "Success"; 
			}
		}catch(Exception e) {
			return "Error";
		}	
		return null;
	}
	
private String parsePhoneNumber(String phoneNumber) {
		
		String lastTenDigits = "";    		 
		if (phoneNumber.length() > 10) {
			lastTenDigits = phoneNumber.substring(phoneNumber.length() - 10);
		} 
		else {
			lastTenDigits = phoneNumber;
		}
		
		return lastTenDigits;
	}
}
					
