package com.arrkgroup.apps.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.AccessRole;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;

@Repository("AuthorizeDao")
@Transactional
public class AuthorizeDaoImpl implements AuthorizeDao {
	private final String MANAGER_ROLE = "MANAGER";
	private final String ASSESSOR_ROLE = "ASSESSOR";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String findRoleFromHR(String email) {
		// TODO Auto-generated method stub

		try {
			AccessRole userRole = entityManager
					.createNamedQuery(AccessRole.FIND_BY_EMAIL,
							AccessRole.class).setParameter("email", email)
					.getSingleResult();
		//	entityManager.
			System.out.println(userRole.getRole());

			return userRole.getRole();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public String findRoleFromEmployee(String email) {
		// TODO Auto-generated method stub

		try {
			Employee employee = entityManager
					.createNamedQuery(Employee.FIND_ROLE_BY_EMAIL,
							Employee.class).setParameter("email", email)
					.getSingleResult();

			System.out.println("Employee "+employee.getClass().getName() );
			

			return MANAGER_ROLE;
		} catch (NoResultException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public String findRoleFromSelfReporting(String email) {
		// TODO Auto-generated method stub

		try {
			AssesseesAssessor selfReporting = entityManager
					.createNamedQuery(AssesseesAssessor.FIND_ROLE_BY_EMAIL,
							AssesseesAssessor.class).setParameter("email", email)
					.getSingleResult();

			System.out.println("SelfReporting "+selfReporting.getClass().getName());

			return ASSESSOR_ROLE;
		} catch (NoResultException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean addNewRole(AccessRole accessRole) {
		// TODO Auto-generated method stub
		entityManager.persist(accessRole);
		
	
		
		return true;
	}

}
