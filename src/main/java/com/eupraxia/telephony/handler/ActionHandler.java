package com.eupraxia.telephony.handler;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.response.ManagerResponse;

import com.eupraxia.telephony.DTO.ConnectionDTO;
import com.eupraxia.telephony.DTO.OriginateDTO;
import com.eupraxia.telephony.DTO.QueueDTO;

public interface ActionHandler {
	
	ManagerConnection getManageConnection(ConnectionDTO connectionDTO) throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException;
	
	ManagerResponse originateCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	
	ManagerResponse spyCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	
	ManagerResponse hangUpCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	
	ManagerResponse holdCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	
	ManagerResponse redirectCall(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	
	ManagerResponse queueAdd(QueueDTO queueDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	
	ManagerResponse queueRemove(QueueDTO queueDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;

	ManagerResponse AttendedTransfer(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;

	ManagerResponse unHold(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;

	ManagerResponse conference(OriginateDTO originateDTO) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;

	ManagerResponse addQueue(String extension)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;

	ManagerResponse removeQueue(String extension)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	void redirectFeedBack(String channel,String dir)throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;

}
