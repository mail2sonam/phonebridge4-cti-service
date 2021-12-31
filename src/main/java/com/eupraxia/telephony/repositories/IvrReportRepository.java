package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.IvrReportModel;
import com.eupraxia.telephony.Model.Disposition.CallDetailModel;
@Transactional
public interface IvrReportRepository extends JpaRepository<IvrReportModel, Integer>{

}
