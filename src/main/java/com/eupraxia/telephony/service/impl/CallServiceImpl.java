package com.eupraxia.telephony.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.eupraxia.telephony.DTO.CallPropertiesDTO;
import com.eupraxia.telephony.DTO.PostDataDTO;
import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.Model.CallPropertiesTestingModel;
import com.eupraxia.telephony.components.JwtToken;
import com.eupraxia.telephony.components.TelephonyManagerConnection;
import com.eupraxia.telephony.constants.WebStreamProperties;
import com.eupraxia.telephony.Model.CallLogsModel;
import com.eupraxia.telephony.repositories.CallPropertiesRepository;
import com.eupraxia.telephony.repositories.CallPropertiesTestingRepository;
import com.eupraxia.telephony.service.CallService;

import reactor.core.publisher.Mono;


@Service
@CacheConfig(cacheNames={"callProperties"})   
public class CallServiceImpl implements CallService{
	@Autowired
	CallPropertiesRepository callPropertiesRepository;
	
	@Autowired
	JwtToken jwtToken;;
	
	@Autowired
	CallPropertiesTestingRepository callPropertiesTestingRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void saveOrUpdate(CallPropertiesModel callPropertiesModel) {
		callPropertiesRepository.save(callPropertiesModel);
		try {
			this.postData(callPropertiesModel);
		}catch(Exception e) {e.printStackTrace();}
	}
	
	@Override
	public CallPropertiesModel findById(String id) {
		// TODO Auto-generated method stub
		ObjectId objId=new ObjectId(id);
		return callPropertiesRepository.findById(objId);
	}

	
	
	public CallPropertiesModel initCallData(CallPropertiesDTO callPropertiesDTO) {
		CallPropertiesModel callPropertiesModel = new CallPropertiesModel();
		CallPropertiesModel callPropertiesModel1 = new CallPropertiesModel();	
		
		String deleteStatus = "";
		callPropertiesModel = modelMapper.map(callPropertiesDTO, CallPropertiesModel.class);
		
		List<CallPropertiesModel> callPropertiesModelPrevious=new ArrayList<>();
		callPropertiesModelPrevious = findAllByExtension(callPropertiesDTO.getExtension());		
		deleteStatus = deleteCallProperties(callPropertiesModelPrevious);
		
		callPropertiesModelPrevious = findAllByExtension("");
		deleteStatus = deleteCallProperties(callPropertiesModelPrevious);
		
		if(!deleteStatus.contentEquals("Error")) {
			callPropertiesModel1=callPropertiesRepository.save(callPropertiesModel);	
		}
		return callPropertiesModel1;
		
	}
	
	
	public CallPropertiesModel checkPhoneNo(String phoneNo) {
		return callPropertiesRepository.findByPhoneNo(phoneNo);
	}
	
	public void delete(CallPropertiesModel callPropertiesModel) {
		callPropertiesRepository.deleteById(callPropertiesModel.getId());
	}
		
	public List<CallPropertiesModel> findAllCallProperties() {
        return callPropertiesRepository.findAll();
    }

	@Override
	public void initCallDataLog(CallPropertiesDTO callPropertiesDTO) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public CallPropertiesModel checkOutgoingOrNot(String uniqueId) {
		return callPropertiesRepository.findById(new ObjectId(uniqueId));
	}


	@Override
	public CallPropertiesModel findByChannelsIn(String channel) {
		return callPropertiesRepository.findByChannelsIn(channel);
	}


	@Override
	public CallPropertiesModel findBySecondNumber(String secondnumber) {
	//	System.out.println("Hi");
		return callPropertiesRepository.findBySecondNumber(secondnumber);
		
	}


	@Override
	public CallPropertiesModel findBySecondChannel(String sencondChannel) {
		return callPropertiesRepository.findBySecondChannel(sencondChannel);
	}


	@Override
	public CallPropertiesModel findByPhoneNo(String phoneNumber) {
		return callPropertiesRepository.findByPhoneNo(phoneNumber);
		
	}

	@Override
	public CallPropertiesModel deleteById(String callId) {
		return callPropertiesRepository.deleteById(new ObjectId(callId));
		
	}

	@Override
	public CallPropertiesModel findByExtension(String extension) {
		return callPropertiesRepository.findByExtension(extension);
	}

	@Override
	public ArrayList<CallPropertiesModel> findByPopupStatus(String popupstatus) {		
		return callPropertiesRepository.findByPopupStatus(popupstatus);
	}

	/*public CallPropertiesModel findByPhoneNoAndIsClosed(String phoneNumber) {
		
		return callPropertiesRepository.findByPhoneNoAndIsClosed(phoneNumber,"0");
	}*/
	
	@Override
    public CallPropertiesModel findByPhoneNoAndIsClosed(String phoneNumber) {
		
		return callPropertiesRepository.findByPhoneNoAndIsClosed(phoneNumber,"0");
	}
	

	@Override
	public List<CallPropertiesModel> findByAll() {
		// TODO Auto-generated method stub
		return callPropertiesRepository.findAll();
	}

	@Override
	public List<CallPropertiesModel> findAllByExtension(String extension) {
		// TODO Auto-generated method stub
		return callPropertiesRepository.findAllByExtension(extension);
	}
	
	private String deleteCallProperties(List<CallPropertiesModel> callPropertiesList) {		
		try {
			for(CallPropertiesModel cpm:callPropertiesList) {
				deleteById(cpm.getId());
			}
		}catch(Exception e) {
			return "Error";			
		}
		return "Success";
	}
	
	
	@Override
	public void postData(CallPropertiesModel callPropertiesModel) {
		String json=null;
		try {
		    PostDataDTO data=new PostDataDTO();
		data.setPayLoadId(callPropertiesModel.hashCode());
        data.setSource("admin");
        data.setDestSubscriberId(callPropertiesModel.getExtension());
        data.setTopicName("topicname");
        data.setAccountId("accid");
        data.setMessage(callPropertiesModel);
        data.setMsgPostedOn("");
		WebClient client=WebClient.builder().baseUrl(WebStreamProperties.PostDataUrl).build();
		json=client.post().contentType(MediaType.APPLICATION_JSON).header("Authorization","Bearer "+jwtToken.getToken())
					.body(Mono.just(data), PostDataDTO.class)
					.retrieve()
					.bodyToMono(String.class).block();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(json);
		
		}
	}


}
