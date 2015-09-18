package com.arrkgroup.apps.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "assessee_assessor")

@NamedQueries({
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_EMPLOYEE_ID, query = "SELECT s FROM AssesseesAssessor s where s.assesseeId.id= :id and s.roleId.id= :role_id"),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSOR, query = "SELECT s FROM AssesseesAssessor s where s.assessorId.id= :id"),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSOR_BY_CYCLE, query = "SELECT DISTINCT s FROM AssesseesAssessor s where s.cycleId.id= :id"),
	@NamedQuery(name = AssesseesAssessor.FIND_ROLE_BY_EMAIL, query = "SELECT s FROM AssesseesAssessor s where s.assessorId.id=(select id from Employee ee where ee.email= :email) "),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_EMAIL, query = "SELECT s FROM AssesseesAssessor s where s.assessorId.id=(select id  from  Employee ee where ee.email= :email) ")

})
public class AssesseesAssessor {
	public static final String FIND_ASSESSEES_BY_ASSESSOR = "AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSOR";
	public static final String FIND_ASSESSOR_BY_CYCLE = "AssesseesAssessor.FIND_ASSESSOR_BY_CYCLE";
	public static final String FIND_ROLE_BY_EMAIL = "AssesseesAssessor.FIND_ROLE_BY_EMAIL";
	public static final String FIND_ASSESSEES_BY_EMAIL = "AssesseeAssessor.FIND_ASSESSEES_BY_EMAIL";
	public static final String FIND_ASSESSEES_BY_EMPLOYEE_ID = "AssesseeAssessor.FIND_ASSESSEES_BY_EMPLOYEE_ID";

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "assessor_id")
	Employee assessorId;
	String project_name;
	Date start_date;
	Date end_date;
	String period_edit_flag;
	String status;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	Employee assesseeId;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id")
	Role roleId;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cycle_id")
	Cycle cycleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(Employee assessorId) {
		this.assessorId = assessorId;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getPeriod_edit_flag() {
		return period_edit_flag;
	}

	public void setPeriod_edit_flag(String period_edit_flag) {
		this.period_edit_flag = period_edit_flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getAssesseeId() {
		return assesseeId;
	}

	public void setAssesseeId(Employee assesseeId) {
		this.assesseeId = assesseeId;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public Cycle getCycleId() {
		return cycleId;
	}

	public void setCycleId(Cycle cycleId) {
		this.cycleId = cycleId;
	}

}
