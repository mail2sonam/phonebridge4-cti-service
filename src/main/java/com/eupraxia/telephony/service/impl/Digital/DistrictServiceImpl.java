package com.eupraxia.telephony.service.impl.Digital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Digital.DistrictModel;
import com.eupraxia.telephony.repositories.Digital.DistrictRepository;
import com.eupraxia.telephony.service.Digital.DistrictService;
@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictRepository districtRepository;
	
	@Override
	public void saveOrUpdate(DistrictModel districtModel) {
		districtRepository.save(districtModel);
	}

	@Override
	public List<DistrictModel> findAll() {
		// TODO Auto-generated method stub
		return districtRepository.findAll();
	}

	@Override
	public DistrictModel findByDistrictName(String districtName) {
		// TODO Auto-generated method stub
		return districtRepository.findByDistrictName(districtName);
	}
	
}
