package com.arrkgroup.apps.hr.assignobjective;

import java.util.List;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.model.ObjectivesModel;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.SectionModel;

public interface RoleDao {

	public List<Role> showRole();

	public List<SectionModel> showSections();

	public boolean insert_record(RoleObjectivesBean bean);

	public boolean delete_record(RoleObjectivesBean bean);

	public ObjectivesModel objective_from_id(int id);

	public List<ObjectivesModel> objective_from_section_id(int id);

	public List<RoleModel> showObjectives_By_Section_Role(int section_id,
			int role_id);

	public List<ObjectivesModel> showObjectives_By_Notin_Section_Role(
			int section_id, int role_id);

	List<ObjectivesModel> getObjectivesBySection(int sectionId);
	public List<RoleModel> Objectives_to_Role_Id(RoleObjectivesBean bean);

}
