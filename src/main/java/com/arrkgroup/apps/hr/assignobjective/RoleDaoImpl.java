package com.arrkgroup.apps.hr.assignobjective;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;

import com.arrkgroup.apps.model.Objective;

import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;




@Repository("RoleDao")@Transactional
public class RoleDaoImpl implements RoleDao {

	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List < Role > showRole() {

		return entityManager.createNamedQuery(Role.FIND_ALL, Role.class)
			.getResultList();

	}


	@Override
	public Objective objective_from_id(int id) {

		return entityManager.createNamedQuery(Objective.GET_BY_ID, Objective.class)
			.setParameter("id", id).getSingleResult();

	}

	public List < Objective > objective_from_section_id(int id) {
		return entityManager.createNamedQuery(Objective.GET_ALL_SECTION_OBJECTIVES,
				Objective.class).setParameter("section", id)
			.getResultList();

	}


	//insert record implementation
	public boolean insert_record(RoleObjectivesBean bean) {

		log.info("in insert record method");

		int count= entityManager.createNamedQuery(RoleModel.delete_BY_role_id_section_id)
			.setParameter("role_id", bean.getRole_id()).setParameter("section_id", bean.getSection_id()).executeUpdate();

		log.info("delete count is "+count);
		
		for (int i = 0; i < bean.getMultiselect_to().size(); i++) {

			Role role=entityManager.createNamedQuery(Role.FIND_BY_ROLE_ID,
					Role.class).setParameter("id", bean.getRole_id())
						.getSingleResult();
			log.info("role is "+role.getTitle());
			
			
			Objective objective=entityManager.createNamedQuery(Objective.GET_BY_ID,
					Objective.class).setParameter("id", Integer.parseInt(bean.getMultiselect_to().get(i)))
						.getSingleResult();
			
			log.info("objective is is "+objective.getObjectiveDesc());
			
			Section section=entityManager.createNamedQuery(Section.FIND_BY_ID,
					Section.class).setParameter("id", bean.getSection_id())
						.getSingleResult();
			log.info("section is "+section.getSection());
		/*	
			Role role=new Role();
			Objectives objective=new Objectives();
			Section section=new Section();
			
			
			role.setId(bean.getRole_id());
			objective.setId(Integer.parseInt(bean.getMultiselect_to().get(i)));
			section.setId(bean.getSection_id());*/
			
			//RoleModel objectives = new RoleModel();

			RoleModel objectives = new RoleModel(new Date(),role,objective,section);

			//RoleModel objectives = new RoleModel(new Date(),new Role(bean.getRole_id()),new Objectives(bean.getMultiselect_to().get(i)),new Section(bean.getSection_id()));
			
			//objectives.setLast_modified_date(new Date());

		/*	objectives.setRole()   setRole_id(bean.getRole_id());

			objectives.setSection_id(bean.getSection_id());

			
			objectives.setObjective_id(Integer.parseInt(bean.getMultiselect_to().get(i)));

*/

			try {
				log.info("before  merging ");
				entityManager.persist(objectives);
				log.info("after  merging ");
				
			} catch (Exception e) {
				
				log.info(e.getMessage());
				return false;

			}


		}

		return true;

	}

	public boolean delete_record(RoleObjectivesBean bean) {

		int count = entityManager.createNamedQuery(RoleModel.delete_BY_role_id_section_id)
			.setParameter("role_id", bean.getRole_id()).setParameter("section_id", bean.getSection_id()).executeUpdate();

	

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}
	//list all section present in db
	public List < Section > showSections() {

		return entityManager.createNamedQuery(Section.GET_ALL_SECTIONS,
		Section.class).getResultList();
	}

	@Override
	public List < RoleModel > showObjectives_By_Section_Role(int section_id,
	int role_id) {

		return entityManager.createNamedQuery(RoleModel.FIND_BY_ROLE_SECTION,
		RoleModel.class).setParameter("role_id", role_id)
			.setParameter("section_id", section_id).getResultList();
	}



	public List < Objective > showObjectives_By_Notin_Section_Role(int section_id,
	int role_id) {

	

		List < Objective > ObjectivesList = new ArrayList < Objective> ();

		ObjectivesList = entityManager.createNamedQuery(Objective.FIND_BY_SECTION_ID_NOT_PRESENT,
				Objective.class).setParameter("role_id", role_id)
			.setParameter("section_id", section_id).getResultList();
	
		return ObjectivesList;
	}

/*
	@Override
	public List < ObjectivesModel > getObjectivesBySection(int sectionId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(ObjectivesModel.FIND_BY_SECTION_ID,
		ObjectivesModel.class).setParameter("section_id", sectionId)
			.getResultList();

	}*/
	public List<RoleModel> Objectives_to_Role_Id(RoleObjectivesBean bean) {

		return entityManager
				.createNamedQuery(RoleModel.FIND_BY_ROLE_SECTION,
						RoleModel.class)
				.setParameter("role_id", bean.getRole_id()).setParameter("section_id", bean.getSection_id()).getResultList();

	}


}