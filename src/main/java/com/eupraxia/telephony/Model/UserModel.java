package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.annotation.Cacheable;


import lombok.Getter;
import lombok.Setter;

@Entity
@Cacheable

@Getter
@Setter
@Table(name = "user")
public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long id;
	
	@Column(name = "username")
	private String username;	
	
	@Column(name = "userextension")
	private String userextension;
	
	@Column(name = "extensionstatus")
	private String extensionstatus;
	
	@Column(name = "onbreak")
	private String onbreak;
	
	@Column(name = "popupstatus")
	private String popupstatus;
	
	@Column(name = "usertype")
	private String usertype;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "context")
	private String context;
	
	@Column(name = "callstatus")
	private String callstatus;
	
	
	@Column(name = "branch_name")
	private String branchid;
	
	@Column(name = "ivruser")
	private boolean ivruser;
	
	
	@Column(name = "deleted")
	private String deleted;
	
	@Column(name = "prefix")
	private String prefix;
	
	@Column(name = "extensiontype")
	private String extensiontype;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "popupstatusupdatetime")
	private String popupStatusUpdateTime;
	
	@Column(name = "agenttype")
	private String agenttype;
	
	@Column(name = "department_code")
	private String departmentcode;
	@Column(name = "department_name")
	private String department_name;
	
	@Column(name = "queue_name")
	private String queueName;
	
	@Column(name = "dialler_active")
	private String diallerActive;
	
	@Column(name = "inactive")
	private int inActive;
	
//	@Column(name = "team_lead")
//	private String teamLead;
	
	private boolean enabled;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles = new HashSet<>();

}
