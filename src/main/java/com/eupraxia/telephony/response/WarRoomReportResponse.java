package com.eupraxia.telephony.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import com.eupraxia.telephony.DTO.WarRoomReportDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WarRoomReportResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2444697039416023816L;
	List<WarRoomReportDTO> data;
	String responseCode;
	String responseMessage;
	String responseType;

}
