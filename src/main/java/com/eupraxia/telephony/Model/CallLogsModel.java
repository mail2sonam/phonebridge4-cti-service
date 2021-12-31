package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
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
@Document(collection = "message")

public class CallLogsModel implements Serializable{

	private static final long serialVersionUID = -5351633339065847608L;

	@Id
	@Generated
	@BsonProperty("id")
    private ObjectId id;   
	private String phoneNo;
	private String extension;
	private String isClosed;
	private String callStatus;
	private String popupStatus;
	private Date callStartTime;
	private Date HoldStartTime;
	private Date HoldEndTime;
	private Date callEndTime;
	private Date CallConnectTime;
	private String trunkChannel;
	private String sipChannel;
	private String secondChannel;
	private String callDirection;
	private ArrayList<String> channels;
	
	private String secondNumber;
	


}
