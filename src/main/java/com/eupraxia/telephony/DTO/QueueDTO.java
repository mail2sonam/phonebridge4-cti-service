package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import org.asteriskjava.manager.ManagerConnection;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class QueueDTO implements Serializable {

	private static final long serialVersionUID = 9115893963340112067L;
	
	private ConnectionDTO connectionDTO;
	private ManagerConnection managerConnection;
	private String queue;
    private String iface;
    private Integer penalty;
    private Boolean paused;
    private String memberName;
    private String stateInterface;

	
	
}
