package com.arrkgroup.apps.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;

@Transactional
@Service
public class ModelObjectServiceImpl implements ModelObjectService {
	@Autowired
	ModelObjectDao modelObjectDao;
	
	@Override
	public Cycle findCycleById(int id) {
		
		// TODO Auto-generated method stub
		return modelObjectDao.findCycleById(id);
	}

	@Override
	public Role findRoleById(int id) {
		// TODO Auto-generated method stub
		Role role=new Role();
		
		role=modelObjectDao.findRoleById(id);
		
		System.out.println("role is "+role.getTitle());
	return role;
	}

	@Override
	public Section findSectionById(int id) {
		// TODO Auto-generated method stub
		return modelObjectDao.findSectionById(id);
	}

	@Override
	public Objective findObjectiveById(int id) {
		// TODO Auto-generated method stub
		return modelObjectDao.findObjectiveById(id);
	}

	@Override
	public Employee findEmployeeById(int id) {
		// TODO Auto-generated method stub
		
		Employee employee=new Employee();
		employee= modelObjectDao.findEmployeeById(id);
		
		System.out.println("employee is "+employee.getFullname() +"  "+employee.getId() );
		return employee;
	}

	@Override
	public List<RoleModel> getObjectivesByRoleId(int id) {
		// TODO Auto-generated method stub
		return modelObjectDao.getObjectivesByRoleId(id);
	}

	@Override
	public List<RoleModel> getObjectivesByRoleAndSectionId(int roleId,
			int sectionId) {
		// TODO Auto-generated method stub
		return modelObjectDao.getObjectivesByRoleAndSectionId(roleId, sectionId);
	}

	@Override
	public List<Objective> getObjectiveBySectionId(int id) {
		// TODO Auto-generated method stub
		return modelObjectDao.getObjectiveBySectionId(id);
	}

	@Override
	public List<Section> getAllSections() {
		// TODO Auto-generated method stub
		return modelObjectDao.getAllSections();
	}

	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return modelObjectDao.getAllRoles();
	}

	@Override
	public List<Cycle> getAllCycles() {
		
		modelObjectDao.getAllCycles();
		return modelObjectDao.getAllCycles();
	}
	

}
