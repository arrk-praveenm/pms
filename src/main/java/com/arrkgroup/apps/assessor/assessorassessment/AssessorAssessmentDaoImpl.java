package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.dao.ModelObjectDao;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Rating;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.SectionConsolidated;
import com.arrkgroup.apps.model.Weightage;





@Repository("AssessorAssessmentDao")
public class AssessorAssessmentDaoImpl implements AssessorAssessmentDao {

	private final Logger log = LoggerFactory
			.getLogger(AssessorAssessmentController.class);


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


	public AssesseesAssessor  getAssessees(int assesse_id,int role_id, int projectId)
	{

		System.out.println();


		return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_EMPLOYEE_ID,
						AssesseesAssessor.class).setParameter("id", assesse_id)
						.setParameter("role_id", role_id)
						.setParameter("projectId", projectId)
						.getSingleResult();


	}







	@Override
	public List<AssesseeObjectives> getAssesseeObjectives(int sectionID,
			int assesseID) {
	log.info(sectionID+" "+assesseID );


	try
	{



	return entityManager
				.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_OBJECTIVES_BY_ASSESSE_AND_SECTION,
						AssesseeObjectives.class).setParameter("sectionID", sectionID).setParameter("assesseID", assesseID).getResultList();
	}
	catch(Exception e)
	{

		log.info("exception is "+e.getMessage());
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


	public boolean saveAssessorAssessment(AssessorAssessmentBean bean)
	{
	 int n=	entityManager
		.createNamedQuery(AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID_V2
				).setParameter("managerComments",bean.getManager_comments())
				.setParameter("managerRating", bean.getManager_rating())
				.setParameter("assessebjectiveId", bean.getObjectiveid())
				.setParameter("weight", bean.getWeightage())
				/*.setParameter("assesseeComments",bean.getEmployee_comments())
				.setParameter("assesseeRatingId", bean.getSelf_rating())
				.setParameter("self_score", bean.getSelf_score())
				.setParameter("weight", bean.getWeightage())*/
				.setParameter("manager_score", bean.getManager_score()).executeUpdate();







	if(n>0)
	{
		return true;
	}else
	{
		return false;
	}


	}
	@Override
	public int getMaxWightage() {
	return (int) entityManager
			.createNamedQuery(Weightage.GET_Weightage_MAX).getSingleResult();
	}
	@Override
	public int getMaxRating() {
		return (int) entityManager
				.createNamedQuery(Rating.GET_RATING_MAX).getSingleResult();
	}

	@Override
	public boolean saveSectionConsolidatedData(int self_score,int manger_score,int max_score,int section_id,int assessor_id)
	{


	Date currentdate=new Date();
float managerscore=manger_score;



	Section	section=entityManager.createNamedQuery(Section.FIND_BY_ID,
			Section.class).setParameter("id", section_id)
			.getSingleResult();



	AssesseesAssessor assessor=entityManager.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_ID,
			AssesseesAssessor.class).setParameter("id", assessor_id).getSingleResult();




		SectionConsolidated consolidated=new SectionConsolidated(currentdate, self_score, managerscore, max_score,section,assessor);

		log.info("section is  "+ section.getSection());
		log.info("assessor is  "+ assessor.getAssesseeId().getFullname());


		try {


			entityManager.merge(consolidated);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;


	}


	public List<AssesseeObjectives> findSectionByAssessor(int assessorId)
	{

		return entityManager
				.createNamedQuery(AssesseeObjectives.FIND_SECTION_BY_ASSESSOR,
						AssesseeObjectives.class).setParameter("assesseeAssessorId", assessorId).getResultList();



	}
	@Override
	public List<SectionConsolidated> findById(int assessorId) {
		// TODO Auto-generated method stub
		return entityManager
				.createNamedQuery(SectionConsolidated.FIND_BY_ID,
						SectionConsolidated.class).setParameter("id", assessorId).getResultList();

	}


	public int updateSectionConsolidatedData(int self_score,int manger_score,int max_score,int section_id,int assessor_id)
	{
		float managerscore=manger_score;



		return entityManager
				.createNamedQuery(SectionConsolidated.UPDATE_ASSESSE_SECTON_ASSESSOR).setParameter("date", new Date())
						.setParameter("max_score", max_score)
						.setParameter("manager_score", managerscore)
						.setParameter("self_score", self_score)
						.setParameter("sectionid", section_id)
						.setParameter("assessorid", assessor_id).executeUpdate();

	}

	@Override
	public boolean saveSelfAssessment(AssessorAssessmentBean bean) {


		// TODO Auto-generated method stub
		 int n=	entityManager
					.createNamedQuery(AssesseeObjectives.UPDATE_ASSESSE_OBJECTIVE_BY_OBJECTIVEID_V1
							).setParameter("assessee_comments",bean.getEmployee_comments())
							.setParameter("assesseeRatingId", bean.getSelf_rating())
							.setParameter("self_score", bean.getSelf_score())
							.setParameter("weight", bean.getWeightage())
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
	public List<AssesseesAssessor> getRoleOfCurrentUser(String email) {
		// TODO Auto-generated method stub
	return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ROLES_OF_ASSESSEE,
						AssesseesAssessor.class).setParameter("email", email)
				.getResultList();
	}
	@Override
	public AssesseeObjectives getAssesseObjective(int assesseeObjectiveId) {
		// TODO Auto-generated method stub
		return entityManager
				.createNamedQuery(AssesseeObjectives.GET_ASSESSEE_ASSESSOR_ID,AssesseeObjectives.class
						)
						.setParameter("assessebjectiveId", assesseeObjectiveId).getSingleResult();

	}




	public boolean  updateAssesseesAssessorStatus(AssessorAssessmentBean bean ,String status)

	{


 int count=	entityManager
	.createNamedQuery(AssesseesAssessor.UPDATE_STATUS
			).setParameter("assesseeId", bean.getEmployee_id())
						.setParameter("status", status)
						.setParameter("roleId", bean.getRoleid())
						.setParameter("project_id", bean.getProjectId())
						.setParameter("statusCondition", bean.getAssesseeAssessorStatus())
			.executeUpdate();


if(count>0)
{
	return true;
}
else
{

return false;
}



	}






}
