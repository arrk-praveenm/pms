package com.arrkgroup.apps.model;

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

@Entity
@Table(name = "assessee_objectives")
@NamedQueries({
	@NamedQuery(name = AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION, query = "FROM AssesseeObjectives so where so.assesseeAssessor.id=(SELECT id FROM AssesseesAssessor aa where aa.assesseeId.id=:employeeId and  aa.project_name=:projectName and aa.start_date=:StartDate) and section.id=:id"),
	@NamedQuery(name = AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION, query = "FROM AssesseeObjectives so where so.assesseeAssessor.id=:assesseID and so.section.id=:sectionID")
	

})
public class AssesseeObjectives {
	 
	//@NamedQuery(name = AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ID_AND_SECTION, query = "FROM AssesseeObjectives s where s.assesseeAssessor.assesseeId.id=(select id from Employee ee where ee.id= :employeeId) and s.section.id=:sectionId orderby s.assesseeAssessor.start_date , s.assesseeAssessor.end_date ")
	public static final String GET_ASSESSEE_OBJECTIVES_BY_ID_AND_SECTION = "AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ID_AND_SECTION";
	public static final String GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION = "AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION";
	public static final String GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION = "AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;	
	
	String description;
	
	@Column(name="weightage")
	int weightage;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assessee_assessor_id")
	AssesseesAssessor assesseeAssessor;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
	Section section;
	
	@Column(name="last_modified_date")
	Date lastModifiedDate;
	

	@Column(name="self_rating")
	private int self_rating;

	@Column(name="self_score")
	private int self_score;

	@Column(name="manager_rating")
	private int manager_rating;

	@Column(name="manager_score")
	private int manager_score;

	@Column(name="manager_comments")
	private String manager_comments;

	@Column(name="assessee_comments")
	private String assessee_comments;

	@Column(name="max_score")
	private int max_score;
	
	
	
	
	
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}		

	public int getWeightage() {
		return weightage;
	}

	public void setWeightage(int weightage) {
		this.weightage = weightage;
	}

	public AssesseesAssessor getAssesseeAssessor() {
		return assesseeAssessor;
	}

	public void setAssesseeAssessor(AssesseesAssessor assesseeAssessor) {
		this.assesseeAssessor = assesseeAssessor;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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

	public String getAssessee_comments() {
		return assessee_comments;
	}

	public void setAssessee_comments(String assessee_comments) {
		this.assessee_comments = assessee_comments;
	}

	public int getMax_score() {
		return max_score;
	}

	public void setMax_score(int max_score) {
		this.max_score = max_score;
	}
	
	
	
	

}
