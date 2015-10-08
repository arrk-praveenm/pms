package com.arrkgroup.apps.assessor.assignobjectives;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.form.CopyObjectivesBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;

@Repository("AssignObjectivesRepository")
public class AssignObjectivesDaoImpl implements AssignObjectivesDao {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private ModelObjectDao modelObjectDao;
	
	private static final int DEFAULT_WEIGHTAGE_ID = 1;
	private static final int DEFAULT_RATING_ID = 1;
	private static final String ASSIGNEDOBJECTIVES = "assignedObjectives";
	private static final String SELFRATINGCOMPLETED = "selfRatingCompleted";

	@Override
	public List<Cycle> getAllAssessmentCycles() {
		return entityManager.createNamedQuery(Cycle.FIND_ALL, Cycle.class)
				.getResultList();

	}

	@Override
	public List<Employee> getAllAssesses() {
		return entityManager.createNamedQuery(Employee.FIND_ALL, Employee.class)
				.getResultList();
	}

	@Override
	public List<Role> getAllRoles() {
		return entityManager.createNamedQuery(Role.FIND_ALL, Role.class)
				.getResultList();
	}

	@Override
	@Transactional
	public boolean copyRoleObjectives(AssesseesAssessor assesseesAssessor) {
		List<String> statusList = Arrays.asList(ASSIGNEDOBJECTIVES, SELFRATINGCOMPLETED);
		try{
			
			AssesseesAssessor assesseesAssessorUpdate=entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
					.setParameter("cycleId", assesseesAssessor.getCycleId().getId())
					.setParameter("project_name", assesseesAssessor.getProjectId().getId())
					.setParameter("assesseeId", assesseesAssessor.getAssesseeId().getId())
					//.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
					.setParameter("statusList", statusList)
					.setParameter("roleId", assesseesAssessor.getRoleId().getId()).getSingleResult();
			entityManager
			.createNamedQuery(AssesseesAssessor.UPDATE_ASSESSE_ASSESSOR_BY_ID
					).setParameter("startdate", assesseesAssessor.getStart_date())
					.setParameter("enddate", assesseesAssessor.getEnd_date())
					.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
					.setParameter("assesseesAssessorId", assesseesAssessorUpdate.getId()).executeUpdate();
			copyingRoleObjectives( assesseesAssessorUpdate);
			
	}catch(NoResultException re){
		//
				try {
					
					AssesseesAssessor assesseesAssessor1=entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
							.setParameter("cycleId", assesseesAssessor.getCycleId().getId())
							.setParameter("start_date", assesseesAssessor.getStart_date())
							.setParameter("end_date", assesseesAssessor.getEnd_date())
							.setParameter("project_name", assesseesAssessor.getProjectId().getId())
							.setParameter("assesseeId", assesseesAssessor.getAssesseeId().getId())
							.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
							.setParameter("statusList", statusList)
							.setParameter("roleId", assesseesAssessor.getRoleId().getId()).getSingleResult();
					
					copyingRoleObjectives( assesseesAssessor1);
					
				}catch (NoResultException nre){
				
					
					entityManager.persist(assesseesAssessor);
					copyingRoleObjectives( assesseesAssessor);
					
				}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;		
	}
	private void copyingRoleObjectives(AssesseesAssessor assesseesAssessor)
	{
		List<RoleModel> roleObjectives = entityManager.createNamedQuery(RoleModel.FIND_BY_ROLE_ID, RoleModel.class)
				.setParameter("role_id", assesseesAssessor.getRoleId().getId())
				.getResultList();
		
		
		for(RoleModel roleModel : roleObjectives){
			AssesseeObjectives assesseeObjective = new AssesseeObjectives();
			assesseeObjective.setAssesseeAssessor(assesseesAssessor);
			assesseeObjective.setDescription(roleModel.getObjectives().getObjectiveDesc());
			assesseeObjective.setSection(roleModel.getSection());
			assesseeObjective.setWeightage(modelObjectDao.findWeightageById(DEFAULT_WEIGHTAGE_ID));
			assesseeObjective.setSelf_rating(modelObjectDao.findRatingById(DEFAULT_RATING_ID));
			assesseeObjective.setManager_rating(modelObjectDao.findRatingById(DEFAULT_RATING_ID));
			assesseeObjective.setLastModifiedDate(new Date());
			entityManager.persist(assesseeObjective);
		}
	}

	@Override
	public boolean copyAssesseObjectives(AssesseesAssessor assesseesAssessor , AssesseesAssessor OtherAssesseesAssessor) 
	{
		List<String> statusList = Arrays.asList(ASSIGNEDOBJECTIVES, SELFRATINGCOMPLETED);
		try{
			//if No records goes to catch block
			
			List<AssesseesAssessor> OtherassesseesAssessor1=entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSEEID_ROLEID, AssesseesAssessor.class)
					.setParameter("cycleId", OtherAssesseesAssessor.getCycleId().getId())
					//.setParameter("start_date", OtherAssesseesAssessor.getStart_date())
					//.setParameter("end_date", OtherAssesseesAssessor.getEnd_date())
					.setParameter("project_name", OtherAssesseesAssessor.getProjectId().getId())
					.setParameter("assesseeId", OtherAssesseesAssessor.getAssesseeId().getId())
					//.setParameter("assessorId", OtherAssesseesAssessor.getAssessorId().getId())
					.setParameter("statusList", statusList)
					.setParameter("roleId", OtherAssesseesAssessor.getRoleId().getId()).getResultList();
try{
	System.out.println("Test 1");
	AssesseesAssessor assesseesAssessorUpdate=entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
			.setParameter("cycleId", assesseesAssessor.getCycleId().getId())
			.setParameter("project_name", assesseesAssessor.getProjectId().getId())
			.setParameter("assesseeId", assesseesAssessor.getAssesseeId().getId())
			//.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
			.setParameter("statusList", statusList)
			.setParameter("roleId", assesseesAssessor.getRoleId().getId()).getSingleResult();
	System.out.println("Test 11");
	entityManager
	.createNamedQuery(AssesseesAssessor.UPDATE_ASSESSE_ASSESSOR_BY_ID
			).setParameter("startdate", assesseesAssessor.getStart_date())
			.setParameter("enddate", assesseesAssessor.getEnd_date())
			.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
			.setParameter("assesseesAssessorId", assesseesAssessorUpdate.getId())
			.executeUpdate();
	copyingAssesseeObjectives(assesseesAssessorUpdate, OtherassesseesAssessor1);
		}catch(NoResultException re){
			//
		try {
			System.out.println("Test 2");
			AssesseesAssessor assesseesAssessor1=entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
					.setParameter("cycleId", assesseesAssessor.getCycleId().getId())
					.setParameter("start_date", assesseesAssessor.getStart_date())
					.setParameter("end_date", assesseesAssessor.getEnd_date())
					.setParameter("project_name", assesseesAssessor.getProjectId().getId())
					.setParameter("assesseeId", assesseesAssessor.getAssesseeId().getId())
					.setParameter("assessorId", assesseesAssessor.getAssessorId().getId())
					.setParameter("statusList", statusList)
					.setParameter("roleId", assesseesAssessor.getRoleId().getId()).getSingleResult();
			System.out.println("Test 22");
			copyingAssesseeObjectives(assesseesAssessor1,OtherassesseesAssessor1);
		}catch(NoResultException nre){
			
			entityManager.persist(assesseesAssessor);			
			copyingAssesseeObjectives(assesseesAssessor,OtherassesseesAssessor1);
			}
		}catch(Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
			return false;
		}
		}catch(NoResultException nre){
			nre.printStackTrace();
			return false;
		}
		return true;
	}

	private void copyingAssesseeObjectives(AssesseesAssessor assesseesAssessor,List<AssesseesAssessor> OtherAssesseesAssessor)
	{
		System.out.println("Test get Objectives");
		for(AssesseesAssessor otherAssesseesAssessor : OtherAssesseesAssessor){
		
		List<AssesseeObjectives> assesseeObjectives = entityManager.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSEEID_CYCLEID_ROLEID,
				AssesseeObjectives.class)
				.setParameter("assesseeId", otherAssesseesAssessor.getAssesseeId().getId())
				.setParameter("cycleId", otherAssesseesAssessor.getCycleId().getId())
				.setParameter("project_name", otherAssesseesAssessor.getProjectId().getId())
				.setParameter("roleId", otherAssesseesAssessor.getRoleId().getId()).getResultList();
		System.out.println("Test copy Objectives");
		for(AssesseeObjectives copiedassesseeObjectives : assesseeObjectives){
			AssesseeObjectives assesseeObjective = new AssesseeObjectives();
			assesseeObjective.setAssesseeAssessor(assesseesAssessor);
			assesseeObjective.setDescription(copiedassesseeObjectives.getDescription());
			assesseeObjective.setSection(copiedassesseeObjectives.getSection());
			assesseeObjective.setWeightage(copiedassesseeObjectives.getWeightage());
			assesseeObjective.setSelf_rating(modelObjectDao.findRatingById(DEFAULT_RATING_ID));
			assesseeObjective.setManager_rating(modelObjectDao.findRatingById(DEFAULT_RATING_ID));
			assesseeObjective.setLastModifiedDate(new Date());
			entityManager.persist(assesseeObjective);
				}
		}
	}
	@Override
	public List<AssesseeObjectives> getAssesseObjectives(
			CopyObjectivesBean copyObjectivesBean, int sectionId) {
		// TODO Auto-generated method stub
		List<String> statusList = Arrays.asList(ASSIGNEDOBJECTIVES, SELFRATINGCOMPLETED);
				
		return entityManager.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_PROJECTNAME_STARTDATE_AND_SECTION, AssesseeObjectives.class)
				.setParameter("employeeId", copyObjectivesBean.getAssessee())
				.setParameter("projectName", copyObjectivesBean.getProjectName())
				//.setParameter("StartDate", convertStringToDate(copyObjectivesBean.getAssessmentFromDate().substring(0, 10)))
				.setParameter("id",sectionId).setParameter("Cycle", copyObjectivesBean.getAssessmentCycle())
			//	.setParameter("assessorId", copyObjectivesBean.getAssessor())
				.setParameter("roleId",copyObjectivesBean.getAssesseeRole())
			
				.setParameter("statusList",statusList)
				//.setParameter("statusTwo",SELFRATINGCOMPLETED)
			//	.setParameter("endDate", convertStringToDate(copyObjectivesBean.getAssessmentToDate().substring(0, 10)))
				.getResultList();
	}//
	
	
	
	private Date convertStringToDate(String datestring)
	{
	
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date=null ;
		try {
			 date = format.parse(datestring);
			System.out.println("date "+date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	@Override
	public boolean addAssesseeObjective(AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		try{
			
			entityManager.persist(assesseeObjectives);
			return true;
		}catch(Exception e){
			System.err.println(" Error "+e);
			return false;
		}
		
		
	}

	@Override
	public AssesseesAssessor getAssesseeAssessor(CopyObjectivesBean copyObjectivesBean) {
		// TODO Auto-generated method stub
	
		return entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PERIOD_PROJECT_ASSESSORID_ASSESSEEID_ROLEID, AssesseesAssessor.class)
				.setParameter("cycleId", modelObjectDao.findCycleById(copyObjectivesBean.getAssessmentCycle()).getId())
			     .setParameter("start_date", convertStringToDate(copyObjectivesBean.getAssessmentFromDate().substring(0, 10)))
			     .setParameter("end_date", convertStringToDate(copyObjectivesBean.getAssessmentToDate().substring(0, 10)))
			     .setParameter("project_name", copyObjectivesBean.getProjectName())
			     .setParameter("assesseeId", modelObjectDao.findEmployeeById(copyObjectivesBean.getAssessee()).getId())
			     .setParameter("assessorId", modelObjectDao.findEmployeeById(copyObjectivesBean.getAssessor()).getId())
			     .setParameter("roleId", modelObjectDao.findRoleById(copyObjectivesBean.getAssesseeRole()).getId()).getSingleResult();
		
	}

	@Override
	public boolean saveAssesseeObjectivebySection(
			AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		
		entityManager
		.createNamedQuery(AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID
				).setParameter("description", assesseeObjectives.getDescription())
				//.setParameter("weightageId", assesseeObjectives.getWeightage().getId())
				.setParameter("assessebjectiveId", assesseeObjectives.getId()).executeUpdate()
		;
		return false;
	}

	@Override
	public boolean deleteAssesseeObjectivebySection(int objectiveId)
			throws SQLException {
		// TODO Auto-generated method stub
		try{
		
		entityManager
		.createNamedQuery(AssesseeObjectives.DELETE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID
				).setParameter("assessebjectiveId", objectiveId).executeUpdate();
		
		return true;
		}catch(Exception exe)
		{
			
			throw new SQLException("Assessee Objective Record can't delete");
		
			
		}catch(Error e)
		{
			return false;
		}
	}

	@Override
	public AssesseeObjectives getAssesseeAssessorId(int assesseeObjectiveId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_ASSESSOR_ID, AssesseeObjectives.class)
				.setParameter("assessebjectiveId", assesseeObjectiveId)
				.getSingleResult();
	}

	@Override
	public List<AssesseeObjectives> getALLAssesseObjectivesBySectionId(AssesseeObjectives assesseeObjectives) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery(AssesseeObjectives.GET_ALL_ASSESSEE_SECTION_ID, AssesseeObjectives.class)
				.setParameter("assesseeAssessorId", assesseeObjectives.getAssesseeAssessor().getId())
				.setParameter("sectionId", assesseeObjectives.getSection().getId())
				.getResultList();
	}

	@Override
	public boolean saveWeightage(int objectiveId, int weightageId) {
		// TODO Auto-generated method stub
		
		entityManager
		.createNamedQuery(AssesseeObjectives.UPDATE_WEIGHTAGE_BY_OBJECTIVEID
				).setParameter("weightageId",weightageId)
				.setParameter("objectiveId", objectiveId).executeUpdate()
		;
		
		return true;
	}

	@Override
	public AssesseesAssessor getAssesseeAssessorWithoutPeriod(
			CopyObjectivesBean copyObjectivesBean) {
		// TODO Auto-generated method stub
		try{
			List<String> statusList = Arrays.asList(ASSIGNEDOBJECTIVES, SELFRATINGCOMPLETED);
			
			AssesseesAssessor assesseesAssessor=entityManager.createNamedQuery(AssesseesAssessor.FIND_BY_CYCLEID_PROJECT_ASSESSEEID_ROLEID, AssesseesAssessor.class)
					.setParameter("cycleId", copyObjectivesBean.getAssessmentCycle() )
					//.setParameter("start_date", OtherAssesseesAssessor.getStart_date())
					//.setParameter("end_date", OtherAssesseesAssessor.getEnd_date())
					.setParameter("project_name", copyObjectivesBean.getProjectName())
					.setParameter("assesseeId", copyObjectivesBean.getAssessee())
					.setParameter("statusList", statusList)
					.setParameter("roleId",copyObjectivesBean.getAssesseeRole()).getSingleResult();
			
			
			return assesseesAssessor;
		}catch(Exception e){
			System.out.println("Bo records "+e);
		return null;
		}
	}
	


}
