package com.eupraxia.telephony.service;

import com.eupraxia.telephony.Model.OtpModel;

public interface OtpService {

	OtpModel findByPhoneNo(String phoneNo);

	void saveOrUpdate(OtpModel otp2);

	void deleteByPhoneNo(String phoneNo);

}
