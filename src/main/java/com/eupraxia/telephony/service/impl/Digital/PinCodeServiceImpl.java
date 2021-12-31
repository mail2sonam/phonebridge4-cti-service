package com.eupraxia.telephony.service.impl.Digital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.Digital.ContactModel;
import com.eupraxia.telephony.Model.Digital.PinCodeModel;
import com.eupraxia.telephony.repositories.Digital.ContactRepository;
import com.eupraxia.telephony.repositories.Digital.PinCodeRepository;
import com.eupraxia.telephony.service.Digital.ContactService;
import com.eupraxia.telephony.service.Digital.PinCodeService;

@Service
public class PinCodeServiceImpl implements PinCodeService{

	@Autowired
	private PinCodeRepository pinCodeRepository;
	
	
	
	@Override
	public List<PinCodeModel> findByDistrictContainingAndTownContaining(String district, String town) {
		// TODO Auto-generated method stub
		return pinCodeRepository.findByDistrictContainingAndTownContaining(district, town);
	}
	
	@Override
	public List<String> findAllDistrictFromPinCode() {
		// TODO Auto-generated method stub
		return pinCodeRepository.findAllDistrictFromPinCode();
	}
	
	@Override
	public List<PinCodeModel> findAllTaluksFromPinCode(String district) {
		// TODO Auto-generated method stub
		return pinCodeRepository.findByDistrict(district);
	}
	
	@Override
	public List<PinCodeModel> findAllTownsFromPinCode(String taluk) {
		// TODO Auto-generated method stub
		return pinCodeRepository.findByTaluk(taluk);
	}
	
	@Override
	public List<PinCodeModel> findAllPinsFromPinCode(String town) {
		// TODO Auto-generated method stub
		return pinCodeRepository.findByTown(town);
	}
	
}
