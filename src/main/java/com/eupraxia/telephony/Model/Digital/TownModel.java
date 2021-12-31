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
@Table(name = "town")
public class TownModel implements Serializable{
	@Column(name="town_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int townId;
	
	@Column(name="district_id")
	private int districtId;
	
	@Column(name="town_name")
	private String townName;
}
