package com.eupraxia.telephony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eupraxia.telephony.Model.Role;



public interface RoleRepository extends JpaRepository<Role, String> {

	Role findByName(String name);
}
