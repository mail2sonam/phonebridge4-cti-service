package com.eupraxia.telephony.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.DispositionModel;
//import com.eupraxia.telephony.Model.SubDispositionModel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface  DispositionRepository extends JpaRepository<DispositionModel, Integer>{
	public DispositionModel save(DispositionModel dispositionModel);
	public DispositionModel findById(int id);
	public List<DispositionModel> findAll();
	//public SubDispositionModel save(SubDispositionModel subdispositionModel);
	public List<DispositionModel> findByParentId(int parentId);
}

