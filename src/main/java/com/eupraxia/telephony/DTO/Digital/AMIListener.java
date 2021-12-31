package com.eupraxia.telephony.DTO.Digital;

import java.io.IOException;

import org.asteriskjava.manager.AbstractManagerEventListener;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.event.BridgeEnterEvent;
import org.asteriskjava.manager.event.BridgeEvent;
import org.asteriskjava.manager.event.DialBeginEvent;
import org.asteriskjava.manager.event.DialEndEvent;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.DtmfBeginEvent;
import org.asteriskjava.manager.event.DtmfEndEvent;
import org.asteriskjava.manager.event.ExtensionStatusEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.JoinEvent;
import org.asteriskjava.manager.event.LeaveEvent;
import org.asteriskjava.manager.event.MeetMeJoinEvent;
import org.asteriskjava.manager.event.MeetMeLeaveEvent;
import org.asteriskjava.manager.event.PeerEntryEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.asteriskjava.manager.event.QueueCallerJoinEvent;
import org.asteriskjava.manager.event.QueueCallerLeaveEvent;
import org.asteriskjava.manager.event.QueueMemberStatusEvent;
import org.asteriskjava.pbx.internal.core.PeerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.ExtensionDataModel;
import com.eupraxia.telephony.handler.EventsHandler;

@Service
public class AMIListener extends AbstractManagerEventListener{
	
	@Autowired
	EventsHandler eventsHandler;
	
	
	
	public void registerListener(ManagerConnection managerConnection) {
		managerConnection.addEventListener(this);
	}
	
	@Override
	public void handleEvent(PeerStatusEvent event) {
	
		eventsHandler.peerStatusEvent(event);	
		//System.out.println("PeerStatus Event "+event);	
		//eventsHandler.hangupEvent(event);
	}
	
	@Override
	public void handleEvent(ExtensionStatusEvent event) {
	
		//System.out.println("Extension Event "+event);	
		//eventsHandler.hangupEvent(event);
	}
	
	
	
	@Override
	public void handleEvent(BridgeEvent event) {	
		
	}
	
	@Override
	public void handleEvent(PeerEntryEvent event) {
		
	}
	
	@Override
	public void handleEvent(HangupEvent event) {
		//System.out.println(event);
		eventsHandler.hangupEvent(event);
	}
	
	@Override
	public void handleEvent(DialEndEvent event) {		
		eventsHandler.dialEndEvent(event);
	}
	
	@Override
	public void handleEvent(DialBeginEvent event) {			
	eventsHandler.dialBeginEvent(event);
	
	}
	
	public void handleEvent(QueueMemberStatusEvent event) {
	
	}
	
	@Override
	public void handleEvent(QueueCallerJoinEvent event) {	
		//System.out.println(event);	
		eventsHandler.queueCallerJoinEvent(event);
		}
	
	@Override
	public void handleEvent(QueueCallerLeaveEvent event) {	
		//System.out.println(event);
		eventsHandler.queueCallerLeaveEvent(event);
		}
	
	@Override
	public void handleEvent(MeetMeJoinEvent event) {
		eventsHandler.meetMeJoinEvent(event);
	}
	
	
	
	@Override
	public void handleEvent(MeetMeLeaveEvent event) {
		try {
			eventsHandler.meetMeLeaveEvent(event);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void handleEvent(DtmfBeginEvent event) {		
		
		
	}
	
	@Override
	public void handleEvent(DtmfEndEvent event) {
		
	
		
	}
	
}
	
	
	
	




