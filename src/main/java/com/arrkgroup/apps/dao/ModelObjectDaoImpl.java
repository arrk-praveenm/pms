package com.arrkgroup.apps.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Project;
import com.arrkgroup.apps.model.Rating;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.Weightage;

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

	@Override
	public Project findProjectById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Project.GET_PROJECT_ID,Project.class).setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public List<Project> getAllProjects() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Project.GET_ALL_PROJECTS,Project.class)
				.getResultList();
	}

	@Override
	public Weightage findWeightageById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Weightage.GET_Weightage_ID,Weightage.class).setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public List<Weightage> getAllWeightages() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Weightage.GET_ALL_Weightage, Weightage.class)
				.getResultList();
	}

	@Override
	public Rating findRatingById(int id) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Rating.GET_RATING_ID,Rating.class).setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public List<Rating> getAllRatings() {
		// TODO Auto-generated method stub
		return  entityManager.createNamedQuery(Rating.GET_ALL_RATING, Rating.class)
				.getResultList();
	}

	@Override
	public Employee findEmployeeByEmail(String email) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(Employee.FIND_BY_EMAIl, Employee.class).setParameter("email", email)
				.getSingleResult();
	}

	public int getMaxRating() {
		return (int) entityManager
				.createNamedQuery(Rating.GET_RATING_MAX).getSingleResult();
	}



}
