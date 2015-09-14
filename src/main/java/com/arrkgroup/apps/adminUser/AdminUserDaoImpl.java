package com.arrkgroup.apps.adminUser;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.AccessRole;
@Repository("AdminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean addNewHrUserRole(AccessRole accessRole) {
		// TODO Auto-generated method stub
		try {
						
			entityManager.persist(accessRole);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<AccessRole> getAllHRUsers() {
		// TODO Auto-generated method stub
		return entityManager
				.createNamedQuery(AccessRole.GET_ALL_USERS,
						AccessRole.class)
				.getResultList();
	}

	@Override
	public boolean deleteHrUserRole(String email) {
		// TODO Auto-generated method stub
		System.out.println(email);
		entityManager
		.createNamedQuery(AccessRole.delete_BY_EMAILid
				).setParameter("email", email).executeUpdate();
		
		
		return true;
	}

}
