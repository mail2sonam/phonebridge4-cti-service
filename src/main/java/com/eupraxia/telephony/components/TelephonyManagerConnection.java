package com.eupraxia.telephony.components;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.eupraxia.telephony.DTO.ConnectionDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TelephonyManagerConnection {

	ManagerConnection managerConnection;
	String token;
	
	public ManagerConnection getManageConnection(ConnectionDTO connectionDTO) throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {
		ManagerConnectionFactory factory = new ManagerConnectionFactory(connectionDTO.getAmiManagerIp(),connectionDTO.getAmiUserName(),connectionDTO.getAmiPassword());    	
		managerConnection = factory.createManagerConnection();
		managerConnection.login();
	
		return managerConnection;
	}
	
	public ManagerConnection getExisitingConnection() {
		if(managerConnection.getState().CONNECTED !=null) 
			return managerConnection;	
		else {
			System.out.println(managerConnection.getState());
			
		}
		return managerConnection;
				
	}
	
	public String getToken() {
	return token;	
	}
	
	 @Scheduled(cron = "0 0/13 * * * ?")
		public void tokenSetting() {
			 try {
				 WebClient client=WebClient.builder().baseUrl("http://34.134.149.85:8080/auth/realms/phonebridge-cti/protocol/openid-connect/token").build();
				String json=client.post().header("Content-Type", "application/x-www-form-urlencoded")
						.body(BodyInserters.fromFormData("grant_type", "client_credentials")
								.with("scope", "openid")
								.with("client_id", "phonebridge-cti-client")
								.with("client_secret", "pV2TSCP3uU1E9COUDJ8d0FsKSmGCabiR")).retrieve()
						.bodyToMono(String.class).block();
				ObjectMapper objectMapper=new ObjectMapper();
				JsonNode jsonNode=objectMapper.readTree(json);
				String token1=jsonNode.get("access_token").asText();
				 System.out.println(
						 token1);
				token=token1;
			
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
		}
}
