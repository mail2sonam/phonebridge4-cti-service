package com.eupraxia.telephony.repositories.Digital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.Digital.PinCodeModel;
import com.eupraxia.telephony.Model.Disposition.EmergencyScreenModel;


@Transactional
public interface PinCodeRepository  extends JpaRepository<PinCodeModel, Integer>{

List<PinCodeModel> findByDistrictContainingAndTownContaining(String district, String town);


@Query("SELECT DISTINCT district FROM PinCodeModel")     
List<String> findAllDistrictFromPinCode();

//@Query("SELECT DISTINCT town FROM PinCodeModel WHERE district = :district")     
List<PinCodeModel> findByDistrict(String district);

List<PinCodeModel> findByTaluk(String taluk);

List<PinCodeModel> findByTown(String town);



}
