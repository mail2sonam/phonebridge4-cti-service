package com.eupraxia.telephony.Reports.DAO;

import java.util.List;

import com.eupraxia.telephony.Model.UserModel;



public interface UserDao {

	UserModel saveOrUpdateUserDetails(UserModel userModel);
	UserModel findById(long id);
	Long deleteById(long id);
	List<UserModel> findAll();
	List<UserModel> findAllDepartments();
	UserModel findByUsername(String username);
	List<UserModel> findByDepartmentcode(String departmentcode);
	UserModel checkUser(String username, String password);
	UserModel findByUserextension(String userextension);
	UserModel findFirstByUserextension(String userextension);
	void saveOrUpdate(UserModel userModel);
	List<UserModel> findByUsertype(String userType);
}
