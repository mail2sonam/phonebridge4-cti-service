package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.asteriskjava.manager.ManagerConnection;
import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CallPropertiesDTO implements Serializable {

	private static final long serialVersionUID = 6751765686797145531L;
	private ObjectId id;
	private String phoneNo;
	private String extension;
	private String isClosed;
	private String callStatus;
	private String popupStatus;
	private LocalDateTime callStartTime;
	private Date callEndTime;
	private LocalDateTime ringStartTime;
	private LocalDateTime ringEndTime;
	private LocalDateTime CallConnectTime;
	private String trunkChannel;
	private String sipChannel;
	private String secondChannel;
	private String callDirection;
	private String deleted;
	private List<String> channels;
	private String queue;
	private LocalDateTime queueJoinTime;
	private LocalDateTime queueRemoveTime;
	private String dialMethod;
	private String disposition;
	private String ivr;
	private int camId;
	private String camName;
	private String callSource;
	private Date wrapuptime;
	private String name;
	private int feedback;
}
