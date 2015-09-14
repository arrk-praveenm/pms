package com.arrkgroup.apps.service;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.arrkgroup.apps.model.AccessRole;

public interface AuthorizeService {
	
	public Set<GrantedAuthority> getUserRole(String email);
	
	public boolean addNewRole(AccessRole accessRole);

}
