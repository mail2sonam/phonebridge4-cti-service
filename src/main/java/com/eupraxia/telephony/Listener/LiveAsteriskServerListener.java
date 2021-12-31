package com.eupraxia.telephony.Listener;

import java.io.IOException;

import org.asteriskjava.manager.AbstractManagerEventListener;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.event.BridgeEvent;
import org.asteriskjava.manager.event.DialBeginEvent;
import org.asteriskjava.manager.event.DialEndEvent;
import org.asteriskjava.manager.event.DialEvent;
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

import org.asteriskjava.live.AsteriskAgent;
import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.AsteriskQueue;
import org.asteriskjava.live.AsteriskQueueEntry;
import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.AsteriskServerListener;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.live.DialedChannelHistoryEntry;
import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.live.MeetMeRoom;
import org.asteriskjava.live.MeetMeUser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

@Service
public class LiveAsteriskServerListener implements AsteriskServerListener, PropertyChangeListener {
	
	

	@Override
	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {			
		//System.out.println("Property Name : "+propertyChangeEvent.getPropertyName());		
		//System.out.println("Event Source : "+propertyChangeEvent.getSource());
		//System.out.println("Source :"+propertyChangeEvent.getNewValue().toString());
	
		
		// System.out.println(propertyChangeEvent);
		
	}

	@Override
	public void onNewAsteriskChannel(AsteriskChannel channel) {	
		
		//channel.addPropertyChangeListener(this);		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewMeetMeUser(MeetMeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewAgent(AsteriskAgent agent) {	
		// TODO Auto-generated method stub		
	}

	@Override
	public void onNewQueueEntry(AsteriskQueueEntry entry) {		
		// TODO Auto-generated method stub
		
	}
	
}
	
	
	
	




