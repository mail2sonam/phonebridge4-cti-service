package com.eupraxia.telephony.components;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.eupraxia.telephony.constants.WebStreamProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtToken {
	String token;
	public String getToken() {
		return token;	
		}
		
		 @Scheduled(cron = "0 0/13 * * * ?")
			public void tokenSetting() {
			 String json=null;	
			 try {
					 WebClient client=WebClient.builder().baseUrl(WebStreamProperties.TokenUrl).build();
					json=client.post().header("Content-Type", WebStreamProperties.Content_Type)
							.body(BodyInserters.fromFormData("grant_type", WebStreamProperties.grant_type)
									.with("scope",WebStreamProperties.reqScope)
									.with("client_id", WebStreamProperties.client_id)
									.with("client_secret", WebStreamProperties.client_secret)).retrieve()
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
			 System.out.println(json);
			}

}
