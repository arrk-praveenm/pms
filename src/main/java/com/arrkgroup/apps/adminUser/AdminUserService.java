package com.arrkgroup.apps.adminUser;

import java.util.List;

import com.arrkgroup.apps.model.AccessRole;

public interface AdminUserService {

	public boolean addNewHrUserRole(AccessRole accessRole);
	public List<AccessRole> getAllHRUsers();
	public boolean deleteHrUserRole(String email);
}
