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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;





@Entity
@Table(name="objectives")
@NamedQueries({
	@NamedQuery(name = ObjectivesModel.FIND_BY_ID, query = "SELECT e FROM ObjectivesModel e where e.id = :id"),
	@NamedQuery(name = ObjectivesModel.findAll, query = "select c from ObjectivesModel c "),
@NamedQuery(name = ObjectivesModel.FIND_BY_SECTION_ID, query = "SELECT e FROM ObjectivesModel e where e.section.id = :section_id"),
	@NamedQuery(name = ObjectivesModel.FIND_BY_SECTION_ID_NOT_PRESENT , query = "SELECT e FROM ObjectivesModel e where  e.section.id = :section_id AND e.id  not in ( SELECT objectives.id FROM RoleModel e2 where e2.role.id = :role_id AND e2.section.id = :section_id)")
})
public class ObjectivesModel {
	public static final String findAll = "ObjectivesModel.findAll";
	public static final String FIND_BY_ID = "ObjectivesModel.FIND_BY_ID";
	public static final String FIND_BY_SECTION_ID = "ObjectivesModel.FIND_BY_SECTION_ID";
	public static final String FIND_BY_SECTION_ID_NOT_PRESENT = "ObjectivesModel.FIND_BY_SECTION_ID_NOT_PRESENT";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	

	
	@Column(name="last_modified_date")
	private Date last_modified_date;
	
	@Column(name="description")
	String description;
	
	
	  @ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "section_id")
	    private Section section;
	
	
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
	
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}

	
	
	

}
