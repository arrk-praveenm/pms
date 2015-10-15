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

import org.hibernate.validator.constraints.Email;
@Entity
@Table(name="employee")
@NamedQueries({
@NamedQuery(name = Employee.FIND_ALL, query = "from Employee e order by e.fullname"),
@NamedQuery(name = Employee.FIND_ROLE_BY_EMAIL, query = "from Employee e where e.reporting_manager = (select ee.id from Employee ee where ee.email= :email)"),
@NamedQuery(name = Employee.FIND_BY_ID, query = "from Employee e where e.id = :id"),
@NamedQuery(name = Employee.FIND_BY_EMAIl, query = "from Employee e where e.email = :email"),
@NamedQuery(name = Employee.FIND_ASSESSEE_BY_ID, query = "select ee from Employee ee where ee.id= :id "),
@NamedQuery(name = Employee.FIND_ASSESSOR_BY_MANAGER_CYCLE, query = "from Employee e where e.id in (select ee.assessorId.id from AssesseesAssessor ee where ee.cycleId.id= :cycle) and e.reporting_manager=:manager "),
@NamedQuery(name = Employee.FIND_ALL_MANAGER, query = "from Employee e where e.id in (select ee.reporting_manager from Employee ee) ")

})












public class Employee {
	public static final String FIND_ALL = "Employee.FIND_ALL";
	public static final String FIND_ROLE_BY_EMAIL = "Employee.FIND_ROLE_BY_EMAIL";
	public static final String FIND_BY_ID = "Employee.FIND_BY_ID";
	public static final String FIND_BY_EMAIl = "Employee.FIND_BY_EMAIl";
	public static final String FIND_ASSESSEE_BY_ID = "Employee.FIND_ASSESSEE_BY_ID";
	public static final String FIND_ALL_MANAGER = "Employee.FIND_ALL_MANAGER";

	public static final String FIND_ASSESSOR_BY_MANAGER_CYCLE = "Employee.FIND_ASSESSOR_BY_MANAGER_CYCLE";

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String fname;
	String lname;
	String fullname;
	@Email
    String email;
	String designation;
	String location;
	Date date_of_joining;
	Date date_of_leaving;
	Date date_of_resignation;
	int reporting_manager;
	String active;
	int cycle;
	Date last_modified_date;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getDate_of_joining() {
		return date_of_joining;
	}
	public void setDate_of_joining(Date date_of_joining) {
		this.date_of_joining = date_of_joining;
	}
	public Date getDate_of_leaving() {
		return date_of_leaving;
	}
	public void setDate_of_leaving(Date date_of_leaving) {
		this.date_of_leaving = date_of_leaving;
	}
	public Date getDate_of_resignation() {
		return date_of_resignation;
	}
	public void setDate_of_resignation(Date date_of_resignation) {
		this.date_of_resignation = date_of_resignation;
	}




	public int getReporting_manager() {
		return reporting_manager;
	}
	public void setReporting_manager(int reporting_manager) {
		this.reporting_manager = reporting_manager;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public Date getLast_modified_date() {
		return last_modified_date;
	}
	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}









}
