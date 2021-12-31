package com.eupraxia.telephony.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class CommonUtil {
	public static boolean isNull(Object obj) {
		return obj == null;
	}
	public static boolean isNullOrEmpty(String s) {
		return (s != null && !s.isEmpty());
	}
	 public static String getExtensionFromChannelOrDestination(String extensionSource){
	        String extension = null;
	        try{
	            String[] extensionSourceArr = extensionSource.split("-");
	            String[] extensionArr = extensionSourceArr[0].split("/");
	            if(extensionArr[extensionArr.length-1].contains("@")){
	                extensionArr = extensionArr[extensionArr.length-1].split("@");
	                extension = extensionArr[0];
	            }
	            else
	                extension = extensionArr[extensionArr.length-1];
	        }
	        catch(Exception ex){
	          }
	        return extension;
	    }
	    
	
	public static boolean isListNotNullAndEmpty(List list) {
		return (list != null && !list.isEmpty());
	}
	
	public static <Source, Destination> List<Destination> copyListBeanProperty(Iterable<Source> sourceList, Class Destiniation) throws InstantiationException, IllegalAccessException {
		List<Destination> list = new ArrayList<Destination>();
		for (Source source: sourceList) {
			list.add((Destination) copyBeanProperties(source, Destiniation));
		}
		return list;
	}
	
	public static <Source, Destination> Destination copyBeanProperties(Source source, Class Destination) throws InstantiationException, IllegalAccessException{
		Destination destination = (Destination) Destination.newInstance();
		BeanUtils.copyProperties(source, destination);		
		return destination;
	}
	
	public static String convertStringToDate(String unFormatedDate) {	
		System.out.println("unFormatedDate :"+unFormatedDate);
		String [] monthNames = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};    
		String day = unFormatedDate.substring(8,10);
		String month = unFormatedDate.substring(4, 7);
		String monthNumber = "";
		String year = unFormatedDate.substring(11,15);

		for(int i=0;i<=monthNames.length-1;i++){
		      if(monthNames[i].equals(month)) 
		        monthNumber=(i<9)?"0"+(i+1):String.valueOf(i+1);
		    }
		
		System.out.println("year monthNumber day :"+year+"-"+monthNumber+"-"+day);
		return year+"-"+monthNumber+"-"+day;      
	}

	public static String convertStringToHour(String unFormatedDate) {	
		 String hour = unFormatedDate.substring(16,18);
		 String minute = unFormatedDate.substring(19,21);
		 return hour;		    
	}
	public static String convertStringToMinute(String unFormatedDate) {			
		 String minute = unFormatedDate.substring(19,21);
		 return minute;		    
	}
	
	public static String convertStringToSeconds(String unFormatedDate) {			
		 String seconds = unFormatedDate.substring(22,24);
		 System.out.println("seconds: "+seconds);
		 return seconds;		    
	}


}
