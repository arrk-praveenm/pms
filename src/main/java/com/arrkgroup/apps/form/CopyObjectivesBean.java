package com.arrkgroup.apps.form;

import java.util.Date;

public class CopyObjectivesBean {
	int assessmentCycle;
	String assessmentFromDate;
	String assessmentToDate;
	int projectName;
	String action;
	int assessor;
	int assessee;
	int assesseeRole;
	
	public int getAssessmentCycle() {
		return assessmentCycle;
	}
	public void setAssessmentCycle(int assessmentCycle) {
		this.assessmentCycle = assessmentCycle;
	}
	
	public String getAssessmentFromDate() {
		return assessmentFromDate;
	}
	public void setAssessmentFromDate(String assessmentFromDate) {
		this.assessmentFromDate = assessmentFromDate;
	}
	public String getAssessmentToDate() {
		return assessmentToDate;
	}
	public void setAssessmentToDate(String assessmentToDate) {
		this.assessmentToDate = assessmentToDate;
	}

	public int getProjectName() {
		return projectName;
	}
	public void setProjectName(int projectName) {
		this.projectName = projectName;
	}
	public int getAssessor() {
		return assessor;
	}
	public void setAssessor(int assessor) {
		this.assessor = assessor;
	}
	public int getAssessee() {
		return assessee;
	}
	public void setAssessee(int assessee) {
		this.assessee = assessee;
	}
	public int getAssesseeRole() {
		return assesseeRole;
	}
	public void setAssesseeRole(int assesseeRole) {
		this.assesseeRole = assesseeRole;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}	

}
