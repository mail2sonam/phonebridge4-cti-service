package com.eupraxia.telephony.service.impl.Digital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Digital.TownModel;
import com.eupraxia.telephony.repositories.Digital.TownRepository;
import com.eupraxia.telephony.service.Digital.TownService;
@Service
public class TownServiceImpl implements TownService{
@Autowired
private TownRepository townRepository;
	@Override
	public void saveOrUpdate(TownModel townModel) {
		townRepository.save(townModel);
		
	}
	@Override
	public List<TownModel> findByDistrictId(int districtId) {
		// TODO Auto-generated method stub
		return townRepository.findByDistrictId(districtId);
	}
	
	@Override
	public TownModel findByTownName(String townName) {
		// TODO Auto-generated method stub
		return townRepository.findByTownName(townName);
	}
	
	

}
