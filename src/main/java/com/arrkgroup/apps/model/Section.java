package com.arrkgroup.apps.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="section")
@NamedQueries({
	@NamedQuery(name = Section.FIND_BY_ID, query = "SELECT e FROM Section e where e.id = :id"),
@NamedQuery(name = Section.GET_ALL_SECTIONS_WITH_SORTING, query = "select a from Section a order by a.section desc "),
@NamedQuery(name = Section.GET_ALL_SECTIONS, query = "select a from Section a  "),

})
public class Section {
	public static final String FIND_BY_ID = "Section.FIND_BY_ID";
	public static final String GET_ALL_SECTIONS = "Section.GET_ALL_SECTIONS";
	public static final String GET_ALL_SECTIONS_WITH_SORTING = "Section.GET_ALL_SECTIONS_WITH_SORTING";


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "title", nullable = false)
	private String section;

	@Column(name = "section_weightage")
	private float section_weightage;


	@Column(name = "last_modified_date", nullable = false)
	private Date lastModifiedDate=new Date();

	public Section(){}

	public Section(int id, String section, Date lastModifiedDate){
		this.id=id;
		this.section=section;
		this.lastModifiedDate=lastModifiedDate;
	}



	public float getSection_weightage() {
		return section_weightage;
	}

	public void setSection_weightage(float section_weightage) {
		this.section_weightage = section_weightage;
	}

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
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


}
