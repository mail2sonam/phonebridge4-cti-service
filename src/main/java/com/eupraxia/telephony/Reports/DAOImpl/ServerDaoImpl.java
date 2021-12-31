package com.eupraxia.telephony.Reports.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eupraxia.telephony.Model.ServerModel;
import com.eupraxia.telephony.Reports.DAO.ServerDao;
import com.eupraxia.telephony.repositories.Reports.ServerRepository;

@Repository
public class ServerDaoImpl implements ServerDao {

	@Autowired
	ServerRepository serverRepository;
	
	@Override
	public ServerModel saveOrUpdateServerDetails(ServerModel serverModel) {
		return serverRepository.save(serverModel);
	}

	@Override
	public ServerModel findById(long id) {
		return serverRepository.findById(id);
	}

	@Override
	public ServerModel findByHostName(String hostname) {
		return serverRepository.findByHostName(hostname);
	}

	@Override
	public void deleteById(long id) {
		serverRepository.deleteById(id);	
	}

	@Override
	public List<ServerModel> findAll() {
		return serverRepository.findAll();
	}

}
