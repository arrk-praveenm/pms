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
@Table(name="section_consolidated")
@NamedQueries({
@NamedQuery(name = SectionConsolidated.UPDATE_ASSESSE_SECTON_ASSESSOR, query = "update SectionConsolidated set last_modified_date=:date , section_self_score=:self_score ,section_manager_score=:manager_score, section_max_score=:max_score where section.id = :sectionid and assesseesassessor.id=:assessorid"),
@NamedQuery(name = SectionConsolidated.FIND_BY_ID, query = "FROM SectionConsolidated e where e.assesseesassessor.id = :id")
})
public class SectionConsolidated {
	
	public static final String UPDATE_ASSESSE_SECTON_ASSESSOR = "SectionConsolidated.UPDATE_ASSESSE_SECTON_ASSESSOR";
	public static final String FIND_BY_ID = "SectionConsolidated.FIND_BY_ID";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name="last_modified_date")
	private Date last_modified_date;



	@Column(name="section_self_score")
	private int section_self_score;


	@Column(name="section_manager_score")
	private float section_manager_score;


	@Column(name="section_max_score")
	private int section_max_score;



	

	   @ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "section_id")
	    private Section section;
	


	@ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "employee_assessor_id")
	    private AssesseesAssessor assesseesassessor;



	public SectionConsolidated()
	{}
	
	public SectionConsolidated(Date last_modified_date, int section_self_score,
			float section_manager_score, int section_max_score, Section section,
			AssesseesAssessor assesseesassessor) {
		super();
		this.last_modified_date = last_modified_date;
		this.section_self_score = section_self_score;
		this.section_manager_score = section_manager_score;
		this.section_max_score = section_max_score;
		this.section = section;
		this.assesseesassessor = assesseesassessor;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Date getLast_modified_date() {
		return last_modified_date;
	}



	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}



	public int getSection_self_score() {
		return section_self_score;
	}



	public void setSection_self_score(int section_self_score) {
		this.section_self_score = section_self_score;
	}







	public float getSection_manager_score() {
		return section_manager_score;
	}

	public void setSection_manager_score(float section_manager_score) {
		this.section_manager_score = section_manager_score;
	}

	public int getSection_max_score() {
		return section_max_score;
	}



	public void setSection_max_score(int section_max_score) {
		this.section_max_score = section_max_score;
	}



	public Section getSection() {
		return section;
	}



	public void setSection(Section section) {
		this.section = section;
	}



	public AssesseesAssessor getAssesseesassessor() {
		return assesseesassessor;
	}



	public void setAssesseesassessor(AssesseesAssessor assesseesassessor) {
		this.assesseesassessor = assesseesassessor;
	}
	
	
	

}
