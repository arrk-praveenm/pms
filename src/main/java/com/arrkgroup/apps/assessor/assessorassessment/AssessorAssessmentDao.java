package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.List;

import com.arrkgroup.apps.form.SectionDataBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;

public interface AssessorAssessmentDao {

	public List<AssesseesAssessor>  getMyAssessees(String email);
	public Employee  getAssessee(int id);
	
	public List<Section> getAllSections();
	
	public AssesseesAssessor  getAssesseesAssessor(int id);
	
	public AssesseesAssessor  getAssessees(int assesse_id,int role_id);
	
	
	public List<AssesseeObjectives>  getAssesseeObjectives(int sectionID,int objectiveID);
	
	public boolean saveSectionData(SectionDataBean bean);
	
	public List<Role>   getRoleOfCurrentUser(String email);
	

	
	
}
