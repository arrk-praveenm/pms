package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.List;

import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.SectionConsolidated;

public interface AssessorAssessmentService {
	
	public List<AssesseesAssessor>  getMyAssessees(String email);
	public Employee  getAssessee(int id);
	public EmployeeBean getAssesseeBean(int id);
	
	public List<Section> getAllSections();
	

	
	public List<AssesseeObjectives>  getAssesseeObjectives(int sectionID,int objectiveID);
	public List<AssesseeObjectives>  getAssesseeObjectives(int sectionID,int assesseID,int role_id);
	
	public boolean saveSectionData(AssessorAssessmentBean bean);
	
	public void  saveSectionSummary(int sectionID,int employeeid,int roleid);
	
	
	
	public List<SectionConsolidatedBean> findById(String assessorId,String role);
	public List<SectionConsolidatedBean> getAllSectionsBean();
	

}
