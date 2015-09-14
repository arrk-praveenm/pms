package com.arrkgroup.apps.config;

import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

import com.arrkgroup.apps.model.AccessRole;
import com.arrkgroup.apps.service.AuthorizeService;

public class DatabaseAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator{
	@Autowired
	AuthorizeService authorizeServiceObj;
		
	public DatabaseAuthoritiesPopulator(ContextSource contextSource,String groupSearchBase) {
		super(contextSource, groupSearchBase);
	}
	
    public Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, String username) { 
    	//Get roles from a datasource of your choice
    	Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    		
    	
    	try {
			String  userEmailId=(String)user.getAttributes().get("mail").get();
			
			System.out.println("userEmailId "+userEmailId);
			
			authorities = authorizeServiceObj.getUserRole(userEmailId);
		
			System.out.println("Role mapped for the mail ID is  "+authorities.toString());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
    	System.out.println("Complete list of roles for email Id: "+authorities.toString());
        return authorities;
    
    }
}
