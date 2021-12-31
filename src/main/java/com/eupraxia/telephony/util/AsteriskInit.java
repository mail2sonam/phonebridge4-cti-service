package com.eupraxia.telephony.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.asteriskjava.live.AsteriskQueue;
import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eupraxia.telephony.DTO.ConnectionDTO;
import com.eupraxia.telephony.DTO.Digital.AMIListener;
import com.eupraxia.telephony.Listener.LiveAsteriskServerListener;
import com.eupraxia.telephony.Listener.LiveAsteriskServerQueueListener;
import com.eupraxia.telephony.components.TelephonyManagerConnection;

@Component
public class AsteriskInit {

	@Autowired
	TelephonyManagerConnection telephonyManagerConnection;
	
	@Autowired
	AMIListener amiListener;
	
	@Autowired
	LiveAsteriskServerListener liveAsteriskServerListener;	
	
	@Autowired
	LiveAsteriskServerQueueListener liveAsteriskServerQueueListener;
	
	private AsteriskServer asteriskServer;
	
	@Value("${asterisk.host}")
	private String asteriskHost;
	
	@Value("${asterisk.userName}")
	private String asteriskUser;
	
	@Value("${asterisk.password}")
	private String asteriskPass;
	

	@PostConstruct
	private void postConstruct() throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {

		Map<Object, Object> map = new HashMap<>();	
		ConnectionDTO connectionDTO = new ConnectionDTO();		
		connectionDTO.setAmiManagerIp(asteriskHost);	
		connectionDTO.setAmiUserName(asteriskUser);
		connectionDTO.setAmiPassword(asteriskPass);
		ManagerConnection managerConnection = telephonyManagerConnection.getManageConnection(connectionDTO);
		managerConnection.addEventListener(amiListener);
		
		////AsteriskServerImpl asteriskServer = new AsteriskServerImpl(managerConnection);
		
		asteriskServer = new DefaultAsteriskServer(asteriskHost, asteriskUser, asteriskPass);	
		////asteriskServer.addAsteriskServerListener(liveAsteriskServerListener);			
		
		 //for (AsteriskQueue asteriskQueue : asteriskServer.getQueues()) {	              
	          
	      //      asteriskServer.getQueueByName(asteriskQueue.getName()).addAsteriskQueueListener(liveAsteriskServerQueueListener);	           
	      // }
	
		////asteriskServer.getQueueByName("1111").addAsteriskQueueListener(liveAsteriskServerQueueListener);
		
		
	  }
	
	public AsteriskServer getServer() {
		return this.asteriskServer;
	}
}
