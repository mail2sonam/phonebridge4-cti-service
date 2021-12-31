package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AgentWiseDTO implements Serializable {
private String agentName;
private List<AgentWiseDataDTO> data;

}
