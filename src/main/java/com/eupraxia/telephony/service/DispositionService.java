package com.eupraxia.telephony.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.eupraxia.telephony.Model.DispositionModel;

import com.eupraxia.telephony.Reports.DAO.DispositionDAO;


@Service
public class DispositionService {
	@Autowired 
	DispositionDAO dispositionDao; 
	
	
	public DispositionModel saveDisposition(DispositionModel dispositionModel) {

		return this.dispositionDao.save(dispositionModel);
	}


	public DispositionModel findByDispositiond(int id) {
		
		return this.dispositionDao.findById(id);
	}


	public List<DispositionModel> findAllDispositions() {
		
		return this.dispositionDao.findAll();
	}


	public List<DispositionModel> findAllDispositionsOFParent(int parentId) {
		// TODO Auto-generated method stub
		return this.dispositionDao.findByParentId(parentId);
	}


/*
 * public SubDispositionModel saveSubDisposition(SubDispositionModel
 * subdispositionModel) { return
 * this.dispositionDao.saveSubDisposition(subdispositionModel);
 * 
 * }
 * 
 * 
 * public List<SubDispositionModel> findAllSubDispositions() { // TODO
 * Auto-generated method stub return this.dispositionDao.findAllSub(); }
 */
}
