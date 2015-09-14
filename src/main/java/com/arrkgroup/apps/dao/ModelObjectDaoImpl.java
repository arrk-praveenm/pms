package com.arrkgroup.apps.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;

@Repository("ModelObjectDao")
public class ModelObjectDaoImpl implements ModelObjectDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Cycle findCycleById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Cycle.FIND_BY_ID,Cycle.class)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public Role findRoleById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Role.FIND_BY_ROLE_ID,Role.class)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public Section findSectionById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Section.FIND_BY_ID,Section.class)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public Objective findObjectiveById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Objective.GET_BY_ID,Objective.class)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public Employee findEmployeeById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Employee.FIND_BY_ID,Employee.class)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public List<RoleModel> getObjectivesByRoleId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleModel> getObjectivesByRoleAndSectionId(int roleId,
			int sectionId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(RoleModel.FIND_BY_ROLE_SECTION,RoleModel.class)
				.setParameter("role_id", roleId).setParameter("section_id", sectionId).getResultList();
	}

	@Override
	public List<Objective> getObjectiveBySectionId(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Objective.GET_ALL_SECTION_OBJECTIVES,Objective.class)
				.setParameter("section", id).getResultList();
	}

	@Override
	public List<Section> getAllSections() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Section.GET_ALL_SECTIONS,Section.class)
				.getResultList();
	}

	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Role.FIND_ALL,Role.class)
				.getResultList();
	}

	@Override
	public List<Cycle> getAllCycles() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Cycle.FIND_ALL,Cycle.class)
				.getResultList();
	}	

}
