package com.eupraxia.telephony.repositories.Digital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Digital.TownModel;



@Transactional
public interface TownRepository extends JpaRepository<TownModel, Integer>{

	List<TownModel> findByDistrictId(int districtId);

	TownModel findByTownName(String townName);

}
