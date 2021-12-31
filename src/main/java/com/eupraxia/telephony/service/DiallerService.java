package com.eupraxia.telephony.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eupraxia.telephony.Model.DiallerModel;

public interface DiallerService {

	void save(MultipartFile file, Long campaignId, String campaignName);

	DiallerModel findByStatusAndDate(int campaignId);

	DiallerModel findById(int id);

	void saveOrUpdate(DiallerModel diallerModel1);

	List<DiallerModel> findAllByCampaignId(int campaignId);

	List<DiallerModel> findByCampaignIdAndStatus(int campaignId, String string);

}
