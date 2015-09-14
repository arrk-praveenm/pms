package com.arrkgroup.apps.assessor.assignobjectives;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.form.CopyObjectivesBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;

@Repository("AssignObjectivesRepository")
public class AssignObjectivesDaoImpl implements AssignObjectivesDao {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private ModelObjectDao modelObjectDao;

	@Override
	public List<Cycle> getAllAssessmentCycles() {
		return entityManager.createNamedQuery(Cycle.FIND_ALL, Cycle.class)
				.getResultList();

	}

	@Override
	public List<Employee> getAllAssesses() {
		return entityManager.createNamedQuery(Employee.FIND_ALL, Employee.class)
				.getResultList();
	}

	@Override
	public List<Role> getAllRoles() {
		return entityManager.createNamedQuery(Role.FIND_ALL, Role.class)
				.getResultList();
	}

	@Override
	@Transactional
	public boolean copyRoleObjectives(AssesseesAssessor assesseesAssessor) {
		try {
			entityManager.persist(assesseesAssessor);
			List<RoleModel> roleObjectives = entityManager.createNamedQuery(RoleModel.FIND_BY_ROLE_ID,RoleModel.class)
					.setParameter("role_id", assesseesAssessor.getRoleId().getId())
					.getResultList();
			for(RoleModel roleModel : roleObjectives){
				AssesseeObjectives assesseeObjectives = new AssesseeObjectives();
				assesseeObjectives.setAssesseeAssessor(assesseesAssessor);
				assesseeObjectives.setDescription(roleModel.getObjectives().getObjectiveDesc());
				assesseeObjectives.setSection(roleModel.getSection());
				assesseeObjectives.setWeightage(0);
				assesseeObjectives.setLastModifiedDate(new Date());
				entityManager.persist(assesseeObjectives);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;		
	}

	@Override
	public List<Objective> copyAssesseObjectives() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssesseeObjectives> getAssesseObjectives(
			CopyObjectivesBean copyObjectivesBean, int sectionId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION, AssesseeObjectives.class)
				.setParameter("employeeId", copyObjectivesBean.getAssessee()).setParameter("projectName", copyObjectivesBean.getProjectName()).setParameter("StartDate", copyObjectivesBean.getAssessmentFromDate())
				.setParameter("id",sectionId).setParameter("Cycle", copyObjectivesBean.getAssessmentCycle()).getResultList();
	}//
	
	@Override
	public boolean addAssesseeObjective(AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		try{
			System.out.println("TESTTSTTST");
			entityManager.persist(assesseeObjectives);
			return true;
		}catch(Exception e){
			System.err.println(" Error "+e);
			return false;
		}
		
		
	}

	@Override
	public AssesseesAssessor getAssesseeAssessor(CopyObjectivesBean copyObjectivesBean) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
				.setParameter("cycleId", modelObjectDao.findCycleById(copyObjectivesBean.getAssessmentCycle()).getId())
			     .setParameter("start_date", copyObjectivesBean.getAssessmentFromDate())
			     .setParameter("end_date", copyObjectivesBean.getAssessmentToDate())
			     .setParameter("project_name", copyObjectivesBean.getProjectName())
			     .setParameter("assesseeId", modelObjectDao.findEmployeeById(copyObjectivesBean.getAssessee()).getId())
			     .setParameter("assessorId", modelObjectDao.findEmployeeById(copyObjectivesBean.getAssessor()).getId())
			     .setParameter("roleId", modelObjectDao.findRoleById(copyObjectivesBean.getAssesseeRole()).getId()).getSingleResult();
		
	}

	@Override
	public boolean saveAssesseeObjectivebySection(
			AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		System.out.println("Save");
		entityManager
		.createNamedQuery(AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID
				).setParameter("description", assesseeObjectives.getDescription()).setParameter("assessebjectiveId", assesseeObjectives.getId()).executeUpdate()
		;
		return false;
	}

	@Override
	public boolean deleteAssesseeObjectivebySection(int objectiveId)
			throws SQLException {
		// TODO Auto-generated method stub
		try{
		System.out.println("Objective id requested to delete is "+objectiveId);
		entityManager
		.createNamedQuery(AssesseeObjectives.DELETE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID
				).setParameter("assessebjectiveId", objectiveId).executeUpdate();
		
		return true;
		}catch(Exception exe)
		{
			
			throw new SQLException("Assessee Objective Record can't delete");
		
			
		}catch(Error e)
		{
			return false;
		}
	}

	@Override
	public AssesseeObjectives getAssesseeAssessorId(int assesseeObjectiveId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_ASSESSOR_ID, AssesseeObjectives.class)
				.setParameter("assessebjectiveId", assesseeObjectiveId)
				.getSingleResult();
	}

	@Override
	public List<AssesseeObjectives> getALLAssesseObjectivesBySectionId(AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(AssesseeObjectives.GET_ALL_ASSESSEE_SECTION_ID, AssesseeObjectives.class)
				.setParameter("assesseeAssessorId", assesseeObjectives.getAssesseeAssessor().getId())
				.setParameter("sectionId", assesseeObjectives.getSection().getId())
				.getResultList();
	}
	


}
