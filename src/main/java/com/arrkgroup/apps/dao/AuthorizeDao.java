package com.arrkgroup.apps.dao;

import com.arrkgroup.apps.model.AccessRole;

public interface AuthorizeDao {

	
public String findRoleFromHR(String email);
	
	public String findRoleFromEmployee(String email);
	
	public String findRoleFromSelfReporting(String email);
	
	public boolean addNewRole(AccessRole accessRole);
	
}
