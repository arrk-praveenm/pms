package com.arrkgroup.apps.hr.assignobjective;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;
import com.arrkgroup.apps.model.Objective;

import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;


@Service("RoleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);
	
	@Autowired
	RoleDao roleDao;

	// list all roles presnt in Database
	@Override
	public List<Role> showRole() {

		return roleDao.showRole();
	}

	// checking objectives for role
	public boolean checkRoleObjectives(RoleObjectivesBean bean) {

		List<RoleModel> objectives = roleDao.Objectives_to_Role_Id(bean);
		log.info("objectives size is " + objectives.size());

		if (objectives.size() > 0) {
			return false;
		} else {
			return true;
		}

	}

	// insert record service
	public boolean insert_record(RoleObjectivesBean bean) {

		return roleDao.insert_record(bean);

	}

	public List<Objective> showObjectives_By_Section(int section_id) {

		return roleDao.objective_from_section_id(section_id);

	}

	public boolean delete_record(RoleObjectivesBean bean) {

		return roleDao.delete_record(bean);

	}

	public List<Section> showSections() {

		return roleDao.showSections();

	}

	// populate objectives by selected section and role
	public List<Objective> showObjectives_By_Section_Role(int section_id,
			int role_id) {

		List<Objective> ObjectivesList = new ArrayList<Objective>();

		for (RoleModel objectives : roleDao.showObjectives_By_Section_Role(
				section_id, role_id)) {
			ObjectivesList.add(roleDao.objective_from_id(objectives.getObjectives().getId()));

		}

		return ObjectivesList;

	}

}