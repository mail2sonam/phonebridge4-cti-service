package com.eupraxia.telephony.components;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.springframework.stereotype.Component;

import com.eupraxia.telephony.DTO.ConnectionDTO;

@Component
public class TelephonyManagerConnection {

	ManagerConnection managerConnection;
	
	public ManagerConnection getManageConnection(ConnectionDTO connectionDTO) throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {
		ManagerConnectionFactory factory = new ManagerConnectionFactory(connectionDTO.getAmiManagerIp(),connectionDTO.getAmiUserName(),connectionDTO.getAmiPassword());    	
		managerConnection = factory.createManagerConnection();
		managerConnection.login();
		//amiListener.registerListener(managerConnection);
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
}
