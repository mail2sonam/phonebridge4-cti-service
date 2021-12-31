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
@Table(name = "sub_categories")
public class SubCategoryModel implements Serializable{
	
	@Column(name="sub_category_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int subCategoryId;
	
	@Column(name="sub_category_name")
	private String subCategoryName ;
	
	@Column(name="category_id")
	private int categoryId;
	
	

}
