package com.eupraxia.telephony.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.eupraxia.telephony.Model.CallPropertiesModel;
import com.eupraxia.telephony.components.JwtToken;
import com.eupraxia.telephony.constants.WebStreamProperties;
import com.eupraxia.telephony.service.CallService;

@CrossOrigin
@RestController
@RequestScope
@RequestMapping("/stream")
public class WebStreamController {
	@Autowired
	CallService callService;
	
	@Autowired
	JwtToken jwtToken;
	
	@GetMapping("/testApi")
	public String testApi() {
		callService.postData(new CallPropertiesModel());
		return jwtToken.getToken();
	}
	
	@GetMapping("/getStreamApis")
	public ResponseEntity<?> getStreamApis() {
		Map<Object, Object> map = new HashMap<>();
		map.put("StreamApi", WebStreamProperties.WebStreamUrl);
		return new ResponseEntity(map, HttpStatus.OK); 
	}
}
