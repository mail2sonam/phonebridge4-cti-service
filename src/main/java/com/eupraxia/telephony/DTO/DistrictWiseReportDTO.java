package com.eupraxia.telephony.DTO;

import java.util.Date;

import lombok.*;

@Setter
@Getter
public class DistrictWiseReportDTO {
private Long calls;
private String district;
private Date  callStartTime;
}
