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
@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_ID, query = "SELECT s FROM AssesseesAssessor s where s.id= :id "),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_EMPLOYEE_ID, query = "SELECT s FROM AssesseesAssessor s where s.assesseeId.id= :id and s.roleId.id= :role_id and s.projectId.id=:projectId"),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSOR, query = "SELECT s FROM AssesseesAssessor s where s.assessorId.id= :id"),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSOR_BY_CYCLE, query = "SELECT DISTINCT s FROM AssesseesAssessor s where s.cycleId.id= :id"),
		@NamedQuery(name = AssesseesAssessor.FIND_ROLE_BY_EMAIL, query = "SELECT s FROM AssesseesAssessor s where s.assessorId.id=(select id from Employee ee where ee.email= :email) "),
	@NamedQuery(name = AssesseesAssessor.FIND_ASSESSEES_BY_EMAIL, query = "SELECT s FROM AssesseesAssessor s where s.assessorId.id=(select id  from  Employee ee where ee.email= :email) "),
	@NamedQuery(name = AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, query="FROM AssesseesAssessor s where s.cycleId.id=:cycleId "
			   + "and s.start_date=:start_date and s.end_date=:end_date and s.projectId.id=:project_name "
			   + "and s.assesseeId.id=:assesseeId and s.assessorId.id=:assessorId and s.roleId.id=:roleId)"),
   @NamedQuery(name = AssesseesAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSEEID_ROLEID, query="FROM AssesseesAssessor s where s.cycleId.id=:cycleId "
		   + " and s.projectId.id=:project_name "
		   + "and s.assesseeId.id=:assesseeId  and s.roleId.id=:roleId and s.status in (:statusList)"),
	@NamedQuery(name = AssesseesAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, query="FROM AssesseesAssessor s where s.cycleId.id=:cycleId "
					   + "and  s.projectId.id=:project_name "
					   + "and s.assesseeId.id=:assesseeId and s.roleId.id=:roleId and s.status in (:statusList))"),
	@NamedQuery(name = AssesseesAssessor.UPDATE_ASSESSE_ASSESSOR_BY_ID, query = "update AssesseesAssessor set start_date=:startdate , end_date=:enddate , assessorId.id=:assessorId where id = :assesseesAssessorId"),
	@NamedQuery(name = AssesseesAssessor.FIND_ROLES_OF_ASSESSEE, query = "from AssesseesAssessor aa where aa.assesseeId.id=(select id from  Employee ee where ee.email=:email) "),


	@NamedQuery(name = AssesseesAssessor.UPDATE_STATUS, query = "update AssesseesAssessor set status=:status where projectId.id=:project_id "
			   + "and assesseeId.id=:assesseeId and roleId.id=:roleId and status=:statusCondition ")


})
public class AssesseesAssessor {
	public static final String FIND_ASSESSEES_BY_ASSESSOR = "AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSOR";
	public static final String FIND_ASSESSOR_BY_CYCLE = "AssesseesAssessor.FIND_ASSESSOR_BY_CYCLE";
	public static final String FIND_ROLE_BY_EMAIL = "AssesseesAssessor.FIND_ROLE_BY_EMAIL";
	public static final String FIND_ASSESSEES_BY_EMAIL = "AssesseeAssessor.FIND_ASSESSEES_BY_EMAIL";

	public static final String FIND_ASSESSEES_BY_EMPLOYEE_ID = "AssesseeAssessor.FIND_ASSESSEES_BY_EMPLOYEE_ID";

	public static final String FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID = "AssesseeAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID";
	public static final String FIND_BY_CYCLEID_PROJECT_ASSESSORID_ASSESSEEID_ROLEID = "AssesseeAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSORID_ASSESSEEID_ROLEID";
	public static final String UPDATE_ASSESSE_ASSESSOR_BY_ID = "AssesseeAssessor.UPDATE_ASSESSE_ASSESSOR_BY_ID";
	public static final String FIND_ROLES_OF_ASSESSEE = "AssesseesAssessor.FIND_ROLES_OF_ASSESSEE";
	public static final String FIND_BY_CYCLEID_PROJECT_ASSESSEEID_ROLEID = "AssesseeAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSEEID_ROLEID";
	public static final String FIND_ASSESSEES_BY_ID = "AssesseesAssessor.FIND_ASSESSEES_BY_ID";



	public static final String UPDATE_STATUS = "AssesseesAssessor.UPDATE_STATUS";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "assessor_id")
	Employee assessorId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_name")
	Project projectId;

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



	public Project getProjectId() {
		return projectId;
	}

	public void setProjectId(Project projectId) {
		this.projectId = projectId;
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
