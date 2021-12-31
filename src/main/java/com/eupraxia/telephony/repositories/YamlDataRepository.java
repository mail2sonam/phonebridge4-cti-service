package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.YamlDataModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface YamlDataRepository extends JpaRepository<YamlDataModel, Integer>{

	List<YamlDataModel> findByClient(String client);
	
	List<YamlDataModel> findByMainDispo(String mainDispo);
	
	List<YamlDataModel> findByCampaign(String campaign);
	
	List<YamlDataModel> findBySubDispo(String subDispo);
	
	List<YamlDataModel> findBySubSubDispo(String subSubDispo);
	
}
