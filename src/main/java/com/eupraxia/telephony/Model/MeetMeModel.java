package com.eupraxia.telephony.Model;

import java.io.Serializable;
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
@Document(collection = "meetme")
public class MeetMeModel implements Serializable{
	@Id
	@Generated
	@BsonProperty("id")
    private String id;   
	
	
	private List<String> channels;
	
	private String room;
	
	private String status;
}
