package com.eupraxia.telephony.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eupraxia.telephony.DTO.ReportDTO;
import com.eupraxia.telephony.DTO.UploadModelDTO;
import com.eupraxia.telephony.Model.UploadModel;
import com.eupraxia.telephony.service.UploadService;
import com.eupraxia.telephony.service.Reports.ReportService;;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ExcelUploadController {
	@Autowired 
	 UploadService uploadService;
	
	@PostMapping(value="/uploadData")
	public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file) throws IOException{
		System.out.println("HIIIIIIIIIIIIIII");
		List<UploadModel> uploadList = new ArrayList<>();

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		XSSFRow row;

		// HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
		// HSSFSheet worksheet = workbook.getSheetAt(0);
		for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				UploadModel uploadModel = new UploadModel();
				row = worksheet.getRow(index);
				String id =  row.getCell(0).getStringCellValue();
				uploadModel.setRowid(Integer.parseInt(id));
				uploadModel.setName(row.getCell(1).getStringCellValue());
				//uploadModel.setPhonenumber1(row.getCell(2).getStringCellValue());
				//uploadModel.setPhonenumber2(row.getCell(3).getStringCellValue());
				//uploadModel.setAddress(row.getCell(4).getStringCellValue());
				uploadModel.setStatus("NotInitiated");
				uploadService.saveOrUpdate(uploadModel);
				uploadList.add(uploadModel);
			}
		}

		Map<Object, Object> model = new HashMap<>();
		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("data", uploadList);
		return new ResponseEntity(model, HttpStatus.OK); 

	}
	 
	@RequestMapping(value="/getNumberForDialler",method = RequestMethod.GET)
	public ResponseEntity<?> getNumberForDialler() throws InstantiationException, IllegalAccessException{
		List<UploadModelDTO> dispositionList = new ArrayList<>();
		UploadModelDTO dispositionDTO = new UploadModelDTO();
		Map<Object, Object> model = new HashMap<>();
		UploadModel uploadModel = uploadService.NumberForDialler();
		uploadModel.setStatus("Initiated");
		uploadService.saveOrUpdate(uploadModel);

		model.put("responseCode", "200");
		model.put("responseMessage", "Success");
		model.put("responseType", "Success");  
		model.put("Number", uploadModel.getPhonenumber1());
		return new ResponseEntity(model, HttpStatus.OK);       
	}

}
