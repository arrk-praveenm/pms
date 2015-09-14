package com.arrkgroup.apps.model;

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
@NamedQuery(name = SectionModel.GET_ALL_SECTIONS, query = "select a from SectionModel a "),

})
public class SectionModel {
	public static final String GET_ALL_SECTIONS = "SectionModel.GET_ALL_SECTIONS";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	private String title;
	
	
	
	@Column(name = "last_modified_date", nullable = false)
	private String lastModifiedDate;
	

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
	
}
