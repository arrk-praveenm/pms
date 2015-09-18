package com.arrkgroup.apps.assessor.assignobjectives;

import java.sql.SQLException;
import java.util.List;

import com.arrkgroup.apps.form.CopyObjectivesBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;

public interface AssignObjectivesService {
	public List<Cycle> getAllAssessmentCycles();
	public List<Employee> getAllAssesses();
	public List<Role> getAllRoles();
	public void copyRoleObjectives(CopyObjectivesBean copyObjectivesBean);
	public void copyAssesseObjectives(CopyObjectivesBean copyObjectivesBean);
	public List<AssesseeObjectives> getAssesseObjectives(CopyObjectivesBean copyObjectivesBean,int sectionId);
	public boolean addAssesseeObjective(CopyObjectivesBean copyObjectivesBean,String sectionId, String Description);
	public boolean saveAssesseeObjectivebySection(AssesseeObjectives assesseeObjectives);
	public boolean deleteAssesseeObjectivebySection(int sectionId)  throws SQLException ;
	public AssesseeObjectives getAssesseeAssessorId(int assesseeObjectiveId);
	public List<AssesseeObjectives> getALLAssesseObjectivesBySectionId(AssesseeObjectives assesseeObjectives);
}
