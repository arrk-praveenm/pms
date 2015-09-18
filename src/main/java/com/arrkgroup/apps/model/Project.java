package com.arrkgroup.apps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "project")
@NamedQueries({

	@NamedQuery(name = Project.GET_PROJECT_ID, query = "FROM Project where id=:id "),
	@NamedQuery(name= Project.GET_ALL_PROJECTS, query = "FROM Project")
	
})
public class Project {

	public static final String GET_PROJECT_ID= "Project.GET_PROJECT_ID";
	public static final String GET_ALL_PROJECTS= "Project.GET_ALL_PROJECTS";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;	
	
	String project_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	
}
