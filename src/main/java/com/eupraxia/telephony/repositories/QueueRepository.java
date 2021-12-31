package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.QueueModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QueueRepository extends JpaRepository<QueueModel, Integer>{

	QueueModel findByQueue(String queue);

}
