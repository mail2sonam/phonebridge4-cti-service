package com.eupraxia.telephony.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.LoginModel;
import com.eupraxia.telephony.repositories.LoginRepository;
import com.eupraxia.telephony.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	LoginRepository loginRepository;
	
	@Override
	public void saveOrUpdate(LoginModel loginModel) {
		
		loginRepository.save(loginModel);
	}

	@Override
	public LoginModel findByExtensionAndStatus(String userextension, String status) {
		// TODO Auto-generated method stub
		return loginRepository.findByExtensionAndStatus(userextension,status);
	}

	@Override
	public ArrayList<LoginModel> findByStatus(String string) {
		// TODO Auto-generated method stub
		return loginRepository.findByLoginStatus("login");
	}

	@Override
	public LoginModel findByAgentName(String agentName) {
		// TODO Auto-generated method stub
		return loginRepository.findByAgentName(agentName);
	}

	@Override
	public void deleteById(int id) {
		loginRepository.deleteById(id);
		
	}

	@Override
	public List<LoginModel> findAllByExtensionAndStatus(String userextension, String status) {
		// TODO Auto-generated method stub
		return loginRepository.findAllByExtensionAndStatus(userextension,status);
	}

}
