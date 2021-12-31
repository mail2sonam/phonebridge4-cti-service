package com.eupraxia.telephony.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BreakDTO implements Serializable{
	private Long id;
	private String userextension;
	private String onbreak;
	private String modeChangeReason;
}
