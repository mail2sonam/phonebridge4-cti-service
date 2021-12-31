package com.eupraxia.telephony.service.Digital;

import java.util.List;

import com.eupraxia.telephony.Model.Digital.ContactModel;
import com.eupraxia.telephony.Model.Digital.PinCodeModel;



public interface PinCodeService {

	List<PinCodeModel> findByDistrictContainingAndTownContaining(String district, String town);
	
	List<String> findAllDistrictFromPinCode();
	
	List<PinCodeModel> findAllTaluksFromPinCode(String district);
	
	List<PinCodeModel> findAllTownsFromPinCode(String taluk);
	
	List<PinCodeModel> findAllPinsFromPinCode(String town);

}
