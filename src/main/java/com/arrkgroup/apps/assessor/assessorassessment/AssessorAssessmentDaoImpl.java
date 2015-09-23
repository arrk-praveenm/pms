package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;




@Repository("AssessorAssessmentDao")
public class AssessorAssessmentDaoImpl implements AssessorAssessmentDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ModelObjectDao modelObjectDao;
	
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
	System.out.println(sectionID+" "+assesseID );
	
	
	try
	{
		
	
	
	return entityManager
				.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION,
						AssesseeObjectives.class).setParameter("sectionID", sectionID).setParameter("assesseID", assesseID).getResultList();
	}
	catch(Exception e)
	{
	
		System.out.println("exception is "+e.getMessage());
	return null;
	}
	
		
		
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
	
	
	public boolean saveSectionData(AssessorAssessmentBean bean)
	{
	 int n=	entityManager
		.createNamedQuery(AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID_V1
				).setParameter("comments",bean.getManager_comments()).setParameter("managerId", bean.getManager_rating())
				.setParameter("assessebjectiveId", bean.getObjectiveid()).executeUpdate();
	
		
	if(n>0)
	{
		return true;
	}else
	{
		return false;
	}
		
	
	}
	@Override
	public List<Role> getRoleOfCurrentUser(String email) {
		// TODO Auto-generated method stub
	return entityManager
				.createNamedQuery(Role.FIND_ROLES_OF_ASSESSEE,
						Role.class).setParameter("email", email)
				.getResultList();
	}
	
	
	

}
