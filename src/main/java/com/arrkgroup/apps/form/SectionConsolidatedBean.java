package com.arrkgroup.apps.form;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


public class SectionConsolidatedBean {
	


	private int id;

	private String section;



	private float section_point;


	
	

	private int section_max_score;

	private int section_manager_score;




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getSection_max_score() {
		return section_max_score;
	}

	public void setSection_max_score(int section_max_score) {
		this.section_max_score = section_max_score;
	}

	public int getSection_manager_score() {
		return section_manager_score;
	}

	public void setSection_manager_score(int section_manager_score) {
		this.section_manager_score = section_manager_score;
	}

	public float getSection_point() {
		return section_point;
	}

	public void setSection_point(float section_point) {
		this.section_point = section_point;
	}

	
	

}
