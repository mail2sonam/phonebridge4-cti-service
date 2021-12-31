package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import org.asteriskjava.manager.ManagerConnection;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class QueueDetailsDTO implements Serializable {

	private static final long serialVersionUID = 9115893963340112067L;	
	
	private String queueName;
    private Integer totalCallsInQueue;
    private Integer abandonedCalls;
    private Integer currentWaitingCalls;
    private String strategy;
    private Integer holdTime;
    private Integer completedCalls;
    private Integer talkTime;
    private String queueMembers;

	
	
}
