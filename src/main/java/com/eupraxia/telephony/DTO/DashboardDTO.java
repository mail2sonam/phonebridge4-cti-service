package com.eupraxia.telephony.DTO;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DashboardDTO implements Serializable{
	

	private static final long serialVersionUID = 6459416573255929593L;

	private String statusName;
	private String statusCount;
	private String statusPercentage;
	

}
