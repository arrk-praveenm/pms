package com.arrkgroup.apps.form;

public class AssesseRoleBean {
	
	int roleId;
	int projectId;
	String assesseeProjectRole;
	String assessmentStatus;
	int assesseeAssessorId;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getAssesseeProjectRole() {
		return assesseeProjectRole;
	}
	public void setAssesseeProjectRole(String assesseeProjectRole) {
		this.assesseeProjectRole = assesseeProjectRole;
	}
	public String getAssessmentStatus() {
		return assessmentStatus;
	}
	public void setAssessmentStatus(String assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}
	public int getAssesseeAssessorId() {
		return assesseeAssessorId;
	}
	public void setAssesseeAssessorId(int assesseeAssessorId) {
		this.assesseeAssessorId = assesseeAssessorId;
	}
	
	

}
