package com.arrkgroup.apps.assessor.assignobjectives;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.form.CopyObjectivesBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;

@Service("AssignObjectivesService")
@Transactional
public class AssignObjectivesServiceImpl implements AssignObjectivesService{
	@Autowired
	AssignObjectivesDao assignObjectivesDao;
	
	@Autowired
	ModelObjectDao modelObjectiveDao;
	
	private static final String STATUS = "Initiated";
	private static final String EDIT_FLAG = "False";

	@Override
	public List<Cycle> getAllAssessmentCycles() {
		return assignObjectivesDao.getAllAssessmentCycles();
	}

	@Override
	public List<Employee> getAllAssesses() {
		return assignObjectivesDao.getAllAssesses();
	}

	@Override
	public List<Role> getAllRoles() {
		return assignObjectivesDao.getAllRoles();
	}

	@Override
	public void copyRoleObjectives(CopyObjectivesBean copyObjectivesBean) {	
		assignObjectivesDao.copyRoleObjectives(getAssesseeAssessor(copyObjectivesBean,"false",STATUS));
	}

	@Override
	public void copyAssesseObjectives(CopyObjectivesBean copyObjectivesBean) {
		assignObjectivesDao.copyAssesseObjectives(getAssesseeAssessor(copyObjectivesBean,"false",STATUS));
	}

	@Override
	public List<AssesseeObjectives> getAssesseObjectives(
			CopyObjectivesBean copyObjectivesBean, int sectionId) {
		// TODO Auto-generated method stub
		return assignObjectivesDao.getAssesseObjectives(copyObjectivesBean, sectionId);
	}

	@Override
	public boolean addAssesseeObjective(CopyObjectivesBean copyObjectivesBean,String sectionId, String description) {
		// TODO Auto-generated method stub
		AssesseeObjectives assesseeObjectives=new AssesseeObjectives();
		//get Assesseesor by ID and 
		assesseeObjectives.setAssesseeAssessor(getAssesseeAssessor(copyObjectivesBean,"false",STATUS));
		assesseeObjectives.setDescription(description);
		assesseeObjectives.setLastModifiedDate(new Date());
		assesseeObjectives.setWeightage(0);
		assesseeObjectives.setSection(modelObjectiveDao.findSectionById(Integer.parseInt(sectionId)));
		return false;
	}
	
	public AssesseesAssessor getAssesseeAssessor(CopyObjectivesBean copyObjectivesBean, String editFlag,String Status)
	{
		AssesseesAssessor assesseesAssessor = new AssesseesAssessor();
		assesseesAssessor.setCycleId(modelObjectiveDao.findCycleById(copyObjectivesBean.getAssessmentCycle()));		
		assesseesAssessor.setStart_date(copyObjectivesBean.getAssessmentFromDate());
		assesseesAssessor.setEnd_date(copyObjectivesBean.getAssessmentToDate());
		assesseesAssessor.setProject_name(copyObjectivesBean.getProjectName());
		assesseesAssessor.setAssessorId(modelObjectiveDao.findEmployeeById(copyObjectivesBean.getAssessor()));
		assesseesAssessor.setAssesseeId(modelObjectiveDao.findEmployeeById(copyObjectivesBean.getAssessee()));
		assesseesAssessor.setRoleId(modelObjectiveDao.findRoleById(copyObjectivesBean.getAssesseeRole()));
		assesseesAssessor.setPeriod_edit_flag(editFlag);
		assesseesAssessor.setStatus(Status);
		return assesseesAssessor;
	}

}
