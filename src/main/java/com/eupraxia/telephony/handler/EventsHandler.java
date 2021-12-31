package com.eupraxia.telephony.handler;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.event.BridgeEvent;
import org.asteriskjava.manager.event.DialBeginEvent;
import org.asteriskjava.manager.event.DialEndEvent;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.JoinEvent;
import org.asteriskjava.manager.event.LeaveEvent;
import org.asteriskjava.manager.event.MeetMeJoinEvent;
import org.asteriskjava.manager.event.MeetMeLeaveEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.asteriskjava.manager.event.QueueCallerJoinEvent;
import org.asteriskjava.manager.event.QueueCallerLeaveEvent;

public interface EventsHandler {
	
	
	void hangupEvent(HangupEvent event) ;
	
	void dialEndEvent(DialEndEvent event);	
	
	void dialBeginEvent(DialBeginEvent event);
	
	void queueCallerJoinEvent(QueueCallerJoinEvent event);

	void queueCallerLeaveEvent(QueueCallerLeaveEvent event);

	void meetMeJoinEvent(MeetMeJoinEvent event);
	
	void peerStatusEvent(PeerStatusEvent event);

	void meetMeLeaveEvent(MeetMeLeaveEvent event) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, AuthenticationFailedException;
	
	void bridgeEvent(BridgeEvent event);
	
}
