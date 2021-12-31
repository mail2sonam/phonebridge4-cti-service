package com.eupraxia.telephony;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

public class CheckSendSMS {

	public static void main(String[] args) {
		
		String district = "Sivagangai";
		System.out.println(StringUtils.startsWithIgnoreCase(district, "sivag"));
	
		/*
		String smsContent = "";
		String caseId1 = "TN/WHL/12/03/21/0003";
		String Address1 = "Forrest Ray 191-103";
		String Address2 = "Hello Address2";
		String Address3 = "Hello Address3";
		String pincode = "560024";
		String contactName = "VACANT";
		String contactNo = "9488588465";
		String mailId = "testpriya@test.com";
		String sResult = " ";
		smsContent = "CaseID: "+ caseId1
				+"\n "+makeTextToThirty(Address1)+" "+makeTextToThirty(Address2)+" "+makeTextToThirty(Address3)
				+"\n "+contactNo+" -Women HelpLine";
		
		String phoneNo = "9788798465";
		
		try {
			 // Construct data
			 String apiKey = "apikey=" + URLEncoder.encode("7ank/UwWQ4A-2kMKdE8D1PHQ7SeOCYAONxLYulRJWC", "UTF-8");
				String message = "&message=" + URLEncoder.encode(smsContent, "UTF-8");
				String sender = "&sender=" + URLEncoder.encode("SWDWHL", "UTF-8");
				String numbers = "&numbers=" + URLEncoder.encode(phoneNo, "UTF-8");
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
					
					sResult=sResult+line+" ";
				}
				rd.close();
				
		 } catch (Exception e) {
			 System.out.println("Error SMS "+e);
		 }
		 */

	}
	
	 private static String makeTextToThirty(String str) {
			
		 if(str==null) {
			 return " "+StringUtils.rightPad(str, 18," ");
		 }
		 if(str.length()>18){
			 return str.substring(0,18);
		 }
		 if(str.length()<18){
			 return " "+StringUtils.rightPad(str, 18," ");
		 }
		 
		 return str;
	 }

}
