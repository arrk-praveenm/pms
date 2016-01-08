package com.arrkgroup.apps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="pdf_report_view")

@NamedQueries
({
	@NamedQuery(name = pdftableview.GET_ALL_RECORDS_BY_ID, query = "select a from pdftableview a where a.id=:assesseassessorid order by a.id,a.role,a.section"),
	@NamedQuery(name = pdftableview.GET_ALL_RECORDS, query = "select a from pdftableview a")

})






public class pdftableview {

	public static final String GET_ALL_RECORDS = "pdftableview.GET_ALL_RECORDS";

	public static final String GET_ALL_RECORDS_BY_ID = "pdftableview.GET_ALL_RECORDS_BY_ID";


	 @Id
	 @Column(name = "uid", nullable = false)
	long uid;


    @Column(name = "id", nullable = false)
	int id;

    @Column(name = "fullname", nullable = false)
	String fullname;


    @Column(name = "role", nullable = false)
	String role;


    @Column(name = "cycle", nullable = false)
	String cycle;


    @Column(name = "section", nullable = false)
	String section;


    @Column(name = "objective", nullable = false)
	String objective;


    @Column(name = "weightage", nullable = false)
    int weightage;


    @Column(name = "self_rating", nullable = false)
    int self_rating;


    @Column(name = "self_score", nullable = false)
    int self_score;


    @Column(name = "assessee_comments", nullable = false)
	String assessee_comments;



    @Column(name = "manager_rating", nullable = false)
    int manager_rating;


    @Column(name = "manager_score", nullable = false)
    int manager_score;


    @Column(name = "manager_comments", nullable = false)
    String manager_comments;




    @Column(name = "max_score", nullable = false)
    int max_score;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFullname() {
		return fullname;
	}



	public void setFullname(String fullname) {
		this.fullname = fullname;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public String getCycle() {
		return cycle;
	}



	public void setCycle(String cycle) {
		this.cycle = cycle;
	}



	public String getSection() {
		return section;
	}



	public void setSection(String section) {
		this.section = section;
	}



	public String getObjective() {
		return objective;
	}



	public void setObjective(String objective) {
		this.objective = objective;
	}



	public int getWeightage() {
		return weightage;
	}



	public void setWeightage(int weightage) {
		this.weightage = weightage;
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



	public String getAssessee_comments() {
		return assessee_comments;
	}



	public void setAssessee_comments(String assessee_comments) {
		this.assessee_comments = assessee_comments;
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



	public int getMax_score() {
		return max_score;
	}



	public void setMax_score(int max_score) {
		this.max_score = max_score;
	}



	public String getManager_comments() {
		return manager_comments;
	}



	public void setManager_comments(String manager_comments) {
		this.manager_comments = manager_comments;
	}








}
