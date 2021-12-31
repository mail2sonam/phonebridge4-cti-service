package com.eupraxia.telephony.service.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eupraxia.telephony.service.DiallerService;
import com.eupraxia.telephony.util.CommonUtil;
import com.eupraxia.telephony.DTO.CsvHelper;
import com.eupraxia.telephony.Model.DiallerModel;
import com.eupraxia.telephony.repositories.DiallerRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;




@Service
public class DiallerServiceImpl implements DiallerService{

	@Autowired
	DiallerRepository diallerRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Override
	public void save(MultipartFile file,Long campaignId,String campaignName) {
		
		     try {
		    	 List<DiallerModel> diallerModels=new ArrayList<DiallerModel>();
					BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
			        CSVParser csvParser = new CSVParser(fileReader,
			            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
					
			        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			        for (CSVRecord csvRecord : csvRecords) {
			        	try {
			        	DiallerModel diallerModel = new DiallerModel();
			        	diallerModel.setName(csvRecord.get("Name"));
			        	diallerModel.setPhoneNo(csvRecord.get("PhoneNo"));
			        	diallerModel.setInsertDate(new Date());
			        	diallerModel.setStatus("open");
			        	diallerModel.setCampaignId(campaignId.intValue());
			        	diallerModel.setCampaignName(campaignName);
			        	diallerModels.add(diallerModel);
			        	
			        	}catch(Exception e) {}
			        }
			        if(CommonUtil.isListNotNullAndEmpty(diallerModels)) {
			        	diallerRepository.saveAll(diallerModels);
			        }
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}

		
		
	}

	@Override
	public DiallerModel findByStatusAndDate(int campaignId) {
		System.out.println("pra");
		DiallerModel diallerModel=new DiallerModel();
		Query query =  entityManager.createNativeQuery("select d.id,d.name,d.phone_no from dialler d where d.status='open' and d.campaign_id=:campaignId ORDER BY d.insert_date ASC");
		query.setParameter("campaignId", campaignId);
		query.setMaxResults(1);
		List<Object> list = null;		
		list=query.getResultList();
		 if (CommonUtil.isListNotNullAndEmpty(list)) {
			 System.out.println("veena");
			 Iterator it = list.iterator();
			 while (it.hasNext()){
				 
				 try {
					 Object[] objs = ( Object[]) it.next();
					 diallerModel.setId(CommonUtil.isNull(objs[0])?null:(int)objs[0]);
					 diallerModel.setName(CommonUtil.isNull(objs[1])?null:(String)objs[1]);
					 diallerModel.setPhoneNo(CommonUtil.isNull(objs[2])?null:(String)objs[2]);
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
			 }

		 }
		return diallerModel;
	}

	@Override
	public DiallerModel findById(int id) {
		// TODO Auto-generated method stub
		return diallerRepository.findById(id);
	}

	@Override
	public void saveOrUpdate(DiallerModel diallerModel1) {
		// TODO Auto-generated method stub
		diallerRepository.save(diallerModel1);
	}

	@Override
	public List<DiallerModel> findAllByCampaignId(int campaignId) {
		
		return diallerRepository.findByCampaignId(campaignId);
	}

	@Override
	public List<DiallerModel> findByCampaignIdAndStatus(int campaignId, String status) {
		// TODO Auto-generated method stub
		return diallerRepository.findByCampaignIdAndStatus(campaignId,status);
	}

}
