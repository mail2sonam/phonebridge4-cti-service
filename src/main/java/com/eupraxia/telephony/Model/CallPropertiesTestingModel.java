package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document(collection = "CallPropertiesModel.java")

public class CallPropertiesTestingModel implements Serializable{

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
	private Date callStartTime;
	private Date callEndTime;
	private Date callConnectTime;
	private String trunkChannel;
	private String sipChannel;
	private String secondChannel;
	private String callDirection;
	private String secondNumber;
	private List<String> channels;
	private Date HoldStartTime;
	private Date HoldEndTime;
	private String disposition;
	private String comments;
	private Date callbackDate;
	private String queue;
	private Date queueJoinTime;
	private Date queueRemoveTime;
	private String dialMethod;
	private String ivr;


}
