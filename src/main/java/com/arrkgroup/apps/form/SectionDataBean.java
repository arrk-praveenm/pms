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

import com.arrkgroup.apps.model.AssesseesAssessor;


public class SectionDataBean {
	
	int id;

	
	int sectionid;
	
	private Date last_modified_date;


	private int self_rating;


	private int self_score;

	private int manager_rating;

	private int manager_score;


	private String manager_comments;


	private String employee_comments;


	private int max_score;
	
	 

	    private int employee_assessor_id;







		public int getId() {
			return id;
		}




		public void setId(int id) {
			this.id = id;
		}


		


		public int getSectionid() {
			return sectionid;
		}




		public void setSectionid(int sectionid) {
			this.sectionid = sectionid;
		}




		public Date getLast_modified_date() {
			return last_modified_date;
		}




		public void setLast_modified_date(Date last_modified_date) {
			this.last_modified_date = last_modified_date;
		}




		public int getSelf_rating() {
			return self_rating;
		}




		public void setSelf_rating(int self_rating) {
			this.self_rating = self_rating;
		}




		public int getSelf_score() {
			return self_score;
		}




		public void setSelf_score(int self_score) {
			this.self_score = self_score;
		}




		public int getManager_rating() {
			return manager_rating;
		}




		public void setManager_rating(int manager_rating) {
			this.manager_rating = manager_rating;
		}




		public int getManager_score() {
			return manager_score;
		}




		public void setManager_score(int manager_score) {
			this.manager_score = manager_score;
		}




		public String getManager_comments() {
			return manager_comments;
		}




		public void setManager_comments(String manager_comments) {
			this.manager_comments = manager_comments;
		}




		public String getEmployee_comments() {
			return employee_comments;
		}




		public void setEmployee_comments(String employee_comments) {
			this.employee_comments = employee_comments;
		}




		public int getMax_score() {
			return max_score;
		}




		public void setMax_score(int max_score) {
			this.max_score = max_score;
		}




		public int getEmployee_assessor_id() {
			return employee_assessor_id;
		}




		public void setEmployee_assessor_id(int employee_assessor_id) {
			this.employee_assessor_id = employee_assessor_id;
		}
	
	

	
	
}
