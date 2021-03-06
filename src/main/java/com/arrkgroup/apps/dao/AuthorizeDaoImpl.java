package com.arrkgroup.apps.dao;

import java.util.List;

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
			List<AccessRole> userRole = entityManager
					.createNamedQuery(AccessRole.FIND_BY_EMAIL,
							AccessRole.class).setParameter("email", email)
					.getResultList();

			System.out.println(userRole.get(0).getRole());

			return userRole.get(0).getRole();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public String findRoleFromEmployee(String email) {
		// TODO Auto-generated method stub

		try {
			List<Employee> employee = entityManager
					.createNamedQuery(Employee.FIND_ROLE_BY_EMAIL,
							Employee.class).setParameter("email", email)
					.getResultList();

			// System.out.println("Employee "+employee.getClass().getName() );

			if (employee.size() == 0) {
				return null;
			} else {
				return MANAGER_ROLE;
			}

		} catch (NoResultException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public String findRoleFromSelfReporting(String email) {
		// TODO Auto-generated method stub

		try {

					List<AssesseesAssessor> selfReporting=entityManager
					.createNamedQuery(AssesseesAssessor.FIND_ROLE_BY_EMAIL,
							AssesseesAssessor.class).setParameter("email", email)
					.getResultList();

			System.out.println("List of selfReporting "+selfReporting.size());
			if(selfReporting.size()==0)
			{
				return null;
			}

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
