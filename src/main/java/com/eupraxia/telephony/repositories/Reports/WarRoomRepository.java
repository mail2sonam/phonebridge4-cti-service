package com.eupraxia.telephony.repositories.Reports;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Reports.WarRoomModel;

@Transactional
public interface WarRoomRepository extends JpaRepository<WarRoomModel, Integer>{

	@Query(value = "select * from war_room_report where CAST(call_time AS DATE) BETWEEN ?1 and ?2 ", nativeQuery = true)	
	List<WarRoomModel> findByCallTimeBetween(String startDate, String endDate);

	
}
