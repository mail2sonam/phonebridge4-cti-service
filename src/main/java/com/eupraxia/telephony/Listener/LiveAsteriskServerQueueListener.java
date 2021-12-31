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
import org.asteriskjava.live.AsteriskQueueListener;
import org.asteriskjava.live.AsteriskQueueMember;
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
public class LiveAsteriskServerQueueListener implements AsteriskQueueListener, PropertyChangeListener {
	
	

	@Override
	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {			
		//System.out.println("Property Name : "+propertyChangeEvent.getPropertyName());		
		//System.out.println("Event Source : "+propertyChangeEvent.getSource());
		//System.out.println("Source :"+propertyChangeEvent.getNewValue().toString());
	
		
		//System.out.println(propertyChangeEvent);
		//System.out.println("queue new value : "+propertyChangeEvent.getNewValue().toString());
		
	}

	@Override
	public void onNewEntry(AsteriskQueueEntry entry) {	
		System.out.println("onNewEntry : "+entry.getState());
		//entry.getQueue().getEntries();
		// TODO Auto-generated method stub		
	}

	@Override
	public void onEntryLeave(AsteriskQueueEntry entry) {
		System.out.println("onEntryLeave : "+entry.getState());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMemberStateChange(AsteriskQueueMember member) {	
		System.out.println("OnMemberStateChange :"+member.getState());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntryServiceLevelExceeded(AsteriskQueueEntry entry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMemberAdded(AsteriskQueueMember member) {		
		
		System.out.println("Add - member.getState() :"+member.getState());
			
		}
		// TODO Auto-generated method stub
		
	

	@Override
	public void onMemberRemoved(AsteriskQueueMember member) {
		System.out.println("Remove - member.getState() :"+member.getState());
		// TODO Auto-generated method stub
		
	}

	
	
}
	
	
	
	




