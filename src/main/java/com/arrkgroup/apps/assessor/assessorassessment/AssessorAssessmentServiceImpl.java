package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;

@Service("AssessorAssessmentService")
@Transactional
public class AssessorAssessmentServiceImpl implements AssessorAssessmentService {

	@Autowired
	AssessorAssessmentDao assessorAssessmentDao;
	
	@Autowired 
	ModelObjectDao modelObjectDao;
	
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
			int assesseID, int role_id, int projectId) {
	
		AssesseesAssessor assessor=new AssesseesAssessor();
		assessor = assessorAssessmentDao.getAssessees(assesseID,role_id,projectId);
		
List<AssesseeObjectives> list=new ArrayList<AssesseeObjectives>();
		
		list= assessorAssessmentDao.getAssesseeObjectives(sectionID, assessor.getId());
		
		System.out.println("list size is  "+list.size());
		
		return list;
	}
	
	
	@Override
	public boolean saveAssessorAssessment(AssessorAssessmentBean bean) {
		
		
		
		
		bean.setManager_score(modelObjectDao.findRatingById(bean.getManager_rating()).getScore()* modelObjectDao.findWeightageById(bean.getWeightage()).getWeightage());		
		
		
		
		
		return  assessorAssessmentDao.saveAssessorAssessment(bean) ;
	}
	
	@Override
	public boolean saveSelfAssessment(AssessorAssessmentBean bean) {
		// TODO Auto-generated method stub
		
		bean.setSelf_score(modelObjectDao.findRatingById(bean.getSelf_rating()).getScore()* modelObjectDao.findWeightageById(bean.getWeightage()).getWeightage());
		return assessorAssessmentDao.saveSelfAssessment(bean);
	}
	
	
	@Override
	public List<AssesseesAssessor> getRoleOfCurrentUser(String email) {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getRoleOfCurrentUser(email);
	}
	
	
	
	
	
	

}
