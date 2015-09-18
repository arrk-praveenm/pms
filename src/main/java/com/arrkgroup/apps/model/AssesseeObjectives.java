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

	@NamedQuery(name = AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID_V1, query = "update AssesseeObjectives set manager_comments=:comments , manager_rating.id=:managerId  where id = :assessebjectiveId"),	
	@NamedQuery(name = AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION, query = "FROM AssesseeObjectives so where so.assesseeAssessor.id=:assesseID and so.section.id=:sectionID"),
		@NamedQuery(name = AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION, query = "FROM AssesseeObjectives so where so.assesseeAssessor.id=(SELECT id FROM AssesseesAssessor aa where aa.assessorId.id=:assessorId and aa.assesseeId.id=:employeeId and  aa.projectId.id=:projectName and aa.start_date=:StartDate and aa.cycleId.id=:Cycle and aa.roleId.id=:roleId and aa.end_date=:endDate) and section.id=:id"),
		@NamedQuery(name = AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSEEID_CYCLEID_ROLEID, query = "FROM AssesseeObjectives so where so.assesseeAssessor.id = (SELECT id FROM AssesseesAssessor aa WHERE aa.assesseeId.id=:assesseeId and aa.cycleId.id=:cycleId and aa.roleId.id=:roleId)"),
		@NamedQuery(name = AssesseeObjectives.DELETE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID, query = "delete from AssesseeObjectives a where a.id = :assessebjectiveId"),
		@NamedQuery(name = AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID, query = "update AssesseeObjectives set description=:description , weightage.id=:weightageId  where id = :assessebjectiveId"),
		@NamedQuery(name = AssesseeObjectives.GET_ALL_SECTION_ASSESSEE_OBJECTIVES, query = "from AssesseeObjectives a where a.section.id=:section"),
		@NamedQuery(name = AssesseeObjectives.GET_ASSESSEE_ASSESSOR_ID, query = "from AssesseeObjectives a where a.id=:assessebjectiveId"),
		@NamedQuery(name = AssesseeObjectives.GET_ALL_ASSESSEE_SECTION_ID, query = "from AssesseeObjectives a where a.assesseeAssessor.id=:assesseeAssessorId and a.section.id=:sectionId") })

public class AssesseeObjectives {
	// @NamedQuery(name =
	// AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ID_AND_SECTION, query =
	// "FROM AssesseeObjectives s where s.assesseeAssessor.assesseeId.id=(select id from Employee ee where ee.id= :employeeId) and s.section.id=:sectionId orderby s.assesseeAssessor.start_date , s.assesseeAssessor.end_date ")
	// public static final String GET_ASSESSEE_OBJECTIVES_BY_ID_AND_SECTION =
	// "AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ID_AND_SECTION";
	public static final String GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION = "AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION";

	public static final String GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION = "AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION";
		public static final String GET_ASSESSEE_OBJECTIVES_BY_ASSESSEEID_CYCLEID_ROLEID = "AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSEEID_CYCLEID_ROLEID";
	public static final String DELETE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID = "AssesseeObjectives.DELETE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID";
	public static final String UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID = "AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID";
	public static final String UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID_V1 = "AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID_V1";
	public static final String GET_ALL_SECTION_ASSESSEE_OBJECTIVES = "AssesseeObjectives.GET_ALL_SECTION_ASSESSEE_OBJECTIVES";
	public static final String GET_ASSESSEE_ASSESSOR_ID = "AssesseeObjectives.GET_ASSESSEE_ASSESSOR_ID";
	public static final String GET_ALL_ASSESSEE_SECTION_ID = "AssesseeObjectives.GET_ALL_ASSESSEE_SECTION_ID";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String description;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "weightage")
	Weightage weightage;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "self_rating")
	Rating self_rating;

	int self_score;
	String assessee_comments;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "manager_rating")
	Rating manager_rating;

	int manager_score;
	
	String manager_comments;
	
	int max_score;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "assessee_assessor_id")
	AssesseesAssessor assesseeAssessor;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "section_id")
	Section section;

	@Column(name = "last_modified_date")
	Date lastModifiedDate;

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

	public Weightage getWeightage() {
		return weightage;
	}

	public void setWeightage(Weightage weightage) {
		this.weightage = weightage;
	}

	public Rating getSelf_rating() {
		return self_rating;
	}

	public void setSelf_rating(Rating self_rating) {
		this.self_rating = self_rating;
	}

	public int getSelf_score() {
		return self_score;
	}

	public void setSelf_score(int self_score) {
		this.self_score = self_score;
	}

	public int getManager_score() {
		return manager_score;
	}

	public void setManager_score(int manager_score) {
		this.manager_score = manager_score;
	}

	public Rating getManager_rating() {
		return manager_rating;
	}

	public void setManager_rating(Rating manager_rating) {
		this.manager_rating = manager_rating;
	}

	public String getAssessee_comments() {
		return assessee_comments;
	}

	public void setAssessee_comments(String assessee_comments) {
		this.assessee_comments = assessee_comments;
	}

	public String getManager_comments() {
		return manager_comments;
	}

	public void setManager_comments(String manager_comments) {
		this.manager_comments = manager_comments;
	}

	public int getMax_score() {
		return max_score;
	}

	public void setMax_score(int max_score) {
		this.max_score = max_score;
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

}
