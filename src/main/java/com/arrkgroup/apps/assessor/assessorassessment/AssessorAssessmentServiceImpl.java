package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;

@Service("AssessorAssessmentService")
@Transactional
public class AssessorAssessmentServiceImpl implements AssessorAssessmentService {

	@Autowired
	AssessorAssessmentDao assessorAssessmentDao;
	@Override
	public List<AssesseesAssessor>  getMyAssessees(String email) {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getMyAssessees(email);
	}
	@Override
	public Employee getAssessee(int id) {
		// TODO Auto-generated method stub
	
		return assessorAssessmentDao.getAssessee(id);
	}
	
	@Override
	public EmployeeBean getAssesseeBean(int id) {
		// TODO Auto-generated method stub
		
	Employee employee=getAssessee(id);
	EmployeeBean bean=new EmployeeBean();
	
	bean.setFullname(employee.getFullname());
	bean.setId(employee.getId());
		

		
	return bean;
	
	}
	
	
	
	
	
	@Override
	public List<Section> getAllSections() {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getAllSections();
	}
	
	
	public AssesseesAssessor  getAssesseesAssessor(int id)
	{
		AssesseesAssessor assessor=new AssesseesAssessor();
		
		assessor= assessorAssessmentDao.getAssesseesAssessor(id);
		
			return assessor;
	}
	
	
	
	public List<AssesseeObjectives>  getAssesseeObjectives(int sectionID,int assesseID)
	{
		//System.out.println("in get getAssesseeObjectives");
		
		AssesseesAssessor assessor=new AssesseesAssessor();
		assessor = assessorAssessmentDao.getAssesseesAssessor(assesseID);
		
		//System.out.println("assesssor id is " +assessor.getId());
		
		List<AssesseeObjectives> list=new ArrayList<AssesseeObjectives>();
		
		list= assessorAssessmentDao.getAssesseeObjectives(sectionID, assessor.getId());
		
		//System.out.println("list size is "+list.size());
		
		return list;
	
	}
	
	@Override
	public List<AssesseeObjectives> getAssesseeObjectives(int sectionID,
			int assesseID, int role_id) {
	
		AssesseesAssessor assessor=new AssesseesAssessor();
		assessor = assessorAssessmentDao.getAssessees(assesseID,role_id);
		
List<AssesseeObjectives> list=new ArrayList<AssesseeObjectives>();
		
		list= assessorAssessmentDao.getAssesseeObjectives(sectionID, assessor.getId());
		
		System.out.println("list size is  "+list.size());
		
		return list;
	}
	
	
	@Override
	public boolean saveSectionData(AssessorAssessmentBean bean) {
		
		return  assessorAssessmentDao.saveSectionData(bean) ;
	}
	@Override
	public List<Role> getRoleOfCurrentUser(String email) {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getRoleOfCurrentUser(email);
	}
	
	
	
	

}
