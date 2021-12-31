package com.eupraxia.telephony.Model.Digital;

import java.io.Serializable;

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
@Table(name = "pincode")
public class PinCodeModel implements Serializable{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy="native")
	private Long id;
	
	@Column(name="PINCODE")	
	private String pinCode;
	
	@Column(name="TALUK")
	private String taluk;
	
	@Column(name="Town")
	private String town;
	
	@Column(name="DISTRICT ")
	private String district ;
	
	@Column(name="State")
	private String state;
	
	
}
