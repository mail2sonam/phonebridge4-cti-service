package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.DiallerModel;


public interface DiallerRepository extends JpaRepository<DiallerModel, Integer>{
	DiallerModel findById(int id);

	List<DiallerModel> findByCampaignId(int campaignId);

	List<DiallerModel> findByCampaignIdAndStatus(int campaignId, String status);

	DiallerModel findByPhoneNoAndStatus(String string, String string2);

	DiallerModel findByPhoneNoAndStatusAndCampaignName(String string, String string2, String campaignName);

}
