package com.arrkgroup.apps.assessor.assessorassessment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.form.EmployeeBean;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;


import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Objective;
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
			sectionToLoadInt=assessorAssessmentService.getAllSections().get(0).getId();
			
		}else{
			 selectedAsseesseeID=Integer.parseInt(selectedAsseesseID);
			 sectionToLoadInt=Integer.parseInt(sectionToLoad);
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
				
			//	allObjectives =null;//	modelObjectService.getObjectiveBySectionId(sectionIDtoLoad);
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
		
		model.addAttribute("managerRating", modelObjectService.getAllRatings());
		
	
		
	}
	
	
	

	@RequestMapping(value = "/assessor/SaveSectionData", method = RequestMethod.POST)
	private String SaveSectionData( @ModelAttribute("AssessorAssessmentBean") AssessorAssessmentBean bean,BindingResult error,Model model,Principal principal)
	{
	

		
		log.info("manager rating is  "+bean.getManager_rating());
		log.info("manager rating is  "+bean.getManager_comments());
	
		model.addAttribute("roleid", bean.getRoleid());
		log.info("role id "+bean.getRoleid());
		
		boolean flag= assessorAssessmentService.saveSectionData(bean);
		
		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		isAssessorOREmployee=true;
		setDefaultLoad(model, bean.getSectionid(),userDetails,isAssessorOREmployee);
		
		
		
		
		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}
	

	
	
}
