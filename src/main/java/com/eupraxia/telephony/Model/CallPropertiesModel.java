package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document(collection = "callpropertie")

public class CallPropertiesModel implements Serializable{

	private static final long serialVersionUID = -5351633339065847608L;

	@Id
	@Generated
	@BsonProperty("id")
    private String id;   
	private String phoneNo;
	private String extension;
	private String isClosed;
	private String callStatus;
	private String popupStatus;		
	private LocalDateTime callStartTime;
	private LocalDateTime callEndTime;
	private LocalDateTime callConnectTime;
	private LocalDateTime ringStartTime;
	private LocalDateTime ringEndTime;
	private String trunkChannel;
	private String sipChannel;
	private String secondChannel;
	private String callDirection;
	private String secondNumber;
	private List<String> channels;
	private LocalDateTime HoldStartTime;
	private LocalDateTime HoldEndTime;
	private String disposition;
	private String comments;
	private LocalDate callbackDate;
	private String queue;
	private LocalDateTime queueJoinTime;
	private LocalDateTime queueRemoveTime;
	private String dialMethod;
	private String ivr;
	private String ivrFlow;
	private int camId;
	private String camName;
	private Date wrapuptime;
	private String name;
	private int feedback;
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	

}
