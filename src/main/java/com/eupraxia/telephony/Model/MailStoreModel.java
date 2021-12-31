package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.persistence.Column;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document(collection = "mailstore")
@AllArgsConstructor
@NoArgsConstructor
public class MailStoreModel implements Serializable{


	private static final long serialVersionUID = -5351633339065847608L;

	@Id
	@Generated
	@BsonProperty("id")
	
    private String id;  
	@Field(name = "from")
	private String from;
	
	@Field(name = "caseId")
	private String caseId;
	
	@Field(name = "subject")
	private String subject;
	
	@Field(name = "sentDate")
	private String sentDate;
	
	@Field(name = "body")
	private String body;
	
	@Field(name = "status")
	private String status;
	
	@Field(name = "userId")
	private String userId;
	
	@Field(name = "userName")
	private String userName;	
	
	@Field(name = "enteredDate")
	private Date enteredDate;
	
	@Field(name = "lastUpdateDate")
	private Date lastUpdateDate;
	
	@Field(name = "userExtension")
	private String userExtension;
	
	@Field(name = "attachedFileName")
	private String attachedFileName;
	
	@Field(name = "attachedFileData")
	private byte[] attachedFileData;
	
	@Field(name = "receivedDate")
	private Date receivedDate;
	
	
}
