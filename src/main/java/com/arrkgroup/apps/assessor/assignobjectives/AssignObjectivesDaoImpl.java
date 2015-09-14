package com.arrkgroup.apps.assessor.assignobjectives;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
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
				.setParameter("employeeId", copyObjectivesBean.getAssessee()).setParameter("projectName", copyObjectivesBean.getProjectName()).setParameter("StartDate", copyObjectivesBean.getAssessmentFromDate()).setParameter("id",sectionId).getResultList();
	}//

}
