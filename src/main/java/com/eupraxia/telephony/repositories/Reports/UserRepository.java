package com.eupraxia.telephony.repositories.Reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eupraxia.telephony.Model.UserModel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<UserModel,Long>{
	public UserModel findById(long id);
	public Long deleteById(long id);
	public List<UserModel> findAll();
	public UserModel findByUsername(String username);
	public List<UserModel> findByUsertype(String usertype);
	public List<UserModel> findByDepartmentcode(String departmentcode);
	
	@Query("SELECT e FROM UserModel e WHERE e.userextension NOT IN (:userextension)")    
	List<UserModel> findUsersNotMatchingCampaign(@Param("userextension") List<String> userextension);
	
	@Query("SELECT e FROM UserModel e WHERE e.username = (:username) and e.password =(:password)")  
	public UserModel checkUser(@Param("username") String username, @Param("password") String password);
	public UserModel findByUserextension(String userextension);
	public UserModel findFirstByUserextension(String userextension);
	public UserModel findByEmail(String mail);
	


}
