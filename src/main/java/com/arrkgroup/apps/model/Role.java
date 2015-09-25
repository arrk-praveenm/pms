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
@Table(name="role")
@NamedQueries({
	@NamedQuery(name = Role.FIND_BY_ROLE_ID, query = "from Role e where e.id = :id"),
	@NamedQuery(name = Role.FIND_ALL, query = "from Role c order by c.title"),

	
})
public class Role {
	public static final String FIND_ALL = "Role.FIND_ALL";
	public static final String FIND_BY_ROLE_ID = "Role.FIND_BY_ROLE_ID";
	
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name="last_modified_date")
	private Date last_modified_date;

	@Column(name="title")
	private String title;

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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Role(int id, Date last_modified_date, String title) {
		super();
		this.id = id;
		this.last_modified_date = last_modified_date;
		this.title = title;
	}

	public Role() {
		super();
	}
	
	
	
	
}
