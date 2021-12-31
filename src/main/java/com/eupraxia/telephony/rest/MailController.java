package com.eupraxia.telephony.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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


@CrossOrigin
@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	 private ModelMapper modelMapper;	
	
	@Autowired
	MailStoreService mailStoreService;
	
	@Autowired
	MailRepository mailRepository;
	
	@Autowired
	JavaMailSender mailSender;
	
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
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	private Environment env;
	
	 @RequestMapping(value="/checkOutInbox",method = RequestMethod.GET)
	 public ResponseEntity<?> checkInbox()throws InstantiationException, IllegalAccessException, MessagingException, IOException, GeneralSecurityException{
		 
		 Map<Object, Object> model = new HashMap<>(); 
		
		 Properties props = new Properties();
		 //props.put("mail.store.protocol", "imaps"); 
		 //props.put("mail.imaps.ssl.trust", "*");
		 //MailSSLSocketFactory socketFactory= new MailSSLSocketFactory();
	     //socketFactory.setTrustAllHosts(true);
	     //props.put("mail.pop3.ssl.socketFactory", socketFactory);
	        
		
		 props.setProperty("mail.pop3.ssl.enable", "true"); 
		 props.setProperty("mail.pop3.starttls.enable", "true");
		 props.setProperty("mail.pop3.ssl.trust", "*");	
		 //Session session = Session.getDefaultInstance(new Properties( ));	
		 Session session = Session.getDefaultInstance(props);
		 //Store store = session.getStore("imaps");
		 //store.connect("imap.secureserver.net", 993, "prabhakaran.v@eupraxiatelecom.com", "Welcome1!#$");
		 Store store = session.getStore("pop3");		
		 store.connect("sify-bnglr.tn181whl.org", 995, "whl", "Amtex@123#");
		 Folder inbox = store.getFolder( "INBOX" );
		 inbox.open( Folder.READ_WRITE  );

		 // Fetch unseen messages from inbox folder
		 Message[] messages = inbox.search(
				 new FlagTerm(new Flags(Flags.Flag.SEEN), false));

		 // Sort messages from recent to oldest
		 /*
		 Arrays.sort( messages, ( m1, m2 ) -> {
			 try {
				 return m2.getSentDate().compareTo( m1.getSentDate() );
			 } catch ( MessagingException e ) {
				 throw new RuntimeException( e );
			 }
		 } );
		 */

		 for ( Message message : messages ) {
			 MailParserDTO mailParserDTO = new MailParserDTO();
			 MailStoreModel mailStoreModel = new MailStoreModel();
			 mailParserDTO.setReceivedDate(message.getSentDate());
			 mailParserDTO.setSentDate(CommonUtil.isNull(message.getSentDate())?" ":message.getSentDate().toString());
			 mailParserDTO.setSubject(CommonUtil.isNull(message.getSubject())?" ":message.getSubject());
			 
			 mailParserDTO.setStatus("UNSEEN");
			 mailParserDTO.setCaseId(generateCaseId());
			 mailParserDTO.setUserExtension(null);
			 try {
				 Address[] add=message.getFrom();
			String frm="";
			for(int i=0;i<add.length;i++) {
				Address address=add[i];
				frm=frm.concat(address.toString()).concat(",");
			}
			 mailParserDTO.setFrom(frm);
			
			 }catch(Exception e) {}
			 mailParserDTO.setEnteredDate(new Date());
			 
	
			 if (message.isMimeType("text/plain")) {
				 mailParserDTO.setBody(CommonUtil.isNull(message.getContent())?" ":message.getContent().toString());
				 System.out.println("text/plain :"+message.getContent().toString());
			 } 
			 else if (message.isMimeType("text/html")) { 
				 String result="";
				 String html = (String) message.getContent();
		            result = org.jsoup.Jsoup.parse(html).text();
		            mailParserDTO.setBody(result);
				 
			 }
			 else if (message.isMimeType("multipart/*")) {
				 MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();				 
				 ByteArrayOutputStream result = new ByteArrayOutputStream();
				 byte[] buffer = new byte[1024];
				 int length;
				 //Code for parsing attachement
				 for(int k = 0; k < mimeMultipart.getCount(); k++){
				       BodyPart bodyPart = mimeMultipart.getBodyPart(k);  
				       if (bodyPart.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
				       mailParserDTO.setAttachedFileName(bodyPart.getFileName());
				       InputStream stream = 
			                             (InputStream) bodyPart.getInputStream();  
				       while ((length = stream.read(buffer)) != -1) {
				    	    result.write(buffer, 0, length);
				    	}
				       byte[]  attachedData = result.toByteArray();
				       mailParserDTO.setAttachedFileData(attachedData);
				       //BufferedReader bufferedReader = 
				      //	   new BufferedReader(new InputStreamReader(stream));  
				       
				       /*
				        while (bufferedReader.ready()) {  
				    	       System.out.println(bufferedReader.readLine());  
				    	}  
				    	*/
				    	   //System.out.println(mailParserDTO.getAttachedFileData());  
				       }
				      }  
				 
				 mailParserDTO.setBody(getTextFromMimeMultipart(mimeMultipart));
				
			 } 		    	

			 mailStoreModel = modelMapper.map(mailParserDTO, MailStoreModel.class);
			 MailStoreModel mailStoreModel1 = new MailStoreModel();	
			 mailStoreModel1 = mailStoreService.findByFromAndSubjectAndSentDate(mailParserDTO.getFrom(), mailParserDTO.getSubject(), mailParserDTO.getSentDate());
			 if(CommonUtil.isNull(mailStoreModel1))
			 mailStoreService.saveOrUpdate(mailStoreModel);
			 //mailsData.add(mailParserDTO);
			 // System.out.println( 
			 //   "sendDate: " + message.getSentDate()
			 //   + " subject:" + message.getSubject() );
			 //System.out.println(getTextFromMessage(message));
			 message.setFlag(Flags.Flag.SEEN, true);
			 //To mark deleted in Mail Server
			 message.setFlag(Flags.Flag.DELETED, true);
		 }
		 boolean expunge = true;
		 //Delete Seen Mails from Server
		 inbox.close(expunge);
		 //inbox.close(false);
		 model.put("responseCode", "200");
		 model.put("responseMessage", "Success");
		 model.put("responseType", "Success"); 	
		// model.put("Result", mailsData);
		 return new ResponseEntity(model, HttpStatus.OK);   
	 }
	 
	 private String getTextFromMessage(Message message) throws MessagingException, IOException {
		    String result = "";
		    if (message.isMimeType("text/plain")) {		    
		        result = message.getContent().toString();
		    } else if (message.isMimeType("multipart/*")) {
		        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
		        result = getTextFromMimeMultipart(mimeMultipart);
		    }
		    return result;
		}

		private String getTextFromMimeMultipart(
		        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
		    String result = "";
		    int count = mimeMultipart.getCount();
		    for (int i = 0; i < count; i++) {
		        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
		        if (bodyPart.isMimeType("text/plain")) {
		            result = result + "\n" + bodyPart.getContent();
		            break; // without break same text appears twice in my tests
		        } else if (bodyPart.isMimeType("text/html")) {
		            String html = (String) bodyPart.getContent();
		            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
		            System.out.println(result);
		        } else if (bodyPart.getContent() instanceof MimeMultipart){
		            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
		            System.out.println("multipart : "+result);
		        }
		    }
		    
		    return result;
		}
		
		
		@RequestMapping(value="/getUnseenMails",method = RequestMethod.GET)
		public ResponseEntity<?> getUnseenMails()throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			List<MailStoreModel> mailsData = new ArrayList<>();
			List<MailStoreModel> mailsDataList = new ArrayList<>();			
			mailsData = mailStoreService.findAllByStatus("UNSEEN");	
		
			if(CommonUtil.isListNotNullAndEmpty(mailsData)) {		
				
				
				mailsDataList = mailsData.stream().filter(e->e.getReceivedDate()!=null)						
								.sorted(Comparator.comparing(MailStoreModel::getReceivedDate)).collect(Collectors.toList());				
				
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 	
				model.put("Result", mailsDataList);
				return new ResponseEntity(model, HttpStatus.OK); 
			}
			else {
				model.put("responseCode", "400");
				model.put("responseMessage", "No UnSeen Mails Found");
				model.put("responseType", "Error"); 	
				model.put("Result", new MailStoreModel());
				return new ResponseEntity(model, HttpStatus.OK); 

			}
		}
		
		@RequestMapping(value="/getCountOfUnseenMails",method = RequestMethod.GET)
		public ResponseEntity<?> getCountOfUnseenMails()throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			List<MailStoreModel> mailsData = new ArrayList<>();
			List<MailStoreModel> mailsDataList = new ArrayList<>();
			mailsData = mailStoreService.findAllByStatus("UNSEEN");
			if(CommonUtil.isListNotNullAndEmpty(mailsData)) {
				long count = mailsData.stream().filter(e->e.getUserExtension()==null).collect(Collectors.counting());
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 	
				model.put("MailCount", count);
				return new ResponseEntity(model, HttpStatus.OK); 
			}
			else {
				model.put("responseCode", "400");
				model.put("responseMessage", "No UnSeen Mails Found");
				model.put("responseType", "Error"); 				
				return new ResponseEntity(model, HttpStatus.OK); 

			}
		}
		
		@RequestMapping(value="/getCountOfUserMailsAndSms",method = RequestMethod.POST)
		public ResponseEntity<?> getCountOfUserMailsAndSms(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			
			 List<MailStoreModel>  mailStoreModelList = new ArrayList<>();
			 mailStoreModelList= mailStoreService.findByUserExtension(mailParserDTO.getUserExtension());
			 
			 List<SmsModel>  smsModels=new ArrayList<>();
			 List<SmsModel>  smsDataModels=new ArrayList<>();
			 smsModels= smsService.findByUserExtension(mailParserDTO.getUserExtension());
			 smsDataModels = smsModels.stream().filter(e->e.getStatus().contentEquals("ASSIGNED")).collect(Collectors.toList());	
			 
			 if (CommonUtil.isListNotNullAndEmpty(mailStoreModelList) && CommonUtil.isListNotNullAndEmpty(smsDataModels)) {
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 model.put("MailCount", mailStoreModelList.size());
				 model.put("SmsCount", smsDataModels.size());
				 return new ResponseEntity(model, HttpStatus.OK);				 
			 } else if(CommonUtil.isListNotNullAndEmpty(mailStoreModelList) && !CommonUtil.isListNotNullAndEmpty(smsDataModels)) {
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 model.put("MailCount", mailStoreModelList.size());
				 model.put("SmsCount", "0");
				 return new ResponseEntity(model, HttpStatus.OK);	
			 } else if(!CommonUtil.isListNotNullAndEmpty(mailStoreModelList) && CommonUtil.isListNotNullAndEmpty(smsDataModels)) {
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 model.put("MailCount", "0");
				 model.put("SmsCount", smsDataModels.size());
				 return new ResponseEntity(model, HttpStatus.OK);	
			 }else {
				 model.put("responseCode", "400");
				 model.put("responseMessage", "Error"); 				
				 model.put("MailCount", "0");
				 model.put("SmsCount", "0");
				 return new ResponseEntity(model, HttpStatus.OK);   
			 }

		}
		
		@RequestMapping(value="/getTotalCountOfMailsAndSms",method = RequestMethod.GET)
		public ResponseEntity<?> getTotalCountOfMailsAndSms()throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			
			 List<MailStoreModel>  mailStoreModelAssignedList = new ArrayList<>();
			 List<MailStoreModel> mailsData = new ArrayList<>();
			 List<MailStoreModel> mailsDataList = new ArrayList<>();
			 
			 List<SmsModel>  smsModelsAssigned=new ArrayList<>();
			 List<SmsModel>  smsModelsNotAssigned=new ArrayList<>();
			 List<SmsModel>  smsModelsDataList=new ArrayList<>();
			 
			 long mailNotAssignedCount = 0l;
			 long mailAssignedCount = 0l;
			 long smsNotAssignedCount = 0l;
			 long smsAssignedCount = 0l;
			 
			 mailsData = mailStoreService.findAllByStatus("UNSEEN");
			 if(CommonUtil.isListNotNullAndEmpty(mailsData)) {
				 mailAssignedCount = mailsData.stream().filter(e->e.getUserExtension()!=null).collect(Collectors.counting());				 
				 mailNotAssignedCount = mailsData.stream().filter(e->e.getUserExtension()==null).collect(Collectors.counting());
			 }		 
			
			 
			 smsModelsDataList= smsService.findAll();			 
			 if(CommonUtil.isListNotNullAndEmpty(smsModelsDataList)) {
				 smsNotAssignedCount = smsModelsDataList.stream().filter(e->e.getStatus().contentEquals("UNSEEN")).collect(Collectors.counting());	
				 smsAssignedCount = smsModelsDataList.stream().filter(e->e.getStatus().contentEquals("ASSIGNED")).collect(Collectors.counting());	
			 }
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 model.put("MailAssignedCount", mailAssignedCount);
				 model.put("MailNotAssignedCount", mailNotAssignedCount);
				 model.put("SmsAssignedCount", smsAssignedCount);
				 model.put("SmsNotAssignedCount", smsNotAssignedCount);				
				 return new ResponseEntity(model, HttpStatus.OK);
		}
		
		
		@RequestMapping(value="/getSubjectsOfUnseenMails",method = RequestMethod.GET)
		public ResponseEntity<?> getSubjectsOfUnseenMails()throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			List<MailStoreModel> mailsData = new ArrayList<>();
			List<MailStoreModel> mailsDataList = new ArrayList<>();
			mailsData = mailStoreService.findAllByStatus("UNSEEN");
			if(CommonUtil.isListNotNullAndEmpty(mailsData)) {
				mailsDataList = mailsData.stream().filter(e->e.getUserExtension()==null).collect(Collectors.toList());
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 	
				model.put("MailSubjectsList", mailsDataList);
				return new ResponseEntity(model, HttpStatus.OK); 
			}
			else {
				model.put("responseCode", "400");
				model.put("responseMessage", "No UnSeen Mails Found");
				model.put("responseType", "Error"); 				
				return new ResponseEntity(model, HttpStatus.OK); 

			}
		}
		
		@RequestMapping(value="/getAttachedFile",method = RequestMethod.POST)
		public ResponseEntity<?> getSubjectsOfUnseenMails(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			MailStoreModel mailData = new MailStoreModel();
			mailData = mailStoreService.findById(mailParserDTO.getId()); 			
			MailParserDTO mailParserDTO1 = new MailParserDTO();
			if(!CommonUtil.isNull(mailData)) {
				if(mailData.getAttachedFileData()!=null) {
					mailParserDTO1.setAttachedFileData(mailData.getAttachedFileData());
					String extension = FilenameUtils.getExtension(mailData.getAttachedFileName()); 
					System.out.println(extension);
					mailParserDTO1.setAttachedBase64DataType(getBase64Type(extension));
					mailParserDTO1.setAttachedFileName(mailData.getAttachedFileName());
				}
			}
			
			if(!CommonUtil.isNull(mailParserDTO1)) {				
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 	
				model.put("MailData", mailParserDTO1);
				return new ResponseEntity(model, HttpStatus.OK); 
			}
			else {
				model.put("responseCode", "400");
				model.put("responseMessage", "No Attachements Found");
				model.put("responseType", "Error"); 				
				return new ResponseEntity(model, HttpStatus.OK); 

			}
		}
		
		 @RequestMapping(value="/assigntMailtoUser",method = RequestMethod.POST)
		 public ResponseEntity<?> assigntMailtoUser(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException{			
			 Map<Object, Object> model = new HashMap<>(); 			
			 MailStoreModel mailStoreModel = new MailStoreModel();
			 mailStoreModel = mailStoreService.findById(mailParserDTO.getId());
			 if(!CommonUtil.isNull(mailStoreModel)) {				
				 mailStoreModel.setUserExtension(mailParserDTO.getUserExtension());
				 mailStoreService.saveOrUpdate(mailStoreModel);
				 model.put("respon.seCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 model.put("model", mailStoreModel);
				 return new ResponseEntity(model, HttpStatus.OK);
			 } else {
				 model.put("responseCode", "400");
				 model.put("responseMessage", "Error");
				 model.put("responseType", "Error");  
				 model.put("model", new MailStoreModel());
				 return new ResponseEntity(model, HttpStatus.OK);   
			 }
		 }
		 
		 @RequestMapping(value="/showMailsByUser",method = RequestMethod.POST)
		 public ResponseEntity<?> showMailsByUser(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException{
			 Map<Object, Object> model = new HashMap<>(); 
			 List<MailStoreModel>  mailStoreModels = new ArrayList<>();
			 List<MailStoreModel>  mailStoreModelList = new ArrayList<>();
			 mailStoreModels= mailStoreService.findByUserExtension(mailParserDTO.getUserExtension());
			 if(CommonUtil.isListNotNullAndEmpty(mailStoreModels)) {
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 model.put("model", mailStoreModels);
				 return new ResponseEntity(model, HttpStatus.OK);
			 } else {
				 model.put("responseCode", "400");
				 model.put("responseMessage", "Error");
				 model.put("responseType", "Error");  
				 MailStoreModel mailStoreModel1 = new MailStoreModel("", "", "","", "", "", "", "", "", new Date(), new Date(), "","", new byte[0],new Date());
				 mailStoreModelList.add(mailStoreModel1);
				 model.put("model",mailStoreModelList);
				 return new ResponseEntity(model, HttpStatus.OK);   
			 }
		 }
		
		 @RequestMapping(value="/showMailsByCaseId",method = RequestMethod.POST)
		 public ResponseEntity<?> showMailsByCaseId(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException{
			 Map<Object, Object> model = new HashMap<>(); 
			 MailStoreModel  mailStoreModel = new MailStoreModel();
			 MailParserDTO mailParserDTO1 = new MailParserDTO();
			 mailStoreModel= mailStoreService.findByCaseId(mailParserDTO.getCaseId());
			 mailParserDTO1 = modelMapper.map(mailStoreModel, MailParserDTO.class);
			 if(mailStoreModel.getAttachedFileName()!=null) {
				 String extension = FilenameUtils.getExtension(mailStoreModel.getAttachedFileName()); 
				 mailParserDTO1.setAttachedBase64DataType(getBase64Type(extension));
			 }
			 else {				
				 mailParserDTO1.setAttachedBase64DataType("unknown");
			 }
			 if(!CommonUtil.isNull(mailStoreModel)) {
				 model.put("responseCode", "200");
				 model.put("responseMessage", "Success");
				 model.put("responseType", "Success");  
				 model.put("model", mailParserDTO1);
				 return new ResponseEntity(model, HttpStatus.OK);
			 } else {
				 model.put("responseCode", "400");
				 model.put("responseMessage", "Error");
				 model.put("responseType", "Error");  
				 MailStoreModel mailStoreModel1 = new MailStoreModel("", "", "","", "", "", "", "", "", new Date(), new Date(), "","", new byte[0], new Date());				 
				 model.put("model",mailStoreModel1);
				 return new ResponseEntity(model, HttpStatus.OK);   
			 }
		 }
		@RequestMapping(value="/updateToSeen",method = RequestMethod.POST)
		public ResponseEntity<?> updateSeen(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			MailStoreModel mailStoreModel=mailStoreService.findById(mailParserDTO.getId());
			if(mailStoreModel!=null) {
				mailStoreModel.setStatus("SEEN");
				mailStoreModel.setUserId(mailParserDTO.getUserId());
				mailStoreModel.setUserName(mailParserDTO.getUserName());
				mailStoreModel.setLastUpdateDate(new Date());
				mailStoreService.saveOrUpdate(mailStoreModel);
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}else {
				model.put("responseCode", "400");
				model.put("responseMessage", "not updated");
				model.put("responseType", "Error"); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}
		}
		
		@RequestMapping(value="/updateAsReplied",method = RequestMethod.POST)
		public ResponseEntity<?> updateReply(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			MailStoreModel mailStoreModel=mailStoreService.findById(mailParserDTO.getId());
			if(mailStoreModel!=null) {
				mailStoreModel.setStatus("REPLIED");
				mailStoreModel.setUserId(mailParserDTO.getUserId());
				mailStoreModel.setUserName(mailParserDTO.getUserName());
				mailStoreModel.setLastUpdateDate(new Date());
				mailStoreService.saveOrUpdate(mailStoreModel);
				try {
					MailModel mailModel=new MailModel();
					mailModel.setBody(mailStoreModel.getBody());
					mailModel.setSubject(mailStoreModel.getSubject());
					mailModel.setEnteredDate(mailStoreModel.getEnteredDate());
					try {
					mailModel.setFrom(mailStoreModel.getFrom());
					}catch(Exception e) {}
					mailModel.setUserName(mailStoreModel.getUserName());
					mailModel.setUserId(mailStoreModel.getUserId());
					mailModel.setStatus(mailStoreModel.getStatus());
					mailModel.setLastUpdateDate(mailStoreModel.getLastUpdateDate());
					mailModel.setSentDate(mailStoreModel.getSentDate());
					mailRepository.save(mailModel);
					mailStoreService.deleteByCaseId(mailStoreModel.getCaseId());
				}catch(Exception e) {}
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}else {
				model.put("responseCode", "400");
				model.put("responseMessage", "not updated");
				model.put("responseType", "Error"); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}
		}
		
		@RequestMapping(value="/getMailById",method = RequestMethod.POST)
		public ResponseEntity<?> getMailById(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			MailStoreModel mailStoreModel=mailStoreService.findById(mailParserDTO.getId());
			if(mailStoreModel!=null) {
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 
				model.put("model", mailStoreModel); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}else {
				model.put("responseCode", "400");
				model.put("responseMessage", "not updated");
				model.put("responseType", "Error"); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}
		}
		
		@PostMapping(value = "/getMailsByStatus")
		public ResponseEntity<?> getMailsByStatus(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			List<MailStoreModel> mailStoreModels=mailStoreService.findAllByStatus(mailParserDTO.getStatus());
			if(CommonUtil.isListNotNullAndEmpty(mailStoreModels)) {
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 
				model.put("model", mailStoreModels); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}else {
				model.put("responseCode", "400");
				model.put("responseMessage", "not updated");
				model.put("responseType", "Error"); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}
		}
		
		@RequestMapping(value="/listMailsByUserName",method = RequestMethod.POST)
		public ResponseEntity<?> listMailsByUserName(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
			Map<Object, Object> model = new HashMap<>(); 
			List<MailStoreModel> mailStoreModels=mailStoreService.findAllByUserName(mailParserDTO.getUserName());
			if(CommonUtil.isListNotNullAndEmpty(mailStoreModels)) {
				model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success"); 
				model.put("model", mailStoreModels); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}else {
				model.put("responseCode", "400");
				model.put("responseMessage", "not updated");
				model.put("responseType", "Error"); 
				return new ResponseEntity(model, HttpStatus.OK); 
			}
		}
		
		@RequestMapping(value="/sendMail",method = RequestMethod.POST)
		  public ResponseEntity<?> sendMail(@RequestBody MailParserDTO mailParserDTO)throws InstantiationException, IllegalAccessException, MessagingException, IOException{
		    Map<Object, Object> model = new HashMap<>(); 
		    //String caseId = generateCaseId();
		     MimeMessage mimeMessage = mailSender.createMimeMessage();
		     
		          try {		   
		              MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);		   
		             // mimeMessageHelper.setSubject("Case Id :"+caseId+" "+mailParserDTO.getSubject());
		              mimeMessageHelper.setSubject(mailParserDTO.getSubject());
		              //mimeMessageHelper.setFrom(new InternetAddress("whl@tn181whl.org"));
		              mimeMessageHelper.setFrom("whl@tn181whl.org");
		              mimeMessageHelper.setTo(mailParserDTO.getTo());
		              mimeMessageHelper.setText(mailParserDTO.getBody());
		              
		              if(mailParserDTO.getAttachedFileData().length>0)
		              mimeMessageHelper.addAttachment(mailParserDTO.getAttachedFileName(), new ByteArrayResource(mailParserDTO.getAttachedFileData()));		             
		              mailSender.send(mimeMessageHelper.getMimeMessage());		   
		          } catch (MessagingException e) {
		        	  model.put("responseCode", "400");
		        	  model.put("responseMessage", "Error1");
		        	  model.put("responseType", "Error");   
		        	  // model.put("Result", mailsData);
		        	  return new ResponseEntity(model, HttpStatus.OK);  
		          }
		          
		          try {
		        	  MailStoreModel mailStoreModel=mailStoreService.findById(mailParserDTO.getId());
		        	  if(mailStoreModel!=null) {
		        	  mailStoreModel.setStatus("REPLIED");
					mailStoreModel.setUserId(mailParserDTO.getUserId());
						mailStoreModel.setUserName(mailParserDTO.getUserName());
						mailStoreModel.setLastUpdateDate(new Date());
						
		        	  MailModel mailModel=new MailModel();
						mailModel.setBody(mailStoreModel.getBody());
						mailModel.setSubject(mailStoreModel.getSubject());
						mailModel.setEnteredDate(mailStoreModel.getEnteredDate());
						try {
						mailModel.setFrom(mailStoreModel.getFrom());
						}catch(Exception e) {
							model.put("responseCode", "400");
							model.put("responseMessage", "Error2");
							model.put("responseType", "Error");   
							// model.put("Result", mailsData);
							return new ResponseEntity(model, HttpStatus.OK);  
						}
						mailModel.setUserName(mailStoreModel.getUserName());
						mailModel.setUserId(mailStoreModel.getUserId());
						mailModel.setStatus(mailStoreModel.getStatus());
						mailModel.setLastUpdateDate(mailStoreModel.getLastUpdateDate());
						mailModel.setSentDate(mailStoreModel.getSentDate());	
						mailModel.setCaseId(mailStoreModel.getCaseId());
						mailRepository.save(mailModel);
						mailStoreService.deleteByCaseId(mailStoreModel.getCaseId());
		        	  }
		          }catch(Exception e) {
		        	  System.out.println("Exception : "+e);
		        	  System.out.println("Exception : "+e);
		        	  System.out.println("Exception : "+e);
		        	  model.put("responseCode", "400");
		        	  model.put("responseMessage", "Error");
		        	  model.put("responseType", "Error");   
		        	  // model.put("Result", mailsData);
		        	  return new ResponseEntity(model, HttpStatus.OK);  
		          }
		     model.put("responseCode", "200");
		     model.put("responseMessage", "Success");
		     model.put("responseType", "Success");   
		    // model.put("Result", mailsData);
		     return new ResponseEntity(model, HttpStatus.OK);  
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
			 }
			 
			 else {
				 callDetailDTO.setId(callDetailModel.getId());
				callDetailModel = modelMapper.map(callDetailDTO, CallDetailModel.class);
				 callDetailService.saveOrUpdate(callDetailModel);
			 }
			 
			
			 model.put("responseCode", "200");
				model.put("responseMessage", "Success");
				model.put("responseType", "Success");  
				return new ResponseEntity(model, HttpStatus.OK);   
		 }	 

		

		 private String generateCaseId() {
				
			 String caseId="TN/WHL/MAIL";		
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
		 
		 private String getBase64Type(String extension) {
			 
			 if(extension.contentEquals("png")||extension.contentEquals("jpg")||extension.contentEquals("jpeg")) {
					return "data:image/png;base64,";					
				}
			 
				if(extension.contentEquals("txt")) {
					return "data:text/plain;base64,";					
				}	
				
				if(extension.contentEquals("pdf")) {
					return "data:application/pdf;base64,";					
				}

				if(extension.contentEquals("csv")) {
					return"data:application/octet-stream;base64,";					
				}

				if(extension.contentEquals("xls")) {
					return "data:application/vnd.ms-excel;base64,";					
				}

				if(extension.contentEquals("xlsx")) {
					return "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,";					
				}

				if(extension.contentEquals("doc")) {
					return "data:application/msword;base64,";					
				}

				if(extension.contentEquals("docx")) {
					return "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,";					
				}
				
				return "data:application/gzip;base64,";	
		 }
}
