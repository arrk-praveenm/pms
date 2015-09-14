package com.arrkgroup.apps.adminUser;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.model.AccessRole;

@Service("AdminUserService")
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	AdminUserDao adminUserDao;

	@Override
	public boolean addNewHrUserRole(AccessRole accessRole) {
		// TODO Auto-generated method stub
		return adminUserDao.addNewHrUserRole(accessRole);
	}

	@Override
	public List<AccessRole> getAllHRUsers() {
		// TODO Auto-generated method stub
		return adminUserDao.getAllHRUsers();
	}

	@Override
	public boolean deleteHrUserRole(String email) {
		// TODO Auto-generated method stub
		return adminUserDao.deleteHrUserRole(email);
	}

}
