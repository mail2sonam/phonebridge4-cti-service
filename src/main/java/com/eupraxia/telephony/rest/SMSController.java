package com.eupraxia.telephony.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.CallDetailDTO;
import com.eupraxia.telephony.DTO.EmergencyScreenDTO;
import com.eupraxia.telephony.DTO.GuidenceScreenDTO;
import com.eupraxia.telephony.DTO.InformationScreenDTO;
import com.eupraxia.telephony.DTO.MailParserDTO;
import com.eupraxia.telephony.DTO.SmsDTO;
import com.eupraxia.telephony.Model.CaseModel;
import com.eupraxia.telephony.Model.MailModel;
import com.eupraxia.telephony.Model.MailStoreModel;
import com.eupraxia.telephony.Model.SmsModel;
import com.eupraxia.telephony.Model.Disposition.CallDetailModel;
import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;
import com.eupraxia.telephony.Model.Disposition.GuidenceScreenModel;
import com.eupraxia.telephony.Model.Disposition.InformationScreenModel;
import com.eupraxia.telephony.repositories.MailRepository;
import com.eupraxia.telephony.service.CallDetailService;
import com.eupraxia.telephony.service.CaseService;
import com.eupraxia.telephony.service.EmergencyScreenService;
import com.eupraxia.telephony.service.GuidenceScreenService;
import com.eupraxia.telephony.service.InformationScreenService;
import com.eupraxia.telephony.service.MailStoreService;
import com.eupraxia.telephony.service.SmsService;
import com.eupraxia.telephony.util.CommonUtil;


import org.json.simple.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

@CrossOrigin
@RestController
@RequestMapping("/sms")
public class SMSController {
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	private ModelMapper modelMapper;	
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private EmergencyScreenService emergencyScreenService;
	
	@Autowired
	private GuidenceScreenService guidenceScreenService;
	
	@Autowired
	private InformationScreenService informationScreenService;
	
	@Autowired
	private CallDetailService callDetailService;
	
	 @RequestMapping(value="/receiveSMS",method = RequestMethod.GET)
	 public ResponseEntity<?> receiveSMS()throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 		 
		 try {	

			 String apiKey = "apikey=" + URLEncoder.encode("7ank/UwWQ4A-2kMKdE8D1PHQ7SeOCYAONxLYulRJWC", "UTF-8");
			 String inbox_id = "&inbox_id=" + URLEncoder.encode("1165839", "UTF-8");

			 // Send data
			 String data = "https://api.textlocal.in/get_messages/?" + apiKey + inbox_id;
			 URL url = new URL(data);
			 URLConnection conn = url.openConnection();
			 conn.setDoOutput(true);
			 OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			 wr.write(data);
			 wr.flush();
			 // Get the response
			 BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			 String line;
			 String sResult="";
			 while ((line = rd.readLine()) != null) {
				 // Process line...
				 sResult=sResult+line+" ";
			 }
			 rd.close();	
			 try {
				 JSONParser parser = new JSONParser();
				 Object obj = parser.parse(sResult);
				 JSONObject array = (JSONObject)obj;
				 JSONArray array1 = (JSONArray)  array.get("messages");
				 for(int i=0;i<array1.size();i++) {						
					 JSONObject jsonObject=(JSONObject) array1.get(i);
					 //System.out.println("jsonObject :"+jsonObject.get(i));					 
					 //if((boolean)jsonObject.get("isNew")) {
						 SmsModel smsModel=new SmsModel();
						 smsModel.setDate((String) jsonObject.get("date"));
						 smsModel.setCaseId(generateCaseId());
						 System.out.println(smsModel.getCaseId());
						 smsModel.setEnteredDate(new Date());
						 smsModel.setIsNew((boolean)jsonObject.get("isNew"));
						 smsModel.setMessage((String) jsonObject.get("message"));
						 smsModel.setMessageId((String) jsonObject.get("id"));
						 smsModel.setNumber((Long) jsonObject.get("number"));
						 //smsModel.setStatus((String) jsonObject.get("status"));
						 smsModel.setStatus("UNSEEN");
						 SmsModel smsModel1=new SmsModel();
						 smsModel1 = smsService.findByMessageId(smsModel.getMessageId());
						 if(CommonUtil.isNull(smsModel1))
						 smsService.saveOrUpdate(smsModel);
					 //}
				 }
			 }catch(Exception e) {
				 System.out.println(e);
			 }
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("message", sResult);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } catch (Exception e) {
			 System.out.println("Error SMS "+e);
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Error");
			 model.put("responseType", "Error");  
			 model.put("message", e);
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
	 
	 @RequestMapping(value="/showAll",method = RequestMethod.GET)
	 public ResponseEntity<?> showAll()throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 List<SmsModel>  smsModels=new ArrayList<>();
		 smsModels= smsService.findAll();
		 if(CommonUtil.isListNotNullAndEmpty(smsModels)) {
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("model", smsModels);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } else {
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Error");
			 model.put("responseType", "Error");  
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
	 
	 
	 @RequestMapping(value="/showMessagesByUser",method = RequestMethod.POST)
	 public ResponseEntity<?> showMessagesByUser(@RequestBody SmsDTO smsDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 List<SmsModel>  smsModels=new ArrayList<>();
		 List<SmsModel>  smsModels1=new ArrayList<>();
		 List<SmsModel>  smsDataModels=new ArrayList<>();
		 smsModels= smsService.findByUserExtension(smsDTO.getUserExtension());
		 if(CommonUtil.isListNotNullAndEmpty(smsModels)) {			 
			 smsDataModels = smsModels.stream().filter(e->e.getStatus().contentEquals("ASSIGNED")).collect(Collectors.toList());			
		 }
		 if(CommonUtil.isListNotNullAndEmpty(smsDataModels)) {	
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("model", smsDataModels);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } else {
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 SmsModel smsModel = new SmsModel("", "", "",(long) 0, "", "", false, "", new Date(), "");	
			 smsModels1.add(smsModel);
			 model.put("model",smsModels1);
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
	 
	 @RequestMapping(value="/findByNumber",method = RequestMethod.POST)
	 public ResponseEntity<?> findByNumber(@RequestBody SmsDTO smsDTO)throws InstantiationException, IllegalAccessException{			
		 Map<Object, Object> model = new HashMap<>(); 
		 List<SmsModel>  smsModels=new ArrayList<>();
		 smsModels= smsService.findByNumber(smsDTO.getNumber());
		 if(CommonUtil.isListNotNullAndEmpty(smsModels)) {
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("model", smsModels);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } else {
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Error");
			 model.put("responseType", "Error");  
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
	 
	 @RequestMapping(value="/assigntSMStoUser",method = RequestMethod.POST)
	 public ResponseEntity<?> assigntSMStoUser(@RequestBody SmsDTO smsDTO)throws InstantiationException, IllegalAccessException{			
		 Map<Object, Object> model = new HashMap<>(); 
		 Optional<SmsModel>  smsModel ;
		 SmsModel smsModel1 = new SmsModel();
		 smsModel = smsService.findById(smsDTO.getId());
		 if(!CommonUtil.isNull(smsModel)) {
			smsModel1.setDate(smsModel.get().getDate());
			smsModel1.setEnteredDate(smsModel.get().getEnteredDate());
			smsModel1.setCaseId(smsModel.get().getCaseId());
			smsModel1.setId(smsModel.get().getId());
			smsModel1.setIsNew(smsModel.get().isNew());
			smsModel1.setMessage(smsModel.get().getMessage());
			smsModel1.setMessageId(smsModel.get().getMessageId());
			smsModel1.setNumber(smsModel.get().getNumber());
			//smsModel1.setStatus(smsModel.get().getStatus());
			smsModel1.setStatus("ASSIGNED");
			smsModel1.setUserExtension(smsDTO.getUserExtension());
			smsService.saveOrUpdate(smsModel1);
			 model.put("respon.seCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("model", smsModel1);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } else {
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Error");
			 model.put("responseType", "Error");  
			 model.put("model", new SmsModel("", "", "",(long) 0, "", "", false, "", new Date(), ""));
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
	 
	 @RequestMapping(value="/findByIsNew",method = RequestMethod.POST)
	 public ResponseEntity<?> findByIsNew(@RequestBody SmsDTO smsDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 List<SmsModel>  smsModels=new ArrayList<>();
		 List<SmsModel>  smsModelList=new ArrayList<>();
		 smsModels= smsService.findByIsNew(smsDTO.isNew());
		 if(CommonUtil.isListNotNullAndEmpty(smsModels)) {
		 smsModelList = smsModels.stream().filter(e->e.getUserExtension()==null).collect(Collectors.toList());		
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("model", smsModelList);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } else {
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Error");
			 model.put("responseType", "Error");  
			 SmsModel smsModel = new SmsModel("", "", "",(long) 0, "", "", false, "", new Date(), "");
			 smsModelList.add(smsModel);
			 model.put("model",smsModelList);
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
	 
	 @RequestMapping(value="/getCountOfUnseenSMS",method = RequestMethod.GET)
		public ResponseEntity<?> getCountOfUnseenMails()throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			List<SmsModel> smsData = new ArrayList<>();
			List<SmsModel> smsDataList = new ArrayList<>();
			//smsData = smsService.findByIsNew(true);
			smsData = smsService.findByStatus("UNSEEN");
			if(CommonUtil.isListNotNullAndEmpty(smsData)) {				
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 	
				model.put("SMSCount", smsData.size());
				return new ResponseEntity(model, HttpStatus.OK); 
			}
			else {
				model.put("responseCode", "400");
				model.put("responseMessage", "No UnSeen Mails Found");
				model.put("responseType", "Success"); 	
				SmsModel smsModel = new SmsModel("", "","", (long) 0, "", "", false, "", new Date(), "");
				smsDataList.add(smsModel);
				model.put("model",smsDataList);
				return new ResponseEntity(model, HttpStatus.OK); 

			}
		}
	 
	 @RequestMapping(value="/showSmsByCaseId",method = RequestMethod.POST)
	 public ResponseEntity<?> showSmsByCaseId(@RequestBody SmsDTO smsDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 SmsModel  smsModel = new SmsModel();
		 SmsDTO smsDTO1 = new SmsDTO();
		 smsModel= smsService.findByCaseId(smsDTO.getCaseId());
		
		
		 if(!CommonUtil.isNull(smsModel)) {
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("model", smsModel);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } else {
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Error");
			 model.put("responseType", "Error");  
			 SmsModel smsModel1 = new SmsModel("", "","", (long) 0, "", "", false, "", new Date(), "");			 
			 model.put("model",smsModel1);
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
		
	 @RequestMapping(value="/findByStatus",method = RequestMethod.POST)
	 public ResponseEntity<?> findByStatus(@RequestBody SmsDTO smsDTO)throws InstantiationException, IllegalAccessException{			
		 Map<Object, Object> model = new HashMap<>(); 
		 List<SmsModel>  smsModels=new ArrayList<>();
		 List<SmsModel>  smsDataList=new ArrayList<>();
		 List<SmsModel>  smsDataListSorted=new ArrayList<>();
		 smsModels= smsService.findByStatus(smsDTO.getStatus());
		 if(CommonUtil.isListNotNullAndEmpty(smsModels)) {
			 smsDataList = smsModels.stream().filter(e->e.getUserExtension()==null).collect(Collectors.toList());
			 smsDataListSorted = smsDataList.stream().sorted(Comparator.comparing(SmsModel::getDate)).collect(Collectors.toList());
			 model.put("responseCode", "200");
			 model.put("responseMessage", "Success");
			 model.put("responseType", "Success");  
			 model.put("model", smsDataListSorted);
			 return new ResponseEntity(model, HttpStatus.OK);
		 } else {
			 model.put("responseCode", "400");
			 model.put("responseMessage", "Error");
			 model.put("responseType", "Error");  
			 return new ResponseEntity(model, HttpStatus.OK);   
		 }
	 }
	 
	 @RequestMapping(value="/addEmergencyScreen",method = RequestMethod.POST)
	    public ResponseEntity<?> addEmergencyScreen(@RequestBody EmergencyScreenDTO emergencyScreenDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 EmergencyScreenModel emergencyScreenModel=emergencyScreenService.findByCaseId(emergencyScreenDTO.getCaseId());
		 if(emergencyScreenModel==null) {
			 EmergencyScreenModel emergencyScreenModel1=new EmergencyScreenModel();
			 emergencyScreenModel1 = modelMapper.map(emergencyScreenDTO, EmergencyScreenModel.class);
			 emergencyScreenService.saveOrUpdate(emergencyScreenModel1);
		 }
		 else {
			 emergencyScreenDTO.setId(emergencyScreenModel.getId());
			 emergencyScreenModel = modelMapper.map(emergencyScreenDTO, EmergencyScreenModel.class);
			 emergencyScreenService.saveOrUpdate(emergencyScreenModel);
		 }
		  
		 model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			return new ResponseEntity(model, HttpStatus.OK);   
	 }


		
		@RequestMapping(value="/addGuidenceScreen",method = RequestMethod.POST)
	    public ResponseEntity<?> addGuidenceScreen(@RequestBody GuidenceScreenDTO guidenceScreenDTO)throws InstantiationException, IllegalAccessException{
		 Map<Object, Object> model = new HashMap<>(); 
		 GuidenceScreenModel guidenceScreenModel=guidenceScreenService.findByCaseId(guidenceScreenDTO.getCaseId());
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
			InformationScreenModel informationScreenModel=informationScreenService.findByCaseId(informationScreenDTO.getCaseId());
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
			 CallDetailModel callDetailModel=callDetailService.findByCaseId(callDetailDTO.getCaseId());
		
			
			 if(callDetailModel==null) {
				 CallDetailModel callDetailModel1=new CallDetailModel();
				 callDetailModel1 = modelMapper.map(callDetailDTO, CallDetailModel.class);				 
				 callDetailService.saveOrUpdate(callDetailModel1);
				 SmsModel  smsModel = new SmsModel();				
				 smsModel= smsService.findByCaseId(callDetailDTO.getCaseId());
				 smsModel.setStatus("REPLIED");
				 smsService.saveOrUpdate(smsModel);
				 //smsService.deleteByCaseId(callDetailDTO.getCaseId());
			 }
			 
			 else {
				 callDetailDTO.setId(callDetailModel.getId());
				 callDetailModel = modelMapper.map(callDetailDTO, CallDetailModel.class);
				 callDetailService.saveOrUpdate(callDetailModel);
				 SmsModel  smsModel = new SmsModel();		
				 smsModel= smsService.findByCaseId(callDetailDTO.getCaseId());
				 smsModel.setStatus("REPLIED");
				 smsService.saveOrUpdate(smsModel);
				 //smsService.deleteByCaseId(callDetailDTO.getCaseId());
			 }
			 
			
			 model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success");  
				return new ResponseEntity(model, HttpStatus.OK);   
		 }	 

		 @RequestMapping(value="/sendReplySms",method = RequestMethod.POST)
		  public ResponseEntity<?> sendReplySms(@RequestBody SmsDTO smsDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
		    Map<Object, Object> model = new HashMap<>(); 
		    
		    String smsContent = "";
		    if(smsDTO.getMessage().length()<150) {
		    String message = StringUtils.rightPad(smsDTO.getMessage(), 150, " ");
		    String caseId = StringUtils.rightPad(smsDTO.getCaseId(), 30, " ");	
		    String text1 = message.substring(0, 25);
		    String text2 = message.substring(25,50);
		    String text3 = message.substring(51,69);
		    String text4 = message.substring(70,88);
		    
		    smsContent = "CaseID: "+ caseId.substring(0, 25)
					+"\n "+text1+" "+text2+" "+text3
					+"\n "+text4+" -Women HelpLine";
		    }
		    sendSMS(smsDTO.getNumber(),smsContent);
		    model.put("responseCode", "200");
			model.put("responseMessage", "Success");
			model.put("responseType", "Success");  
			model.put("smsContent", smsContent);  
		    return new ResponseEntity(model, HttpStatus.OK);   
		 }
		   
	 
	 private String generateCaseId() {
			
		 String caseId="TN/WHL/SMS";		
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
					return caseId;
	 }
	 
	 private String sendSMS(long phoneNo, String content) {
		 String sResult="";
		 try {
			 System.out.println(content);
			 // Construct data
			 String apiKey = "apikey=" + URLEncoder.encode("7ank/UwWQ4A-2kMKdE8D1PHQ7SeOCYAONxLYulRJWC", "UTF-8");
				String message = "&message=" + URLEncoder.encode(content, "UTF-8");
				String sender = "&sender=" + URLEncoder.encode("SWDWHL", "UTF-8");
				String numbers = "&numbers=" + URLEncoder.encode(String.valueOf(phoneNo), "UTF-8");
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
					//System.out.println("line :"+line);
					//System.out.println("line :"+line);
					//System.out.println("line :"+line);
					sResult=sResult+line+" ";
				}
				rd.close();
				
		 } catch (Exception e) {
			 System.out.println("Error SMS "+e);
		 }
		 return sResult;
	 }

}
