package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.eupraxia.telephony.Model.MailModel;

public interface MailRepository extends JpaRepository<MailModel, Integer>{

}
