package com.eupraxia.telephony.Reports.DAOImpl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.eupraxia.telephony.Model.DispositionModel;
import com.eupraxia.telephony.Reports.DAO.DispositionDAO;
//import com.eupraxia.telephony.Model.SubDispositionModel;
import com.eupraxia.telephony.repositories.DispositionRepository;

@Repository
public class DispositionDAOImpl implements DispositionDAO{
	
	

	@Autowired
	DispositionRepository dispositionRepository;
	/*
	 * @Autowired SubDispositionRepository subdispositionRepository;
	 */

	/*
	 * @Override public List<SubDispositionModel> findAllSub() { // TODO
	 * Auto-generated method stub return subdispositionRepository.findAll(); }
	 */
@Override
	public DispositionModel save(DispositionModel dispositionModel) {

		return dispositionRepository.save(dispositionModel);
	}

@Override
	public DispositionModel findById(int id) {
		// TODO Auto-generated method stub
		return dispositionRepository.findById(id);
	}

@Override
	public List<DispositionModel> findAll() {
		// TODO Auto-generated method stub
		return dispositionRepository.findAll();
	}


@Override
public List<DispositionModel> findByParentId(int parentId) {
	// TODO Auto-generated method stub
	return dispositionRepository.findByParentId(parentId);
}

	/*
	 * @Override public SubDispositionModel saveSubDisposition(SubDispositionModel
	 * subdispositionModel) { // TODO Auto-generated method stub return
	 * dispositionRepository.save(subdispositionModel); }
	 */


}

