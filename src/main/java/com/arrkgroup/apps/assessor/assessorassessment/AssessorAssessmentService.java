package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.List;
import java.util.Map;

import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;

public interface AssessorAssessmentService {
	
	public List<AssesseesAssessor>  getMyAssessees(String email);
	public Employee  getAssessee(int id);
	public EmployeeBean getAssesseeBean(int id);
	
	public List<Section> getAllSections();
	

	
	public List<AssesseeObjectives>  getAssesseeObjectives(int sectionID,int objectiveID);
	public List<AssesseeObjectives>  getAssesseeObjectives(int sectionID,int assesseID,int role_id, int projectId ,int assesseeAssessorId);
	

	//public boolean saveSectionData(AssessorAssessmentBean bean);
	
	public void  saveSectionSummary(int sectionID,int employeeid,int roleid,int projectid, int assesseeAssessorId);
	
	
	
	public List<SectionConsolidatedBean> findById(String assessorId,String role,int projectid,int assesseeAssessorId);
	public List<SectionConsolidatedBean> getAllSectionsBean();
	


	public List<AssesseesAssessor>   getRoleOfCurrentUser(String email);

	public boolean saveAssessorAssessment(AssessorAssessmentBean bean);
	public boolean saveSelfAssessment(AssessorAssessmentBean bean);

	public Map<Integer, List<AssesseeObjectives>> checkAllObjectiveStatus(AssessorAssessmentBean bean);
	
	public boolean  updateAssesseesAssessorStatus(AssessorAssessmentBean bean,String status);
	
	public AssesseesAssessor  getAssesseesAssessor(int id);
	 
	

}
