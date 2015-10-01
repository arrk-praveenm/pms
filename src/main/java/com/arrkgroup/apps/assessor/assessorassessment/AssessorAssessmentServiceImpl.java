package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.SectionConsolidated;

@Service("AssessorAssessmentService")
@Transactional
public class AssessorAssessmentServiceImpl implements AssessorAssessmentService {

	private final Logger log = LoggerFactory
			.getLogger(AssessorAssessmentController.class);
	
	
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
	
	public List<SectionConsolidatedBean> getAllSectionsBean() {
		// TODO Auto-generated method stub
		List<Section> list= assessorAssessmentDao.getAllSections();
		List<SectionConsolidatedBean> beans=new  ArrayList<SectionConsolidatedBean>();
		for (Section section : list) {
			
			SectionConsolidatedBean bean =new SectionConsolidatedBean();
			bean.setId(section.getId());
			bean.setSection(section.getSection());
		beans.add(bean);
		}
		
		return beans;
	}
	
	
	public AssesseesAssessor  getAssesseesAssessor(int id)
	{
		AssesseesAssessor assessor=new AssesseesAssessor();
		
		assessor= assessorAssessmentDao.getAssesseesAssessor(id);
		
			return assessor;
	}
	
	
	
	public List<AssesseeObjectives>  getAssesseeObjectives(int sectionID,int assesseID)
	{
		//log.info("in get getAssesseeObjectives");
		
		AssesseesAssessor assessor=new AssesseesAssessor();
		assessor = assessorAssessmentDao.getAssesseesAssessor(assesseID);
		
		//log.info("assesssor id is " +assessor.getId());
		
		List<AssesseeObjectives> list=new ArrayList<AssesseeObjectives>();
		
		list= assessorAssessmentDao.getAssesseeObjectives(sectionID, assessor.getId());
		
		//log.info("list size is "+list.size());
		
		return list;
	
	}
	
	@Override
	public List<AssesseeObjectives> getAssesseeObjectives(int sectionID,
			int assesseID, int role_id, int projectId) {
	
		AssesseesAssessor assessor=new AssesseesAssessor();
		assessor = assessorAssessmentDao.getAssessees(assesseID,role_id,projectId);
		
List<AssesseeObjectives> list=new ArrayList<AssesseeObjectives>();
		
		list= assessorAssessmentDao.getAssesseeObjectives(sectionID, assessor.getId());
		
		log.info("list size is  "+list.size());
		
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
		bean.setWeightage(assessorAssessmentDao.getAssesseObjective(bean.getObjectiveid()).getWeightage().getId());
		bean.setSelf_score(modelObjectDao.findRatingById(bean.getSelf_rating()).getScore()* modelObjectDao.findWeightageById(bean.getWeightage()).getWeightage());
		return assessorAssessmentDao.saveSelfAssessment(bean);
	}
	
	
	@Override
	public List<AssesseesAssessor> getRoleOfCurrentUser(String email) {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getRoleOfCurrentUser(email);
	}
	
	
	
	
	public void  saveSectionSummary(int sectionID,int employeeid,int roleid,int projectid){
		
		
		
		
		int MAX_WEIGHTAGE=assessorAssessmentDao.getMaxWightage();
		int MAX_RATING=assessorAssessmentDao.getMaxRating(); 		
		
		
		AssesseesAssessor assessor=assessorAssessmentDao.getAssessees(employeeid,roleid,projectid);
		
		
		List<SectionConsolidated> consolidated= assessorAssessmentDao.findById(assessor.getId());
		
		///// *** for checking exsting records
		
		List<AssesseeObjectives> list=assessorAssessmentDao.findSectionByAssessor(assessor.getId());
		 Set<Integer> hashsetList = new HashSet<Integer>();
		
		 for (AssesseeObjectives assesseeObjectives : list) {
		hashsetList.add(assesseeObjectives.getSection().getId());
				
		}
		
		log.info("no of sections"+hashsetList.size());
		
		for (Integer sections : hashsetList) {
			
			log.info("section id is"+sections.intValue());
			List<AssesseeObjectives> objectives=assessorAssessmentDao.getAssesseeObjectives(sections.intValue(), assessor.getId());
			int max_score_objective=0;
			int score_manager_objective=0;
			int score_self_objective=0; 

			for (AssesseeObjectives assesseeObjectives : objectives) {
				
				max_score_objective +=assesseeObjectives.getWeightage().getWeightage()*MAX_RATING;
				log.info("max score "+max_score_objective);
				//log.info("manager score "+assesseeObjectives.getManager_score() +" "+"weightage "+assesseeObjectives.getWeightage().getWeightage());
				//score_manager_objective +=assesseeObjectives.getManager_score() * assesseeObjectives.getWeightage().getWeightage();
				score_manager_objective +=assesseeObjectives.getManager_score();
				log.info("manager rating "+score_manager_objective);
				
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		if(consolidated.size()>0)
		{
			
			assessorAssessmentDao.updateSectionConsolidatedData(score_self_objective, score_manager_objective, max_score_objective, sections.intValue(), assessor.getId());
			
			
		}else
		{
			assessorAssessmentDao.saveSectionConsolidatedData(score_self_objective, score_manager_objective, max_score_objective, sections.intValue(), assessor.getId());
			
		}
		
		
			
		}
		
		






	}
	
	public List<SectionConsolidatedBean> findById(String selectedAsseesseID,String role_id,int projectid)
	{
		 List<SectionConsolidated> list=new ArrayList<SectionConsolidated>();
		List<SectionConsolidatedBean> beans=new ArrayList<SectionConsolidatedBean>();
		 
		AssesseesAssessor assessor=new AssesseesAssessor();
		
		assessor=assessorAssessmentDao.getAssessees(Integer.parseInt(selectedAsseesseID),Integer.parseInt(role_id),projectid);
		list=assessorAssessmentDao.findById(assessor.getId());
		
		log.info( "section consolidated  size is "+list.size());
		
		int MAX_RATING=assessorAssessmentDao.getMaxRating(); 
		
		
		
		for (SectionConsolidated sectionConsolidated : list) {
			
			float points=(sectionConsolidated.getSection_manager_score()/sectionConsolidated.getSection_max_score())*MAX_RATING;
			
			
			SectionConsolidatedBean bean=new SectionConsolidatedBean();
			bean.setId(sectionConsolidated.getSection().getId());
			bean.setSection(sectionConsolidated.getSection().getSection());
			bean.setSection_point(points);
			
			beans.add(bean);
		}
		
		
		
		
		
		
		
		
		
		
		return beans;
		
			
		
	}
	@Override
	public Map<Integer, List<AssesseeObjectives>> checkAllObjectiveStatus(AssessorAssessmentBean bean) {
		// TODO Auto-generated method stub
		List<Section> list= assessorAssessmentDao.getAllSections();
		Map<Integer, List<AssesseeObjectives>> errorMessage=new HashMap<Integer, List<AssesseeObjectives>>();
		int count=0 , count1=0;
		for(Section section:list)
		{	count++;
			AssesseesAssessor assessor=new AssesseesAssessor();
			assessor = assessorAssessmentDao.getAssessees(bean.getEmployee_id(),bean.getRoleid(),bean.getProjectId());
		List<AssesseeObjectives> assesseeObjectivesList= assessorAssessmentDao.getAssesseeObjectives(section.getId(), assessor.getId());
		List<AssesseeObjectives> errorAssesseeObjectives=new ArrayList<AssesseeObjectives>();
		for(AssesseeObjectives assesseeObjectives: assesseeObjectivesList)
		{
			boolean added=false;
			count1++;
			if(!added && assesseeObjectives.getWeightage().getId()!=1)
			{
				if(!added && assesseeObjectives.getSelf_rating().getId()==1)
				{
					System.out.println(assesseeObjectives.getSection().getSection()+" Self Rating Zero "+assesseeObjectives.getDescription());
					errorAssesseeObjectives.add(assesseeObjectives);
					added=true;
					
				}
				
				if(!added && assesseeObjectives.getManager_rating().getId()==1)
				{
					System.out.println(assesseeObjectives.getSection().getSection()+" Manager Reating Zero  "+assesseeObjectives.getDescription());
					errorAssesseeObjectives.add(assesseeObjectives);
					added=true;
					
				}
			}else{
				System.out.println(assesseeObjectives.getSection().getSection()+" Weightage Zero "+assesseeObjectives.getDescription());
				errorAssesseeObjectives.add(assesseeObjectives);
				added=true;
			}
			
		}
		errorMessage.put(section.getId(), errorAssesseeObjectives);
		System.out.println(count+"  "+count1);
		
		}
		System.out.println("Size of Map "+errorMessage.size());
		if(errorMessage.size()==0)
		{
			errorMessage=null;
		}
		
		return errorMessage;
	}
	
	
	

}
