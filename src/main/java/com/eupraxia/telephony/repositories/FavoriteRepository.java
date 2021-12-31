package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.FavoriteModel;

public interface FavoriteRepository extends JpaRepository<FavoriteModel,Integer>{

}
