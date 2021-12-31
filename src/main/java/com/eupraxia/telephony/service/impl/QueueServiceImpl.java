package com.eupraxia.telephony.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eupraxia.telephony.Model.QueueModel;
import com.eupraxia.telephony.repositories.QueueRepository;
import com.eupraxia.telephony.service.MissedCallService;
import com.eupraxia.telephony.service.QueueService;

@Service
public class QueueServiceImpl implements QueueService {

	@Autowired
	private QueueRepository queueRepository;
	
	@Override
	public QueueModel findByQueue(String queue) {
		//System.out.println("Hi");
		// TODO Auto-generated method stub
		return queueRepository.findByQueue(queue);
	}

	@Override
	public void saveOrUpdate(QueueModel queueModel1) {
		queueRepository.save(queueModel1);
	}

}
