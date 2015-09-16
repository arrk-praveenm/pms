package com.arrkgroup.apps.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "weightage")
@NamedQueries({

	@NamedQuery(name = Weightage.GET_Weightage_ID, query = "FROM Weightage where id=:id "),
	@NamedQuery(name= Weightage.GET_ALL_Weightage, query = "FROM Weightage")
	
})
public class Weightage {

	public static final String GET_Weightage_ID= "Project.GET_Weightage_ID";
	public static final String GET_ALL_Weightage= "Project.GET_ALL_Weightage";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;	
	
	int weightage;
	
	String description;
	Date last_modified_date;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWeightage() {
		return weightage;
	}

	public void setWeightage(int weightage) {
		this.weightage = weightage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLast_modified_date() {
		return last_modified_date;
	}

	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}

	
	
	
	
}
