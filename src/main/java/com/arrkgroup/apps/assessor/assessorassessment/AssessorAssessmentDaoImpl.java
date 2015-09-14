package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;



@Repository("AssessorAssessmentDao")
public class AssessorAssessmentDaoImpl implements AssessorAssessmentDao {

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<AssesseesAssessor> getMyAssessees(String email) {
		// TODO Auto-generated method stub
		
		return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_EMAIL,
						AssesseesAssessor.class).setParameter("email", email)
				.getResultList();
	}
	@Override
	public Employee getAssessee(int id) {
		// TODO Auto-generated method stub

		Employee employee = entityManager
				.createNamedQuery(Employee.FIND_ASSESSEE_BY_ID,
						Employee.class).setParameter("id", id)
				.getSingleResult();
		return employee;
	}
	@Override
	public List<Section> getAllSections() {
		// TODO Auto-generated method stub
		return entityManager
				.createNamedQuery(Section.GET_ALL_SECTIONS,
						Section.class)
				.getResultList();
	}

}
