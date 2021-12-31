package com.eupraxia.telephony.repositories.Reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.ServerModel;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ServerRepository extends JpaRepository<ServerModel, Long> {
	public ServerModel findById(long id);
	public ServerModel findByHostName(String hostname);
	public Long deleteById(long id);
	public List<ServerModel> findAll();

}
