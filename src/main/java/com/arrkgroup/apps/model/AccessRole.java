package com.arrkgroup.apps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="access_role")
@NamedQueries({
@NamedQuery(name = AccessRole.FIND_BY_EMAIL, query = "select a from AccessRole a where a.email = :email"),
@NamedQuery(name = AccessRole.GET_ALL_USERS, query = "select a from AccessRole a where a.role='HR'"),
@NamedQuery(name = AccessRole.delete_BY_EMAILid, query = "delete  from AccessRole a where a.email = :email")
})
public class AccessRole {
	public static final String FIND_BY_EMAIL = "AccessRole.FIND_BY_EMAIL";
	public static final String delete_BY_EMAILid = "AccessRole.delete_BY_EMAILid";
	public static final String GET_ALL_USERS = "AccessRole.GET_ALL_USERS";
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Email
    @Column(name = "EMAIL", nullable = false)
	String email;
	
	String fname;
	String lname;
	
	String  role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}


}
