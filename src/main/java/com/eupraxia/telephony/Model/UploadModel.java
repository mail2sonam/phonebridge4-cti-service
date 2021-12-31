package com.eupraxia.telephony.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "autodialler_data")
public class UploadModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7669462305262836737L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;

	@Column(name="rowid")
	private int rowid;

	@Column(name="name")
	private String name;

	@Column(name="phonenumber1")
	private String phonenumber1;

	@Column(name="phonenumber2")
	private String phonenumber2;

	@Column(name="address")
	private String address;

	@Column(name="status")
	private String status;




}
