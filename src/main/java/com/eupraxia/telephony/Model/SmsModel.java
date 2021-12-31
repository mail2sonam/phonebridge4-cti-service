package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document(collection = "SmsData")
public class SmsModel implements Serializable{
	@Id
	@Generated
	@BsonProperty("id")
    private String id; 
	private String messageId;
	private String caseId;
	private Long number;
	private String message;
	private String date;
	private boolean isNew;
	private String status;
	private Date enteredDate;
	private String userExtension;
	public void setIsNew(boolean b) {
		this.isNew=b;
		
	}
}
