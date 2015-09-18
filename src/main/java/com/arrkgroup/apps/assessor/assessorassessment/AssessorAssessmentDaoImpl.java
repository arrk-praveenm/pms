package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.form.SectionDataBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.SectionData;



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
	
	
	public AssesseesAssessor  getAssesseesAssessor(int id)
	{
		
		
		return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_EMPLOYEE_ID,
						AssesseesAssessor.class).setParameter("id", id).getSingleResult();
		
	}
	
	
	public AssesseesAssessor  getAssessees(int assesse_id,int role_id)
	{
		
		
		return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_EMPLOYEE_ID,
						AssesseesAssessor.class).setParameter("id", assesse_id).setParameter("role_id", role_id).getSingleResult();
		
	}
	
	
	
	
	
	
	
	@Override
	public List<AssesseeObjectives> getAssesseeObjectives(int sectionID,
			int assesseID) {
	
		return entityManager
				.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION,
						AssesseeObjectives.class).setParameter("sectionID", sectionID).setParameter("assesseID", assesseID).getResultList();
		
		
		
	}
	
	public boolean addSectionData(Section section) {
		// TODO Auto-generated method stub
		
		try {
			
			entityManager.persist(section);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean saveSectionData(SectionDataBean bean)
	{
		
		SectionData dataBean=new SectionData(bean.getManager_rating(),bean.getManager_comments(),bean.getEmployee_assessor_id(),new Date());
		
		
		try {

			entityManager.merge(dataBean);
		}

		catch (Exception e) {
			// log it or do something
			return false;
		}

		return true;
		
		
	
	}
	
	
	

}
