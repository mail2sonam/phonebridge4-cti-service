package com.eupraxia.telephony.rest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eupraxia.telephony.DTO.ServerDTO;
import com.eupraxia.telephony.Model.ServerModel;
import com.eupraxia.telephony.service.Reports.ServerService;


@CrossOrigin
@RestController
@RequestMapping("/server")
public class ServerController {
	@Autowired
	ServerService serverService;
	
	@PostMapping(value = "/save")
    public ResponseEntity<?> saveServers(@RequestBody ServerDTO serverDTO) throws MalformedURLException, URISyntaxException {
		String test= serverService.saveServer(serverDTO);
		return new ResponseEntity<>(test, HttpStatus.OK); 
	}

	@GetMapping(value="/servers")
	public ResponseEntity<?> getServers() throws MalformedURLException, URISyntaxException {
		List<ServerModel> serverModels = new ArrayList<ServerModel>();
		serverModels= serverService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", serverModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/findById")
	public ResponseEntity<?> getServerById(@RequestBody ServerDTO serverDTO) throws MalformedURLException, URISyntaxException {
		ServerModel serverModel = new ServerModel();
		serverModel= serverService.findById(serverDTO.getId());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", serverModel); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@PostMapping(value="/deleteById")
	public ResponseEntity<?> deleteServers(@RequestBody ServerDTO serverDTO) throws MalformedURLException, URISyntaxException {
		List<ServerModel> serverModels = new ArrayList<ServerModel>();
		serverService.deleteServer(serverDTO.getId());
		serverModels= serverService.findAll();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("responseCode", "200");
		map.put("responseMessage", "call Data");
		map.put("responseType", "Success");   
		map.put("model", serverModels); 
		 return new ResponseEntity(map, HttpStatus.OK);
	}
	@PostMapping(value="/getByHostName")
	public ResponseEntity<?> getServerByHostName(@RequestBody ServerDTO serverDTO) throws MalformedURLException, URISyntaxException {
		String test= serverService.findByHostName(serverDTO.getHostName());
		return new ResponseEntity<>(test, HttpStatus.OK); 
	}

}
