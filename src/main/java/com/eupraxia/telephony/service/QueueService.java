package com.eupraxia.telephony.service;

import com.eupraxia.telephony.Model.QueueModel;

public interface QueueService {

	QueueModel findByQueue(String queue);

	void saveOrUpdate(QueueModel queueModel1);

}
