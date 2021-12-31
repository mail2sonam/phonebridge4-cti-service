package com.eupraxia.telephony.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.annotation.Cacheable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Cacheable
@Getter
@Setter
@Table(name = "branch")
public class BranchModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long id;  
		
	@Column(name ="branchcode")
	private String branchCode; 
	@Column(name = "branchname")
	private String branchName;
	
}
