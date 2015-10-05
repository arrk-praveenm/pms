package com.arrkgroup.apps.assessor.assignobjectives;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.arrkgroup.apps.model.Role;

@Service("AssignObjectivesService")
@Transactional
public class AssignObjectivesServiceImpl implements AssignObjectivesService{
	@Autowired
	AssignObjectivesDao assignObjectivesDao;
	
	@Autowired
	ModelObjectDao modelObjectiveDao;
	
	private static final String STATUS = "assignedObjectives";
	private static final String EDIT_FLAG = "false";
	private static final int DEFAULT_WEIGHTAGE_ID = 1;
	private static final int DEFAULT_RATING_ID = 1;
	

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
           assignObjectivesDao.copyRoleObjectives(getAssesseeAssessor(copyObjectivesBean,EDIT_FLAG,STATUS));
    }

    @Override
    public void copyAssesseObjectives(CopyObjectivesBean copyObjectivesBean) {
           assignObjectivesDao.copyAssesseObjectives(getAssesseeAssessor(copyObjectivesBean,EDIT_FLAG,STATUS),getOtherAssesseeAssessor(copyObjectivesBean,EDIT_FLAG,STATUS));
    }


public AssesseesAssessor getAssesseeAssessor(CopyObjectivesBean copyObjectivesBean, String editFlag,String Status)
    {
           AssesseesAssessor assesseesAssessor = new AssesseesAssessor();
           assesseesAssessor.setCycleId(modelObjectiveDao.findCycleById(copyObjectivesBean.getAssessmentCycle()));              
           assesseesAssessor.setStart_date(convertStringToDate(copyObjectivesBean.getAssessmentFromDate().substring(0, 10)));
           assesseesAssessor.setEnd_date(convertStringToDate(copyObjectivesBean.getAssessmentToDate().substring(0, 10)));
           assesseesAssessor.setProjectId(modelObjectiveDao.findProjectById(copyObjectivesBean.getProjectName()));
           assesseesAssessor.setAssessorId(modelObjectiveDao.findEmployeeById(copyObjectivesBean.getAssessor()));
           assesseesAssessor.setAssesseeId(modelObjectiveDao.findEmployeeById(copyObjectivesBean.getAssessee()));
           assesseesAssessor.setRoleId(modelObjectiveDao.findRoleById(copyObjectivesBean.getAssesseeRole()));
           assesseesAssessor.setPeriod_edit_flag(editFlag);
           assesseesAssessor.setStatus(Status);
           return assesseesAssessor;
    }


public AssesseesAssessor getOtherAssesseeAssessor(CopyObjectivesBean copyObjectivesBean, String editFlag,String Status)
    {
           AssesseesAssessor assesseesAssessor = new AssesseesAssessor();
           assesseesAssessor.setCycleId(modelObjectiveDao.findCycleById(copyObjectivesBean.getAssessmentCycle()));              
           assesseesAssessor.setStart_date(convertStringToDate(copyObjectivesBean.getAssessmentFromDate().substring(0, 10)));
           assesseesAssessor.setEnd_date(convertStringToDate(copyObjectivesBean.getAssessmentToDate().substring(0, 10)));
           assesseesAssessor.setProjectId(modelObjectiveDao.findProjectById(copyObjectivesBean.getProjectName()));
           assesseesAssessor.setAssessorId(modelObjectiveDao.findEmployeeById(copyObjectivesBean.getAssessor()));
           assesseesAssessor.setAssesseeId(modelObjectiveDao.findEmployeeById(copyObjectivesBean.getOtherAssessee()));
           assesseesAssessor.setRoleId(modelObjectiveDao.findRoleById(copyObjectivesBean.getAssesseeRole()));
           assesseesAssessor.setPeriod_edit_flag(editFlag);
           assesseesAssessor.setStatus(Status);
           return assesseesAssessor;
    }


private Date convertStringToDate(String datestring)
{
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	Date date=null ;
	try {
		 date = format.parse(datestring);
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return date;
}
	@Override
	public List<AssesseeObjectives> getAssesseObjectives(
			CopyObjectivesBean copyObjectivesBean, int sectionId) {
		// TODO Auto-generated method stub
		return assignObjectivesDao.getAssesseObjectives(copyObjectivesBean, sectionId);
	}

	@Override
	public boolean addAssesseeObjective(CopyObjectivesBean copyObjectivesBean, String sectionId, String description) {
		// TODO Auto-generated method stub
		AssesseeObjectives assesseeObjectives=new AssesseeObjectives();
		//get Assesseesor by ID and 
		assesseeObjectives.setAssesseeAssessor(assignObjectivesDao.getAssesseeAssessor(copyObjectivesBean));
		assesseeObjectives.setDescription(description);
		assesseeObjectives.setLastModifiedDate(new Date());
		assesseeObjectives.setWeightage(modelObjectiveDao.findWeightageById(DEFAULT_WEIGHTAGE_ID));
		assesseeObjectives.setSection(modelObjectiveDao.findSectionById(Integer.parseInt(sectionId)));
		assesseeObjectives.setSelf_rating(modelObjectiveDao.findRatingById(DEFAULT_RATING_ID));
		assesseeObjectives.setManager_rating(modelObjectiveDao.findRatingById(DEFAULT_RATING_ID));
	
		return assignObjectivesDao.addAssesseeObjective(assesseeObjectives);
	}

	@Override
	public boolean saveAssesseeObjectivebySection(
			AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		return assignObjectivesDao.saveAssesseeObjectivebySection(assesseeObjectives);
	}

	@Override
	public boolean deleteAssesseeObjectivebySection(int objectiveId)
			throws SQLException {
		// TODO Auto-generated method stub
		return assignObjectivesDao.deleteAssesseeObjectivebySection(objectiveId);
	}

	@Override
	public AssesseeObjectives getAssesseeAssessorId(int assesseeObjectiveId) {
		// TODO Auto-generated method stub
		return assignObjectivesDao.getAssesseeAssessorId(assesseeObjectiveId);
	}

	@Override
	public List<AssesseeObjectives> getALLAssesseObjectivesBySectionId(AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		return assignObjectivesDao.getALLAssesseObjectivesBySectionId(assesseeObjectives);
	}

	@Override
	public boolean saveWeightage(int objectiveId, int weightageId) {
		// TODO Auto-generated method stub
		return assignObjectivesDao.saveWeightage(objectiveId, weightageId);
	}

	@Override
	public AssesseesAssessor getAssesseeAssessorWithoutPeriod(
			CopyObjectivesBean copyObjectivesBean) {
		// TODO Auto-generated method stub
		return assignObjectivesDao.getAssesseeAssessorWithoutPeriod(copyObjectivesBean);
	}
	
	

}
