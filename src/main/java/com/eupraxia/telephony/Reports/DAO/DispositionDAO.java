package com.eupraxia.telephony.Reports.DAO;

import java.util.List;

import com.eupraxia.telephony.Model.DispositionModel;



public interface DispositionDAO {
	public DispositionModel save(DispositionModel dispositionModel);
	public DispositionModel findById(int id);
	public List<DispositionModel> findAll();
	public List<DispositionModel> findByParentId(int parentId);

	
}
