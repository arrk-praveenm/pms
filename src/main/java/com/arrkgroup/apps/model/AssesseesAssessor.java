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
	@NamedQuery(name = AssesseesAssessor.FIND_ROLE_BY_EMAIL, query = "FROM AssesseesAssessor s where s.assessorId.id=(select id from Employee ee where ee.email= :email) "),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_EMAIL, query = "FROM AssesseesAssessor s where s.assessorId.id=(select id  from  Employee ee where ee.email= :email) "),
	@NamedQuery(name = AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, query="FROM AssesseesAssessor s where s.cycleId.id=:cycleId "
			+ "and s.start_date=:start_date and s.end_date=:end_date and s.project_name=:project_name "
			+ "and s.assesseeId.id=:assesseeId and s.assessorId.id=:assessorId and s.roleId.id=:roleId")
})
public class AssesseesAssessor {
	public static final String FIND_ROLE_BY_EMAIL = "AssesseesAssessor.FIND_ROLE_BY_EMAIL";
	public static final String FIND_ASSESSEES_BY_EMAIL = "AssesseeAssessor.FIND_ASSESSEES_BY_EMAIL";
	public static final String FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID = "AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID";

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
