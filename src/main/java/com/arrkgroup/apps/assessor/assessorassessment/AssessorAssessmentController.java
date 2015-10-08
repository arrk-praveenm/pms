package com.arrkgroup.apps.assessor.assessorassessment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.form.AssesseRoleBean;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Project;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;

@Controller
public class AssessorAssessmentController {

	private final String ASSESSORASSESSMENT = "assessor/AssessorAssessment";
	private final String LOGIN = "login/login";
	private final String SELFRATING_COMPLETED="selfRatingCompleted";
	private final String ASSESSEMENT_COMPLETED="AssessementCompleted";
	private static final String STATUS = "assignedObjectives";
		private static final String NOTAGREE = "notAgree";
		private static final String CLOSED = "closed";
	private final Logger log = LoggerFactory
			.getLogger(AssessorAssessmentController.class);

	@Autowired
	AssessorAssessmentService assessorAssessmentService;

	@Autowired
	ModelObjectService modelObjectService;


	InetOrgPerson userDetails = null;
	boolean isAssessorOREmployee=false;

//For Manager or Assessor tab initial load
	@RequestMapping(value = "/assessor/assessorAssessment", method = RequestMethod.GET)
	public String loadCreateSectionPage(Principal principal,
			Model model ) {


		userDetails=(InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		model.addAttribute("AssessorAssessmentBean", new AssessorAssessmentBean());


		int sectionToLoad=0;

		isAssessorOREmployee=true;

		setDefaultLoad(model, sectionToLoad, userDetails, isAssessorOREmployee);

		return principal != null ? ASSESSORASSESSMENT : LOGIN;
		}


	//For Employee tab initial load
	@RequestMapping(value = "/assessor/assesseeRating", method = RequestMethod.GET)
	public String loadAssesseeSelfRating(Principal principal,
			Model model ) {

		 userDetails = (InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());



		isAssessorOREmployee=false;


		model.addAttribute("AssessorAssessmentBean", new AssessorAssessmentBean());




		int sectionToLoad=0;

		setDefaultLoad(model, sectionToLoad, userDetails, isAssessorOREmployee);


		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}

	@RequestMapping(value = "/assessor/ajax/assesseeObjectives", method = RequestMethod.GET)
	public @ResponseBody
	List<AssesseeObjectives> loadAssignedObjectivesAssessee(
			@RequestParam("selectedAsseesseID") String selectedAsseesseID,
			@RequestParam("sectionToLoad") String sectionToLoad,
	@RequestParam("projectId") String projectId,
			@RequestParam("role_id") String role_id,Model model)
	{

		log.info("data  is "+selectedAsseesseID  +"  "+sectionToLoad+"  "+role_id + " projectid "+ projectId);
		int selectedAsseesseeID=0;
		int sectionToLoadInt=0;

		userDetails=(InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		if(selectedAsseesseID.equalsIgnoreCase("0"))
		{

			selectedAsseesseeID= modelObjectService.findEmployeeByEmail(userDetails.getMail()).getId();
			if(sectionToLoad.equalsIgnoreCase("0")){
			sectionToLoadInt=assessorAssessmentService.getAllSections().get(0).getId();
			}else{
				 sectionToLoadInt=Integer.parseInt(sectionToLoad);
			}


		}else{
			 selectedAsseesseeID=Integer.parseInt(selectedAsseesseID);
			 sectionToLoadInt=Integer.parseInt(sectionToLoad);
		}




		if(sectionToLoadInt==0)
		{
			sectionToLoadInt = ((Section) assessorAssessmentService.getAllSections().get(0)).getId();
		}




		log.info(" request for objectives to load for section id is "
				+ sectionToLoad);

		List<AssesseeObjectives> allassesseeObjectives=null;
try{
		 allassesseeObjectives =	assessorAssessmentService.getAssesseeObjectives(sectionToLoadInt, selectedAsseesseeID,Integer.parseInt(role_id), Integer.parseInt(projectId));



}catch(Exception e)
{
	System.out.println(e.getMessage());
	}

log.info(allassesseeObjectives.size()+" "+allassesseeObjectives);
		return allassesseeObjectives;





	}

private void setDefaultLoad(Model model,  int sectionIDtoLoad, InetOrgPerson userDetails,boolean isAssessorOREmployee)
	{

		List<EmployeeBean> assessorAssessees=null;
		List<AssesseRoleBean> allRolesofCurrentUser=new ArrayList<AssesseRoleBean>();

		List<AssesseeObjectives> allObjectives =	null;

		List<Section> allSections = assessorAssessmentService.getAllSections();
		if(sectionIDtoLoad==0)
		{
		sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}

		if(isAssessorOREmployee)
		{
		List<AssesseesAssessor> employeeAssessees=assessorAssessmentService.getMyAssessees(userDetails.getMail().toString());
		assessorAssessees=new ArrayList<EmployeeBean>();
		for (AssesseesAssessor assessees : employeeAssessees) {

			EmployeeBean employee=new EmployeeBean();
			Role role=new Role();
			Project proj=new Project();
			 role=modelObjectService.findRoleById(assessees.getRoleId().getId());
			 employee=assessorAssessmentService.getAssesseeBean(assessees.getAssesseeId().getId());
			proj= modelObjectService.findProjectById(assessees.getProjectId().getId());

			 String Name=employee.getFullname();
				employee.setFullname(proj.getProject_name()+" - "+role.getTitle()+" - "+Name+"  -  "+assessees.getStatus());
			   employee.setRole_id(role.getId());
			   employee.setProjectId(proj.getId());
				assessorAssessees.add(employee);


		}
		}else{

			// allRolesofCurrentUser
			List<AssesseesAssessor> employeeAssessees=assessorAssessmentService.getRoleOfCurrentUser(userDetails.getMail());
			 //List rolesofCure
			 for (AssesseesAssessor assessees : employeeAssessees) {

				 AssesseRoleBean assesseRoleBean=new AssesseRoleBean();
					Role role=new Role();
					Project proj=new Project();
					 role=modelObjectService.findRoleById(assessees.getRoleId().getId());

					proj= modelObjectService.findProjectById(assessees.getProjectId().getId());


					assesseRoleBean.setAssesseeProjectRole(proj.getProject_name()+" - "+role.getTitle()+" - "+assessees.getStatus());
					assesseRoleBean.setRoleId(role.getId());
					assesseRoleBean.setProjectId(proj.getId());
					assesseRoleBean.setAssessmentStatus(assessees.getStatus());
					allRolesofCurrentUser.add(assesseRoleBean);


				}


			 if(allRolesofCurrentUser.size()==1)
			 {
			 allObjectives =assessorAssessmentService.getAssesseeObjectives(sectionIDtoLoad, modelObjectService.findEmployeeByEmail(userDetails.getMail()).getId(), allRolesofCurrentUser.get(0).getRoleId(),allRolesofCurrentUser.get(0).getProjectId());
			 }

			System.out.println("User Roles Size"+allRolesofCurrentUser.size());
		}




		model.addAttribute("selectedAsseses", 0);


		model.addAttribute("sectionToLoad", sectionIDtoLoad);
		log.info("section to load is " + sectionIDtoLoad);

		model.addAttribute("allSections", allSections);

		model.addAttribute("allSectionsBeans",
				assessorAssessmentService.getAllSectionsBean());



		model.addAttribute("allObjectives", allObjectives);
		model.addAttribute("allRolesofCurrentUser", allRolesofCurrentUser);
		model.addAttribute("assessorAssessees", assessorAssessees);

		model.addAttribute("ratingList", modelObjectService.getAllRatings());
		model.addAttribute("selfratingList", modelObjectService.getAllRatings());
		model.addAttribute("weightageList", modelObjectService.getAllWeightages());



	}




@RequestMapping(value = "/assessor/ajax/GetSummaryRating", method = RequestMethod.GET)
public @ResponseBody
List<SectionConsolidatedBean> getsummarydata(
		@RequestParam("selectedAsseesseID") String selectedAsseesseID,
		@RequestParam("role_id") String role_id, Model model,
		@RequestParam("projectId") String projectId){

	log.info("in GetSummaryRating method" + selectedAsseesseID + ""
			+ role_id);

	int selectedAsseesseeID=0;

	userDetails=(InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	if(selectedAsseesseID.equalsIgnoreCase("0"))
	{

		selectedAsseesseeID= modelObjectService.findEmployeeByEmail(userDetails.getMail()).getId();



	}else{
		selectedAsseesseeID=Integer.parseInt(selectedAsseesseID);

	}



	List<SectionConsolidatedBean> list = assessorAssessmentService
			.findById(String.valueOf( selectedAsseesseeID), role_id,Integer.parseInt(projectId));

	log.info("in GetSummaryRating method  list size is " + list.size());

	return list;
}








	@RequestMapping(value = "/assessor/SaveSectionData", method = RequestMethod.POST, params="save")
	private String SaveSectionData( @ModelAttribute("AssessorAssessmentBean") AssessorAssessmentBean bean,
					BindingResult error,Model model,Principal principal)
	{



		System.out.println("SAVE");
		log.info("manager rating is  "+bean.getManager_rating());
		log.info("manager comments is  "+bean.getManager_comments());
		log.info("Employee score is  "+bean.getSelf_score());
		log.info("Employee rating is  "+bean.getSelf_rating());
		log.info("weightage is  "+bean.getWeightage());




		model.addAttribute("projectId", bean.getProjectId());
		model.addAttribute("roleid", bean.getRoleid());
		model.addAttribute("assesseeid", bean.getEmployee_id());

		log.info("projectid "+bean.getProjectId()+" role id "+bean.getRoleid()+" assesseeid"+bean.getEmployee_id());

		boolean flag=false;


		if(bean.getUserType().equalsIgnoreCase("assesse"))
		{

			//saving assessee data
			flag=assessorAssessmentService.saveSelfAssessment(bean);
			isAssessorOREmployee=false;




		}else if(bean.getUserType().equalsIgnoreCase("assessor")){

			flag= assessorAssessmentService.saveAssessorAssessment(bean);
			isAssessorOREmployee=true;


			System.out.println("manager saving");

			System.out.println(" section"+bean.getSectionid()+"employee"+bean.getEmployee_id()+"  role"+ bean.getRoleid()+"  project"+bean.getProjectId());



			assessorAssessmentService.saveSectionSummary(bean.getSectionid(),
					bean.getEmployee_id(), bean.getRoleid(),bean.getProjectId());
		}



		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		setDefaultLoad(model, bean.getSectionid(), userDetails, isAssessorOREmployee);

      System.out.println();



		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}


	@RequestMapping(value = "/assessor/SaveSectionData", method = RequestMethod.POST, params="assesseeSubmit")
	private String assesseeSubmit( @ModelAttribute("AssessorAssessmentBean") AssessorAssessmentBean bean,
					BindingResult error,Model model,Principal principal)
	{
		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		System.out.println("NEW");
		log.info("projectid "+bean.getProjectId()+" role id "+bean.getRoleid()+" assesseeid"+bean.getEmployee_id());

		if(bean.getEmployee_id()==0)
		{

			//ASSESSEMENT_COMPLETED

			bean.setEmployee_id(modelObjectService.findEmployeeByEmail(userDetails.getMail()).getId());
			bean.setAssesseeAssessorStatus(STATUS);
			assessorAssessmentService.updateAssesseesAssessorStatus(bean, SELFRATING_COMPLETED);
			isAssessorOREmployee=false;
		}else if(bean.getEmployee_id()!=0)
		{
			log.info("Employee ID ");

			Map<Integer, List<AssesseeObjectives>> errorMessage=	assessorAssessmentService.checkAllObjectiveStatus(bean);

			System.out.println("controller errorMessage is "+errorMessage);


			if(errorMessage==null)
			{
				System.out.println("controller errorMessage is "+errorMessage);
				//check objective status and return success or failure message
				bean.setAssesseeAssessorStatus(SELFRATING_COMPLETED);
			assessorAssessmentService.updateAssesseesAssessorStatus(bean, ASSESSEMENT_COMPLETED);
			}else{
			model.addAttribute("errorMessage",errorMessage);
			}
			isAssessorOREmployee=true;
		}

		model.addAttribute("projectId", bean.getProjectId());
		model.addAttribute("roleid", bean.getRoleid());
		model.addAttribute("assesseeid", bean.getEmployee_id());

		log.info("Section " + bean.getSectionid());
		setDefaultLoad(model, bean.getSectionid(), userDetails,
				isAssessorOREmployee);
		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}

	//when close or dispute button clicked then this action will be called
	@RequestMapping(value = "/assessor/SaveSectionData", method = RequestMethod.POST, params = "close")
	private String closeAssesseeAssessment(
			@ModelAttribute("AssessorAssessmentBean") AssessorAssessmentBean bean,
			BindingResult error, Model model, Principal principal,
			@RequestParam(value = "close", required = true) String close) {

		System.out.println("getAssesseeAssessorStatus  value is " + bean.getAssesseeAssessorStatus());
		// NOTAGREE
		if (bean.getEmployee_id() == 0) {
			userDetails = (InetOrgPerson) (SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal());
			bean.setEmployee_id(modelObjectService.findEmployeeByEmail(
					userDetails.getMail()).getId());

			log.info("current status of the assesee while assesse making disagree  "
					+ bean.getAssesseeAssessorStatus());

			bean.setAssesseeAssessorStatus(ASSESSEMENT_COMPLETED);
			assessorAssessmentService.updateAssesseesAssessorStatus(bean,
					NOTAGREE);
			// ASSESSEMENT_COMPLETED

			isAssessorOREmployee = false;
		} else if (bean.getEmployee_id() != 0) {
			log.info("Objective ID is "+bean.getObjectiveid());

			log.info("current status of the assesee while assessor submitting "
					+ bean.getAssesseeAssessorStatus());
			assessorAssessmentService.updateAssesseesAssessorStatus(bean,
					CLOSED);

			isAssessorOREmployee = true;
		}
		model.addAttribute("projectId", bean.getProjectId());
		model.addAttribute("roleid", bean.getRoleid());
		model.addAttribute("assesseeid", bean.getEmployee_id());
		System.out.println("Section " + bean.getSectionid());
		setDefaultLoad(model, bean.getSectionid(), userDetails,
				isAssessorOREmployee);

		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}

}
