package com.eupraxia.telephony.service.Reports;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.DTO.UserDTO;
import com.eupraxia.telephony.Model.UserModel;
import com.eupraxia.telephony.Reports.DAO.UserDao;
import com.eupraxia.telephony.util.CommonUtil;




@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	@Autowired
	 private ModelMapper modelMapper;
	
	
	
	public List<UserModel> findAll() {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userModels=userDao.findAll();
		return userModels;
		
	}
	
	public List<UserModel> findAllDepartments(){
		List<UserModel> userModels = new ArrayList<UserModel>();
		userModels=userDao.findAllDepartments();
		return userModels;
	}
	public UserModel findById(long id) {
		UserModel bm=new UserModel();
		bm=userDao.findById(id);
		return bm;
	}
	
	public String saveUser(UserDTO userDTO) {
		UserModel userModel = new UserModel();
		userModel = modelMapper.map(userDTO, UserModel.class);	
		UserModel userModel1 = userDao.saveOrUpdateUserDetails(userModel);
		if(CommonUtil.isNull(userModel1)) {
			return "Error in Saving Document";
		}
		else {
			return "Success";
		}	
	}
	public String getUserByUserName(String username) {
		UserModel userModel = new UserModel();
		 userModel = userDao.findByUsername(username);
		if(CommonUtil.isNull(userModel)) {
			return "200";
		}
		else {
			return "400";
		}
		
	}
	
	public UserModel findByUserName(String username) {
		UserModel userModel = new UserModel();
		 userModel = userDao.findByUsername(username);
		 return userModel;
	}
	
	
	public void deleteUser(long id) {
	 userDao.deleteById(id);
	}

	public List<UserModel> findByDepartmentcode(String departmentcode) {
		List<UserModel> userModels = new ArrayList<UserModel>();
		userModels=userDao.findByDepartmentcode(departmentcode);
		return userModels;
	}

	public UserModel checkUserUser(UserDTO userDTO) {
		UserModel userModel1 = userDao.checkUser(userDTO.getUsername(),userDTO.getPassword());
		return userModel1;
	}

	public UserModel findByUserextension(String userextension) {
		// TODO Auto-generated method stub
		return userDao.findByUserextension(userextension);
	}

	public UserModel findFirstByUserextension(String userextension) {
		// TODO Auto-generated method stub
		return userDao.findFirstByUserextension(userextension);
	}

	public void saveOrUpdate(UserModel userModel) {
		// TODO Auto-generated method stub
		userDao.saveOrUpdate(userModel);
	}

	public List<UserModel> findByUsertype(String userType) {
		// TODO Auto-generated method stub
		return userDao.findByUsertype(userType);
	}

	
	
}