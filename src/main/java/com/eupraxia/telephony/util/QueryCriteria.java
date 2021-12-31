package com.eupraxia.telephony.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.DTO.AgentWiseDTO;
import com.eupraxia.telephony.DTO.AgentWiseDataDTO;
import com.eupraxia.telephony.DTO.HistoryReport;
import com.eupraxia.telephony.DTO.SeniorCitizenReportDTO;
import com.eupraxia.telephony.DTO.TestDTO;
import com.eupraxia.telephony.Model.CSECEModel;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Model.Reports.ReportsModel;
import com.eupraxia.telephony.repositories.CseCeRepository;


@Service
public class QueryCriteria {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	CseCeRepository cseCeRepository;


	
	public Query reportQueryAllReport(String status, String phoneNo, String direction, String extension, String agentName, String campaignName) {
		String que="SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
					 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
					 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
					 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time, r.disposition, r.comments, r.callback_Date, "
				     + "r.rec_path,r.case_Id,r.cam_name,r.call_droped,r.cus_name,r.second_status,r.dispo,r.maindispo,r.subdispo,r.subsubdispo FROM eupraxia_report r "
					 + "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate ";
		if(CommonUtil.isNullOrEmpty(status)) {
			if("ANSWER".equalsIgnoreCase(status)) {
			que=que.concat("AND call_status='ANSWER' ");
			}else {
				que=que.concat("AND call_status !='ANSWER' ");
			}
		}
		if(CommonUtil.isNullOrEmpty(phoneNo)) {
			que=que.concat("AND r.phone_No= :phoneNo ");
		}
		if(CommonUtil.isNullOrEmpty(extension)) {
			que=que.concat("AND r.extension= :extension ");
		}
		if(CommonUtil.isNullOrEmpty(agentName)) {
			que=que.concat("AND r.extension= :extension ");
		}
		if(CommonUtil.isNullOrEmpty(campaignName)) {
			que=que.concat("AND r.cam_name= :campaignName ");
		}
		if(CommonUtil.isNullOrEmpty(direction)) {
			que=que.concat("AND r.call_Direction = :direction ");
		}
		que=que.concat("ORDER BY r.call_Start_Time ASC");
	   Query query =  entityManager.createNativeQuery(que);
					 
			return query;
		}
	
	public List<TestDTO> resultReportSetAll(List<Object> result,List<UserModel> um,String typeOfReport) {
		List<TestDTO> dispositionReportDTOs = new ArrayList<>();
		LocalDateTime startDateTimeDataConverted1 = null; 
		LocalDateTime endDateTimeDataConverted1 = null; 
		LocalDateTime startDateTimeDataConverted = null; 
		LocalDateTime endDateTimeDataConverted = null; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   Map<String,String> exts=new HashMap<String,String>();
   for(UserModel uml:um) {
	exts.put(uml.getUserextension(), uml.getUsername());
   }
		if (CommonUtil.isListNotNullAndEmpty(result)) {
			Iterator it = result.iterator();
			while (it.hasNext()){
				try {
					Date stdate=null;
					Date endDate=null;	
					startDateTimeDataConverted1 = null; 
					endDateTimeDataConverted1 = null; 
					Object[] objs = ( Object[]) it.next();
					TestDTO dto=new TestDTO();
					
					dto.setId(CommonUtil.isNull(objs[0])?null:(int)objs[0]);
					dto.setDocId(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
					dto.setPhoneNo(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);
					dto.setComments(CommonUtil.isNull(objs[18])?" ":(String)objs[18]);
					dto.setDisposition(CommonUtil.isNull(objs[17])?" ":(String)objs[17]);
					
					dto.setExtension(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
					try {
					if(dto.getExtension()!=null&&!dto.getExtension().isEmpty()) {
						dto.setAgentName(exts.get(dto.getExtension()));
					}
					}catch(Exception e) {}
					dto.setCallStatus(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);						

					//dto.setCallStartTime(CommonUtil.isNull(objs[7])?" ":objs[7].toString());


					if(!CommonUtil.isNull(objs[7])) {
						stdate=(Date) objs[7];
						//startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
						//dto.setCallStartTime(startDateTimeDataConverted1.toLocalDate().toString());
						startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
						startDateTimeDataConverted = startDateTimeDataConverted1;
						//dto.setCallStartTime(startDateTimeDataConverted.toLocalDate().toString());
						
						String dd=startDateTimeDataConverted.toString();
					
						dd=dd.replace("T", " ");
						dto.setCallStartTime(dd);
						
					}

					if(CommonUtil.isNull(objs[7])) {
						dto.setCallStartTime("");
					}
					//dto.setCallEndTime(CommonUtil.isNull(objs[8])?" ":objs[8].toString());
					//System.out.println(objs[8]);

					if(!CommonUtil.isNull(objs[8])) {
						endDate=(Date) objs[8];
						//endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter);
						//dto.setCallEndTime(endDateTimeDataConverted1.toLocalDate().toString());
						endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter);
						endDateTimeDataConverted = endDateTimeDataConverted1;
						String dd=endDateTimeDataConverted.toString();
						dd=dd.replace("T", " ");
						dto.setCallEndTime(dd);
					}

					if(CommonUtil.isNull(objs[8])) {
						dto.setCallEndTime("");
					}
					if(startDateTimeDataConverted !=null && endDateTimeDataConverted !=null) {
						Duration duration = Duration.between(startDateTimeDataConverted, endDateTimeDataConverted);					     
						dto.setDuration(duration.toHours() + "h " + duration.toMinutes() + "m "+duration.getSeconds()+"s");
						//System.out.println(dto.getDuration());
					}

					if(startDateTimeDataConverted ==null || endDateTimeDataConverted ==null) {
						dto.setDuration("");
					}
					
					if(endDate!=null&&stdate!=null) {
						long dur=(int)((endDate.getTime() - stdate.getTime())/1000);
						dto.setDuration(String.valueOf(dur));
					}
					
					dto.setCallConnectTime(CommonUtil.isNull(objs[9])?" ":objs[9].toString());	
					dto.setDirection(CommonUtil.isNull(objs[13])?" ":(String)objs[13]);
					dto.setSecondNumber(CommonUtil.isNull(objs[14])?" ":(String)objs[14]);
					dto.setHoldStartTime(CommonUtil.isNull(objs[16])?" ":objs[16].toString());
					dto.setRecPath(CommonUtil.isNull(objs[20])?null:(String)objs[20]);
					dto.setCaseId(CommonUtil.isNull(objs[21])?null:(String)objs[21]);
					dto.setCamName(CommonUtil.isNull(objs[22])?null:(String)objs[22]);
					dto.setCalldrop(CommonUtil.isNull(objs[23])?null:(String)objs[23]);
					dto.setCusName(CommonUtil.isNull(objs[24])?null:(String)objs[24]);
					dto.setSecondStatus(CommonUtil.isNull(objs[25])?null:(String)objs[25]);
					dto.setDispo(CommonUtil.isNull(objs[26])?null:(String)objs[26]);
					dto.setMaindispo(CommonUtil.isNull(objs[27])?null:(String)objs[27]);
					dto.setSubdispo(CommonUtil.isNull(objs[28])?null:(String)objs[28]);
					dto.setSubsubdispo(CommonUtil.isNull(objs[29])?null:(String)objs[29]);
					if(typeOfReport.contentEquals("war")) {
						CSECEModel cSECEModel=cseCeRepository.findByPhoneNumber(dto.getPhoneNo());
						if(cSECEModel!=null) {
							dto.setCSECEModel(cSECEModel);
							dispositionReportDTOs.add(dto);
						}
					}else {
						dispositionReportDTOs.add(dto);
					}
					
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
		return dispositionReportDTOs;

	}

	public Query reportQueryAllANS() {
		 
		 Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time, r.disposition, r.comments, r.callback_Date, "
				 + "g.gs_information_sought, g.gs_risk_asses, g.gs_aggrieved, g.gs_other_agg_name, g.gs_other_agg_mobile, g.gs_other_agg_age, "
				 + "g.gs_other_agg_gender, g.gs_age, g.gs_other_agg_address, g.gs_age_group, g.gs_education, g.gs_occupation, g.gs_gender,"
				 + " g.gs_personal_ident, g.gs_marital_status, g.gs_living_status, g.gs_family_status, g.gs_houseno, g.gs_street, g.gs_block, "
				 + "g.gs_village, g.gs_state, g.gs_district, g.gs_pincode, g.gs_placeof_inc, g.gs_frequency, g.gs_statusof_inc, g.gs_case_cat1, "
				 + "g.gs_sub_cat, g.gs_typeof_abuse, g.gs_prior_redressal, g.gs_perpetrator, g.gs_service_offered, g.gs_add_obtain,  "
				 + "g.gs_agency, g.gs_nameof_person, g.gs_remarks, g.gs_statusof_case,  g.gs_perpetrator_name, "
				 + "g.gs_perpetrator_age, g.gs_perpetrator_gender, g.gs_perpetrator_mobile, g.gs_perpetrator_occup, g.gs_perpetrator_addition, "
				 + "i.is_name, i.is_age, i.is_age_group, i.is_education, i.is_gender, i.is_houseno, i.is_street, "
				 + "i.is_block, i.is_village, i.is_state, i.is_district, i.is_pincode, i.is_information_sought, i.is_service_offered, "
				 + "i.is_add_obtain_info, i.is_agency, i.is_nameof_person, i.is_contact_num, i.is_remarks,"
				 + "c.call_type, c.incident_date, c.incident_time,  e.es_information_sought,"
				 + "e.es_risk_asses,e.es_aggrieved,e.es_other_agg_name,e.es_other_agg_mobile,"
				 + "e.es_other_agg_address,e.es_age,e.es_age_group,e.es_education,e.es_occupation,e.es_gender,"
				 + "e.es_person_alident,e.es_marital_status,e.es_living_status,e.es_family_status,e.es_houseno,e.es_street,e.es_block,"
				 + "e.es_village,e.es_state,e.es_district,e.es_pincode,e.es_placeof_inc,e.es_frequency,e.es_statusof_inc,e.es_casecat1,"
				 + "e.es_subcat,e.es_typeof_abuse,e.es_priorred_ressal,e.es_perpetrator,e.es_service_offered,e.es_addobtain,e.es_statusof_case,"
				 + "e.es_agency,e.es_nameof_Person,e.es_remarks,e.es_perpetrator_name,e.es_perpetrator_age,e.es_perpetrator_gender,"
				 + "e.es_perpetrator_mobile,e.es_perpetrator_occup,e.es_perpetrator_addition,r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time FROM eupraxia_report r "				
				 + "LEFT JOIN  guidence_case_details g ON r.doc_Id = g.call_id "
				 + "LEFT JOIN information_case_details i ON r.doc_Id = i.call_id "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "
				 + "LEFT JOIN emergency_case_details e  ON r.doc_Id = e.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='inbound' AND r.call_Status='ANSWER' ORDER BY r.call_Start_Time ASC");

		 return query;
		 
	 }


	public Query reportQueryAll() {
		 
		 Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time, r.disposition, r.comments, r.callback_Date, "
				 + "g.gs_information_sought, g.gs_risk_asses, g.gs_aggrieved, g.gs_other_agg_name, g.gs_other_agg_mobile, g.gs_other_agg_age, "
				 + "g.gs_other_agg_gender, g.gs_age, g.gs_other_agg_address, g.gs_age_group, g.gs_education, g.gs_occupation, g.gs_gender,"
				 + " g.gs_personal_ident, g.gs_marital_status, g.gs_living_status, g.gs_family_status, g.gs_houseno, g.gs_street, g.gs_block, "
				 + "g.gs_village, g.gs_state, g.gs_district, g.gs_pincode, g.gs_placeof_inc, g.gs_frequency, g.gs_statusof_inc, g.gs_case_cat1, "
				 + "g.gs_sub_cat, g.gs_typeof_abuse, g.gs_prior_redressal, g.gs_perpetrator, g.gs_service_offered, g.gs_add_obtain,  "
				 + "g.gs_agency, g.gs_nameof_person, g.gs_remarks, g.gs_statusof_case,  g.gs_perpetrator_name, "
				 + "g.gs_perpetrator_age, g.gs_perpetrator_gender, g.gs_perpetrator_mobile, g.gs_perpetrator_occup, g.gs_perpetrator_addition, "
				 + "i.is_name, i.is_age, i.is_age_group, i.is_education, i.is_gender, i.is_houseno, i.is_street, "
				 + "i.is_block, i.is_village, i.is_state, i.is_district, i.is_pincode, i.is_information_sought, i.is_service_offered, "
				 + "i.is_add_obtain_info, i.is_agency, i.is_nameof_person, i.is_contact_num, i.is_remarks,"
				 + "c.call_type, c.incident_date, c.incident_time,  e.es_information_sought,"
				 + "e.es_risk_asses,e.es_aggrieved,e.es_other_agg_name,e.es_other_agg_mobile,"
				 + "e.es_other_agg_address,e.es_age,e.es_age_group,e.es_education,e.es_occupation,e.es_gender,"
				 + "e.es_person_alident,e.es_marital_status,e.es_living_status,e.es_family_status,e.es_houseno,e.es_street,e.es_block,"
				 + "e.es_village,e.es_state,e.es_district,e.es_pincode,e.es_placeof_inc,e.es_frequency,e.es_statusof_inc,e.es_casecat1,"
				 + "e.es_subcat,e.es_typeof_abuse,e.es_priorred_ressal,e.es_perpetrator,e.es_service_offered,e.es_addobtain,e.es_statusof_case,"
				 + "e.es_agency,e.es_nameof_Person,e.es_remarks,e.es_perpetrator_name,e.es_perpetrator_age,e.es_perpetrator_gender,"
				 + "e.es_perpetrator_mobile,e.es_perpetrator_occup,e.es_perpetrator_addition,r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration,r.queue_join,r.queue_leave FROM eupraxia_report r "				
				 + "LEFT JOIN  guidence_case_details g ON r.doc_Id = g.call_id "
				 + "LEFT JOIN information_case_details i ON r.doc_Id = i.call_id "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "
				 + "LEFT JOIN emergency_case_details e  ON r.doc_Id = e.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='inbound' ORDER BY r.call_Start_Time ASC");

		 return query;
		 
	 }

	
	public Query reportQueryInformation() {
		 
		 Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time,  "				 
				 + "i.is_name, i.is_age, i.is_age_group, i.is_education, i.is_gender, i.is_houseno, i.is_street, "
				 + "i.is_block, i.is_village, i.is_state, i.is_district, i.is_pincode, i.is_information_sought, i.is_service_offered, "
				 + "i.is_add_obtain_info, i.is_agency, i.is_nameof_person, i.is_contact_num, i.is_remarks,"
				 + "r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration FROM eupraxia_report r "				 
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "				
				 + "INNER JOIN information_case_details i ON r.doc_Id = i.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='inbound' ORDER BY r.call_Start_Time ASC");
		 return query;
		 
	 }
	
	public Query reportQueryGuidance() {
		 
		 Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time,  "
				 + "g.gs_information_sought, g.gs_risk_asses, g.gs_aggrieved, g.gs_other_agg_name, g.gs_other_agg_mobile, g.gs_other_agg_age, "
				 + "g.gs_other_agg_gender, g.gs_age, g.gs_other_agg_address, g.gs_age_group, g.gs_education, g.gs_occupation, g.gs_gender,"
				 + " g.gs_personal_ident, g.gs_marital_status, g.gs_living_status, g.gs_family_status, g.gs_houseno, g.gs_street, g.gs_block, "
				 + "g.gs_village, g.gs_state, g.gs_district, g.gs_pincode, g.gs_placeof_inc, g.gs_frequency, g.gs_statusof_inc, g.gs_case_cat1, "
				 + "g.gs_sub_cat, g.gs_typeof_abuse, g.gs_prior_redressal, g.gs_perpetrator, g.gs_service_offered, g.gs_add_obtain,  "
				 + "g.gs_agency, g.gs_nameof_person, g.gs_remarks, g.gs_statusof_case,  g.gs_perpetrator_name, "
				 + "g.gs_perpetrator_age, g.gs_perpetrator_gender, g.gs_perpetrator_mobile, g.gs_perpetrator_occup, g.gs_perpetrator_addition, "				
				 + "c.call_type, c.incident_date, c.incident_time,  r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration FROM eupraxia_report r "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "				
				 + "INNER JOIN  guidence_case_details g ON r.doc_Id = g.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='inbound' ORDER BY r.call_Start_Time ASC");
		 return query;
		 
	 }
	
	
	
	public Query reportQueryEmergency() {
		 
		 Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time,  "
				 + " c.call_type, c.incident_date, c.incident_time,e.es_information_sought,"
				 + "e.es_risk_asses,e.es_aggrieved,e.es_other_agg_name,e.es_other_agg_mobile,"
				 + "e.es_other_agg_address,e.es_age,e.es_age_group,e.es_education,e.es_occupation,e.es_gender,"
				 + "e.es_person_alident,e.es_marital_status,e.es_living_status,e.es_family_status,e.es_houseno,e.es_street,e.es_block,"
				 + "e.es_village,e.es_state,e.es_district,e.es_pincode,e.es_placeof_inc,e.es_frequency,e.es_statusof_inc,e.es_casecat1,"
				 + "e.es_subcat,e.es_typeof_abuse,e.es_priorred_ressal,e.es_perpetrator,e.es_service_offered,e.es_addobtain,e.es_statusof_case,"
				 + "e.es_agency,e.es_nameof_Person,e.es_remarks,e.es_perpetrator_name,e.es_perpetrator_age,e.es_perpetrator_gender,"
				 + "e.es_perpetrator_mobile,e.es_perpetrator_occup,e.es_perpetrator_addition,r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration FROM eupraxia_report r "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "
				 + "INNER JOIN emergency_case_details e  ON r.doc_Id = e.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='inbound' ORDER BY r.call_Start_Time ASC");

		 return query;
		 
	 }

	
	
	
	
	
//	public List<TestDTO> resultSetAll(List<Object> result, List<UserModel> um) {
//		List<TestDTO> dispositionReportDTOs = new ArrayList<>();
//		LocalDateTime startDateTimeDataConverted1 = null; 
//		LocalDateTime endDateTimeDataConverted1 = null; 
//		LocalDateTime startDateTimeDataConverted = null; 
//		LocalDateTime endDateTimeDataConverted = null; 
//		
//		LocalDateTime connectTime=null;
//		LocalDateTime connectTimeConvert = null; 
//		
//		LocalDateTime queuejoin=null;
//		LocalDateTime queuejoinconvert = null; 
//		
//		LocalDateTime queueleave=null;
//		LocalDateTime queueleaveconvert = null; 
//		
//		LocalDateTime ringstart=null;
//		LocalDateTime ringstartconvert = null; 
//		
//		LocalDateTime ringend=null;
//		LocalDateTime ringendconvert = null; 
//		
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		 Map<String,String> exts=new HashMap<String,String>();
//		   for(UserModel uml:um) {
//			exts.put(uml.getUserextension(), uml.getUsername());
//		   }
//		if (CommonUtil.isListNotNullAndEmpty(result)) {
//			Iterator it = result.iterator();
//			while (it.hasNext()){
//				try {
//					startDateTimeDataConverted1 = null; 
//					endDateTimeDataConverted1 = null; 
//					Object[] objs = ( Object[]) it.next();
//					TestDTO dto=new TestDTO();
//					dto.setId(CommonUtil.isNull(objs[0])?null:(int)objs[0]);
//					dto.setDocId(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
//					dto.setPhoneNo(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);
//					dto.setExtension(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
//					
//					try {
//						String ext=(String)objs[3];
//						if(ext!=null&&!ext.isEmpty()) {
//						dto.setAgentName(exts.get((String)objs[3]));
//						}					}catch(Exception e) {}
//					dto.setCallStatus(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);						
//
//					//dto.setCallStartTime(CommonUtil.isNull(objs[7])?" ":objs[7].toString());
//
//
//					if(!CommonUtil.isNull(objs[7])) {
//						//startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
//						//dto.setCallStartTime(startDateTimeDataConverted1.toLocalDate().toString());
//						startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,19), formatter).minusHours(5);
//						startDateTimeDataConverted = startDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallStartTime(startDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[7])) {
//						dto.setCallStartTime("");
//					}
//					//dto.setCallEndTime(CommonUtil.isNull(objs[8])?" ":objs[8].toString());
//					//System.out.println(objs[8]);
//
//					if(!CommonUtil.isNull(objs[8])) {
//						//endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter);
//						//dto.setCallEndTime(endDateTimeDataConverted1.toLocalDate().toString());
//						endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,19), formatter).minusHours(5);
//						endDateTimeDataConverted = endDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallEndTime(endDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[8])) {
//						dto.setCallEndTime("");
//					}
//				
//					if(!CommonUtil.isNull(objs[9])) {
//						connectTime = LocalDateTime.parse(objs[9].toString().substring(0,19), formatter).minusHours(5);
//						connectTimeConvert = connectTime.minusMinutes(30);
//						dto.setCallConnectTime(connectTimeConvert.toLocalTime().toString());	
//					
//					}
//					
//					dto.setDirection(CommonUtil.isNull(objs[13])?" ":(String)objs[13]);
//					
//					dto.setSecondNumber(CommonUtil.isNull(objs[14])?" ":(String)objs[15]);
//					try {
//					dto.setHoldStartTime(CommonUtil.isNull(objs[16])?" ":objs[16].toString());
//					dto.setHoldEndTime(CommonUtil.isNull(objs[17])?" ":objs[17].toString());
//					}catch(Exception e) {}
//					dto.setGsInformationSought(CommonUtil.isNull(objs[20])?" ":(String)objs[20]);
//					dto.setGsRiskAsses(CommonUtil.isNull(objs[21])?" ":(String)objs[21]);
//					dto.setGsAggrieved(CommonUtil.isNull(objs[22])?" ":(String)objs[22]);
//					dto.setGsOtherAggName(CommonUtil.isNull(objs[23])?" ":(String)objs[23]);
//					dto.setGsOtherAggMobile(CommonUtil.isNull(objs[24])?" ":(String)objs[24]);					
//					dto.setGsOtherAggAge(CommonUtil.isNull(objs[25])?" ":(String)objs[25]);
//					dto.setGsOtherAggGender(CommonUtil.isNull(objs[26])?" ":(String)objs[26]);
//					dto.setGsAge(CommonUtil.isNull(objs[27])?" ":(String)objs[27]);
//					dto.setGsOtherAggAddress(CommonUtil.isNull(objs[28])?" ":(String)objs[28]);
//					dto.setGsAgeGroup(CommonUtil.isNull(objs[29])?" ":(String)objs[29]);
//					dto.setGsEducation(CommonUtil.isNull(objs[30])?" ":(String)objs[30]);
//					dto.setGsOccupation(CommonUtil.isNull(objs[31])?" ":(String)objs[31]);
//					dto.setGsGender(CommonUtil.isNull(objs[32])?" ":(String)objs[32]);
//					dto.setGsPersonalIdent(CommonUtil.isNull(objs[33])?" ":(String)objs[33]);
//					dto.setGsMaritalStatus(CommonUtil.isNull(objs[34])?" ":(String)objs[34]);
//					dto.setGsLivingStatus(CommonUtil.isNull(objs[35])?" ":(String)objs[35]);
//					dto.setGsFamilyStatus(CommonUtil.isNull(objs[36])?" ":(String)objs[36]);
//					dto.setGsHouseno(CommonUtil.isNull(objs[37])?" ":(String)objs[37]);
//					dto.setGsStreet(CommonUtil.isNull(objs[38])?" ":(String)objs[38]);
//					dto.setGsBock(CommonUtil.isNull(objs[39])?" ":(String)objs[39]);
//					dto.setGsVillage(CommonUtil.isNull(objs[40])?" ":(String)objs[40]);
//					dto.setGsState(CommonUtil.isNull(objs[41])?" ":(String)objs[41]);
//					dto.setGsDistrict(CommonUtil.isNull(objs[42])?" ":(String)objs[42]);
//					dto.setGsPincode(CommonUtil.isNull(objs[43])?" ":(String)objs[43]);
//					dto.setGsPlaceofInc(CommonUtil.isNull(objs[44])?" ":(String)objs[44]);
//					dto.setGsFrequency(CommonUtil.isNull(objs[45])?" ":(String)objs[45]);
//					dto.setGsStatusofInc(CommonUtil.isNull(objs[46])?" ":(String)objs[46]);
//					dto.setGsCaseCat1(CommonUtil.isNull(objs[47])?" ":(String)objs[47]);
//					dto.setGsSubCat(CommonUtil.isNull(objs[48])?" ":(String)objs[48]);
//					dto.setGsTypeofAbuse(CommonUtil.isNull(objs[49])?" ":(String)objs[49]);
//					dto.setGsPriorRedressal(CommonUtil.isNull(objs[50])?" ":(String)objs[50]);
//					dto.setGsPerpetrator(CommonUtil.isNull(objs[51])?" ":(String)objs[51]);
//					dto.setGsServiceOffered(CommonUtil.isNull(objs[52])?" ":(String)objs[52]);
//					dto.setGsAddObtain(CommonUtil.isNull(objs[53])?" ":(String)objs[53]);
//					dto.setGsAgency(CommonUtil.isNull(objs[54])?" ":(String)objs[54]);
//					dto.setGsNameofPerson(CommonUtil.isNull(objs[55])?" ":(String)objs[55]);
//					dto.setGsRemarks(CommonUtil.isNull(objs[56])?" ":(String)objs[56]);
//					dto.setGsStatusofCase(CommonUtil.isNull(objs[57])?" ":(String)objs[57]);
//					dto.setGsPerpetratorName(CommonUtil.isNull(objs[58])?" ":(String)objs[58]);
//					dto.setGsPerpetratorAge(CommonUtil.isNull(objs[59])?" ":(String)objs[59]);
//					dto.setGsPerpetratorGender(CommonUtil.isNull(objs[60])?" ":(String)objs[60]);
//					dto.setGsPerpetratorMobile(CommonUtil.isNull(objs[61])?" ":(String)objs[61]);
//					dto.setGsPerpetratorOccup(CommonUtil.isNull(objs[62])?" ":(String)objs[62]);
//					dto.setGsPerpetratorAddition(CommonUtil.isNull(objs[63])?" ":(String)objs[63]);
//					dto.setIsName(CommonUtil.isNull(objs[64])?" ":(String)objs[64]);
//					dto.setIsAge(CommonUtil.isNull(objs[65])?" ":(String)objs[65]);
//					dto.setIsAgeGroup(CommonUtil.isNull(objs[66])?" ":(String)objs[66]);
//					dto.setIsEducation(CommonUtil.isNull(objs[67])?" ":(String)objs[67]);
//					dto.setIsGender(CommonUtil.isNull(objs[68])?" ":(String)objs[68]);
//					dto.setIsHouseno(CommonUtil.isNull(objs[69])?" ":(String)objs[69]);
//					dto.setIsStreet(CommonUtil.isNull(objs[70])?" ":(String)objs[70]);
//					dto.setIsBlock(CommonUtil.isNull(objs[71])?" ":(String)objs[71]);
//					dto.setIsVillage(CommonUtil.isNull(objs[72])?" ":(String)objs[72]);
//					dto.setIsState(CommonUtil.isNull(objs[73])?" ":(String)objs[73]);
//					dto.setIsDistrict(CommonUtil.isNull(objs[74])?" ":(String)objs[74]);
//					dto.setIsPincode(CommonUtil.isNull(objs[75])?" ":(String)objs[75]);
//					dto.setIsInformationSought(CommonUtil.isNull(objs[76])?" ":(String)objs[76]);
//					dto.setIsServiceoffered(CommonUtil.isNull(objs[77])?" ":(String)objs[77]);
//					dto.setIsAddObtainInfo(CommonUtil.isNull(objs[78])?" ":(String)objs[78]);
//					dto.setIsAgency(CommonUtil.isNull(objs[79])?" ":(String)objs[79]);
//					dto.setIsNameofPerson(CommonUtil.isNull(objs[80])?" ":(String)objs[80]);
//					dto.setIsContactNum(CommonUtil.isNull(objs[81])?" ":(String)objs[81]);
//					dto.setIsRemarks(CommonUtil.isNull(objs[82])?" ":(String)objs[82]);	
//					dto.setCallType(CommonUtil.isNull(objs[83])?" ":(String)objs[83]);
//					dto.setIncidentdate(CommonUtil.isNull(objs[84])?" ":(String)objs[84]);
//					dto.setIncidentTime(CommonUtil.isNull(objs[85])?" ":(String)objs[85]);
//					dto.setEsIinformationSought(CommonUtil.isNull(objs[86])?" ":(String)objs[86]);
//					dto.setEsRiskAsses(CommonUtil.isNull(objs[87])?" ":(String)objs[87]);
//					dto.setEsAggrieved(CommonUtil.isNull(objs[88])?" ":(String)objs[88]);
//					dto.setEsOtherAggName(CommonUtil.isNull(objs[89])?" ":(String)objs[89]);
//					dto.setEsOtherAggMobile(CommonUtil.isNull(objs[90])?" ":(String)objs[90]);
//					dto.setEsOtherAggAddress(CommonUtil.isNull(objs[91])?" ":(String)objs[91]);
//					dto.setEsAge(CommonUtil.isNull(objs[92])?" ":(String)objs[92]);
//					dto.setEsAgeGroup(CommonUtil.isNull(objs[93])?" ":(String)objs[93]);
//					dto.setEsEducation(CommonUtil.isNull(objs[94])?" ":(String)objs[94]);
//					dto.setEsOccupation(CommonUtil.isNull(objs[95])?" ":(String)objs[95]);
//					dto.setEsGender(CommonUtil.isNull(objs[96])?" ":(String)objs[96]);
//					dto.setEsPersonalIdent(CommonUtil.isNull(objs[97])?" ":(String)objs[97]);
//					dto.setEsMaritalStatus(CommonUtil.isNull(objs[98])?" ":(String)objs[98]);
//					dto.setEsLivingStatus(CommonUtil.isNull(objs[99])?" ":(String)objs[99]);
//					dto.setEsFamilyStatus(CommonUtil.isNull(objs[100])?" ":(String)objs[100]);
//					dto.setEsHouseno(CommonUtil.isNull(objs[101])?" ":(String)objs[101]);
//					dto.setEsStreet(CommonUtil.isNull(objs[102])?" ":(String)objs[102]);
//					dto.setEsBlock(CommonUtil.isNull(objs[103])?" ":(String)objs[103]);
//					dto.setEsVillage(CommonUtil.isNull(objs[104])?" ":(String)objs[104]);
//					dto.setEsState(CommonUtil.isNull(objs[105])?" ":(String)objs[105]);
//					dto.setEsDistrict(CommonUtil.isNull(objs[106])?" ":(String)objs[106]);
//					dto.setEsPincode(CommonUtil.isNull(objs[107])?" ":(String)objs[107]);
//					dto.setEsPlaceofInc(CommonUtil.isNull(objs[108])?" ":(String)objs[108]);
//					dto.setEsFrequency(CommonUtil.isNull(objs[109])?" ":(String)objs[109]);
//					dto.setEsStatusofInc(CommonUtil.isNull(objs[110])?" ":(String)objs[110]);
//					dto.setEsCaseCat1(CommonUtil.isNull(objs[111])?" ":(String)objs[111]);
//					dto.setEsSubCat(CommonUtil.isNull(objs[112])?" ":(String)objs[112]);
//					dto.setEsTypeofAbuse(CommonUtil.isNull(objs[113])?" ":(String)objs[113]);
//					dto.setEsPriorRedressal(CommonUtil.isNull(objs[114])?" ":(String)objs[114]);
//					dto.setEsPerpetrator(CommonUtil.isNull(objs[115])?" ":(String)objs[115]);
//					dto.setEsServiceOffered(CommonUtil.isNull(objs[116])?" ":(String)objs[116]);
//					dto.setEsAddObtain(CommonUtil.isNull(objs[117])?" ":(String)objs[117]);
//					dto.setEsStatusofCase(CommonUtil.isNull(objs[118])?" ":(String)objs[118]);
//					dto.setEsAgency(CommonUtil.isNull(objs[119])?" ":(String)objs[119]);
//					dto.setEsNameofPerson(CommonUtil.isNull(objs[120])?" ":(String)objs[120]);
//					dto.setEsRemarks(CommonUtil.isNull(objs[121])?" ":(String)objs[121]);
//					dto.setEsPerpetratorName(CommonUtil.isNull(objs[122])?" ":(String)objs[122]);
//					dto.setEsPerpetratorAge(CommonUtil.isNull(objs[123])?" ":(String)objs[123]);
//					dto.setEsPerpetratorMobile(CommonUtil.isNull(objs[124])?" ":(String)objs[124]);
//					dto.setEsPerpetratorGender(CommonUtil.isNull(objs[125])?" ":(String)objs[125]);
//					dto.setEsPerpetratorOccup(CommonUtil.isNull(objs[126])?" ":(String)objs[126]);
//					dto.setEsPerpetratorAddition(CommonUtil.isNull(objs[127])?" ":(String)objs[127]);
//					dto.setRecPath(CommonUtil.isNull(objs[128])?null:(String)objs[128]);
//					dto.setCaseId(CommonUtil.isNull(objs[129])?null:(String)objs[129]);
//					dto.setFeedback(CommonUtil.isNull(objs[130])?null:(String)objs[130]);
//					try {
//					if(!CommonUtil.isNull(objs[131])) {
//						ringstart = LocalDateTime.parse(objs[131].toString().substring(0,19), formatter).minusHours(5);
//						ringstartconvert = ringstart.minusMinutes(30);
//						dto.setRingStartTime(ringstartconvert.toLocalTime().toString());	
//					
//					}
//					}catch(Exception e) {}
//					try {
//						if(!CommonUtil.isNull(objs[132])) {
//							ringend = LocalDateTime.parse(objs[132].toString().substring(0,19), formatter).minusHours(5);
//							ringendconvert = ringend.minusMinutes(30);
//							dto.setRingEndTime(ringendconvert.toLocalTime().toString());	
//						
//						}
//						}catch(Exception e) {}
//					try {
//						if(!CommonUtil.isNull(objs[136])) {
//							queuejoin = LocalDateTime.parse(objs[136].toString().substring(0,19), formatter).minusHours(5);
//							queuejoinconvert = queuejoin.minusMinutes(30);
//							dto.setQueueStartTime(queuejoinconvert.toLocalTime().toString());	
//						
//						}
//						}catch(Exception e) {}
//					
//					try {
//						if(!CommonUtil.isNull(objs[137])) {
//							queueleave = LocalDateTime.parse(objs[137].toString().substring(0,19), formatter).minusHours(5);
//							queueleaveconvert = queueleave.minusMinutes(30);
//							dto.setQueueEndTime(queueleaveconvert.toLocalTime().toString());	
//						
//						}
//						}catch(Exception e) {}
//					
//					try {
//						try {
//							if(objs[135]!=null) {
//								if(dto.getCallStatus().equalsIgnoreCase("answer")) {
//										dto.setDuration(CommonUtil.isNull(objs[135])?0:((int)objs[135]+1));
//								}else {
//									dto.setDuration(CommonUtil.isNull(objs[135])?0:(int)objs[135]);
//									
//								}
//								}
//						if(dto.getDuration()==0) {
//							if(startDateTimeDataConverted !=null && endDateTimeDataConverted !=null&&dto.getCallStatus().equalsIgnoreCase("answer")) {
//								Duration duration = Duration.between(startDateTimeDataConverted, endDateTimeDataConverted);					     
//								if(String.valueOf(duration.getSeconds()).startsWith("-")) {
//									duration = Duration.between(endDateTimeDataConverted, startDateTimeDataConverted);	
//								}
//								dto.setDuration((int)duration.getSeconds());
//								
//								//System.out.println(dto.getDuration());
//							}
//
//							
//						}
//						}catch(Exception e) {
//							e.printStackTrace();
//						}
//						try {
//							if(dto.getCallStatus().equalsIgnoreCase("answer")) {
//								dto.setRingTime(CommonUtil.isNull(objs[134])?null:((int)objs[134]));
//							}else {
//						dto.setRingTime(CommonUtil.isNull(objs[134])?null:(int)objs[134]);
//							}
//						}catch(Exception e) {}
//						dto.setQueueTime(CommonUtil.isNull(objs[133])?null:(int)objs[133]);				
//						
//						}catch(Exception e) {}
//					dto.setCallStartTimeOnly(startDateTimeDataConverted.toLocalTime().toString());
//					
//					if(endDateTimeDataConverted !=null) 
//						dto.setCallEndTimeOnly(endDateTimeDataConverted.toLocalTime().toString());
//					if(endDateTimeDataConverted ==null) 
//						dto.setCallEndTimeOnly("");
//					dispositionReportDTOs.add(dto);
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//			}
//		}
//		return dispositionReportDTOs;
//	}
	
	public List<TestDTO> resultSetInformation(List<Object> result,List<UserModel> um) {
		List<TestDTO> dispositionReportDTOs = new ArrayList<>();
		LocalDateTime startDateTimeDataConverted1 = null; 
		LocalDateTime endDateTimeDataConverted1 = null; 
		LocalDateTime startDateTimeDataConverted = null; 
		LocalDateTime endDateTimeDataConverted = null; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 Map<String,String> exts=new HashMap<String,String>();
		   for(UserModel uml:um) {
			exts.put(uml.getUserextension(), uml.getUsername());
		   }
//		if (CommonUtil.isListNotNullAndEmpty(result)) {
//			Iterator it = result.iterator();
//			while (it.hasNext()){
//				try {
//					startDateTimeDataConverted1 = null; 
//					endDateTimeDataConverted1 = null; 
//					Object[] objs = ( Object[]) it.next();
//					TestDTO dto=new TestDTO();
//					
//					dto.setId(CommonUtil.isNull(objs[0])?null:(int)objs[0]);
//					dto.setDocId(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
//					dto.setPhoneNo(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);
//					dto.setExtension(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
//					try {
//						String ext=(String)objs[3];
//						if(ext!=null&&!ext.isEmpty()) {
//						dto.setAgentName(exts.get((String)objs[3]));
//						}					}catch(Exception e) {}
//					dto.setCallStatus(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);						
//
//					//dto.setCallStartTime(CommonUtil.isNull(objs[7])?" ":objs[7].toString());
//
//
//					if(!CommonUtil.isNull(objs[7])) {
//						//startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
//						//dto.setCallStartTime(startDateTimeDataConverted1.toLocalDate().toString());
//						startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter).minusHours(5);
//						startDateTimeDataConverted = startDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallStartTime(startDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[7])) {
//						dto.setCallStartTime("");
//					}
//					//dto.setCallEndTime(CommonUtil.isNull(objs[8])?" ":objs[8].toString());
//					//System.out.println(objs[8]);
//
//					if(!CommonUtil.isNull(objs[8])) {
//						//endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter);
//						//dto.setCallEndTime(endDateTimeDataConverted1.toLocalDate().toString());
//						endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter).minusHours(5);
//						endDateTimeDataConverted = endDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallEndTime(endDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[8])) {
//						dto.setCallEndTime("");
//					}
//					
//					
//					dto.setCallConnectTime(CommonUtil.isNull(objs[9])?" ":objs[9].toString());	
//					dto.setDirection(CommonUtil.isNull(objs[13])?" ":(String)objs[13]);
//					
//					dto.setSecondNumber(CommonUtil.isNull(objs[14])?" ":(String)objs[14]);
//					dto.setHoldStartTime(CommonUtil.isNull(objs[15])?" ":objs[15].toString());
//					dto.setHoldEndTime(CommonUtil.isNull(objs[16])?" ":objs[16].toString());
//					
//					
//					dto.setIsName(CommonUtil.isNull(objs[17])?" ":(String)objs[17]);
//					dto.setIsAge(CommonUtil.isNull(objs[18])?" ":(String)objs[18]);
//					dto.setIsAgeGroup(CommonUtil.isNull(objs[19])?" ":(String)objs[19]);
//					dto.setIsEducation(CommonUtil.isNull(objs[20])?" ":(String)objs[20]);
//					dto.setIsGender(CommonUtil.isNull(objs[21])?" ":(String)objs[21]);
//					dto.setIsHouseno(CommonUtil.isNull(objs[22])?" ":(String)objs[22]);
//					dto.setIsStreet(CommonUtil.isNull(objs[23])?" ":(String)objs[23]);
//					dto.setIsBlock(CommonUtil.isNull(objs[24])?" ":(String)objs[24]);
//					dto.setIsVillage(CommonUtil.isNull(objs[25])?" ":(String)objs[25]);
//					dto.setIsState(CommonUtil.isNull(objs[26])?" ":(String)objs[26]);
//					dto.setIsDistrict(CommonUtil.isNull(objs[27])?" ":(String)objs[27]);
//					dto.setIsPincode(CommonUtil.isNull(objs[28])?" ":(String)objs[28]);
//					dto.setIsInformationSought(CommonUtil.isNull(objs[29])?" ":(String)objs[29]);
//					dto.setIsServiceoffered(CommonUtil.isNull(objs[30])?" ":(String)objs[30]);
//					dto.setIsAddObtainInfo(CommonUtil.isNull(objs[31])?" ":(String)objs[31]);
//					dto.setIsAgency(CommonUtil.isNull(objs[32])?" ":(String)objs[32]);
//					dto.setIsNameofPerson(CommonUtil.isNull(objs[33])?" ":(String)objs[33]);
//					dto.setIsContactNum(CommonUtil.isNull(objs[34])?" ":(String)objs[34]);
//					dto.setIsRemarks(CommonUtil.isNull(objs[35])?" ":(String)objs[35]);	
//					dto.setRecPath(CommonUtil.isNull(objs[36])?null:(String)objs[36]);
//					dto.setCaseId(CommonUtil.isNull(objs[37])?null:(String)objs[37]);
//					dto.setFeedback(CommonUtil.isNull(objs[38])?null:(String)objs[38]);
//					try {
//						try {
//							dto.setDuration(CommonUtil.isNull(objs[43])?null:(int)objs[43]);
//							if((int)objs[43]==0) {
//								if(startDateTimeDataConverted !=null && endDateTimeDataConverted !=null) {
//									Duration duration = Duration.between(startDateTimeDataConverted, endDateTimeDataConverted);					     
//									dto.setDuration((int)duration.getSeconds());
//									//System.out.println(dto.getDuration());
//								}
//
//								
//							}
//							dto.setRingTime(CommonUtil.isNull(objs[42])?null:(int)objs[42]);
//							dto.setQueueTime(CommonUtil.isNull(objs[41])?null:(int)objs[41]);				
//					
//							}catch(Exception e) {}
//					}catch(Exception e) {}
//					dto.setCallStartTimeOnly(startDateTimeDataConverted.toLocalTime().toString());
//					
//					if(endDateTimeDataConverted !=null) 
//						dto.setCallEndTimeOnly(endDateTimeDataConverted.toLocalTime().toString());
//					if(endDateTimeDataConverted ==null) 
//						dto.setCallEndTimeOnly("");
//					dispositionReportDTOs.add(dto);
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//			}
//		}
		return dispositionReportDTOs;
	}
	
//	public List<TestDTO> resultSetGuidance(List<Object> result,List<UserModel> um) {
//		List<TestDTO> dispositionReportDTOs = new ArrayList<>();
//		LocalDateTime startDateTimeDataConverted1 = null; 
//		LocalDateTime endDateTimeDataConverted1 = null; 
//		LocalDateTime startDateTimeDataConverted = null; 
//		LocalDateTime endDateTimeDataConverted = null; 
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		 Map<String,String> exts=new HashMap<String,String>();
//		   for(UserModel uml:um) {
//			exts.put(uml.getUserextension(), uml.getUsername());
//		   }
//		if (CommonUtil.isListNotNullAndEmpty(result)) {
//			Iterator it = result.iterator();
//			while (it.hasNext()){
//				try {
//					startDateTimeDataConverted1 = null; 
//					endDateTimeDataConverted1 = null; 
//					Object[] objs = ( Object[]) it.next();
//					TestDTO dto=new TestDTO();
//					dto.setId(CommonUtil.isNull(objs[0])?null:(int)objs[0]);
//					dto.setDocId(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
//					dto.setPhoneNo(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);
//					dto.setExtension(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
//					try {
//						String ext=(String)objs[3];
//						if(ext!=null&&!ext.isEmpty()) {
//						dto.setAgentName(exts.get((String)objs[3]));
//						}
//					}catch(Exception e) {}
//					dto.setCallStatus(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);						
//
//					//dto.setCallStartTime(CommonUtil.isNull(objs[7])?" ":objs[7].toString());
//
//
//					if(!CommonUtil.isNull(objs[7])) {
//						//startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
//						//dto.setCallStartTime(startDateTimeDataConverted1.toLocalDate().toString());
//						startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter).minusHours(5);
//						startDateTimeDataConverted = startDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallStartTime(startDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[7])) {
//						dto.setCallStartTime("");
//					}
//					//dto.setCallEndTime(CommonUtil.isNull(objs[8])?" ":objs[8].toString());
//					//System.out.println(objs[8]);
//
//					if(!CommonUtil.isNull(objs[8])) {
//						//endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter);
//						//dto.setCallEndTime(endDateTimeDataConverted1.toLocalDate().toString());
//						endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter).minusHours(5);
//						endDateTimeDataConverted = endDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallEndTime(endDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[8])) {
//						dto.setCallEndTime("");
//					}
//					if(startDateTimeDataConverted !=null && endDateTimeDataConverted !=null) {
//						Duration duration = Duration.between(startDateTimeDataConverted, endDateTimeDataConverted);					     
//						//dto.setDuration(duration.toHours() + "h " + duration.toMinutes() + "m");
//						//System.out.println(dto.getDuration());
//					}
//
//					if(startDateTimeDataConverted ==null || endDateTimeDataConverted ==null) {
//						//dto.setDuration("");
//					}
//					dto.setCallConnectTime(CommonUtil.isNull(objs[9])?" ":objs[9].toString());	
//					dto.setDirection(CommonUtil.isNull(objs[13])?" ":(String)objs[13]);
//					
//					dto.setSecondNumber(CommonUtil.isNull(objs[14])?" ":(String)objs[14]);
//					dto.setHoldStartTime(CommonUtil.isNull(objs[15])?" ":objs[15].toString());
//					dto.setHoldEndTime(CommonUtil.isNull(objs[16])?" ":objs[16].toString());
//						
//					
//					dto.setGsInformationSought(CommonUtil.isNull(objs[17])?" ":(String)objs[17]);
//					dto.setGsRiskAsses(CommonUtil.isNull(objs[18])?" ":(String)objs[18]);
//					dto.setGsAggrieved(CommonUtil.isNull(objs[19])?" ":(String)objs[19]);
//					dto.setGsOtherAggName(CommonUtil.isNull(objs[20])?" ":(String)objs[20]);
//					dto.setGsOtherAggMobile(CommonUtil.isNull(objs[21])?" ":(String)objs[21]);
//					dto.setGsOtherAggAge(CommonUtil.isNull(objs[22])?" ":(String)objs[22]);
//					dto.setGsOtherAggGender(CommonUtil.isNull(objs[23])?" ":(String)objs[23]);
//					dto.setGsAge(CommonUtil.isNull(objs[24])?" ":(String)objs[24]);
//					dto.setGsOtherAggAddress(CommonUtil.isNull(objs[25])?" ":(String)objs[25]);
//					dto.setGsAgeGroup(CommonUtil.isNull(objs[25])?" ":(String)objs[26]);
//					dto.setGsEducation(CommonUtil.isNull(objs[26])?" ":(String)objs[26]);
//					dto.setGsOccupation(CommonUtil.isNull(objs[27])?" ":(String)objs[27]);
//					dto.setGsGender(CommonUtil.isNull(objs[28])?" ":(String)objs[28]);
//					dto.setGsPersonalIdent(CommonUtil.isNull(objs[29])?" ":(String)objs[29]);
//					dto.setGsMaritalStatus(CommonUtil.isNull(objs[30])?" ":(String)objs[30]);
//					dto.setGsLivingStatus(CommonUtil.isNull(objs[31])?" ":(String)objs[31]);
//					dto.setGsFamilyStatus(CommonUtil.isNull(objs[32])?" ":(String)objs[32]);
//					dto.setGsHouseno(CommonUtil.isNull(objs[33])?" ":(String)objs[33]);
//					dto.setGsStreet(CommonUtil.isNull(objs[34])?" ":(String)objs[34]);
//					dto.setGsBock(CommonUtil.isNull(objs[35])?" ":(String)objs[35]);
//					dto.setGsVillage(CommonUtil.isNull(objs[36])?" ":(String)objs[36]);
//					dto.setGsState(CommonUtil.isNull(objs[37])?" ":(String)objs[37]);
//					dto.setGsDistrict(CommonUtil.isNull(objs[38])?" ":(String)objs[38]);
//					dto.setGsPincode(CommonUtil.isNull(objs[39])?" ":(String)objs[39]);
//					dto.setGsPlaceofInc(CommonUtil.isNull(objs[40])?" ":(String)objs[40]);
//					dto.setGsFrequency(CommonUtil.isNull(objs[41])?" ":(String)objs[41]);
//					dto.setGsStatusofInc(CommonUtil.isNull(objs[42])?" ":(String)objs[42]);
//					dto.setGsCaseCat1(CommonUtil.isNull(objs[43])?" ":(String)objs[43]);
//					dto.setGsSubCat(CommonUtil.isNull(objs[44])?" ":(String)objs[44]);
//					dto.setGsTypeofAbuse(CommonUtil.isNull(objs[45])?" ":(String)objs[45]);
//					dto.setGsPriorRedressal(CommonUtil.isNull(objs[46])?" ":(String)objs[46]);
//					dto.setGsPerpetrator(CommonUtil.isNull(objs[47])?" ":(String)objs[47]);
//					dto.setGsServiceOffered(CommonUtil.isNull(objs[48])?" ":(String)objs[48]);
//					dto.setGsAddObtain(CommonUtil.isNull(objs[49])?" ":(String)objs[49]);
//					dto.setGsAgency(CommonUtil.isNull(objs[50])?" ":(String)objs[50]);
//					dto.setGsNameofPerson(CommonUtil.isNull(objs[51])?" ":(String)objs[51]);
//					dto.setGsRemarks(CommonUtil.isNull(objs[52])?" ":(String)objs[52]);
//					dto.setGsStatusofCase(CommonUtil.isNull(objs[53])?" ":(String)objs[53]);
//					dto.setGsPerpetratorName(CommonUtil.isNull(objs[54])?" ":(String)objs[54]);
//					dto.setGsPerpetratorAge(CommonUtil.isNull(objs[55])?" ":(String)objs[55]);
//					dto.setGsPerpetratorGender(CommonUtil.isNull(objs[56])?" ":(String)objs[56]);
//					dto.setGsPerpetratorMobile(CommonUtil.isNull(objs[57])?" ":(String)objs[57]);
//					dto.setGsPerpetratorOccup(CommonUtil.isNull(objs[58])?" ":(String)objs[58]);
//					dto.setGsPerpetratorAddition(CommonUtil.isNull(objs[59])?" ":(String)objs[59]);
//					dto.setRecPath(CommonUtil.isNull(objs[60])?null:(String)objs[60]);
//					dto.setCaseId(CommonUtil.isNull(objs[61])?null:(String)objs[61]);
//					dto.setFeedback(CommonUtil.isNull(objs[62])?null:(String)objs[62]);
//					try {
//						try {
//							dto.setDuration(CommonUtil.isNull(objs[67])?null:(int)objs[67]);
//							if((int)objs[67]==0) {
//								if(startDateTimeDataConverted !=null && endDateTimeDataConverted !=null) {
//									Duration duration = Duration.between(startDateTimeDataConverted, endDateTimeDataConverted);					     
//									dto.setDuration((int)duration.getSeconds());
//									//System.out.println(dto.getDuration());
//								}
//
//								
//							}
//							dto.setRingTime(CommonUtil.isNull(objs[66])?null:(int)objs[66]);
//							dto.setQueueTime(CommonUtil.isNull(objs[65])?null:(int)objs[65]);				
//					
//							}catch(Exception e) {}
//					}catch(Exception e) {}
//					dto.setCallStartTimeOnly(startDateTimeDataConverted.toLocalTime().toString());
//					
//					if(endDateTimeDataConverted !=null) 
//						dto.setCallEndTimeOnly(endDateTimeDataConverted.toLocalTime().toString());
//					if(endDateTimeDataConverted ==null) 
//						dto.setCallEndTimeOnly("");
//					dispositionReportDTOs.add(dto);
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//			}
//		}
//		return dispositionReportDTOs;
//	}
	
//	public List<TestDTO> resultSetEmergency(List<Object> result,List<UserModel> um) {
//		List<TestDTO> dispositionReportDTOs = new ArrayList<>();
//		LocalDateTime startDateTimeDataConverted1 = null; 
//		LocalDateTime endDateTimeDataConverted1 = null; 
//		LocalDateTime startDateTimeDataConverted = null; 
//		LocalDateTime endDateTimeDataConverted = null; 
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		 Map<String,String> exts=new HashMap<String,String>();
//		   for(UserModel uml:um) {
//			exts.put(uml.getUserextension(), uml.getUsername());
//		   }
//		if (CommonUtil.isListNotNullAndEmpty(result)) {
//			Iterator it = result.iterator();
//			while (it.hasNext()){
//				try {
//					startDateTimeDataConverted1 = null; 
//					endDateTimeDataConverted1 = null; 
//					Object[] objs = ( Object[]) it.next();
//					TestDTO dto=new TestDTO();
//					dto.setId(CommonUtil.isNull(objs[0])?null:(int)objs[0]);
//					dto.setDocId(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
//					dto.setPhoneNo(CommonUtil.isNull(objs[2])?" ":(String)objs[2]);
//					dto.setExtension(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);	
//					try {
//						String ext=(String)objs[3];
//						if(ext!=null&&!ext.isEmpty()) {
//						dto.setAgentName(exts.get((String)objs[3]));
//						}
//					}catch(Exception e) {}
//					dto.setCallStatus(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);						
//
//					//dto.setCallStartTime(CommonUtil.isNull(objs[7])?" ":objs[7].toString());
//
//
//					if(!CommonUtil.isNull(objs[7])) {
//						//startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
//						//dto.setCallStartTime(startDateTimeDataConverted1.toLocalDate().toString());
//						startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter).minusHours(5);
//						startDateTimeDataConverted = startDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallStartTime(startDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[7])) {
//						dto.setCallStartTime("");
//					}
//					//dto.setCallEndTime(CommonUtil.isNull(objs[8])?" ":objs[8].toString());
//					//System.out.println(objs[8]);
//
//					if(!CommonUtil.isNull(objs[8])) {
//						//endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter);
//						//dto.setCallEndTime(endDateTimeDataConverted1.toLocalDate().toString());
//						endDateTimeDataConverted1 = LocalDateTime.parse(objs[8].toString().substring(0,16), formatter).minusHours(5);
//						endDateTimeDataConverted = endDateTimeDataConverted1.minusMinutes(30);
//						dto.setCallEndTime(endDateTimeDataConverted.toLocalDate().toString());
//					}
//
//					if(CommonUtil.isNull(objs[8])) {
//						dto.setCallEndTime("");
//					}
//					if(startDateTimeDataConverted !=null && endDateTimeDataConverted !=null) {
//						Duration duration = Duration.between(startDateTimeDataConverted, endDateTimeDataConverted);					     
//					//	dto.setDuration(duration.toHours() + "h " + duration.toMinutes() + "m");
//						//System.out.println(dto.getDuration());
//					}
//
//					if(startDateTimeDataConverted ==null || endDateTimeDataConverted ==null) {
//					//	dto.setDuration("");
//					}
//					dto.setCallConnectTime(CommonUtil.isNull(objs[9])?" ":objs[9].toString());	
//					dto.setDirection(CommonUtil.isNull(objs[13])?" ":(String)objs[13]);
//					
//					dto.setSecondNumber(CommonUtil.isNull(objs[14])?" ":(String)objs[14]);
//					dto.setHoldStartTime(CommonUtil.isNull(objs[15])?" ":objs[15].toString());
//					dto.setHoldEndTime(CommonUtil.isNull(objs[16])?" ":objs[16].toString());
//					
//					dto.setEsIinformationSought(CommonUtil.isNull(objs[20])?" ":(String)objs[20]);
//					dto.setEsRiskAsses(CommonUtil.isNull(objs[21])?" ":(String)objs[21]);
//					dto.setEsAggrieved(CommonUtil.isNull(objs[22])?" ":(String)objs[22]);
//					dto.setEsOtherAggName(CommonUtil.isNull(objs[23])?" ":(String)objs[23]);
//					dto.setEsOtherAggMobile(CommonUtil.isNull(objs[24])?" ":(String)objs[24]);
//					dto.setEsOtherAggAddress(CommonUtil.isNull(objs[25])?" ":(String)objs[25]);
//					dto.setEsAge(CommonUtil.isNull(objs[26])?" ":(String)objs[26]);
//					dto.setEsAgeGroup(CommonUtil.isNull(objs[27])?" ":(String)objs[27]);
//					dto.setEsEducation(CommonUtil.isNull(objs[28])?" ":(String)objs[28]);
//					dto.setEsOccupation(CommonUtil.isNull(objs[29])?" ":(String)objs[29]);
//					dto.setEsGender(CommonUtil.isNull(objs[30])?" ":(String)objs[30]);
//					dto.setEsPersonalIdent(CommonUtil.isNull(objs[31])?" ":(String)objs[31]);
//					dto.setEsMaritalStatus(CommonUtil.isNull(objs[32])?" ":(String)objs[32]);
//					dto.setEsLivingStatus(CommonUtil.isNull(objs[33])?" ":(String)objs[33]);
//					dto.setEsFamilyStatus(CommonUtil.isNull(objs[34])?" ":(String)objs[34]);
//					dto.setEsHouseno(CommonUtil.isNull(objs[35])?" ":(String)objs[35]);
//					dto.setEsStreet(CommonUtil.isNull(objs[36])?" ":(String)objs[36]);
//					dto.setEsBlock(CommonUtil.isNull(objs[37])?" ":(String)objs[37]);
//					dto.setEsVillage(CommonUtil.isNull(objs[38])?" ":(String)objs[38]);
//					dto.setEsState(CommonUtil.isNull(objs[39])?" ":(String)objs[39]);
//					dto.setEsDistrict(CommonUtil.isNull(objs[40])?" ":(String)objs[40]);
//					dto.setEsPincode(CommonUtil.isNull(objs[41])?" ":(String)objs[41]);
//					dto.setEsPlaceofInc(CommonUtil.isNull(objs[42])?" ":(String)objs[42]);
//					dto.setEsFrequency(CommonUtil.isNull(objs[43])?" ":(String)objs[43]);
//					dto.setEsStatusofInc(CommonUtil.isNull(objs[44])?" ":(String)objs[44]);
//					dto.setEsCaseCat1(CommonUtil.isNull(objs[45])?" ":(String)objs[45]);
//					dto.setEsSubCat(CommonUtil.isNull(objs[46])?" ":(String)objs[46]);
//					dto.setEsTypeofAbuse(CommonUtil.isNull(objs[47])?" ":(String)objs[47]);
//					dto.setEsPriorRedressal(CommonUtil.isNull(objs[48])?" ":(String)objs[48]);
//					dto.setEsPerpetrator(CommonUtil.isNull(objs[49])?" ":(String)objs[49]);
//					dto.setEsServiceOffered(CommonUtil.isNull(objs[50])?" ":(String)objs[50]);
//					dto.setEsAddObtain(CommonUtil.isNull(objs[51])?" ":(String)objs[51]);
//					dto.setEsStatusofCase(CommonUtil.isNull(objs[52])?" ":(String)objs[52]);
//					dto.setEsAgency(CommonUtil.isNull(objs[53])?" ":(String)objs[53]);
//					dto.setEsNameofPerson(CommonUtil.isNull(objs[54])?" ":(String)objs[54]);
//					dto.setEsRemarks(CommonUtil.isNull(objs[55])?" ":(String)objs[55]);
//					dto.setEsPerpetratorName(CommonUtil.isNull(objs[56])?" ":(String)objs[56]);
//					dto.setEsPerpetratorAge(CommonUtil.isNull(objs[57])?" ":(String)objs[57]);
//					dto.setEsPerpetratorMobile(CommonUtil.isNull(objs[58])?" ":(String)objs[58]);
//					dto.setEsPerpetratorGender(CommonUtil.isNull(objs[59])?" ":(String)objs[59]);
//					dto.setEsPerpetratorOccup(CommonUtil.isNull(objs[60])?" ":(String)objs[60]);
//					dto.setEsPerpetratorAddition(CommonUtil.isNull(objs[61])?" ":(String)objs[61]);
//					dto.setRecPath(CommonUtil.isNull(objs[62])?null:(String)objs[62]);
//					dto.setCaseId(CommonUtil.isNull(objs[63])?null:(String)objs[63]);
//					dto.setFeedback(CommonUtil.isNull(objs[64])?null:(String)objs[64]);
//					try {
//						try {
//							dto.setDuration(CommonUtil.isNull(objs[69])?null:(int)objs[69]);
//							if((int)objs[69]==0) {
//								if(startDateTimeDataConverted !=null && endDateTimeDataConverted !=null) {
//									Duration duration = Duration.between(startDateTimeDataConverted, endDateTimeDataConverted);					     
//									dto.setDuration((int)duration.getSeconds());
//									//System.out.println(dto.getDuration());
//								}
//
//								
//							}
//							dto.setRingTime(CommonUtil.isNull(objs[68])?null:(int)objs[68]);
//							dto.setQueueTime(CommonUtil.isNull(objs[67])?null:(int)objs[67]);				
//					
//							}catch(Exception e) {}
//					}catch(Exception e) {}
//					dto.setCallStartTimeOnly(startDateTimeDataConverted.toLocalTime().toString());
//					
//					if(endDateTimeDataConverted !=null) 
//						dto.setCallEndTimeOnly(endDateTimeDataConverted.toLocalTime().toString());
//					if(endDateTimeDataConverted ==null) 
//						dto.setCallEndTimeOnly("");
//					dispositionReportDTOs.add(dto);
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//			}
//		}
//		return dispositionReportDTOs;
//	}


	public Query reportQuerySeniorCitizen() {
		 Query query =  entityManager.createNativeQuery("SELECT  r.doc_Id,r.phone_No,r.call_Start_Time,r.case_Id,g.gs_district,i.is_name, i.is_age, i.is_district,e.es_district,c.call_type FROM eupraxia_report r "				
				 + "LEFT JOIN  guidence_case_details g ON r.doc_Id = g.call_id "
				 + "LEFT JOIN information_case_details i ON r.doc_Id = i.call_id "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "
				 + "LEFT JOIN emergency_case_details e  ON r.doc_Id = e.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND i.is_age>=60 ORDER BY r.call_Start_Time ASC");

		 return query;

	}


	public List<SeniorCitizenReportDTO> resultSetSeniorCitizen(List<Object> list) {
		List<SeniorCitizenReportDTO> res=new ArrayList<SeniorCitizenReportDTO>();
		LocalDateTime startDateTimeDataConverted1 = null; 
		LocalDateTime endDateTimeDataConverted1 = null; 
		LocalDateTime startDateTimeDataConverted = null; 
		LocalDateTime endDateTimeDataConverted = null; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		if (CommonUtil.isListNotNullAndEmpty(list)) {
			Iterator it = list.iterator();
			
			while (it.hasNext()){
				try {
					Object[] objs = ( Object[]) it.next();
					SeniorCitizenReportDTO seniorCitizenReportDTO=new SeniorCitizenReportDTO();
					seniorCitizenReportDTO.setCaseId(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
					seniorCitizenReportDTO.setCallerName(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);
					seniorCitizenReportDTO.setAge(CommonUtil.isNull(objs[6])?" ":(String)objs[6]);
					if(!CommonUtil.isNull(objs[2])) {
						//startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
						//dto.setCallStartTime(startDateTimeDataConverted1.toLocalDate().toString());
						startDateTimeDataConverted1 = LocalDateTime.parse(objs[2].toString().substring(0,16), formatter).minusHours(5);
						startDateTimeDataConverted = startDateTimeDataConverted1.minusMinutes(30);
						seniorCitizenReportDTO.setCallDate(startDateTimeDataConverted.toLocalDate().toString());
					}
					
					seniorCitizenReportDTO.setPhoneNo(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
					seniorCitizenReportDTO.setCaseType(CommonUtil.isNull(objs[9])?" ":(String)objs[9]);
					seniorCitizenReportDTO.setDistrict(CommonUtil.isNull(objs[7])?CommonUtil.isNull(objs[8])?CommonUtil.isNull(objs[4])?"":(String)objs[4]:(String)objs[8]:(String)objs[7]);
					res.add(seniorCitizenReportDTO);
				}catch(Exception e) {}
		}
		}
		return res;
	}


	public Query reportQueryForAgent() {
		 Query query =  entityManager.createNativeQuery("SELECT i.is_houseno,i.is_street,i.is_block,i.is_village,i.is_state,r.phone_No,r.call_Start_Time,r.case_Id,g.gs_district, i.is_district,e.es_district,c.call_type,r.extension FROM eupraxia_report r "				
				 + "LEFT JOIN  guidence_case_details g ON r.doc_Id = g.call_id "
				 + "LEFT JOIN information_case_details i ON r.doc_Id = i.call_id "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "
				 + "LEFT JOIN emergency_case_details e  ON r.doc_Id = e.call_id "
				// +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.extension= :extension ORDER BY r.call_Start_Time ASC");
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.extension IS NOT NULL ORDER BY r.call_Start_Time ASC");

		 return query;
	}


	public List<AgentWiseDataDTO> resultSetAgent(List<Object> list) {
		List<AgentWiseDataDTO> res=new ArrayList<AgentWiseDataDTO>();
		LocalDateTime startDateTimeDataConverted1 = null; 
		LocalDateTime endDateTimeDataConverted1 = null; 
		LocalDateTime startDateTimeDataConverted = null; 
		LocalDateTime endDateTimeDataConverted = null; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		if (CommonUtil.isListNotNullAndEmpty(list)) {
			Iterator it = list.iterator();
			
			while (it.hasNext()){
				try {
					Object[] objs = ( Object[]) it.next();
					AgentWiseDataDTO awd=new AgentWiseDataDTO();
					awd.setCaseId(CommonUtil.isNull(objs[7])?" ":(String)objs[7]);
					awd.setDistrict(CommonUtil.isNull(objs[8])?CommonUtil.isNull(objs[9])?CommonUtil.isNull(objs[10])?"":(String)objs[10]:(String)objs[9]:(String)objs[8]);
					awd.setCaseType(CommonUtil.isNull(objs[11])?" ":(String)objs[11]);
					awd.setMobileNumber(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);
					awd.setExtension(CommonUtil.isNull(objs[12])?" ":(String)objs[12]);
					if(!CommonUtil.isNull(objs[6])) {
						//startDateTimeDataConverted1 = LocalDateTime.parse(objs[7].toString().substring(0,16), formatter);
						//dto.setCallStartTime(startDateTimeDataConverted1.toLocalDate().toString());
						startDateTimeDataConverted1 = LocalDateTime.parse(objs[6].toString().substring(0,16), formatter).minusHours(5);
						startDateTimeDataConverted = startDateTimeDataConverted1.minusMinutes(30);
						awd.setSubmitOn(startDateTimeDataConverted.toLocalDate().toString());
					}
					String address="";
					try {
						if(!CommonUtil.isNull(objs[0])) {
							address=address.concat((String)objs[0]).concat(",");
						}
						if(!CommonUtil.isNull(objs[1])) {
							address=address.concat((String)objs[1]).concat(",");
						}
						if(!CommonUtil.isNull(objs[2])) {
							address=address.concat((String)objs[2]).concat(",");
						}
						if(!CommonUtil.isNull(objs[3])) {
							address=address.concat((String)objs[3]).concat(",");
						}
						if(!CommonUtil.isNull(objs[4])) {
							address=address.concat((String)objs[4]);
						}
					
						
						
					}catch(Exception e) {}
					awd.setIdentication(address);
					
					
					
					res.add(awd);
				}catch(Exception e) {}
			}
			}
			return res;
	}


	public Query queryForPhoneNumberLimit() {
		 Query query =  entityManager.createNativeQuery("SELECT r.case_Id,i.is_name,e.es_age,r.phone_No,c.incident_date,e.es_typeof_abuse,e.es_aggrieved,g.gs_aggrieved,i.is_age,g.gs_age FROM eupraxia_report r" + 
		 					 " LEFT JOIN  guidence_case_details g ON r.doc_Id = g.call_id" + 
		 						 " LEFT JOIN information_case_details i ON r.doc_Id = i.call_id" + 
		 						 " LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id" + 
		 						 " LEFT JOIN emergency_case_details e  ON r.doc_Id = e.call_id " + 
		 						 " WHERE r.phone_No = :phoneNumber ORDER BY r.call_Start_Time DESC");			
				
		 return query;
	}
	


	public ArrayList<HistoryReport> phoneNumberLimitResultSet(List<Object> list) {
		ArrayList<HistoryReport> res=new ArrayList<HistoryReport>();
		if (CommonUtil.isListNotNullAndEmpty(list)) {
			Iterator it = list.iterator();
			
			while (it.hasNext()){
				try {
					Object[] objs = ( Object[]) it.next();
					HistoryReport reportsModel=new HistoryReport();
					
					reportsModel.setAbuseType(CommonUtil.isNull(objs[5])?" ":(String)objs[5]);
					reportsModel.setAge(CommonUtil.isNull(objs[2])?CommonUtil.isNull(objs[8])?CommonUtil.isNull(objs[9])?" ":(String)objs[9]:(String)objs[8]:(String)objs[2]);
					reportsModel.setName(CommonUtil.isNull(objs[1])?" ":(String)objs[1]);
					reportsModel.setPhoneNo(CommonUtil.isNull(objs[3])?" ":(String)objs[3]);
					reportsModel.setIncidentDate(CommonUtil.isNull(objs[4])?" ":(String)objs[4]);
					reportsModel.setCaseId(CommonUtil.isNull(objs[0])?" ":(String)objs[0]);
					reportsModel.setAggrieved(CommonUtil.isNull(objs[6])?CommonUtil.isNull(objs[7])?" ":(String)objs[7]:(String)objs[6]);
				res.add(reportsModel);
				}catch(Exception e) {}
			}
			}
		
		return res;
	}


	public Query queryForIdPhoneNumberLimit() {
		 Query query =  entityManager.createNativeQuery("SELECT r.id FROM eupraxia_report r WHERE r.phone_No = :phoneNumber ORDER BY r.call_Start_Time DESC");			
			
		 return query;
	}


	public Query reportQueryAllForOutgoing() {
		// TODO Auto-generated method stub
		Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time, r.disposition, r.comments, r.callback_Date, "
				 + "g.gs_information_sought, g.gs_risk_asses, g.gs_aggrieved, g.gs_other_agg_name, g.gs_other_agg_mobile, g.gs_other_agg_age, "
				 + "g.gs_other_agg_gender, g.gs_age, g.gs_other_agg_address, g.gs_age_group, g.gs_education, g.gs_occupation, g.gs_gender,"
				 + " g.gs_personal_ident, g.gs_marital_status, g.gs_living_status, g.gs_family_status, g.gs_houseno, g.gs_street, g.gs_block, "
				 + "g.gs_village, g.gs_state, g.gs_district, g.gs_pincode, g.gs_placeof_inc, g.gs_frequency, g.gs_statusof_inc, g.gs_case_cat1, "
				 + "g.gs_sub_cat, g.gs_typeof_abuse, g.gs_prior_redressal, g.gs_perpetrator, g.gs_service_offered, g.gs_add_obtain,  "
				 + "g.gs_agency, g.gs_nameof_person, g.gs_remarks, g.gs_statusof_case,  g.gs_perpetrator_name, "
				 + "g.gs_perpetrator_age, g.gs_perpetrator_gender, g.gs_perpetrator_mobile, g.gs_perpetrator_occup, g.gs_perpetrator_addition, "
				 + "i.is_name, i.is_age, i.is_age_group, i.is_education, i.is_gender, i.is_houseno, i.is_street, "
				 + "i.is_block, i.is_village, i.is_state, i.is_district, i.is_pincode, i.is_information_sought, i.is_service_offered, "
				 + "i.is_add_obtain_info, i.is_agency, i.is_nameof_person, i.is_contact_num, i.is_remarks,"
				 + "c.call_type, c.incident_date, c.incident_time,  e.es_information_sought,"
				 + "e.es_risk_asses,e.es_aggrieved,e.es_other_agg_name,e.es_other_agg_mobile,"
				 + "e.es_other_agg_address,e.es_age,e.es_age_group,e.es_education,e.es_occupation,e.es_gender,"
				 + "e.es_person_alident,e.es_marital_status,e.es_living_status,e.es_family_status,e.es_houseno,e.es_street,e.es_block,"
				 + "e.es_village,e.es_state,e.es_district,e.es_pincode,e.es_placeof_inc,e.es_frequency,e.es_statusof_inc,e.es_casecat1,"
				 + "e.es_subcat,e.es_typeof_abuse,e.es_priorred_ressal,e.es_perpetrator,e.es_service_offered,e.es_addobtain,e.es_statusof_case,"
				 + "e.es_agency,e.es_nameof_Person,e.es_remarks,e.es_perpetrator_name,e.es_perpetrator_age,e.es_perpetrator_gender,"
				 + "e.es_perpetrator_mobile,e.es_perpetrator_occup,e.es_perpetrator_addition,r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration FROM eupraxia_report r "				
				 + "LEFT JOIN  guidence_case_details g ON r.doc_Id = g.call_id "
				 + "LEFT JOIN information_case_details i ON r.doc_Id = i.call_id "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "
				 + "LEFT JOIN emergency_case_details e  ON r.doc_Id = e.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='OutBound' ORDER BY r.call_Start_Time ASC");

		 return query;

	}


	public Query reportQueryInformationForOutgoing() {
		// TODO Auto-generated method stub
		 Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time,  "				 
				 + "i.is_name, i.is_age, i.is_age_group, i.is_education, i.is_gender, i.is_houseno, i.is_street, "
				 + "i.is_block, i.is_village, i.is_state, i.is_district, i.is_pincode, i.is_information_sought, i.is_service_offered, "
				 + "i.is_add_obtain_info, i.is_agency, i.is_nameof_person, i.is_contact_num, i.is_remarks,"
				 + "r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration FROM eupraxia_report r "				 
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "				
				 + "INNER JOIN information_case_details i ON r.doc_Id = i.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='OutBound' ORDER BY r.call_Start_Time ASC");
		 return query;
	}


	public Query reportQueryGuidanceForOutgoing() {
		 Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time,  "
				 + "g.gs_information_sought, g.gs_risk_asses, g.gs_aggrieved, g.gs_other_agg_name, g.gs_other_agg_mobile, g.gs_other_agg_age, "
				 + "g.gs_other_agg_gender, g.gs_age, g.gs_other_agg_address, g.gs_age_group, g.gs_education, g.gs_occupation, g.gs_gender,"
				 + " g.gs_personal_ident, g.gs_marital_status, g.gs_living_status, g.gs_family_status, g.gs_houseno, g.gs_street, g.gs_block, "
				 + "g.gs_village, g.gs_state, g.gs_district, g.gs_pincode, g.gs_placeof_inc, g.gs_frequency, g.gs_statusof_inc, g.gs_case_cat1, "
				 + "g.gs_sub_cat, g.gs_typeof_abuse, g.gs_prior_redressal, g.gs_perpetrator, g.gs_service_offered, g.gs_add_obtain,  "
				 + "g.gs_agency, g.gs_nameof_person, g.gs_remarks, g.gs_statusof_case,  g.gs_perpetrator_name, "
				 + "g.gs_perpetrator_age, g.gs_perpetrator_gender, g.gs_perpetrator_mobile, g.gs_perpetrator_occup, g.gs_perpetrator_addition, "				
				 + "c.call_type, c.incident_date, c.incident_time,  r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration FROM eupraxia_report r "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "				
				 + "INNER JOIN  guidence_case_details g ON r.doc_Id = g.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND e.call_direction='OutBound' ORDER BY r.call_Start_Time ASC");
		 return query;
	}


	public Query reportQueryEmergencyForOutgoing() {
		Query query =  entityManager.createNativeQuery("SELECT r.id, r.doc_Id,  r.phone_No, r.extension, r.is_Closed, r.call_Status, "
				 + "r.popup_Status, r.call_Start_Time, r.call_End_Time, "
				 + "r.Call_Connect_Time, r.trunk_Channel, r.sip_Channel, r.second_Channel, "
				 + "r.call_Direction, r.second_Number, r.Hold_Start_Time, r.Hold_End_Time,  "
				 + " c.call_type, c.incident_date, c.incident_time,e.es_information_sought,"
				 + "e.es_risk_asses,e.es_aggrieved,e.es_other_agg_name,e.es_other_agg_mobile,"
				 + "e.es_other_agg_address,e.es_age,e.es_age_group,e.es_education,e.es_occupation,e.es_gender,"
				 + "e.es_person_alident,e.es_marital_status,e.es_living_status,e.es_family_status,e.es_houseno,e.es_street,e.es_block,"
				 + "e.es_village,e.es_state,e.es_district,e.es_pincode,e.es_placeof_inc,e.es_frequency,e.es_statusof_inc,e.es_casecat1,"
				 + "e.es_subcat,e.es_typeof_abuse,e.es_priorred_ressal,e.es_perpetrator,e.es_service_offered,e.es_addobtain,e.es_statusof_case,"
				 + "e.es_agency,e.es_nameof_Person,e.es_remarks,e.es_perpetrator_name,e.es_perpetrator_age,e.es_perpetrator_gender,"
				 + "e.es_perpetrator_mobile,e.es_perpetrator_occup,e.es_perpetrator_addition,r.rec_path,r.case_Id,r.feedback,r.ring_Start_Time,r.ring_End_Time,r.queue_duration,r.ring_duration,r.call_duration FROM eupraxia_report r "
				 + "LEFT JOIN call_case_details c ON  r.doc_Id = c.call_id "
				 + "INNER JOIN emergency_case_details e  ON r.doc_Id = e.call_id "
				 +  "WHERE CAST(call_Start_Time AS DATE) BETWEEN :startDate AND :endDate AND r.call_direction='OutBound' ORDER BY r.call_Start_Time ASC");

		 return query;
	}

	

	
}
