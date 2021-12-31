package com.eupraxia.telephony.Reports.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Reports.DAO.UserDao;
import com.eupraxia.telephony.repositories.Reports.UserRepository;


@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	UserRepository userRepository;
	
	
	
	@Override
	public UserModel findById(long id) {
		return userRepository.findById(id);
	}
	
	
	@Override
	public Long deleteById(long id) {
		return userRepository.deleteById(id);
	}
	@Override
	public List<UserModel> findAll(){
		return userRepository.findAll();
	}
	@Override
	public UserModel saveOrUpdateUserDetails(UserModel userModel) {
		return userRepository.save(userModel);
	}


	@Override
	public UserModel findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}


	@Override
	public List<UserModel> findAllDepartments() {
		return userRepository.findByUsertype("department");
		
	}


	@Override
	public List<UserModel> findByDepartmentcode(String departmentcode) {
		return userRepository.findByDepartmentcode(departmentcode);
	}


	@Override
	public UserModel checkUser(String username, String password) {
		return userRepository.checkUser(username,password);
	}


	@Override
	public UserModel findByUserextension(String userextension) {
		// TODO Auto-generated method stub
		return userRepository.findByUserextension(userextension);
	}


	@Override
	public UserModel findFirstByUserextension(String userextension) {
		// TODO Auto-generated method stub
		return userRepository.findFirstByUserextension(userextension);
	}


	@Override
	public void saveOrUpdate(UserModel userModel) {
		// TODO Auto-generated method stub
		userRepository.save(userModel);
	}


	@Override
	public List<UserModel> findByUsertype(String userType) {
		// TODO Auto-generated method stub
		return userRepository.findByUsertype(userType);
	}


	
	

}