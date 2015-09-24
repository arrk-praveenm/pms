package com.arrkgroup.apps.assessor.assessorassessment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;

@Controller
public class AssessorAssessmentController {
	private final String ASSESSORASSESSMENT="assessor/AssessorAssessment";
	private final String LOGIN="login/login";
	

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
	public @ResponseBody List<AssesseeObjectives> loadAssignedObjectivesAssessee(
			@RequestParam("selectedAsseesseID") String selectedAsseesseID,
			@RequestParam("sectionToLoad") String sectionToLoad,
			@RequestParam("role_id") String role_id,Model model)
	{
		
		
		log.info("data  is "+selectedAsseesseID  +"  "+sectionToLoad+"  "+role_id);
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
		 allassesseeObjectives =	assessorAssessmentService.getAssesseeObjectives(sectionToLoadInt, selectedAsseesseeID,Integer.parseInt(role_id));
}catch(Exception e)
{
	System.out.println(e.getMessage());
	}
		return allassesseeObjectives;

		
	
		
		
	}
	
private void setDefaultLoad(Model model,  int sectionIDtoLoad, InetOrgPerson userDetails,boolean isAssessorOREmployee)
	{
		
		List<EmployeeBean> assessorAssessees=null;
		List<Role> allRolesofCurrentUser=null;
		
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
			 role=modelObjectService.findRoleById(assessees.getRoleId().getId());
			 employee=assessorAssessmentService.getAssesseeBean(assessees.getAssesseeId().getId());
			
			 String Name=employee.getFullname();
				employee.setFullname(Name+"    "+ "  "+role.getTitle());
			   employee.setRole_id(role.getId());
				assessorAssessees.add(employee);
				
		
		}
		}else{
			
			 allRolesofCurrentUser=assessorAssessmentService.getRoleOfCurrentUser(userDetails.getMail());
			 
			 if(allRolesofCurrentUser.size()==1)
			 {
			 allObjectives =assessorAssessmentService.getAssesseeObjectives(sectionIDtoLoad, modelObjectService.findEmployeeByEmail(userDetails.getMail()).getId(), allRolesofCurrentUser.get(0).getId());
			 }
					 //modelObjectService.getObjectiveBySectionId(sectionIDtoLoad);
			System.out.println("User Roles Size"+allRolesofCurrentUser.size());
		}
	
		

		
		model.addAttribute("selectedAsseses", 0);

		model.addAttribute("sectionToLoad", sectionIDtoLoad);
		log.info("section to load is "+sectionIDtoLoad);
		
		model.addAttribute("allSections", allSections);
		model.addAttribute("allObjectives", allObjectives);
		model.addAttribute("allRolesofCurrentUser", allRolesofCurrentUser);
		model.addAttribute("assessorAssessees", assessorAssessees);
		
		model.addAttribute("ratingList", modelObjectService.getAllRatings());
		model.addAttribute("selfratingList", modelObjectService.getAllRatings());
		model.addAttribute("weightageList", modelObjectService.getAllWeightages());
	
		
	}
	
	
	

	@RequestMapping(value = "/assessor/SaveSectionData", method = RequestMethod.POST)
	private String SaveSectionData( @ModelAttribute("AssessorAssessmentBean") AssessorAssessmentBean bean,BindingResult error,Model model,Principal principal)
	{
	

		
		log.info("manager rating is  "+bean.getManager_rating());
		log.info("manager comments is  "+bean.getManager_comments());
		log.info("Employee score is  "+bean.getSelf_score());
		log.info("Employee rating is  "+bean.getSelf_rating());
		log.info("weightage is  "+bean.getWeightage());
	
		model.addAttribute("roleid", bean.getRoleid());
		log.info("role id "+bean.getRoleid());
		boolean flag=false;
		if(bean.getSelf_rating()!=0)
		{
			//saving assessee data
			flag=assessorAssessmentService.saveSelfAssessment(bean);
			isAssessorOREmployee=false;
			
			
		}else if(bean.getManager_rating()!=0){
			flag= assessorAssessmentService.saveAssessorAssessment(bean);
			isAssessorOREmployee=true;
		}
		
		//= assessorAssessmentService.saveAssessorAssessment(bean);
		
		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		setDefaultLoad(model, bean.getSectionid(), userDetails, isAssessorOREmployee);
		
		
		
		
		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}
	

	
	
}
