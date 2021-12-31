package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import org.asteriskjava.manager.ManagerConnection;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class OriginateDTO implements Serializable {

	private static final long serialVersionUID = 8835127651591342910L;
	private String id;
	private ManagerConnection managerConnection;
	
	private ConnectionDTO connectionDTO;
	
	private String phoneNo;
    private String channel;
    //private String callerId;
   // private Long timoutLimit;
    private String context;
    private String prefix;
    private int priority;
    private String exten;
    private String sipChannel;
    private String extraChannel;
    private String extraExten;
    private String extraContext;
    private int extraPriority;
    private String extension;
    private String dialMethod;
    private int camId;
   	private String camName;
   	private String callSource;
   	private String spyType;
   	private String name;

}
