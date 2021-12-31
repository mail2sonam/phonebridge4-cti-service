package com.eupraxia.telephony.service;

import java.util.ArrayList;
import java.util.List;

import com.eupraxia.telephony.Model.LoginModel;

public interface LoginService {

	void saveOrUpdate(LoginModel loginModel);

	LoginModel findByExtensionAndStatus(String userextension, String string);

	ArrayList<LoginModel> findByStatus(String string);
	
	LoginModel findByAgentName(String agentName);
	
	void deleteById(int id);

	List<LoginModel> findAllByExtensionAndStatus(String extension, String string);

}
