package com.eupraxia.telephony.DTO;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.mail.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailParserDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	private String subject;
	private String sentDate;
	private String body;
	private String status;
	private String userId;
	private String userName;
	private Date enteredDate;
	private Date lastUpdateDate;
	private String id;
	private String userExtension;
	private String attachedBase64DataType;
	private String attachedFileName;
	private byte[] attachedFileData;
	private String caseId;
	private Date receivedDate;
	

}
