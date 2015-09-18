package com.arrkgroup.apps.assessor.assignobjectives;

import java.sql.SQLException;
import java.util.List;

import com.arrkgroup.apps.form.CopyObjectivesBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;

public interface AssignObjectivesDao {
	public List<Cycle> getAllAssessmentCycles();
	public List<Employee> getAllAssesses();
	public List<Role> getAllRoles();
	public boolean copyRoleObjectives(AssesseesAssessor assesseesAssessor);
	public boolean copyAssesseObjectives(AssesseesAssessor assesseesAssessor);
	public List<AssesseeObjectives> getAssesseObjectives(CopyObjectivesBean copyObjectivesBean,int sectionId);
	public boolean addAssesseeObjective(AssesseeObjectives assesseeObjectives);
	public AssesseesAssessor getAssesseeAssessor(CopyObjectivesBean copyObjectivesBean);
	public boolean saveAssesseeObjectivebySection(AssesseeObjectives assesseeObjectives);
	public boolean deleteAssesseeObjectivebySection(int sectionId)  throws SQLException ;
	public AssesseeObjectives getAssesseeAssessorId(int assesseeObjectiveId);
	public List<AssesseeObjectives> getALLAssesseObjectivesBySectionId(AssesseeObjectives assesseeObjectives);
}
