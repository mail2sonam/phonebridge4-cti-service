package com.eupraxia.telephony.Reports.DAO;

import java.util.List;

import com.eupraxia.telephony.Model.ServerModel;





public interface ServerDao {
	ServerModel saveOrUpdateServerDetails(ServerModel serverModel);
	ServerModel findById(long id);
	ServerModel findByHostName(String hostname);
	void deleteById(long id);
	List<ServerModel> findAll();
}
