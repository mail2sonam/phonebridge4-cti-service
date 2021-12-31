package com.eupraxia.telephony;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eupraxia.telephony.DTO.MailParserDTO;
import com.eupraxia.telephony.Model.MailStoreModel;
import com.eupraxia.telephony.util.CommonUtil;
import com.sun.mail.util.MailSSLSocketFactory;

public class DemoCheckEmail{
   public static void main(String[] args) {
    
	   Map<Object, Object> model = new HashMap<>(); 
		 
	   try {
		 Properties props = new Properties();
		 props.setProperty("mail.pop3.ssl.enable", "true"); 
		 props.setProperty("mail.pop3.starttls.enable", "true");
		 //MailSSLSocketFactory socketFactory= new MailSSLSocketFactory();
	     //socketFactory.setTrustAllHosts(true);
	     //props.put("mail.pop3.ssl.socketFactory", socketFactory);
		 props.setProperty("mail.pop3.ssl.trust", "*");		
		 Session session = Session.getDefaultInstance(props);		
		 Store store = session.getStore("pop3");		
		 store.connect("sify-bnglr.tn181whl.org", 995, "whl", "Amtex@123#");
		 Folder inbox = store.getFolder( "INBOX" );
		 inbox.open( Folder.READ_WRITE  );
		 
		 String body = "";
		 // Fetch unseen messages from inbox folder
		 Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
		 for ( Message message : messages ) {
			 if (message.isMimeType("text/plain")) {
				 System.out.println(message.getContent().toString());
			 } 
			 else if (message.isMimeType("text/html")) { 
				 System.out.println(message.getContent().toString());
			 }
			 else if (message.isMimeType("multipart/*")) {
				 MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
				 
				 //Code for parsing attachement
				 for(int k = 0; k < mimeMultipart.getCount(); k++){
				       BodyPart bodyPart = mimeMultipart.getBodyPart(k);  
				       InputStream stream = 
			                             (InputStream) bodyPart.getInputStream();  
				       BufferedReader bufferedReader = 
				    	   new BufferedReader(new InputStreamReader(stream));  
			 
				        while (bufferedReader.ready()) {  
				    	       System.out.println(bufferedReader.readLine());  
				    	}  
				    	   System.out.println();  
				      }  
				 
				 System.out.println(getTextFromMimeMultipart(mimeMultipart));
				
			 } 		    	

		
		 }
		

	   }catch(Exception e) {
		   e.printStackTrace();
	   }

		 
	 }
   private static String getTextFromMessage(Message message) throws MessagingException, IOException {
	    String result = "";
	    if (message.isMimeType("text/plain")) {		    
	        result = message.getContent().toString();
	    } else if (message.isMimeType("multipart/*")) {
	        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	        result = getTextFromMimeMultipart(mimeMultipart);
	    }
	    return result;
	}
   
   private static String getTextFromMimeMultipart(
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
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
   }
