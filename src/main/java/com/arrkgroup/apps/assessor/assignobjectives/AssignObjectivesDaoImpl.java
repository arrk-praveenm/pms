package com.arrkgroup.apps.assessor.assignobjectives;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
			entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
					.setParameter("cycleId", assesseesAssessor.getCycleId().getId())
					.setParameter("start_date", assesseesAssessor.getStart_date())
					.setParameter("end_date", assesseesAssessor.getEnd_date())
					.setParameter("project_name", assesseesAssessor.getProject_name())
					.setParameter("assesseeId", assesseesAssessor.getAssesseeId().getId())
					.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
					.setParameter("roleId", assesseesAssessor.getRoleId().getId()).getSingleResult();
		}catch (NoResultException nre){
			entityManager.persist(assesseesAssessor);
			List<RoleModel> roleObjectives = entityManager.createNamedQuery(RoleModel.FIND_BY_ROLE_ID, RoleModel.class)
					.setParameter("role_id", assesseesAssessor.getRoleId().getId())
					.getResultList();
			for(RoleModel roleModel : roleObjectives){
				AssesseeObjectives assesseeObjective = new AssesseeObjectives();
				assesseeObjective.setAssesseeAssessor(assesseesAssessor);
				assesseeObjective.setDescription(roleModel.getObjectives().getObjectiveDesc());
				assesseeObjective.setSection(roleModel.getSection());
				assesseeObjective.setWeightage(0);
				assesseeObjective.setLastModifiedDate(new Date());
				entityManager.persist(assesseeObjective);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;		
	}

	@Override
	public boolean copyAssesseObjectives(AssesseesAssessor assesseesAssessor) {
		try {
			entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
					.setParameter("cycleId", assesseesAssessor.getCycleId().getId())
					.setParameter("start_date", assesseesAssessor.getStart_date())
					.setParameter("end_date", assesseesAssessor.getEnd_date())
					.setParameter("project_name", assesseesAssessor.getProject_name())
					.setParameter("assesseeId", assesseesAssessor.getAssesseeId().getId())
					.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
					.setParameter("roleId", assesseesAssessor.getRoleId().getId()).getSingleResult();
		}catch(NoResultException nre){
			entityManager.persist(assesseesAssessor);			
			List<AssesseeObjectives> assesseeObjectives = entityManager.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSEEID_CYCLEID_ROLEID,
					AssesseeObjectives.class)
					.setParameter("assesseeId", assesseesAssessor.getAssesseeId().getId())
					.setParameter("cycleId", assesseesAssessor.getCycleId().getId())
					.setParameter("roleId", assesseesAssessor.getRoleId().getId()).getResultList();
			for(AssesseeObjectives copiedassesseeObjectives : assesseeObjectives){
				AssesseeObjectives assesseeObjective = new AssesseeObjectives();
				assesseeObjective.setAssesseeAssessor(assesseesAssessor);
				assesseeObjective.setDescription(copiedassesseeObjectives.getDescription());
				assesseeObjective.setSection(copiedassesseeObjectives.getSection());
				assesseeObjective.setWeightage(0);
				assesseeObjective.setLastModifiedDate(new Date());
				entityManager.persist(assesseeObjective);
			}
		}catch(Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<AssesseeObjectives> getAssesseObjectives(
			CopyObjectivesBean copyObjectivesBean, int sectionId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION, AssesseeObjectives.class)
				.setParameter("employeeId", copyObjectivesBean.getAssessee()).setParameter("projectName", copyObjectivesBean.getProjectName()).setParameter("StartDate", copyObjectivesBean.getAssessmentFromDate()).setParameter("id",sectionId).getResultList();
	}//

}
