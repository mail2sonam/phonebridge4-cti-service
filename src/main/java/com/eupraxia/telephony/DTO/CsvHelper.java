package com.eupraxia.telephony.DTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eupraxia.telephony.Model.DiallerModel;
import com.eupraxia.telephony.repositories.DiallerRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class CsvHelper {
	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "Id", "Title", "Description", "Published" };

	  public static boolean hasCSVFormat(MultipartFile file) {
       String type=file.getOriginalFilename();
	    if (!type.contains(".csv")) {
	      return false;
	    }

	    return true;
	  }

	public static List<DiallerModel> fileToData(InputStream inputStream) {
		List<DiallerModel> diallerModels=new ArrayList<DiallerModel>();
		
		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			
	        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	        for (CSVRecord csvRecord : csvRecords) {
	        	DiallerModel diallerModel = new DiallerModel();
	        	diallerModel.setName(csvRecord.get("Name"));
	        	diallerModel.setPhoneNo(csvRecord.get("PhoneNo"));
	        	diallerModel.setInsertDate(new Date());

	        	diallerModels.add(diallerModel);
	        	
	        }
		}catch(Exception e) {}
		return diallerModels;
	}
}
