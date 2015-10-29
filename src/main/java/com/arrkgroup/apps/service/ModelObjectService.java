package com.arrkgroup.apps.service;

import java.util.List;

import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Project;
import com.arrkgroup.apps.model.Rating;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.Weightage;

public interface ModelObjectService {
	public Cycle findCycleById(int id);
	public Role findRoleById(int id);
	public Section findSectionById(int id);
	public Objective findObjectiveById(int id);
	public Employee findEmployeeById(int id);
	public List<RoleModel> getObjectivesByRoleId(int id);
	public List<RoleModel> getObjectivesByRoleAndSectionId(int roleId,int sectionId);
	public List<Objective> getObjectiveBySectionId(int id);
	public List<Section> getAllSections();
	public List<Role> getAllRoles();
	public List<Cycle> getAllCycles();
	public Project findProjectById(int id);
	public List<Project> getAllProjects();
	public Weightage findWeightageById(int id);
	public List<Weightage> getAllWeightages();

	public Rating findRatingById(int id);
	public List<Rating> getAllRatings();

	public Employee findEmployeeByEmail(String email);
	public int getMaxRating();


}
