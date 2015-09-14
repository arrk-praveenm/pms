package com.arrkgroup.apps.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.dao.AuthorizeDao;
import com.arrkgroup.apps.model.AccessRole;

@Service("AuthorizeService")
@Transactional
public class AuthorizeServiceImpl implements AuthorizeService {

	@Autowired
	AuthorizeDao authorizeDaoObj;
	//By default all employees are having Employee Role
	String userRole = null;	
	private static final String ROLE_ADMIN="ADMIN";
	private static final String ROLE_HR="HR";
	
	@Override
	public Set<GrantedAuthority> getUserRole(String email) {
		// TODO Auto-generated method stub
		//getting currently logged in user  is having  HR or manager roles
		Set<GrantedAuthority> userRoles = new HashSet<GrantedAuthority>();
		userRole = authorizeDaoObj.findRoleFromHR(email);
		if(userRole!=null){
			userRoles.add(new SimpleGrantedAuthority(userRole));
			if(userRole.equalsIgnoreCase(ROLE_ADMIN)){
			userRoles.add(new SimpleGrantedAuthority(ROLE_HR));
			}
		}
		userRole = authorizeDaoObj.findRoleFromEmployee(email);
		if(userRole!=null){
			userRoles.add(new SimpleGrantedAuthority(userRole));
		}
		else{
		userRole = authorizeDaoObj.findRoleFromSelfReporting(email);
		if(userRole!=null){
			userRoles.add(new SimpleGrantedAuthority(userRole));
		}
		}
		return userRoles;
	}

	@Override
	public boolean addNewRole(AccessRole accessRole) {
		// TODO Auto-generated method stub
		
		authorizeDaoObj.addNewRole(accessRole);
		
		return false;
	}
}
