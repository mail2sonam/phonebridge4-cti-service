package com.eupraxia.telephony.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReportDTO {
	private String categoryName;
	private List<SubCategoryReportDTO> subdata;
}
