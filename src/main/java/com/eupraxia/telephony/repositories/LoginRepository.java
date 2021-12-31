package com.eupraxia.telephony.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.LoginModel;


@Transactional
public interface LoginRepository extends JpaRepository<LoginModel, Integer>{
	@Query("SELECT e FROM LoginModel e WHERE e.extension = (:extension) and e.loginStatus =(:loginStatus)") 
	LoginModel findByExtensionAndStatus(@Param("extension") String extension, @Param("loginStatus") String loginStatus);
	
	LoginModel findFirstByExtensionOrderByIdDesc(String extension);
	
	LoginModel findByAgentName(String agentName);
	

	ArrayList<LoginModel> findByLoginStatus(String status);

	@Query("SELECT e FROM LoginModel e WHERE e.extension = (:extension) and e.loginStatus =(:loginStatus)") 
	List<LoginModel> findAllByExtensionAndStatus(@Param("extension") String extension, @Param("loginStatus") String loginStatus);

}
