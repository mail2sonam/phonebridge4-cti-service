package com.eupraxia.telephony.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eupraxia.telephony.Model.MailStoreModel;
import com.eupraxia.telephony.Model.SmsModel;

@Transactional
public interface MailStoreRepository extends MongoRepository<MailStoreModel,String>{

@Query(value="{ 'status' : ?0 }",fields="{ 'id' : 1, 'from' : 1, 'subject' : 1, 'sentDate' : 1, 'body' : 1, 'status' : 1, 'userId' : 1, 'userName' : 1, 'enteredDate' : 1, 'lastUpdateDate' : 1, 'userExtension' : 1,'caseId' : 1}")
List<MailStoreModel> findByStatus(String status);

MailStoreModel findById(ObjectId id);

MailStoreModel findByCaseId(String caseId);

MailStoreModel findByFromAndSubjectAndSentDate(String from, String subject, String sentDate);

List<MailStoreModel> findByUserName(String userName);

List<MailStoreModel> findByUserExtension(String userExtension);

void deleteByCaseId(String caseId);

//@Query(value="{'caseId' : $0}", delete = true)
//public MailStoreModel deleteByCaseId (String caseId);
}
